package llcweb.controller;

import llcweb.dao.repository.PipeProcessingRepository;
import llcweb.dao.repository.PlanTableRepository;
import llcweb.dao.repository.ScannerTableRepository;
import llcweb.dao.repository.ShipTableRepository;
import llcweb.domain.models.*;
import llcweb.jacking.BatchJacking;
import llcweb.jacking.BatchJackingResult;
import llcweb.jacking.PipeCutingItem;
import llcweb.jacking.PipeLength;
import llcweb.service.ArrangeTableService;
import llcweb.service.PlanTableService;
import llcweb.service.TaoliaoService;
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
import java.util.*;

/**
 * taoliao表的相关数据接口文件
 * taoliao表是专门用于显示套料管材列表的表格，以提高套料页面显示速度
 */

@RestController
@RequestMapping("/taoliao")
public class TaoliaoController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UsersService usersService;
    @Autowired
    private TaoliaoService taoliaoService;
    @Autowired
    private PlanTableRepository planTableRepository;
    @Autowired
    private ShipTableRepository shipTableRepository;
    @Autowired
    private ScannerTableRepository scannerTableRepository;
    @Autowired
    private ArrangeTableService arrangeTableService;
    @Autowired
    private PipeProcessingRepository pipeProcessingRepository;

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

        String planId = request.getParameter("planId");
        String isTaoliao =request.getParameter("isTaoliao");
        Taoliao taoliao = new Taoliao();
        if(planId!=null&&planId.length()>0){
            taoliao.setPlanId(Integer.parseInt(planId));
        }
        else{
            //获取下料未完成的批次派工
            List<ArrangeTable> arrangeTableList = arrangeTableService.getUsersArrangeTable(1,0);
            //默认列表中的第一个plan下料 派工
            if(arrangeTableList.size()>0)taoliao.setPlanId(arrangeTableList.get(0).getPlanId());
        }
        if(isTaoliao!=null&&isTaoliao.length()>0){ //默认查找全部
            taoliao.setPlanId(Integer.parseInt(isTaoliao));
        }

        Page<Taoliao> taoliaoPage = taoliaoService.getPage(new PageParam(currentPage,size),taoliao);
        List<Taoliao> taoliaoList= taoliaoPage.getContent();
        //总记录数
        long total = taoliaoPage.getTotalElements();
        map.put("pageData", taoliaoList);
        map.put("total", total);
        map.put("draw", draw);
        return map;
    }


    //更新数据
    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,Object> update(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();


        return map;
    }

    //根据当前登录用户，获取其可以查看的下料计划批次名
    @RequestMapping(value = "/getUsersArrangeTable")
    @ResponseBody
    public Map<String,Object> getPlanBatch(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam("arrangeType")int arrangeType,
                                           @RequestParam("isFinished")int isFinished){
        Map<String,Object> map =new HashMap<String,Object>();

        //获取下料未完成的批次派工
        List<ArrangeTable> arrangeTableList = arrangeTableService.getUsersArrangeTable(arrangeType,isFinished);
        //这里总觉得有问题
        map.put("data",arrangeTableList);
        map.put("result",1);
        map.put("message","获取成功");
        return map;
    }
    /**
     * 返回套料结果
     * @taoliaoId:根据taoliaoId去获取要套料的plan batch 下的管件下料表
     * @pipeLengthList：表单获取的待下料管件信息
     */
    @RequestMapping(value = "/returnJackingResult")
    @ResponseBody
    public Map<String,Object> returnJackingResult(HttpServletRequest request, HttpServletResponse response,
                                                  @RequestParam("taoliaoId") int taoliaoId,
                                                  @RequestBody List<PipeLength> pipeLengthList){
        Map<String,Object> map =new HashMap<String,Object>();
        try {

            ArrayList<Integer> pipeLength = new ArrayList<>();// 测量管件长度信息
            ArrayList<PipeCutingItem> pipeCutingList = new ArrayList<>();// 下料表里的信息

            // 获取测量的管件长度
            for (int i = 0; i < pipeLengthList.size(); i++) {
                pipeLength.add(pipeLengthList.get(i).getLength());
            }

            // 获取该套料管材包含的管件数据
            List<PipeTable> pipeTableList = taoliaoService.getPipeTableByTaoliaoId(taoliaoId);
            logger.info("套料" + taoliaoId + "管材" + pipeLengthList.get(0).getMaterial() + "管材数量" + pipeTableList.size());

            for (PipeTable pipeTable:pipeTableList) {
                PipeCutingItem item = new PipeCutingItem();

                item.setId(pipeTable.getPipeId());
                //item.setBatchName(pipeTableList.get(i).getBatchName());
                //item.setAreaCode(pipeTableList.get(i).getAreaCode());
                item.setCuttingLength(pipeTable.getCutLength());
                //下料加工记录
                PipeProcessing pipeProcessing = pipeProcessingRepository.findByPipeIdAndProcessState(pipeTable.getPipeId(),1);

                //套料算法是否已经套过料的不算入内？
                if(pipeProcessing!=null&&pipeProcessing.getIsFinished()!=1) item.setCutted(false);
                else item.setCutted(true);
                item.setNoInstalled(pipeTable.getNoInstalled());
                item.setOutfieid(pipeTable.getOutfield());
                item.setPipeId(pipeTable.getPipeCode());//集配管号
                item.setPipeMaterial(pipeTable.getPipeMaterial());
                item.setShape(pipeTable.getPipeShape());
                item.setSurfaceTraet(pipeTable.getSurfaceTreat());
                item.setUnit(pipeTable.getUnitName());
                item.setUnitId(pipeTable.getUnitId());
                pipeCutingList.add(item);
            }

            System.out.println("下料表管件id有：" + pipeCutingList);

            // 调用套料算法
            BatchJacking batchJacking = new BatchJacking(pipeLength, pipeCutingList);
            BatchJackingResult batchJackingResult = batchJacking.batchJacking();
            if(batchJackingResult == null){
                map.put("result",0);
                map.put("message","无法分配套料方案");
                map.put("data",null);
            }else{
                map.put("result",1);
                map.put("message","无法分配套料方案");
                map.put("data",batchJackingResult);
            }

        } catch (Exception e) {
            map.put("result",0);
            map.put("message",e.toString());
            map.put("data",null);
            e.printStackTrace();
        }
        return map;
    }


    //根据当前登录用户，获取其可以查看的下料计划批次名
    @RequestMapping(value = "/getPipeTableByTaoliaoId")
    @ResponseBody
    public Map<String,Object> getPipeTableByTaoliaoId(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam("taoliaoId")int taoliaoId){
        Map<String,Object> map =new HashMap<String,Object>();

        //根据taoliaoId 获取该套料下的管件
        List<PipeTable> pipeTableList = taoliaoService.getPipeTableByTaoliaoId(taoliaoId);
        //这里总觉得有问题
        map.put("data",pipeTableList);
        map.put("result",0);
        map.put("message","获取成功");
        return map;
    }
}
