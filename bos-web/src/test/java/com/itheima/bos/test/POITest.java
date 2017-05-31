package com.itheima.bos.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POITest {
    
    //@Test
    public void test01() throws FileNotFoundException, IOException {
        String filePath = "D:/test.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
        try{
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if(row.getRowNum() == 0) continue;
                for (Cell cell : row) {
                    System.out.print(cell.getStringCellValue() + "\t");
                }
                System.out.println();
            }
        }finally{
            if(workbook!=null)
                workbook.close();
        }
    }
}
