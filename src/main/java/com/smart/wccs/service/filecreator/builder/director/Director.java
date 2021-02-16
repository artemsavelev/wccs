package com.smart.wccs.service.filecreator.builder.director;

import com.smart.wccs.service.filecreator.builder.builders.Builder;
import com.smart.wccs.service.filecreator.builder.builders.EstimateBuilder;
import com.smart.wccs.service.filecreator.builder.files.EstimateFile;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * Директор знает в какой последовательности заставлять работать строителя. Он
 * работает с ним через общий интерфейс Строителя. Из-за этого, он может не
 * знать какой конкретно продукт сейчас строится.
 */

public class Director {


    public void  constructorEstimate(Builder builder) {



    }
}
