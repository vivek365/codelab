package com.codelab.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class CommonGridExportManager {
	static final Logger LOGGER = LoggerFactory.getLogger(CommonGridExportManager.class);
	static final String CONTENTDISPOSITION = "Content-Disposition";
	static final String RESPONSE = "response";
	static final String LISTOBJ = "listObj";
	static final String COLUMNARRAY = "columnArray";
	static final String MODULENAME = "moduleName";
	static final String FILENAME = "filename";
	static final String DISPLAYNAME = "DisplayName";
	static final String DISPLAYVALUE = "displayValue";
	static final String COLUMNNAME = "ColumnName";
	static final String DOT = ".";
	static final String ATTACHMENTFILENAME = "attachment; filename=";

	@SuppressWarnings("unchecked")
	public void exportToPdf(Map<String, Object> exportMap) {
		HttpServletResponse response = (HttpServletResponse) exportMap.get(RESPONSE);
		List<Map<String, Object>> listObj = (List<Map<String, Object>>) exportMap.get(LISTOBJ);
		JsonNode columnArray = (JsonNode) exportMap.get(COLUMNARRAY);
		String moduleName = (String) exportMap.get(MODULENAME);

		Document document = new Document();
		JsonNode object = null;
		Map<Integer, Object[]> data = new TreeMap<>();

		try {
			response.setContentType("application/pdf");
			response.setHeader(CONTENTDISPOSITION,
					ATTACHMENTFILENAME + moduleName + "_" + new Date().getTime() + ".pdf");
			response.setHeader(FILENAME, moduleName + "_" + new Date().getTime() + ".pdf");

			PdfWriter.getInstance(document, response.getOutputStream());

			int colLen = columnArray.size();

			PdfPTable table = new PdfPTable(colLen);
			Font font = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD);
			Font font1 = new Font(Font.FontFamily.HELVETICA, 5, Font.NORMAL);

			Object[] columns = new Object[colLen];
			Object[] columnName = new Object[colLen];
			Object[] displayValue = new Object[colLen];

			for (int i = 0; i < colLen; i++) {
				object = columnArray.get(i);

				columns[i] = object.get(DISPLAYNAME).asText();
				columnName[i] = object.get(COLUMNNAME).asText();

				displayValue[i] = setDisplayValue(object.get(DISPLAYVALUE));

				String colName = columnName[i].toString();

				if (colName.contains(".")) {
					columnName[i] = colName.substring(colName.indexOf(DOT) + 1, colName.length());
				}
			}
			data.put(1, columns);

			for (int i = 0; i < listObj.size(); i++) {
				Map<String, Object> map = listObj.get(i);
				Object[] listData = new Object[colLen];

				for (int j = 0; j < colLen; j++) {
					listData[j] = map.get(columnName[j]);

					listData[j] = checkDisplayValue(displayValue[j], listData[j]);

				}
				data.put(i + 2, listData);
			}

			Set<Integer> keyset = data.keySet();

			int row = 0;
			Font tableFont;
			for (Integer key : keyset) {
				row++;

				Object[] objArr = data.get(key);

				for (Object obj : objArr) {
					obj = obj != null ? obj.toString() : "";
					tableFont = row == 1 ? font : font1;

					table.addCell(new Phrase((String) obj, tableFont));

				}
			}
			document.open();
			document.add(table);

		} catch (Exception e) {
			LOGGER.error("error in exportToPdf >" + e);
		}
		document.close();
	}

	public void exportToExcel(Map<String, Object> exportMap) throws IOException {
		HttpServletResponse response = (HttpServletResponse) exportMap.get(RESPONSE);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> listObj = (List<Map<String, Object>>) exportMap.get(LISTOBJ);
		JsonNode columnArray = (JsonNode) exportMap.get(COLUMNARRAY);
		String moduleName = (String) exportMap.get(MODULENAME);

		response.setContentType("application/vnd.ms-excel");
		response.setHeader(CONTENTDISPOSITION, ATTACHMENTFILENAME + moduleName + "_" + new Date().getTime() + ".xls");
		response.setHeader(FILENAME, moduleName + "_" + new Date().getTime() + ".xls");

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sample sheet");

		JsonNode object;

		Map<Integer, Object[]> data = new TreeMap<>();

		int colLen = columnArray.size();

		Object[] columns = new Object[colLen];
		Object[] columnName = new Object[colLen];
		Object[] displayValue = new Object[colLen];

		for (int i = 0; i < colLen; i++) {
			object = columnArray.get(i);

			columns[i] = object.get(DISPLAYNAME).asText();
			columnName[i] = object.get(COLUMNNAME).asText();

			displayValue[i] = setDisplayValue(object.get(DISPLAYVALUE));

			String colName = columnName[i].toString();

			if (colName.contains(DOT)) {
				columnName[i] = colName.substring(colName.indexOf(DOT) + 1, colName.length());
			}
		}

		data.put(1, columns);

		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();

		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);

		style.setFont(font);

		for (int i = 0; i < listObj.size(); i++) {
			Map<String, Object> map = listObj.get(i);
			Object[] listData = new Object[colLen];

			for (int j = 0; j < colLen; j++) {
				listData[j] = map.get(columnName[j]);

				listData[j] = checkDisplayValue(displayValue[j], listData[j]);
			}
			data.put(i + 2, listData);
		}

		Set<Integer> keyset = data.keySet();
		int rownum = 0;

		for (Integer key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);

			int cellnum = 0;

			if (rownum == 1) {
				row.setRowStyle(style);
			}

			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);

				obj = obj != null ? obj.toString() : "";
				obj = "" + obj + "";

				if (rownum == 1) {
					cell.setCellStyle(style);
				}

				cell.setCellValue((String) obj);
			}
		}

		workbook.write(response.getOutputStream()); // Write workbook to
													// response.
		workbook.close();
	}

	@SuppressWarnings("unchecked")
	public void exportToCsv(Map<String, Object> exportMap) throws IOException {
		try {
			HttpServletResponse response = (HttpServletResponse) exportMap.get(RESPONSE);
			List<Map<String, Object>> listObj = (List<Map<String, Object>>) exportMap.get(LISTOBJ);
			JsonNode columnArray = (JsonNode) exportMap.get(COLUMNARRAY);
			String moduleName = (String) exportMap.get(MODULENAME);

			response.setContentType("application/csv");
			response.setHeader(CONTENTDISPOSITION,
					ATTACHMENTFILENAME + moduleName + "_" + new Date().getTime() + ".csv");
			response.setHeader(FILENAME, moduleName + "_" + new Date().getTime() + ".csv");
			PrintWriter w = response.getWriter();

			JsonNode object;

			Map<Integer, Object[]> data = new TreeMap<>();
			int colLen = columnArray.size();

			Object[] columns = new Object[colLen];
			Object[] columnName = new Object[colLen];
			Object[] displayValue = new Object[colLen];

			for (int i = 0; i < colLen; i++) {
				object = columnArray.get(i);
				columns[i] = object.get(DISPLAYNAME).asText();
				columnName[i] = object.get(COLUMNNAME).asText();

				String colName = columnName[i].toString();

				displayValue[i] = setDisplayValue(object.get(DISPLAYVALUE));

				if (colName.contains(DOT)) {
					columnName[i] = colName.substring(colName.indexOf(DOT) + 1, colName.length());
				}

			}

			data.put(1, columns);

			for (int i = 0; i < listObj.size(); i++) {
				Map<String, Object> map = listObj.get(i);
				Object[] listData = new Object[colLen];

				for (int j = 0; j < colLen; j++) {
					listData[j] = map.get(columnName[j]);

					listData[j] = checkDisplayValue(displayValue[j], listData[j]);
				}
				data.put(i + 2, listData);

			}

			Set<Integer> keyset = data.keySet();

			for (Integer key : keyset) {
				Object[] objArr = data.get(key);

				for (Object obj : objArr) {
					obj = obj != null ? obj.toString() : "";
					obj = "\"" + obj + "\"";
					w.print(obj + ",");
				}
				w.println();
			}
			w.flush();
			w.close();

		} catch (Exception e) {
			LOGGER.error("error in exportToCsv >" + e);
		}
	}

	public Object checkDisplayValue(Object displayValue, Object originalValue) {
		Object originalValue1 = originalValue;
		if (!"".equals(displayValue)) {
			String[] disVal = displayValue.toString().split(",");

			for (String dv : disVal) {
				String[] v = dv.split(":");

				if (originalValue != null && v[0].equals(originalValue.toString())) {
					originalValue1 = v[1];
				}
			}
		}

		return originalValue1;
	}

	public Object setDisplayValue(JsonNode element) {
		String displayValue;
		if (element != null) {
			displayValue = element.asText();

		} else {
			displayValue = "";
		}
		return displayValue;
	}

}
