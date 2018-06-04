package com.rx.substation.test;

import com.rx.substation.service.MonthCompleteStatusService;
import com.sun.glass.ui.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/14 0014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ComponentScan(basePackages = { "service","test" })
public class WeekTest {

    @Autowired
    private  MonthCompleteStatusService monthCompleteStatusService;

    @Test
    public void weekTest() throws Exception {
        System.out.println("1111");
        Map<String,Object> mw = monthCompleteStatusService.getMonthCompleteStatus("2018-02-01","2018-02-28","1085E");
    }
}
