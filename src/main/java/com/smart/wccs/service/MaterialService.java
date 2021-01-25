package com.smart.wccs.service;

import com.smart.wccs.model.Material;
import com.smart.wccs.model.Work;

import java.util.List;

public interface MaterialService {

    List<Material> getAllMaterial();

    Material getById(Long id);

    void create(Material material);

    List<Material> createAll(List<Material> materials);

    void delete(Long id);
}
