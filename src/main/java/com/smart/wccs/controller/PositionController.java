package com.smart.wccs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.model.Position;
import com.smart.wccs.model.Views;
import com.smart.wccs.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin")
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping(value = "/all-position/")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<List<Position>> listPosition() {
        List<Position> positions = positionService.getAllPosition();

        if (positions == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @GetMapping(value = "/get-position/{id}")
    @JsonView(Views.UserView.class)
    public ResponseEntity<Position> getPosition(@PathVariable(name = "id") Long id) {
        Position position = positionService.getById(id);

        if (position == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(position, HttpStatus.OK);
    }

    @PostMapping(value = "/save-position/")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> savePosition(@RequestBody Position position) {

        if (position == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        positionService.create(position);
        return new ResponseEntity<>(position, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-position/{id}")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> deletePosition(@PathVariable(name = "id") Long id) {
        Position positionFromDb = positionService.getById(id);

        if (positionFromDb == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        positionService.delete(positionFromDb.getId());
        return new ResponseEntity<>(positionFromDb, HttpStatus.OK);
    }
}
