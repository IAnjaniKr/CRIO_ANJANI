package qtriptest;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;


public class DP1 {
    static String excelPath = "src/test/resources/DatasetsforQTrip.xlsx";


    @DataProvider(name = "data-provider")
    public static Object[][] dataProviderMethod() {
        // String methodName = method.getName();
        Object testData[][] = null;
        try {
            FileInputStream excelFile = new FileInputStream(excelPath);
            Workbook workbook = new XSSFWorkbook(excelFile);

            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
            int numRows = sheet.getPhysicalNumberOfRows();
            int numCols = sheet.getRow(0).getPhysicalNumberOfCells();

            testData = new Object[numRows - 1][numCols];

            for (int i = 1; i < numRows; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < numCols; j++) {
                    Cell cell = row.getCell(j);
                    testData[i - 1][j] = cell.toString();
                }
            }

            for (int i = 0; i < testData.length; i++) {
                // Iterate through columns within each row
                for (int j = 0; j < testData[i].length; j++) {
                    System.out.print(testData[i][j] + " ");
                }
                System.out.println(); 

            }

            workbook.close();

            return testData;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return testData;
    }
}


