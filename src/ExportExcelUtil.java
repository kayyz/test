import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExportExcelUtil {

	 public static void exportExcel(List<FreeClassroomBean> list, String[] titles, String path) {
	        SXSSFWorkbook wb = new SXSSFWorkbook(100);
	        Sheet sheet = wb.createSheet();
	        Row row = sheet.createRow(0);
	        //给单元格设置样式
	        CellStyle cellStyle = wb.createCellStyle();
	        Font font = wb.createFont();
	        //设置字体大小
	        font.setFontHeightInPoints((short) 12);
	        //设置字体加粗
	        font.setBold(true);
	        //给字体设置样式
	        cellStyle.setFont(font);
	        //设置单元格背景颜色
	        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	        //设置单元格填充样式(使用纯色背景颜色填充)
	        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        for (int i = 0; i < titles.length; i++) {
	            Cell cell = row.createCell(i);
	            cell.setCellValue(titles[i]);
	            cell.setCellStyle(cellStyle);
	            //设置列的宽度
	            sheet.setColumnWidth(i, 200*20);
	        }
	        for (int j = 0; j < list.size(); j++) {
	            Row rowData = sheet.createRow(j + 1);
	            FreeClassroomBean person = list.get(j);
	            Cell cell = rowData.createCell(0);
	            cell.setCellValue(person.getCdbh());
	            Cell cell2 = rowData.createCell(1);
	            cell2.setCellValue(person.getCdmc());
	            Cell cell3 = rowData.createCell(2);
	            cell3.setCellValue(person.getXqmc());
	            Cell cell4 = rowData.createCell(3);
	            cell4.setCellValue(person.getCdlbmc());
	            
	            Cell cell5 = rowData.createCell(4);
	            cell5.setCellValue(person.getZws());
	            
	            Cell cell6 = rowData.createCell(5);
	            cell6.setCellValue(person.getKszws1());
	            
	            Cell cell7 = rowData.createCell(6);
	            cell7.setCellValue(person.getJxlmc());
	            
	            Cell cell8 = rowData.createCell(7);
	            cell8.setCellValue(person.getCdjylx());
	            
	            Cell cell9 = rowData.createCell(8);
	            cell9.setCellValue(person.getSydxmc());
	            
	            Cell cell10 = rowData.createCell(9);
	            cell10.setCellValue(person.getJgmc());
	        }
	        try {
	            FileOutputStream fileOutputStream = new FileOutputStream(path);
	            wb.write(fileOutputStream);
	            wb.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
