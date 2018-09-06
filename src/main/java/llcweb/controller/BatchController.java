package llcweb.controller;

import llcweb.dao.repository.BatchTableRepository;
import llcweb.dao.repository.ShipTableRepository;
import llcweb.domain.entities.BatchProcessInfo;
import llcweb.domain.models.BatchTable;
import llcweb.domain.models.ShipTable;
import llcweb.service.BatchTableService;
import llcweb.service.ShipTableService;
import llcweb.tools.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tengj on 2017/4/10.
 */

@RestController
@RequestMapping("/batch")
public class BatchController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ShipTableService shipTableService;
    @Autowired
    private ShipTableRepository shipTableRepository;
    @Autowired
    private BatchTableService batchTableService;
    @Autowired
    private BatchTableRepository batchTableRepository;

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();

        BatchTable batchTable = new BatchTable();
        batchTable.setBatchName("");
        Page<BatchTable> batchTablePage = batchTableService.getPage(new PageParam(1,10),batchTable);
        map.put("page",batchTablePage.getContent());
        return map;
    }

    //获取shipCode下的所有批次名列表
    @RequestMapping(value = "/getBatchNameByShipCode",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getBatchNameByShipCode(@RequestParam("shipCode")String shipCode){
        Map<String,Object> map =new HashMap<String,Object>();

        List<BatchTable> batchTableList =  batchTableRepository.findByShipCode(shipCode);
        List<String> batchNameList = new ArrayList<>();
        for (BatchTable batchTable:batchTableList){
            batchNameList.add(batchTable.getBatchName());
        }
        map.put("data",batchNameList);
        map.put("result",1);
        map.put("message","");
        logger.info("");
        return map;
    }
    //获取shipCode下的所有批次列表
    @RequestMapping(value = "/getBatchByShipCode",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getBatchByShipCode(@RequestParam("shipCode")String shipCode){
        Map<String,Object> map =new HashMap<String,Object>();

        List<BatchTable> batchTableList =  batchTableRepository.findByShipCode(shipCode);
        map.put("data",batchTableList);
        map.put("result",1);
        map.put("message","");
        logger.info("");
        return map;
    }

    /**
     *@Author: Ricardo
     *@Description: 获取批次监控的table数据
     *@Date: 18:26 2018/9/6
     *@param:
     **/
    @RequestMapping(value = "/processInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> processInfo(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();

        //直接返回前台
        String draw = request.getParameter("draw");
        //是否是加工完成的批次
        String isFinishedStr = request.getParameter("isFinished");
        //船
        String shipCode = request.getParameter("shipCode");
        logger.info("shiCode="+shipCode+",isfinished="+isFinishedStr);
        int isFinished =-1;
        if (isFinishedStr!=null&&!isFinishedStr.equals("")) {
            isFinished= Integer.parseInt(isFinishedStr);
            if (isFinished!=0&&isFinished!=1)isFinished=-1;
        }
        ShipTable shipTable = new ShipTable() ;
        if (shipCode!=null&&!shipCode.equals("")){
            shipTable = shipTableRepository.findByShipCode(shipCode);
            if(shipTable!=null){
            } else{
                map.put("result", 0);
                map.put("message","参数错误！");
                logger.error("查找错误！数据库不存在该信息！shipcode = "+shipCode);
            }
        }
        else{
            List<ShipTable> shipTableList= shipTableRepository.findAll();
            if (shipTableList.size()>0)shipTable=shipTableList.get(0);
            else {
                map.put("result", 0);
                map.put("message","参数错误！");
                logger.error("查找错误！数据库不存在产信息！shipcode = "+shipCode);
            }
        }
        List<BatchProcessInfo>batchProcessInfoList = batchTableService.getBatchProcessInfo(shipTable,isFinished);
        map.put("data", batchProcessInfoList);
        map.put("draw", draw);
        return map;
    }
}
