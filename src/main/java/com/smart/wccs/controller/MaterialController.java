package com.smart.wccs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.model.Material;
import com.smart.wccs.model.Views;
import com.smart.wccs.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @JsonView(Views.UserView.class)
    public ResponseEntity<List<Material>> listMaterial() {
        List<Material> materials = materialService.getAllMaterial();

        if (materials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(materials, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @JsonView(Views.UserView.class)
    public ResponseEntity<Material> getMaterial(@PathVariable(name = "id") Long id) {
        Material material = materialService.getById(id);

        if (material == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(material, HttpStatus.OK);
    }

    @PostMapping
    @JsonView(Views.UserView.class)
    public ResponseEntity<Material> saveMaterial(@RequestBody Material material) {

        if (material == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        materialService.create(material);
        return new ResponseEntity<>(material, HttpStatus.OK);

    }

    @RequestMapping(value = "/addAll", method = RequestMethod.POST)
    @JsonView(Views.UserView.class)
    public ResponseEntity<List<Material>> saveMaterialAll(@RequestBody List<Material> materials) {
        List<Material> result = materialService.createAll(materials);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
