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

    @RequestMapping("/test.html")
    public ModelAndView test(@RequestParam("name")String modelName){

        System.out.println("modelName="+modelName);
        List<String> learnList =new ArrayList<>();
        learnList.add("hello1");
        learnList.add("hello2");
        ModelAndView modelAndView = new ModelAndView("test");
        modelAndView.addObject("learnList", learnList);

        return modelAndView;
    }
}
