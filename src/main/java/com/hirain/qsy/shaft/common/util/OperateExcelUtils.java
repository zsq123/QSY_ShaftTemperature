package com.hirain.qsy.shaft.common.util;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hirain.qsy.shaft.common.config.GlobVariableConfig;
import com.hirain.qsy.shaft.model.ExcelToInitialData;

public  class OperateExcelUtils extends GlobVariableConfig{		
	private List<List<Object>> excelData;//存储excel解析后的数据
	private Map<String, String> mapData;//存储initialdata与excel表头映射关系
	

	/**
	 * 
	 * @param path excel数据处理
	 * @return list 读取的excel内容 
	 * @throws IOException
	 */
	public  List<List<Object>> getExcelFileData(String path) throws IOException
	{	 	 	
		 excelData=new ArrayList<List<Object>>();
		 File file = new File(path);
		 FileInputStream fStream = new FileInputStream(file); //文件流对象	
		 ExcelToInitialData Data=new ExcelToInitialData();	
		 mapData=Data.getMapData();
		 Workbook wb=null;
		 try {
			 if (file.isFile() && file.exists()) {
				 String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);  //获取扩展名		
				 if ( "xls".equals(fileExtension)){					  
					 wb = new HSSFWorkbook(fStream);
				 }else if ("xlsx".equals(fileExtension)){					
					 wb = new XSSFWorkbook(fStream);
				 }else {
					 System.out.println("文件类型错误!");						
					 fStream.close();
					 file.delete();
					 return excelData;
				 }             
				 Sheet sheet = wb.getSheetAt(0);               
				 int firstRowIndex = sheet.getFirstRowNum();//第一行的行号
				 int lastRowIndex = sheet.getLastRowNum();//最后一行的行号
             
				 for(int rIndex=firstRowIndex;rIndex<=lastRowIndex;rIndex++) 
				 	{					
					 	List<Object> rowDate = new ArrayList<Object>();//存储行信息
					 	if(!isNull(sheet.getRow(rIndex),sheet.getRow(0),mapData)) 
					 	{					 							 	
					 		for(int cIndex=0;cIndex<sheet.getRow(rIndex).getLastCellNum();cIndex++ ) 
					 		{				
					 			if(mapData.containsKey(sheet.getRow(0).getCell(cIndex).getStringCellValue())) 
					 			{
					 				//转化时间格式剔除毫秒位
					 				if(rIndex>0&&((sheet.getRow(0).getCell(cIndex)).getStringCellValue().equals(acquisitionTime))) 
					 				{
					 					String dateString=sheet.getRow(rIndex).getCell(cIndex).getStringCellValue();
					 					dateString=dateString.substring(0, dateString.lastIndexOf('.'));
					 					rowDate.add(dateString);
					 			
					 				}else 
					 				{
					 					rowDate.add(sheet.getRow(rIndex).getCell(cIndex).getStringCellValue());
					 				}
					 			}
					 		
					 		}   
					 	excelData.add(rowDate);					 						 
					 	}   
								  
				 	}
			 }
		 }catch (Exception e) {
			// TODO: handle exception
		 }		
		 wb.close();
		 fStream.close();
		return excelData;		 
	}
	
	
	//list=readBean(excelData,mapData,);
	

	/**
	 * 判断该行温度是否存在空值
	 * @param rows
	 * @param rowhead
	 * @param map
	 * @return
	 */
	private boolean isNull(Row rows,Row rowhead,Map<String, String>map) 
	{
		List<String> nullList=Arrays.asList(title);//实例化温度表头
		for(int i=0;i<rows.getLastCellNum();i++) 
		{
			if(map.containsKey(rowhead.getCell(i).getStringCellValue())&&rows.getCell(i).getStringCellValue().equals("-")) 			
			{				
				if((nullList.contains(rowhead.getCell(i).getStringCellValue()))) 			
					return true;			
			}
		}
		
		return false;
	}
		
}




