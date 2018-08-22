package llcweb.controller;

import llcweb.domain.models.Departments;
import llcweb.domain.models.ShipTable;
import llcweb.domain.models.Users;
import llcweb.service.ShipTableService;
import llcweb.service.UsersService;
import llcweb.tools.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
public class ShipController {
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
}
