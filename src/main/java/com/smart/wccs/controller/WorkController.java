package com.smart.wccs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.model.Device;
import com.smart.wccs.model.Views;
import com.smart.wccs.model.Work;
import com.smart.wccs.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/work/")
public class WorkController {

    private final WorkService workService;

    @Autowired
    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @GetMapping
    @JsonView(Views.UserView.class)
    public ResponseEntity<List<Work>> listWork() {
        List<Work> works = workService.getAllWork();

        if (works.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(works, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @JsonView(Views.UserView.class)
    public ResponseEntity<Work> getWork(@PathVariable(name = "id") Long id) {
        Work work = workService.getById(id);

        if (work == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(work, HttpStatus.OK);
    }

    @RequestMapping(value = "/addAll", method = RequestMethod.POST)
    @JsonView(Views.UserView.class)
    public ResponseEntity<List<Work>> saveWorkAll(@RequestBody List<Work> works) {
        List<Work> result =  workService.createAll(works);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
