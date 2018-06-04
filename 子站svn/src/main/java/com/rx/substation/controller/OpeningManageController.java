package com.rx.substation.controller;

import com.rx.substation.service.OpeningManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dcx on 2017/8/31 0031.
 * 仪器品牌启用管理
 */
@RestController
@RequestMapping(value = "/scair/openingmanage")
public class OpeningManageController {

    @Autowired
    OpeningManageService openingManageService;

    /**
     * 查询显示近八年内启用的仪器信息
     * @return
     */
    @RequestMapping(value="/findByConditions")
    public List<Map<String, Object>> getOpeningManageData(HttpServletRequest httpServletRequest){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        Calendar cal = Calendar.getInstance();
        //获得八年前时间
        cal.add(Calendar.YEAR, -8);
        String start = sdf.format(cal.getTime());
        //获得当前时间
        String end = sdf.format(new Date());

        String[] cityIds = httpServletRequest.getParameterValues("cityIds");
        String[] stationCodes = httpServletRequest.getParameterValues("stationCodes");
        String sortstatus = httpServletRequest.getParameter("sortstatus");
        List<Map<String, Object>> openingManageData = openingManageService.getOpeningManageData(start,end,cityIds,stationCodes,sortstatus);

        return openingManageData;
    }

}
