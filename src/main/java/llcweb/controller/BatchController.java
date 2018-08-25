package llcweb.controller;

import llcweb.dao.repository.BatchTableRepository;
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
}
