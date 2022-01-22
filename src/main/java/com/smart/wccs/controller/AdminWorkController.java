package com.smart.wccs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.exceptions.BadRequestException;
import com.smart.wccs.model.Views;
import com.smart.wccs.model.Work;
import com.smart.wccs.service.ActionToSetService;
import com.smart.wccs.service.WorkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminWorkController {

    private final ActionToSetService actionToSetService;
    private final WorkService workService;

    public AdminWorkController(ActionToSetService actionToSetService, WorkService workService) {
        this.actionToSetService = actionToSetService;
        this.workService = workService;
    }

    @RequestMapping(value = "work/view-set", method = RequestMethod.GET)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<List<Work>> listWorkForAdmin() {
        List<Work> works = workService.getAllWorkForAdmin();

        if (works.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(works, HttpStatus.OK);
    }

    @RequestMapping(value = "work/to-set/{id}", method = RequestMethod.PATCH)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<Work> toSetWork(@PathVariable(name = "id") Long id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        actionToSetService.toSetWork(id);
        Work workFromDb = workService.getById(id);
        return new ResponseEntity<>(workFromDb, HttpStatus.OK);
    }

    @RequestMapping(value = "work/del-set/{id}", method = RequestMethod.DELETE)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<Work> delSetWork(@PathVariable(name = "id") Long id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        actionToSetService.delSetWork(id);
        Work workFromDb = workService.getById(id);
        return new ResponseEntity<>(workFromDb, HttpStatus.OK);
    }

    @RequestMapping(value = "work/update/{id}", method = RequestMethod.PUT)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> updateWork(@PathVariable(name = "id") Long id, @RequestBody Work work) {

        if (work == null) {
            throw new BadRequestException("Данные не могут быть пустыми");
        } else if (work.getPrice() < 0) {
            throw new BadRequestException("Значение цены не может быть отрицательным");
        }

        workService.update(id, work);
        Work workFromDb = workService.getById(id);
        return new ResponseEntity<>(workFromDb, HttpStatus.OK);
    }

    @RequestMapping(value = "work/save/", method = RequestMethod.POST)
    @JsonView(Views.UserView.class)
    public ResponseEntity<?> saveWork(@RequestBody Work work) {

        if (work == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (work.getPrice() < 0) {
            throw new BadRequestException("Значение цены не может быть отрицательным");
        }

        workService.create(work);
        return new ResponseEntity<>(work, HttpStatus.OK);
    }
}
