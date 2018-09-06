package llcweb.controller;

import llcweb.dao.repository.BatchTableRepository;
import llcweb.dao.repository.ShipTableRepository;
import llcweb.domain.entities.ProcessInfo;
import llcweb.domain.entities.ShipProcessInfo;
import llcweb.domain.models.BatchTable;
import llcweb.domain.models.Departments;
import llcweb.domain.models.ShipTable;
import llcweb.domain.models.Users;
import llcweb.service.BatchTableService;
import llcweb.service.ShipTableService;
import llcweb.service.UnitTableService;
import llcweb.service.UsersService;
import llcweb.tools.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/ship")
public class ShipController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ShipTableService shipTableService;
    @Autowired
    private ShipTableRepository shipTableRepository;

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> page(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();

        ShipTable shipTable = new ShipTable();
        shipTable.setShipName("");
        map.put("page",shipTableService.getPage(new PageParam(1,10),shipTable));
        return map;
    }
    //获取所有船名和shipCode
    @RequestMapping(value = "/getAllShip",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAllShip(){
        Map<String,Object> map =new HashMap<String,Object>();
        List<ShipTable> shipTableList =  shipTableService.getAllShipNameByState(-1);
        map.put("data",shipTableList);
        map.put("message","ok");
        map.put("result",1);
        return map;
    }
    //获取当前未完工的船名和shipCode
    @RequestMapping(value = "/getAllUnfinishedShip",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getUserManageSections(){
        Map<String,Object> map =new HashMap<String,Object>();
        List<ShipTable> shipTableList =  shipTableService.getAllShipNameByState(0);
        map.put("data",shipTableList);
        return map;
    }

    //获取shipCode下的所有批次
    @RequestMapping(value = "/getBatchNameByShipCode",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getBatchNameByShipCode(@RequestParam("shipCode")String shipCode){
        Map<String,Object> map =new HashMap<String,Object>();

        List<ShipTable> shipTableList =  shipTableService.getAllShipNameByState(0);
        map.put("data",shipTableList);
        map.put("result",0);
        map.put("message","");
        logger.info("");
        return map;
    }
    /**
     *@Author: Ricardo
     *@Description: 获取首页船舶加工进度table数据
     *@Date: 18:26 2018/9/6
     *@param:
     **/
    @RequestMapping(value = "/processingInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> processingInfo(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();

        //直接返回前台
        String draw = request.getParameter("draw");
        //是否是加工完成的船
        String isFinishedStr = request.getParameter("isFinished");
        int isFinished =0;
        if (isFinishedStr!=null&&!isFinishedStr.equals("")) {
            isFinished= Integer.parseInt(isFinishedStr);
            List<ShipTable>shipTableList = new ArrayList<>();
            if(isFinished>=-1&&isFinished<=1){
                shipTableList = shipTableService.getAllShipNameByState(isFinished);
                List<ShipProcessInfo> shipProcessInfoList = shipTableService.getShipProcessInfo(shipTableList);

                map.put("data",shipProcessInfoList);
                map.put("result", 1);
                map.put("message","获取成功！");
            }
            else {
                map.put("result", 0);
                map.put("message","参数错误！");
                logger.error("参数错误！");
            }
        }
        //返回全部船数据
        else{
            map.put("result", 0);
            map.put("message","参数错误！");
            logger.error("参数错误！");
        }
        map.put("draw", draw);
        return map;
    }
}
