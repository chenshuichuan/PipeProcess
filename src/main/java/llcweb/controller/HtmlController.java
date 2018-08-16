package llcweb.controller;

import llcweb.service.UsersService;
import llcweb.tools.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Author: Ricardo
 *@Description: 此文件用于所有页面的请求接口，接口名与html文件名对应
 *@Date: 15:14 2018/8/11
 **/
@Controller
@RequestMapping
public class HtmlController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UsersService usersService;

    @RequestMapping("/test.html")
    public ModelAndView test(){

        List<String> learnList =new ArrayList<>();
        learnList.add("hello1");
        learnList.add("hello2");
        ModelAndView modelAndView = new ModelAndView("test");
        modelAndView.addObject("learnList", learnList);

        return modelAndView;
    }
    @RequestMapping("/index.html")
    public ModelAndView index(){

        ModelAndView modelAndView = new ModelAndView("index");
        //modelAndView.addObject("fileInfoList", fileInfoList);
        return modelAndView;
    }
    @RequestMapping("/index")
    public ModelAndView index2(){

        ModelAndView modelAndView = new ModelAndView("index");
        //modelAndView.addObject("fileInfoList", fileInfoList);
        return modelAndView;
    }
    @RequestMapping("/main")
    public ModelAndView main(){
        ModelAndView modelAndView = new ModelAndView("index");
        //modelAndView.addObject("fileInfoList", fileInfoList);
        return modelAndView;
    }
    //工人管理页面
    @RequestMapping("/manage_workers.html")
    public ModelAndView manage_workers(){
        ModelAndView modelAndView = new ModelAndView("manage_workers");
        return modelAndView;
    }
    @RequestMapping("/iframe/workers.html")
    public ModelAndView iframe_workers(){
        ModelAndView modelAndView = new ModelAndView("./iframe/workers");
        return modelAndView;
    }

    //计划管理页面
    @RequestMapping("/monitor_plan.html")
    public ModelAndView monitor_plan(){
        ModelAndView modelAndView = new ModelAndView("monitor_plan");
        return modelAndView;
    }
    @RequestMapping("/iframe/plan.html")
    public ModelAndView iframe_plan(){
        ModelAndView modelAndView = new ModelAndView("./iframe/plan");
        return modelAndView;
    }

    //批次派工页面
    @RequestMapping("/arrange_batchs.html")
    public ModelAndView arrange_batchs(){
        ModelAndView modelAndView = new ModelAndView("arrange_batchs");
        return modelAndView;
    }
    @RequestMapping("/iframe/arrange_batch.html")
    public ModelAndView iframe_arrange_batch(){
        ModelAndView modelAndView = new ModelAndView("./iframe/arrange_batch");
        return modelAndView;
    }
    //单元派工页面
    @RequestMapping("/arrange_units.html")
    public ModelAndView arrange_units(){
        ModelAndView modelAndView = new ModelAndView("arrange_units");
        return modelAndView;
    }
    @RequestMapping("/iframe/arrange_unit.html")
    public ModelAndView iframe_arrange_unit(){
        ModelAndView modelAndView = new ModelAndView("./iframe/arrange_unit");
        return modelAndView;
    }

    //派工记录页面
    @RequestMapping("/arrange_records.html")
    public ModelAndView arrange_records(){
        ModelAndView modelAndView = new ModelAndView("arrange_records");
        return modelAndView;
    }
    @RequestMapping("/iframe/arrange_record.html")
    public ModelAndView iframe_arrange_record(){
        ModelAndView modelAndView = new ModelAndView("./iframe/arrange_record");
        return modelAndView;
    }
}
