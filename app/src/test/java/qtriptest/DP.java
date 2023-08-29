package qtriptest;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DP {
    
    @DataProvider(name="data-provider")
    public static Object[][] dataProviderMethod(Method method) throws IOException{
     String excelPath = "/home/crio-user/workspace/anjanikmr51-ME_QTRIP_QA_V2/app/src/test/resources/DatasetsforQTrip.xlsx";
    
      int rowIndex = 0;
      int cellIndex = 0;
      List<List<String>> outputList = new ArrayList<List<String>>();
      
      FileInputStream excelFile = new FileInputStream(new File(excelPath));
      try{
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet selectedSheet = workbook.getSheet(method.getName());
            Iterator<Row> iterator =  selectedSheet.iterator();
            while(iterator.hasNext()){
              Row nextRow = iterator.next();
              Iterator<Cell> cellIterator = nextRow.cellIterator();
              List<String> innerList = new ArrayList<String>();
              while(cellIterator.hasNext()){
                  Cell cell = cellIterator.next();
                  if(rowIndex>0&&cellIndex>0){
                      String cellValue="";
                      switch(cell.getCellType()){
                          case STRING:
                          cellValue = cell.getStringCellValue();
                          break;
                          case NUMERIC:
                          cellValue = String.valueOf(cell.getNumericCellValue());
                          break;
                          default:
                          throw new UnsupportedOperationException("Unsupported Cell Type: "+cell.getCellType()); 
                      }
                      innerList.add(cellValue);
                  }
                  cellIndex++;
              }
              if(!innerList.isEmpty()){
                outputList.add(innerList);
              }
              rowIndex++;
            }
            workbook.close();
      }catch (IOException e) {
        e.printStackTrace();
      }finally {
        if (excelFile != null) {
            try {
                excelFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
      //converting List<List> to Object[][]
      Object[][] data = new Object[outputList.size()][];
      for(int i = 0 ; i<outputList.size();i++){
        data[i] = outputList.get(i).toArray();
      }
      return data;
    }
}

