package com.smart.wccs.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.model.SectionGroup;
import com.smart.wccs.model.Views;
import com.smart.wccs.service.SectionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/SectionGroup/")
public class SectionGroupController {

    private final SectionGroupService sectionGroupService;

    @Autowired
    public SectionGroupController(SectionGroupService sectionGroupService) {
        this.sectionGroupService = sectionGroupService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<List<SectionGroup>> listSectionGroup() {
        List<SectionGroup> sectionGroups = sectionGroupService.getAllGroup();

        if (sectionGroups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(sectionGroups, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<SectionGroup> getSectionGroup(@PathVariable(name = "id") Long id) {

        SectionGroup sectionGroup = sectionGroupService.getById(id);

        if (sectionGroup == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(sectionGroup, HttpStatus.OK);
    }

}
