package com.rx.substation.service;




import me.chyxion.xls.TableToXls;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by dcx on 17-5-23.
 */
@Service
public class ExportService {

    public void export (HttpServletResponse response, String excelName, String cellValues) throws IOException{

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = encodingFileName(excelName+".xls");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        OutputStream out = response.getOutputStream();
        out.write(TableToXls.process(cellValues));
        out.flush();
        out.close();
    }


    /**
     * 处理文件名中出现的空格
     * 其中%20是空格在UTF-8下的编码
     * @param fileName
     * @return
     */
    public static String encodingFileName(String fileName) {
        String returnFileName = "";
        try {
            returnFileName = URLEncoder.encode(fileName, "UTF-8");
            returnFileName = StringUtils.replace(returnFileName, "+", "%20");
            if (returnFileName.length() > 150) {
                returnFileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
                returnFileName = StringUtils.replace(returnFileName, " ", "%20");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return returnFileName;
    }


}
