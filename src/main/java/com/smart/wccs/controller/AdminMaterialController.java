package com.smart.wccs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.exceptions.BadRequestException;
import com.smart.wccs.model.Material;
import com.smart.wccs.model.Views;
import com.smart.wccs.service.ActionToSetService;
import com.smart.wccs.service.MaterialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminMaterialController {

    private final ActionToSetService actionToSetService;
    private final MaterialService materialService;

    public AdminMaterialController(ActionToSetService actionToSetService, MaterialService materialService) {
        this.actionToSetService = actionToSetService;
        this.materialService = materialService;
    }

    @RequestMapping(value = "material/view-set", method = RequestMethod.GET)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<List<Material>> listMaterialForAdmin() {
        List<Material> materials = materialService.getAllMaterialForAdmin();

        if (materials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(materials, HttpStatus.OK);
    }

    @RequestMapping(value = "material/to-set/{id}", method = RequestMethod.PATCH)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<Material> toSetMaterial(@PathVariable(name = "id") Long id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        actionToSetService.toSetMaterial(id);
        Material materialFromDb = materialService.getById(id);
        return new ResponseEntity<>(materialFromDb, HttpStatus.OK);
    }

    @RequestMapping(value = "material/del-set/{id}", method = RequestMethod.DELETE)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<Material> delSetMaterial(@PathVariable(name = "id") Long id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        actionToSetService.delSetMaterial(id);
        Material materialFromDb = materialService.getById(id);
        return new ResponseEntity<>(materialFromDb, HttpStatus.OK);
    }

    @RequestMapping(value = "material/update/{id}", method = RequestMethod.PUT)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> updateMaterial(@PathVariable(name = "id") Long id, @RequestBody Material material) {

        if (material == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (material.getPrice() < 0) {
            throw new BadRequestException("Значение цены не может быть отрицательным");
        }

        materialService.update(id, material);
        Material materialFromDb = materialService.getById(id);
        return new ResponseEntity<>(materialFromDb, HttpStatus.OK);
    }

    @RequestMapping(value = "material/save/", method = RequestMethod.POST)
    @JsonView(Views.UserView.class)
    public ResponseEntity<?> saveMaterial(@RequestBody Material material) {

        if (material == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (material.getPrice() < 0) {
            throw new BadRequestException("Значение цены не может быть отрицательным");
        }

        materialService.create(material);
        return new ResponseEntity<>(material, HttpStatus.OK);
    }
}
