package com.smart.wccs.service;

import com.smart.wccs.model.Material;

import java.util.List;

public interface MaterialService {

    List<Material> getAllMaterial();

    List<Material> getAllMaterialForAdmin();

    Material getById(Long id);

    void create(Material material);

    void update(Long id, Material material);

    List<Material> createAll(List<Material> materials);

    void delete(Long id);
}
