package com.rx.substation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.rx.substation.service.ExportService;

import java.io.IOException;
import java.util.Map;

/**
 *	一般excel表格导出
 * @author dcx
 *
 */

@RestController
@Component
@RequestMapping(value = "/scair/excelexport")
public class ExcelExportController {

	@Autowired
	ExportService exportService;

	/**
	 * 一般标准表格导出
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/normalexport",produces="application/json;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public void exportExcel(HttpServletRequest request, HttpServletResponse response){
//		String excelName = requerameter("cellValues");
		Map<String, String[]> paras = request.getParameterMap();
		System.out.println("excelName  :  "+paras.get("excelName")[0]);
		System.out.println("cellValues  :  "+paras.get("cellValues")[0]);

		String excelName = paras.get("excelName")[0];
		String cellValues = paras.get("cellValues")[0];
//		String excelName = "dcx";
//			String cellValues = "<table><tbody><tr>\n" +
//					"            <th colspan=\"11\" style=\"text-align: center\">\n" +
//					"                四川省环境空气自动监测数据传输率情况评价表\n" +
//					"            </th>\n" +
//					"        </tr>\n" +
//					"        <tr>\n" +
//					"            <td>运行单位/城市</td>\n" +
//					"            <td>时间</td>\n" +
//					"            <td>SO2</td>\n" +
//					"            <td>NO2</td>\n" +
//					"            <td>O3</td>\n" +
//					"            <td>CO</td>\n" +
//					"            <td>PM10</td>\n" +
//					"            <td>PM2.5</td>\n" +
//					"            <td>运行单位平均传输率</td>\n" +
//					"            <td>运行单位平均得分（总分35分）</td>\n" +
//					"            <td>操作</td>\n" +
//					"        </tr>\n" +
//					"        \n" +
//					"        <tr>\n" +
//					"            <td>10000</td>\n" +
//					"            <td>2017-05</td>\n" +
//					"            <td>100.00%</td>\n" +
//					"            <td>100.00%</td>\n" +
//					"            <td>100.00%</td>\n" +
//					"            <td>100.00%</td>\n" +
//					"            <td>100.00%</td>\n" +
//					"            <td>100.00%</td>\n" +
//					"            <td>100.00%</td>\n" +
//					"            <td>35.00</td>\n" +
//					"            <td>\n" +
//					"                <a class=\"btn btn-primary btn-lg layBtn\" onclick=\"layShow(0)\" data-toggle=\"modal\" data-target=\"#myModals\">详情</a>\n" +
//					"            </td>\n" +
//					"        </tr>\n" +
//					"        </tbody><table>";
		try {
			exportService.export(response,excelName,cellValues);
		}catch (IOException e){
			e.printStackTrace();
		}

	}

}
