package com.rx.substation.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 导出Excel的类，提供了导出excel的方法
 * @author dcx
 *
 */
public class ExcelExportUtil {
	
	/**
	 * 导出为xls文件
	 * @param workbook
	 * @param out
	 * @throws IOException 
	 */
	public static void exportXls(HSSFWorkbook workbook, OutputStream out) throws IOException{
		try{
			workbook.write(out);
			out.flush();
		}finally{
			out.close();
			
		}
	}
	
	/**
	 * 导出为xls文件
	 * @param workbook
	 * @param path
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void exportXlsToFile(HSSFWorkbook workbook, String path) throws FileNotFoundException, IOException{
		OutputStream out = new FileOutputStream(path);
		exportXls(workbook, out);
	}
	
	/**
	 * 导出多个excel为zip文件
	 * @param out 输出流
	 * @param workbooks 要导出的excel
	 * @throws IOException 
	 */
	public static void exportToZip(OutputStream out,List<String> itemNames,List<HSSFWorkbook> workbooks) throws IOException{
			ZipOutputStream zip = new ZipOutputStream(out);
			List<byte[]> datas = new ArrayList<byte[]>();
		try{	
			for (HSSFWorkbook workbook : workbooks) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				workbook.write(bos);
				datas.add(bos.toByteArray());
			}
			for (int i = 0; i < datas.size(); i++) {
				//设置子项目名
				zip.putNextEntry(new ZipEntry(itemNames.size()>i?(itemNames.get(i)+".xls"):("文件"+(i+1)+".xls")));
				//写入子项目数据
				byte[] buf = new byte[1024*1024];
				int length = 0;
				ByteArrayInputStream in = new ByteArrayInputStream(datas.get(i));
				while((length=in.read(buf))!=-1){
					zip.write(buf,0,length);
				}
			}
		}finally{
			zip.finish();
		}
	}
	

}
