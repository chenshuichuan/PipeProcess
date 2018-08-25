package llcweb.controller;

import llcweb.dao.repository.*;
import llcweb.domain.entities.ArrangeRecord;
import llcweb.domain.models.*;
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
    @Autowired
    private WorkersRepository workersRepository;

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
        //高级查找
       else{
            //工段
            String section = request.getParameter("section");
            if (section!=null&&!section.equals("")) {
               arrangeTable.setSection(section);
            }
            //工序
            String stage = request.getParameter("stage");
            if (stage!=null&&!stage.equals("")) {
                arrangeTable.setStage(stage);
            }
            //船名
            String shipCode = request.getParameter("shipCode");
            if (shipCode!=null&&!shipCode.equals("")) {
                //arrangeTable.setSection(section);
            }
            //批次
            String batch = request.getParameter("batch");
            if (batch!=null&&!batch.equals("")) {
                arrangeTable.setName(batch);//name里面包含batch字段
            }
            //派工类别
            String arrangeType = request.getParameter("arrangeType");
            if (arrangeType!=null&&!arrangeType.equals("")) {
                arrangeTable.setArrangeType(Integer.parseInt(arrangeType));
            }
            //是否完工
            String isFinished = request.getParameter("isFinished");
            if (isFinished!=null&&!isFinished.equals("")) {
                arrangeTable.setIsFinished(Integer.parseInt(isFinished));
            }
            //派工时间
            String updateTime = request.getParameter("updateTime");
            if (updateTime!=null&&!updateTime.equals("")) {
                //arrangeTable.setSection(section);
            }
            //工人姓名这个可能会存在两个同名工人？
            //首先按照工号(唯一)去查，查不到则查姓名
            String worker = request.getParameter("worker");
            if (worker!=null&&!worker.equals("")) {
                List<Workers> workersList = workersRepository.findByNameOrCode(worker,worker);
                if (workersList.size()>0)arrangeTable.setWorkerId(workersList.get(0).getId());
            }

        }
        Page<ArrangeTable> arrangeTablePage = arrangeTableService.getPage(new PageParam(currentPage,size),arrangeTable);
        List<ArrangeRecord> arrangeRecordList =
                arrangeTableService.arrangeTableToArrangeRecord(arrangeTablePage.getContent());

        //总记录数
        long total = arrangeTablePage.getTotalElements();
        map.put("pageData", arrangeRecordList);
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
