package viewResolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;



public class XlsView extends AbstractXlsView {

	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Sheet sheet = workbook.createSheet("aaa");
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("hahahah");
		
	}

	

}
