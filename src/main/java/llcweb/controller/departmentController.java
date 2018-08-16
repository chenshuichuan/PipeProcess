package llcweb.controller;

import llcweb.dao.repository.UnitTableRepository;
import llcweb.domain.entities.Units;
import llcweb.domain.models.UnitTable;
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
public class departmentController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UsersService usersService;
    @Autowired
    private UnitTableService unitTableService;
    @Autowired
    private UnitTableRepository unitTableRepository;


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
        UnitTable unitTable = new UnitTable();

        Page<UnitTable> unitTablePage = unitTableService.getPage(new PageParam(currentPage,size),unitTable);
        List<UnitTable> unitTableList = unitTablePage.getContent();
        //总记录数
        long total = unitTablePage.getTotalElements();
        map.put("pageData", unitTableList);
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

        UnitTable unitTable = unitTableRepository.findOne(id);
        if (unitTable!=null) {
            unitTableRepository.delete(unitTable);
            String message ="成功删除批次："+unitTable.getBatchName()+"的:"+unitTable.getUnitName()+"单元！";
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

    //更新数据
    @RequestMapping(value = "/getUnitsByPlanId",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getUnitsByPlanId(HttpServletRequest request, HttpServletResponse response
    ,@RequestParam("planId")int planId){
        Map<String,Object> map =new HashMap<String,Object>();
        List<Units> unitsList = unitTableService.findUnitsByPlanId(planId);
        map.put("data",unitsList);
        //String message = "更改序号的信息失败！请检查数据";
        map.put("result",0);
        map.put("message","");
        logger.info("");

        return map;
    }
}
