package com.hirain.qsy.shaft.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class OperateExcelUtils {

	/**
	 * 获取机车初始excel的数据
	 * 
	 * @param path excel数据处理
	 * @return list 读取的excel内容
	 * @throws IOException
	 */
	/**
	 * public List<List<Object>> getExcelFileData(String path,String mapName) throws
	 * IOException { trainInfor=new HashMap<String, String>(); excelData=new
	 * ArrayList<List<Object>>(); File file = new File(path); FileInputStream
	 * fStream = new FileInputStream(file); //文件流对象
	 * AttributeMappingConfigurationData Data=new
	 * AttributeMappingConfigurationData(mapName); mapData=Data.getMapData();
	 * Workbook wb=null; try { if (file.isFile() && file.exists()) { String
	 * fileExtension = file.getName().substring(file.getName().lastIndexOf(".") +
	 * 1); //获取扩展名 if ( "xls".equals(fileExtension)){ wb = new
	 * HSSFWorkbook(fStream); }else if ("xlsx".equals(fileExtension)){ wb = new
	 * XSSFWorkbook(fStream); }else { System.out.println("文件类型错误!");
	 * fStream.close(); file.delete(); return excelData; } Sheet sheet =
	 * wb.getSheetAt(0); int firstRowIndex = sheet.getFirstRowNum();//第一行的行号 int
	 * lastRowIndex = sheet.getLastRowNum();//最后一行的行号
	 * 
	 * for(int rIndex=firstRowIndex;rIndex<=lastRowIndex;rIndex++) { if(rIndex>=1) {
	 * if(!trainInfor.containsKey(sheet.getRow(rIndex).getCell(0).getStringCellValue())||!trainInfor.containsValue(sheet.getRow(rIndex).getCell(1).getStringCellValue()))
	 * { trainInfor.put(sheet.getRow(rIndex).getCell(0).getStringCellValue(),
	 * sheet.getRow(rIndex).getCell(1).getStringCellValue()); } } List<Object>
	 * rowDate = new ArrayList<Object>();//存储行信息
	 * if(!isNull(sheet.getRow(rIndex),sheet.getRow(0),mapData)) { for(int
	 * cIndex=0;cIndex<sheet.getRow(rIndex).getLastCellNum();cIndex++ ) {
	 * if(mapData.containsKey(sheet.getRow(0).getCell(cIndex).getStringCellValue()))
	 * { //转化时间格式剔除毫秒位
	 * if(rIndex>0&&((sheet.getRow(0).getCell(cIndex)).getStringCellValue().equals(acquisitionTime)))
	 * { String
	 * dateString=sheet.getRow(rIndex).getCell(cIndex).getStringCellValue();
	 * dateString=dateString.substring(0, dateString.lastIndexOf('.'));
	 * rowDate.add(dateString);
	 * 
	 * }else {
	 * rowDate.add(sheet.getRow(rIndex).getCell(cIndex).getStringCellValue()); } }
	 * 
	 * } excelData.add(rowDate); }
	 * 
	 * } } }catch (Exception e) { // TODO: handle exception } wb.close();
	 * fStream.close(); return excelData;
	 * 
	 * }
	 **/

	/**
	 * 获取excel的全部数据
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public List<List<Object>> getExcelFileData(String path) throws IOException {
		List<List<Object>> excelData = new ArrayList<List<Object>>();
		File file = new File(path);
		FileInputStream fStream = new FileInputStream(file); // 文件流对象

		Workbook wb = null;
		try {
			if (file.isFile() && file.exists()) {
				String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1); // 获取扩展名
				if ("xls".equals(fileExtension)) {
					wb = new HSSFWorkbook(fStream);
				} else if ("xlsx".equals(fileExtension)) {
					wb = new XSSFWorkbook(fStream);
				} else {
					System.out.println("文件类型错误!");
					fStream.close();
					file.delete();
					return excelData;
				}
				Sheet sheet = wb.getSheetAt(0);
				int firstRowIndex = sheet.getFirstRowNum();// 第一行的行号
				int lastRowIndex = sheet.getLastRowNum();// 最后一行的行号
				for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
					List<Object> rowDate = new ArrayList<Object>();// 存储行信息
					for (int cIndex = 0; cIndex < sheet.getRow(rIndex).getLastCellNum(); cIndex++) {
						rowDate.add(getCellValue(sheet.getRow(rIndex).getCell(cIndex)));
					}
					excelData.add(rowDate);
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return excelData;
	}

	public String getCellValue(Cell cell) {

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

}
