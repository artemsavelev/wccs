package com.smart.wccs.service.impl;

import com.smart.wccs.model.Department;
import com.smart.wccs.model.Material;
import com.smart.wccs.model.SectionGroup;
import com.smart.wccs.model.Status;
import com.smart.wccs.repo.MaterialRepo;
import com.smart.wccs.repo.SectionGroupRepo;
import com.smart.wccs.repo.UserRepo;
import com.smart.wccs.service.MaterialService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepo materialRepo;
    private final UserRepo userRepo;
    private final SectionGroupRepo sectionGroupRepo;
    private final Utils utils;

    @Autowired
    public MaterialServiceImpl(MaterialRepo materialRepo, UserRepo userRepo, SectionGroupRepo sectionGroupRepo, Utils utils) {
        this.materialRepo = materialRepo;
        this.userRepo = userRepo;
        this.sectionGroupRepo = sectionGroupRepo;
        this.utils = utils;
    }


    @Override
    public List<Material> getAllMaterial() {

        List<Material> materials = materialRepo.findAll()
                .stream()
                .filter(o -> o.getDepartments().contains(userRepo.findByUsername(utils.getAuthUserName()).getDepartment()))
                .collect(Collectors.toList());
        log.info("IN getAllMaterials - {} materials found", materials.size());

        return materials;
    }

    @Override
    public List<Material> getAllMaterialForAdmin() {
        Department department = userRepo.findByUsername(utils.getAuthUserName()).getDepartment();

        List<Material> materials = materialRepo.findAll()
                .stream()
                .filter(dep -> {
                    List<Department> departmentFromDb = dep.getDepartments();

                    if (departmentFromDb.contains(department) || departmentFromDb.size() == 0  || !departmentFromDb.contains(department)) {
                        departmentFromDb.removeIf(d -> !d.equals(department));
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());

        log.info("IN getAllMaterialNoDepartment - {} materials found", materials.size());
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
    public Material create(Material material) {

        material.setCreatedDate(LocalDateTime.now());
        material.setStatus(Status.ACTIVE);
        material.setAuthor(userRepo.findByUsername(utils.getAuthUserName()).getDepartment());
        material.setDepartments(utils.addDepartmentToList());
        material.setGroup(sectionGroupRepo.findSectionGroupById(material.getGroup().getId()));
        Material createdMaterial = materialRepo.save(material);
        log.info("IN create - material: {} successfully added  for department: {}", createdMaterial, createdMaterial.getDepartments());

        return material;
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
    public Material update(Long id, Material material) {
        Material materialFromDb = materialRepo.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(id,
                        "IN update - material with id: " + id + " not updated. Material not found "));
        SectionGroup sectionGroupFromDb = sectionGroupRepo.findSectionGroupById(material.getGroup().getId());

        materialFromDb.setGroup(sectionGroupFromDb);
        materialFromDb.setUpdatedDate(LocalDateTime.now());
        materialFromDb.setName(material.getName());
        materialFromDb.setDimension(material.getDimension());
        materialFromDb.setPrice(material.getPrice());
        materialFromDb.setNote(material.getNote());

        Material updatedMaterial = materialRepo.save(materialFromDb);
        log.info("IN update - material: {} successfully updated", updatedMaterial);
        return material;
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
