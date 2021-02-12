package com.smart.wccs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.model.Estimate;
import com.smart.wccs.model.Views;
import com.smart.wccs.service.EstimateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/")
public class EstimateController {

    @Value("${upload.path}")
    private String uploadPath;

    private final EstimateService estimateService;

    @Autowired
    public EstimateController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }

    @RequestMapping(value = "/createEstimate", method = RequestMethod.POST)
    @JsonView(Views.UserView.class)
    public ResponseEntity<Estimate> saveEstimate(@RequestBody Estimate estimate) {

        if (estimate == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Estimate result = estimateService.create(estimate);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/files/{file_name:.+}", method = RequestMethod.GET)
    public void getFile(@PathVariable("file_name") String fileName, HttpServletResponse response) {
        // Прежде всего стоит проверить, если необходимо, авторизован ли пользователь и имеет достаточно прав на скачивание файла. Если нет, то выбрасываем здесь Exception

        // Авторизованные пользователи смогут скачать файл
        Path file = Paths.get(uploadPath, fileName);
        if (Files.exists(file)) {
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");


            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                log.info("Error writing file to output stream. Filename was '{}'" + fileName, e);
                throw new RuntimeException("IOError writing file to output stream");
            }
        }
    }
}
