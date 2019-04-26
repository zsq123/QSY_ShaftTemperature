package com.hirain.qsy.shaft.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hirain.qsy.shaft.model.ThresholdData;

public class ReadExcelUtil {

	public static Workbook createWorkbook(String excelPath) {
		InputStream is = null;
		Workbook wb = null;
		try {
			File sourcefile = new File(excelPath);
			is = new FileInputStream(sourcefile);
			wb = WorkbookFactory.create(is);
			if (wb instanceof XSSFWorkbook) {
				wb = (XSSFWorkbook) wb;
			} else if (wb instanceof HSSFWorkbook) {
				wb = (HSSFWorkbook) wb;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return wb;
	}

	public static List<ThresholdData> getThresholdDatas() {
		List<ThresholdData> list = new ArrayList<>();
		String excelPath = System.getProperty("user.dir") + File.separator + "threshold.xls";
		Workbook workbook = createWorkbook(excelPath);
		Sheet sheet = workbook.getSheetAt(0);
		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();
		for (int i = firstRowNum + 1; i <= lastRowNum; i++) {
			Row row = sheet.getRow(i);
			ThresholdData thresholdData = new ThresholdData();
			thresholdData.setTrainId(getCellValue(row.getCell(0)));
			thresholdData.setTestNum(getCellValue(row.getCell(1)));
			thresholdData.setParameter1(getCellValue(row.getCell(2)));
			thresholdData.setParameter2(getCellValue(row.getCell(3)));
			thresholdData.setParameter3(getCellValue(row.getCell(4)));
			list.add(thresholdData);
		}
		return list;
	}

	/**
	 * 获取Excel中数据起始行，起始列，结束行，结束列
	 * 
	 * @param sheet
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {

		Map<String, String> map0 = new HashMap<String, String>();
		Map<String, String> map1 = new HashMap<String, String>();
		int mergedNum = sheet.getNumMergedRegions();
		CellRangeAddress range = null;
		for (int i = 0; i < mergedNum; i++) {
			range = sheet.getMergedRegion(i);
			int topRow = range.getFirstRow();
			int topCol = range.getFirstColumn();
			int bottomRow = range.getLastRow();
			int bottomCol = range.getLastColumn();
			map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);
			int tempRow = topRow;
			while (tempRow <= bottomRow) {
				int tempCol = topCol;
				while (tempCol <= bottomCol) {
					map1.put(tempRow + "," + tempCol, "");
					tempCol++;
				}
				tempRow++;
			}
			map1.remove(topRow + "," + topCol);
		}

		Map[] map = { map0, map1 };
		return map;
	}

	/**
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {

		String result = new String();
		switch (cell.getCellTypeEnum()) {
		case NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat sdf = null;
				if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
					sdf = new SimpleDateFormat("HH:mm");
				} else {
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}
				Date date = cell.getDateCellValue();
				result = sdf.format(date);
			} else if (cell.getCellStyle().getDataFormat() == 58) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				double value = cell.getNumericCellValue();
				Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
				result = sdf.format(date);
			} else {
				double value = cell.getNumericCellValue();
				// result = String.valueOf(value);
				CellStyle style = cell.getCellStyle();
				DecimalFormat format = new DecimalFormat();
				String temp = style.getDataFormatString();
				if (temp.equals("General")) {
					format.applyPattern("#");
				}
				result = format.format(value);
			}
			break;
		case STRING:
			result = cell.getRichStringCellValue().toString();
			break;
		case BLANK:
			result = "";
			break;
		default:
			result = "";
			break;
		}
		return result;
	}

	/**
	 * @param sheet
	 * @return
	 * @throws IOException
	 */
	public static Map<ClientAnchor, PictureData> getPictures(Sheet sheet) {
		Map<ClientAnchor, PictureData> map = new HashMap<ClientAnchor, PictureData>();
		if (sheet instanceof XSSFSheet) {
			List<POIXMLDocumentPart> list = ((XSSFSheet) sheet).getRelations();
			for (POIXMLDocumentPart part : list) {
				if (part instanceof XSSFDrawing) {
					XSSFDrawing drawing = (XSSFDrawing) part;
					List<XSSFShape> shapes = drawing.getShapes();
					for (XSSFShape shape : shapes) {
						XSSFPicture picture = (XSSFPicture) shape;
						ClientAnchor anchor = picture.getPreferredSize();
						map.put(anchor, picture.getPictureData());
					}
				}
			}
		} else {
			HSSFPatriarch hssfPatriarch = ((HSSFSheet) sheet).getDrawingPatriarch();
			if (hssfPatriarch != null) {
				List<HSSFShape> shapes = hssfPatriarch.getChildren();
				if (shapes.size() > 0) {
					for (HSSFShape sp : shapes) {
						if (sp instanceof HSSFPicture) {
							HSSFPicture picture = (HSSFPicture) sp;
							HSSFPictureData pictureData = picture.getPictureData();
							if (picture.getAnchor() instanceof HSSFClientAnchor) {
								HSSFClientAnchor anchor = (HSSFClientAnchor) picture.getAnchor();
								map.put(anchor, pictureData);
							}
						}
					}
				}
			}
		}
		return map;
	}

}
