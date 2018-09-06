package llcweb.controller;

import llcweb.domain.entities.DepartmentTree;
import llcweb.domain.models.Users;
import llcweb.service.DepartmentsService;
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
    @Autowired
    private DepartmentsService departmentsService;

    @RequestMapping("/test.html")
    public ModelAndView test(){

        List<String> learnList =new ArrayList<>();
        learnList.add("hello1");
        learnList.add("hello2");
        ModelAndView modelAndView = new ModelAndView("test");
        modelAndView.addObject("learnList", learnList);

        return modelAndView;
    }
    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");
        Users users = usersService.getCurrentUser();
        modelAndView.addObject("user", users);
        return modelAndView;
    }
    @RequestMapping("/index.html")
    public ModelAndView index1(){
        ModelAndView modelAndView = new ModelAndView("index");
        Users users = usersService.getCurrentUser();
        modelAndView.addObject("user", users);
        return modelAndView;
    }
    @RequestMapping("/index")
    public ModelAndView index2(){

        ModelAndView modelAndView = new ModelAndView("index");
        Users users = usersService.getCurrentUser();
        modelAndView.addObject("user", users);
        return modelAndView;
    }
    @RequestMapping("/main")
    public ModelAndView main(){
        ModelAndView modelAndView = new ModelAndView("index");
        Users users = usersService.getCurrentUser();
        modelAndView.addObject("user", users);
        return modelAndView;
    }
    //工人管理页面
    @RequestMapping("/manage_workers.html")
    public ModelAndView manage_workers(){
        ModelAndView modelAndView = new ModelAndView("manage_workers");
        Users users = usersService.getCurrentUser();
        modelAndView.addObject("user", users);
        return modelAndView;
    }
    @RequestMapping("/iframe/workers.html")
    public ModelAndView iframe_workers(){
        ModelAndView modelAndView = new ModelAndView("./iframe/workers");
        //iframe级别的不能获取登录用户，其值为null,为什么??
//        Users users = usersService.getCurrentUser();
//        modelAndView.addObject("user", users);
        return modelAndView;
    }

    //计划管理页面
    @RequestMapping("/monitor_plan.html")
    public ModelAndView monitor_plan(){
        ModelAndView modelAndView = new ModelAndView("monitor_plan");
        Users users = usersService.getCurrentUser();
        modelAndView.addObject("user", users);
        return modelAndView;
    }
    @RequestMapping("/iframe/plan.html")
    public ModelAndView iframe_plan(){
        ModelAndView modelAndView = new ModelAndView("./iframe/plan");
//        Users users = usersService.getCurrentUser();
//        modelAndView.addObject("user", users);
        return modelAndView;
    }
    //批次监控页面
    @RequestMapping("/monitor_batchs.html")
    public ModelAndView monitor_batchs(){
        ModelAndView modelAndView = new ModelAndView("monitor_batchs");
        Users users = usersService.getCurrentUser();
        modelAndView.addObject("user", users);
        return modelAndView;
    }
    //单元监控页面
    @RequestMapping("/monitor_units.html")
    public ModelAndView monitor_units(){
        ModelAndView modelAndView = new ModelAndView("monitor_units");
        Users users = usersService.getCurrentUser();
        modelAndView.addObject("user", users);
        return modelAndView;
    }
    //管件监控页面
    @RequestMapping("/monitor_pipes.html")
    public ModelAndView monitor_pipes(){
        ModelAndView modelAndView = new ModelAndView("monitor_pipes");
        Users users = usersService.getCurrentUser();
        modelAndView.addObject("user", users);
        return modelAndView;
    }
    //批次派工页面
    @RequestMapping("/arrange_batchs.html")
    public ModelAndView arrange_batchs(){
        ModelAndView modelAndView = new ModelAndView("arrange_batchs");
        Users users = usersService.getCurrentUser();
        modelAndView.addObject("user", users);
        return modelAndView;
    }
    @RequestMapping("/iframe/arrange_batch.html")
    public ModelAndView iframe_arrange_batch(){
        ModelAndView modelAndView = new ModelAndView("./iframe/arrange_batch");
//        Users users = usersService.getCurrentUser();
//        modelAndView.addObject("user", users);
        return modelAndView;
    }
    //单元派工页面
    @RequestMapping("/arrange_units.html")
    public ModelAndView arrange_units(){
        ModelAndView modelAndView = new ModelAndView("arrange_units");
        Users users = usersService.getCurrentUser();
        List<DepartmentTree> departmentTreeList = departmentsService.getDepartmentTree(users,true);

        modelAndView.addObject("user", users);
        modelAndView.addObject("treeList",departmentTreeList);
        return modelAndView;
    }
    @RequestMapping("/iframe/arrange_unit.html")
    public ModelAndView iframe_arrange_unit(){
        ModelAndView modelAndView = new ModelAndView("./iframe/arrange_unit");
//        Users users = usersService.getCurrentUser();
//        modelAndView.addObject("user", users);
        return modelAndView;
    }

    //派工记录页面
    @RequestMapping("/arrange_records.html")
    public ModelAndView arrange_records(){
        ModelAndView modelAndView = new ModelAndView("arrange_records");
        Users users = usersService.getCurrentUser();
        modelAndView.addObject("user", users);
        return modelAndView;
    }
    @RequestMapping("/iframe/arrange_record.html")
    public ModelAndView iframe_arrange_record(){
        ModelAndView modelAndView = new ModelAndView("./iframe/arrange_record");
//        Users users = usersService.getCurrentUser();
//        modelAndView.addObject("user", users);
        return modelAndView;
    }

    //派工记录页面
    @RequestMapping("/manage_department.html")
    public ModelAndView manage_department(){
        ModelAndView modelAndView = new ModelAndView("manage_department");

        Users users = usersService.getCurrentUser();
        List<DepartmentTree> departmentTreeList = departmentsService.getDepartmentTree(users,false);

        modelAndView.addObject("user", users);
        modelAndView.addObject("treeList",departmentTreeList);
        return modelAndView;
    }
    //派工记录页面
    @RequestMapping("/online_cut.html")
    public ModelAndView online_cut(){
        ModelAndView modelAndView = new ModelAndView("online_cut");
        Users users = usersService.getCurrentUser();
        modelAndView.addObject("user", users);
        return modelAndView;
    }
    //派工记录页面
    @RequestMapping("/iframe/taoliao.html")
    public ModelAndView taoliao(){
        ModelAndView modelAndView = new ModelAndView("/iframe/taoliao");

        return modelAndView;
    }
}
