package com.smart.wccs.controller;

import com.smart.wccs.dto.MaterialDto;
import com.smart.wccs.model.Material;
import com.smart.wccs.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/material/")
public class MaterialController {

    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public ResponseEntity<List<MaterialDto>> listMaterial() {
        List<Material> materials = materialService.getAllMaterial();

        if (materials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(MaterialDto.materialDtoList(materials), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<MaterialDto> getMaterial(@PathVariable(name = "id") Long id) {
        Material material = materialService.getById(id);

        if (material == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        MaterialDto result = MaterialDto.fromMaterial(material);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MaterialDto> saveMaterial(@RequestBody @Valid Material material) {

        if (material == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        materialService.create(material);
        return new ResponseEntity<>(MaterialDto.fromMaterial(material), HttpStatus.OK);

    }

    @RequestMapping(value = "/addAll", method = RequestMethod.POST)
    public ResponseEntity<List<Material>> saveMaterialAll(@RequestBody List<Material> materials) {
        List<Material> result = materialService.createAll(materials);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
