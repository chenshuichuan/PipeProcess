package llcweb.controller;

import llcweb.dao.repository.PlanTableRepository;
import llcweb.dao.repository.ShipTableRepository;
import llcweb.dao.repository.WorkersRepository;
import llcweb.domain.models.PlanTable;
import llcweb.domain.models.ShipTable;
import llcweb.domain.models.Workers;
import llcweb.service.PlanTableService;
import llcweb.service.UsersService;
import llcweb.service.WorkersService;
import llcweb.tools.DateUtil;
import llcweb.tools.PageParam;
import llcweb.tools.StringUtil;
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
 * plan表的相关数据接口文件
 */

@RestController
@RequestMapping("/plan")
public class PlanController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UsersService usersService;
    @Autowired
    private PlanTableService planTableService;
    @Autowired
    private PlanTableRepository planTableRepository;
    @Autowired
    private ShipTableRepository shipTableRepository;


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

//       //获取客户端需要那一列排序
//        String orderColumn = request.getParameter("orderColumn");
//        if(orderColumn == null){
//            orderColumn = "serialNumber";
//        }
//        //获取排序方式 默认为asc
//        String orderDir = request.getParameter("orderDir");
//        if(orderDir == null){
//            orderDir = "asc";
//        }
        PlanTable planTable = new PlanTable();
        String fuzzy = request.getParameter("fuzzySearch");
        if("true".equals(fuzzy)){//模糊查找
            String searchValue = request.getParameter("fuzzy");
            if (searchValue!=null&&!searchValue.equals("")) {
                planTable.setPlanName(searchValue);
                planTable.setShipName(searchValue);
                planTable.setBatchName(searchValue);
                planTable.setBatchDescription(searchValue);
                planTable.setStocks(searchValue);
                planTable.setSections(searchValue);
                planTable.setProcessPlace(searchValue);
                planTable.setIsCutted(-1);
            }
        }
       else{
            //工段
            String processPlace = request.getParameter("processPlace");
            if (processPlace!=null&&!processPlace.equals("")) {
                planTable.setProcessPlace(processPlace);
            }
            //船号
            String shipCode = request.getParameter("shipCode");
            if (shipCode!=null&&!shipCode.equals("")) {
                ShipTable shipTable = shipTableRepository.findByShipCode(shipCode);
                if (shipTable!=null) planTable.setShipName(shipTable.getShipName());
            }
            //下料状态
            String isCutted = request.getParameter("isCutted");
            if (isCutted!=null&&!isCutted.equals("")) {
                planTable.setIsCutted(Integer.parseInt(isCutted));
            }
        }

        Page<PlanTable> planPage = planTableService.getPage(new PageParam(currentPage,size),planTable);
        List<PlanTable> planTableList = planPage.getContent();
        //总记录数
        long total = planPage.getTotalElements();
        map.put("pageData", planTableList);
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

        PlanTable planTable = planTableRepository.findOne(id);
        if (planTable!=null) {
            planTableRepository.delete(planTable);
            String message ="成功删除序号："+planTable.getSerialNumber()+"的计划信息！";
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

    //更新数据
    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,Object> update(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();

        String serialNumber = request.getParameter("serialNumber");
        String planName = request.getParameter("planName");
        String batchName = request.getParameter("batchName");
        String processPlace = request.getParameter("processPlace");
        String lightBodyPipe = request.getParameter("lightBodyPipe");
        String planStart = request.getParameter("planStart");
        String planEnd = request.getParameter("planEnd");
        String sendPicTime = request.getParameter("sendPicTime");
        //String oneBCutNum = request.getParameter("oneBCutNum");
        String oneBendCut = request.getParameter("oneBendCut");
        String oneVerCut = request.getParameter("oneVerCut");
        String oneBigCut = request.getParameter("oneBigCut");
        String twoSpeBendCut = request.getParameter("twoSpeBendCut");
        String twoSpeVerCut = request.getParameter("twoSpeVerCut");
        String stocks = request.getParameter("stocks");
        String sections = request.getParameter("sections");

        Date startDate = DateUtil.StringTodate(planStart);
        Date endDate = DateUtil.StringTodate(planEnd);
        Date sendDate = DateUtil.StringTodate(sendPicTime);
        //如果传入的日期字符非空，日期不可以为空
        if((planStart!=null&&planStart.length()>0&&startDate==null)
                ||(planEnd!=null&&planEnd.length()>0&&endDate==null)
                ||(sendPicTime!=null&&sendPicTime.length()>0&&sendDate==null)){
            String message = "时间数据不符合格式！请检查数据";
            map.put("result",0);
            map.put("message",message);
            logger.info(message);
            return map;
        }
        //应该增加计划开始时间,计划结束时间，数量，是否完成，这些修改
        PlanTable planTable = planTableRepository.findByBatchNameAndProcessPlace(batchName,processPlace);
        if (planTable!=null&&Integer.parseInt(serialNumber)==planTable.getSerialNumber()) {

            planTable.setSerialNumber(Integer.parseInt(serialNumber));
            planTable.setPlanName(planName);
            planTable.setBatchName(batchName);
            //des
            planTable.setProcessPlace(processPlace);
            planTable.setLightBodyPip(Integer.parseInt(lightBodyPipe));
            planTable.setPlanStart(startDate);
            planTable.setPlanEnd(endDate);
            planTable.setSentPicTime(sendDate);
            //应该做完整的验证
//            if(oneBcutNum!=null&&oneBcutNum.length()>0){
//                if(StringUtil.isPositiveInt(oneBcutNum)){
//                }
//            }
//            if(oneBCutNum!=null&&oneBCutNum.length()>0)planTable.setOneBcutNum(Integer.parseInt(oneBCutNum));
//            else planTable.setOneBcutNum(0);
            int totalNum =0;
            if(oneBendCut!=null&&oneBendCut.length()>0){
                totalNum+=Integer.parseInt(oneBendCut);
                planTable.setOneBendCut(Integer.parseInt(oneBendCut));
            }
            else planTable.setOneBendCut(0);
            if(oneVerCut!=null&&oneVerCut.length()>0){
                totalNum+=Integer.parseInt(oneVerCut);
                planTable.setOneVerCut(Integer.parseInt(oneVerCut));
            }
            else planTable.setOneVerCut(0);
            if(oneBigCut!=null&&oneBigCut.length()>0){
                totalNum+=Integer.parseInt(oneBigCut);
                planTable.setOneBigCut(Integer.parseInt(oneBigCut));
            }
            else planTable.setOneBigCut(0);
            planTable.setOneBcutNum(0);

            if(twoSpeBendCut!=null&&twoSpeBendCut.length()>0)planTable.setTwoSpeBendCut(Integer.parseInt(twoSpeBendCut));
            else planTable.setTwoSpeBendCut(0);
            if(twoSpeVerCut!=null&&twoSpeVerCut.length()>0)planTable.setTwoSpeVerCut(Integer.parseInt(twoSpeVerCut));
            else planTable.setTwoSpeVerCut(0);


            planTable.setStocks(stocks);
            planTable.setSections(sections);
            planTable.setUpldateTime(new Date());
            planTable =  planTableRepository.save(planTable);
            if(planTable!=null){
                String message ="成功更改序号："+planTable.getSerialNumber()+"的信息";
                map.put("result",1);
                map.put("message",message);
                logger.info(message);
            }
            else{
                String message = "更改序号："+serialNumber+"的信息失败！请检查数据";
                map.put("result",0);
                map.put("message",message);
                logger.info(message);
            }
        }
        else{
            String message = "更改序号："+serialNumber+"的信息失败！请检查数据";
            map.put("result",0);
            map.put("message",message);
            logger.info(message);
        }
        return map;
    }

    //添加单条数据的接口暂时不写，计划信息都是用excel上传的

    //
}
