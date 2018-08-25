package llcweb.controller;

import llcweb.domain.models.ShipTable;
import llcweb.service.ShipTableService;
import llcweb.tools.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tengj on 2017/4/10.
 */

@RestController
@RequestMapping("/ship")
public class BatchController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ShipTableService shipTableService;

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();

        ShipTable shipTable = new ShipTable();
        shipTable.setShipName("");
        map.put("page",shipTableService.getPage(new PageParam(1,10),shipTable));
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
}
