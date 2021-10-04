package com.smart.wccs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.dto.EstimateDto;
import com.smart.wccs.dto.EventType;
import com.smart.wccs.dto.ObjectType;
import com.smart.wccs.model.Estimate;
import com.smart.wccs.model.Order;
import com.smart.wccs.model.Views;
import com.smart.wccs.service.EstimateService;
import com.smart.wccs.service.OrderService;
import com.smart.wccs.util.WsSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.BiConsumer;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/estimate")
public class EstimateController {

    @Value("${upload.path}")
    private String uploadPath;
    private final EstimateService estimateService;
    private final OrderService orderService;
    private final BiConsumer<EventType, Order> wsSender;

    @Autowired
    public EstimateController(EstimateService estimateService, OrderService orderService, WsSender wsSender) {
        this.estimateService = estimateService;
        this.orderService = orderService;
        this.wsSender = wsSender.getSender(ObjectType.ORDER, Views.UserView.class);
    }

    @RequestMapping(value = "/createEstimate", method = RequestMethod.POST)
    @JsonView(Views.UserView.class)
    public ResponseEntity<Estimate> saveEstimate(@RequestBody Estimate estimate) {

        if (estimate == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Estimate result = estimateService.create(estimate);

        if (result == null) {
            result = estimate; // заглушка для клиента, иначе ругается
        }

        Order orderFromDb = orderService.getById(estimate.getOrder().getId());
        // ws
        wsSender.accept(EventType.UPDATE, orderFromDb);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "get-estimate/{id}", method = RequestMethod.GET)
    public ResponseEntity<EstimateDto> getEstimate(@PathVariable(name = "id") Long id) {
        Estimate estimate = estimateService.getById(id);
        if (estimate == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        EstimateDto result = EstimateDto.fromEstimate(estimate);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "get-estimate/", method = RequestMethod.GET)
    public ResponseEntity<List<EstimateDto>> loadEstimate(@RequestParam("customer") String customer, @RequestParam("address") String address) {
        List<Estimate> estimates = estimateService.getEstimate(customer, address);
        if (estimates == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(EstimateDto.estimateDtoList(estimates), HttpStatus.OK);
    }

    @RequestMapping(value = "/download/", method = RequestMethod.GET)
    public void getFile(@RequestParam("extId") String extId,
                        @RequestParam("customer") String customer,
                        @RequestParam("address") String address,
                        @RequestHeader String referer, HttpServletResponse response) {

        // получаем имя файла
        String fileName = estimateService.getFileName(extId, customer, address);

        // правильная кодировка
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(fileName, StandardCharsets.UTF_8)
                .build();

        // стоит проверить, если необходимо, авторизован ли пользователь и имеет достаточно прав на скачивание файла. Если нет, то выбрасываем здесь Exception
        if (referer == null || referer.isEmpty()) {
            throw new RuntimeException("Missing header 'referer' when try download file: " + fileName);
        }

        // Авторизованные пользователи смогут скачать файл
        Path file = Paths.get(uploadPath, fileName);
        if (Files.exists(file)) {
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // без этого не хочет показывать headers на клиенте
            response.setHeader("Content-disposition", contentDisposition.toString());
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
