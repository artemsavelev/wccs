package com.smart.wccs.service.impl;

import com.smart.wccs.model.Material;
import com.smart.wccs.model.Status;
import com.smart.wccs.repo.MaterialRepo;
import com.smart.wccs.repo.UserRepo;
import com.smart.wccs.service.MaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepo materialRepo;
    private final UserRepo userRepo;

    @Autowired
    public MaterialServiceImpl(MaterialRepo materialRepo, UserRepo userRepo) {
        this.materialRepo = materialRepo;
        this.userRepo = userRepo;
    }


    @Override
    public List<Material> getAllMaterial() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        List<Material> materials = materialRepo.findAll()
                .stream()
                .filter(o -> o.getDepartments().contains(userRepo.findByUsername(name).getDepartment()))
                .collect(Collectors.toList());
        log.info("IN getAllMaterials - {} materials found", materials.size());
        return materials;
    }

    @Override
    public Material getById(Long id) {
        Material material = materialRepo.findById(id).orElse(null);
        if (material == null) {
            log.warn("IN findById - no material found by id: {}", id);
            return null;
        }
        log.info("IN findById - material found by id: {}", material);
        return material;
    }

    @Override
    public void create(Material material) {
        material.setCreatedDate(LocalDateTime.now());
        material.setStatus(Status.ACTIVE);
        Material createdMaterial = materialRepo.save(material);
        log.info("IN create - material: {} successfully added", createdMaterial);

    }

    @Override
    public List<Material> createAll(List<Material> materials) {
        materials.forEach(material -> material.setCreatedDate(LocalDateTime.now()));
        materials.forEach(material -> material.setStatus(Status.ACTIVE));
        List<Material> createdMaterials = materialRepo.saveAll(materials);
        log.info("IN createAll - materials: {} successfully added", createdMaterials);
        return createdMaterials;
    }

    @Override
    public void delete(Long id) {
        Material material = materialRepo.findById(id).orElse(null);
        if (material == null) {
            log.warn("IN findById - no material found by id: {}", id);
        } else {
            material.setStatus(Status.DELETED);
        }
        materialRepo.deleteById(id);

    }
}
