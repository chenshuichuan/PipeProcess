package llcweb.controller;

import llcweb.dao.repository.DepartmentsRepository;
import llcweb.dao.repository.UnitTableRepository;
import llcweb.domain.entities.DepartmentInfo;
import llcweb.domain.entities.Units;
import llcweb.domain.models.Departments;
import llcweb.domain.models.UnitTable;
import llcweb.service.DepartmentsService;
import llcweb.service.UnitTableService;
import llcweb.service.UsersService;
import llcweb.tools.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * departments表的相关数据接口文件
 */

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UsersService usersService;
    @Autowired
    private DepartmentsService departmentsService;
    @Autowired
    private DepartmentsRepository departmentsRepository;


    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> page(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();

        //直接返回前台
        String draw = request.getParameter("draw");
        //当前数据的起始位置 ，如第10条
        String startIndex = request.getParameter("startIndex");
        //数据长度
        String pageSize = request.getParameter("pageSize");

       //获取客户端需要的那一列排序
        String orderColumn = request.getParameter("orderColumn");
        if(orderColumn == null){
            orderColumn = "serialNumber";
        }
        //获取排序方式 默认为asc
        String orderDir = request.getParameter("orderDir");
        if(orderDir == null){
            orderDir = "asc";
        }
        int size = Integer.parseInt(pageSize);
        int currentPage = Integer.parseInt(startIndex)/size+1;
        Departments departments = new Departments();

        Page<Departments> departmentsPage = departmentsService.getPage(new PageParam(currentPage,size),departments);
        List<Departments> departmentsList = departmentsPage.getContent();
        //总记录数
        long total = departmentsPage.getTotalElements();
        map.put("pageData", departmentsList);
        map.put("total", total);
        map.put("draw", draw);
        return map;
    }


    //根据id删除信息
    @RequestMapping(value = "/deleteById",method = RequestMethod.GET)
    public Map<String,Object> deleteById(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("id")int id){
        Map<String,Object> map =new HashMap<String,Object>();

        Departments departments = departmentsRepository.findOne(id);
        if (departments!=null) {
            departmentsRepository.delete(departments);
            String message ="成功删除："+departments.getName();
            map.put("result",1);
            map.put("message",message);
            logger.info(message);
        }
        else{
            String message = "删除信息失败！请检查数据";
            map.put("result",0);
            map.put("message",message);
            logger.info(message);
        }
        return map;
    }

//    //更新数据
//    @RequestMapping(value = "/update")
//    @ResponseBody
//    public Map<String,Object> update(HttpServletRequest request, HttpServletResponse response){
//        Map<String,Object> map =new HashMap<String,Object>();
//
//        String message = "更改序号的信息失败！请检查数据";
//        map.put("result",0);
//        map.put("message",message);
//        logger.info(message);
//
//        return map;
//    }

    //根据id查找info信息
    @RequestMapping(value = "/getDepartmentInfoById",method = RequestMethod.GET)
    public Map<String,Object> getDepartmentInfoById(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("id")int id){
        Map<String,Object> map =new HashMap<String,Object>();

        DepartmentInfo departmentInfo = departmentsService.getDepartmentInfo(id);
        if (departmentInfo!=null) {

            String message ="成功找到："+departmentInfo.getName();
            map.put("result",1);
            map.put("data",departmentInfo);
            map.put("message",message);
            logger.info(message);
        }
        else{
            String message = "未找到相关信息！请检查数据";
            map.put("result",0);
            map.put("data",null);
            map.put("message",message);
            logger.info(message);
        }
        return map;
    }
}
