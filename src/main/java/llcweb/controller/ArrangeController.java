package llcweb.controller;

import llcweb.dao.repository.ArrangeTableRepository;
import llcweb.dao.repository.DepartmentsRepository;
import llcweb.dao.repository.PlanTableRepository;
import llcweb.dao.repository.ShipTableRepository;
import llcweb.domain.models.ArrangeTable;
import llcweb.domain.models.Departments;
import llcweb.domain.models.PlanTable;
import llcweb.domain.models.ShipTable;
import llcweb.service.ArrangeTableService;
import llcweb.service.DepartmentsService;
import llcweb.service.PlanTableService;
import llcweb.service.UsersService;
import llcweb.tools.DateUtil;
import llcweb.tools.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 派工相关数据接口文件
 */

@RestController
@RequestMapping("/arrange")
public class ArrangeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UsersService usersService;
    @Autowired
    private ArrangeTableService arrangeTableService;
    @Autowired
    private ArrangeTableRepository arrangeTableRepository;
    @Autowired
    private PlanTableRepository planTableRepository;
    @Autowired
    private ShipTableRepository shipTableRepository;
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
        int size = Integer.parseInt(pageSize);
        int currentPage = Integer.parseInt(startIndex)/size+1;

        ArrangeTable arrangeTable = new ArrangeTable();
        String fuzzy = request.getParameter("fuzzySearch");
        if("true".equals(fuzzy)){//模糊查找
            String searchValue = request.getParameter("fuzzy");
            if (searchValue!=null&&!searchValue.equals("")) {

            }
        }
       else{
            //工段
            String processPlace = request.getParameter("processPlace");
            if (processPlace!=null&&!processPlace.equals("")) {
                //planTable.setProcessPlace(processPlace);
            }

        }
        Page<ArrangeTable> arrangeTablePage = arrangeTableService.getPage(new PageParam(currentPage,size),arrangeTable);
        List<ArrangeTable> arrangeTableList = arrangeTablePage.getContent();
        //总记录数
        long total = arrangeTablePage.getTotalElements();
        map.put("pageData", arrangeTableList);
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
        ArrangeTable arrangeTable = arrangeTableRepository.findOne(id);
        if (arrangeTable!=null) {
            planTableRepository.delete(id);
            String message ="成功删除"+arrangeTable.getName()+arrangeTable.getWorkplace()+"的派工信息！";
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

    //将批次下料派工到工位
    @RequestMapping(value = "/arrangeBatchToWorkPlace")
    @ResponseBody
    public Map<String,Object> arrangeBatchToWorkPlace(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();

        String planId = request.getParameter("planId");
        String workPlaceId = request.getParameter("workPlaceId");
        String remark = request.getParameter("remark");
        String message ="";
        int result = 1;
        if(planId!=null&&planId.length()>0&&workPlaceId!=null&&workPlaceId.length()>0){
            PlanTable planTable = planTableRepository.findOne(Integer.parseInt(planId));
            Departments workPlace = departmentsRepository.findOne(Integer.parseInt(workPlaceId));
            if(planTable==null||workPlace==null){
                message = "计划"+planId+"或工位"+workPlaceId+"不存在！请检查！";
                result=0;
            }
            //派工
            else{

            }
        }
        else{
            message = "传入参数错误！计划"+planId+"或工位"+workPlaceId+"请检查！";
            result=0;
        }
        map.put("result",result);
        map.put("message",message);
        logger.info(message);
        return map;
    }


    //将单元派工到工位
    @RequestMapping(value = "/arrangeUnitToWorkPlace")
    @ResponseBody
    public Map<String,Object> arrangeUnitToWorkPlace(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();

        String unitId = request.getParameter("unitId");
        String workPlaceId = request.getParameter("workPlaceId");
        String remark = request.getParameter("remark");
        String message ="";
        int result = 1;
        if(unitId!=null&&unitId.length()>0&&workPlaceId!=null&&workPlaceId.length()>0){
            PlanTable planTable = planTableRepository.findOne(Integer.parseInt(unitId));
            Departments workPlace = departmentsRepository.findOne(Integer.parseInt(workPlaceId));
            if(planTable==null||workPlace==null){
                message = "单元"+unitId+"或工位"+workPlaceId+"不存在！请检查！";
                result=0;
            }
            //单元派工
            else{

            }
        }
        else{
            message = "传入参数错误！单元"+unitId+"或工位"+workPlaceId+"请检查！";
            result=0;
        }
        map.put("result",result);
        map.put("message",message);
        logger.info(message);
        return map;
    }

}
