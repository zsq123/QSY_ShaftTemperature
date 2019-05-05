package com.hirain.qsy.shaft.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hirain.qsy.shaft.model.ExceptionData;

public class ExceptionDataUtils {

	/**
	 * 将python返回的字符串解析成数组
	 * 
	 * @param str
	 * @return
	 */
	public static String[] pythonJsonToList(String str) {

		String substring = str.substring(1).substring(0, str.length() - 1);
		String replace = substring.replace("],[", "]#[");
		// System.out.println(replace);
		return replace.split("#");
	}

	/**
	 * 将数组转成矩阵
	 * 
	 * @param strArr
	 *            数组
	 * @param row
	 *            行数
	 * @param col
	 *            列数
	 * @return
	 */
	public static String[][] arrToMatrix(String[] strArr, int row, int col) {

		String[][] resMatrix = new String[row][col];
		for (int i = 0; i < col; i++) {
			int index = i * row;
			for (int j = 0; j < row; j++) {
				resMatrix[j][i] = strArr[index + j];
			}
		}
		return resMatrix;
	}

	/**
	 * 将字符串转换为矩阵
	 * 
	 * @param str
	 * @param row
	 * @param col
	 * @return
	 */
	public static String[][] pythonJsonToMatrix(String str, int row, int col) {
//		System.out.println(str);
		return arrToMatrix(pythonJsonToList(str), row, col);
	}

	public static List<ExceptionData> pythonToExceptionDataList(Integer trainId, List<Date> acquisitionTimeList, int col,
			String exceptionDataString) {

		String[][] excepDataMatrix = ExceptionDataUtils.pythonJsonToMatrix(exceptionDataString, acquisitionTimeList.size(), col);
		ArrayList<ExceptionData> list = new ArrayList<ExceptionData>();
		for (int i = 0; i < acquisitionTimeList.size(); i++) {
			ExceptionData exceptionData = new ExceptionData(trainId, acquisitionTimeList.get(i), new Date(), excepDataMatrix[i]);
			list.add(exceptionData);
		}
		return list;

	}

//	public static List<ExceptionData> pythonToExceptionDataListNoList(Integer trainId, int col, String exceptionDataString) {
//
//		String[][] excepDataMatrix = ExceptionDataUtils.pythonJsonToMatrix(exceptionDataString, 2, col);
//		ArrayList<ExceptionData> list = new ArrayList<ExceptionData>();
//		for (int i = 0; i < 2; i++) {
//			ExceptionData exceptionData = new ExceptionData(trainId, new Date(), new Date(), excepDataMatrix[i]);
//			list.add(exceptionData);
//		}
//		return list;
//	}

	// public static void main(String[] args) {
	//
	// Scanner sc = new Scanner(System.in);
	// String str = sc.nextLine();
	// String[][] pythonJsonToMatrix = pythonJsonToMatrix(str, 2, 30);
	// for (int i = 0; i < 2; i++) {
	// for (int j = 0; j < 30; j++) {
	// System.out.println(pythonJsonToMatrix[i][j]);
	// }
	// }
	// }

}
