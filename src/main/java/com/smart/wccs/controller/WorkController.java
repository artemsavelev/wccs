package com.smart.wccs.controller;

import com.smart.wccs.dto.WorkDto;
import com.smart.wccs.model.Work;
import com.smart.wccs.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<WorkDto>> listWork() {
        List<Work> works = workService.getAllWork();

        if (works.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(WorkDto.workDtoList(works), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<WorkDto> getWork(@PathVariable(name = "id") Long id) {
        Work work = workService.getById(id);

        if (work == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        WorkDto result = WorkDto.fromWork(work);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WorkDto> saveWork(@RequestBody @Valid Work work) {

        if (work == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        workService.create(work);
        return new ResponseEntity<>(WorkDto.fromWork(work), HttpStatus.OK);
    }

    @RequestMapping(value = "/addAll", method = RequestMethod.POST)
    public ResponseEntity<List<Work>> saveWorkAll(@RequestBody List<Work> works) {
        List<Work> result =  workService.createAll(works);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
