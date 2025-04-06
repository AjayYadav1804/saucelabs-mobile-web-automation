package com.saucedemo.utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {

    private Workbook workbook;

    public ExcelUtils(String excelPath) throws IOException {
        FileInputStream excelFile = new FileInputStream(excelPath);
        this.workbook = WorkbookFactory.create(excelFile);
    }

    public List<Object[]> getTestData(String sheetName, String testMethodName) {
        Sheet sheet = workbook.getSheet(sheetName);
        List<Object[]> testData = new ArrayList<>();
        Iterator<Row> rows = sheet.iterator();

        // Assuming first row is header
        rows.next();

        while (rows.hasNext()) {
            Row row = rows.next();
            if (row.getCell(1).getStringCellValue().equals(testMethodName) && row.getCell(2).getStringCellValue().equalsIgnoreCase("Y")) {
                Object[] data = new Object[3];
                data[0] = row.getCell(3).getStringCellValue(); // FirstName
                data[1] = row.getCell(4).getStringCellValue(); // LastName
                data[2] = row.getCell(5).getStringCellValue(); // PostalCode
                testData.add(data);
            }
        }

        return testData;
    }
}