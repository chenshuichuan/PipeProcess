package llcweb.controller;

import llcweb.dao.repository.WorkersRepository;
import llcweb.domain.models.Workers;
import llcweb.service.UsersService;
import llcweb.service.WorkersService;
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
 * Created by tengj on 2017/4/10.
 */

@RestController
@RequestMapping("/workers")
public class WorkersController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UsersService usersService;
    @Autowired
    private WorkersService workersService;
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

       //获取客户端需要那一列排序
        String orderColumn = request.getParameter("orderColumn");
        if(orderColumn == null){
            orderColumn = "name";
        }
        //获取排序方式 默认为asc
        String orderDir = request.getParameter("orderDir");
        if(orderDir == null){
            orderDir = "asc";
        }
        int size = Integer.parseInt(pageSize);
        int currentPage = Integer.parseInt(startIndex)/size+1;
        Workers workers = new Workers();
        Page<Workers> userPage = workersService.getWorkersPage(new PageParam(currentPage,size),workers);
        List<Workers> userList = userPage.getContent();
        //总记录数
        long total = userPage.getTotalElements();
        map.put("pageData", userList);
        map.put("total", total);
        map.put("draw", draw);
        return map;
    }


    //根据工人的姓名和工号 删除信息
    @RequestMapping(value = "/deleteByNameAndCode",method = RequestMethod.GET)
    public Map<String,Object> deleteUserById(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("name")String name,@RequestParam("code")String code){
        Map<String,Object> map =new HashMap<String,Object>();
        Workers workers = workersRepository.findByNameAndCode(name,code);
        if (workers!=null) {
            workersRepository.delete(workers);
            String message ="成功删除"+name+"的信息";
            map.put("result",1);
            map.put("message",message);
            logger.info(message);
        }
        else{
            String message = "删除"+name+"的信息失败！请检查数据";
            map.put("result",0);
            map.put("message",message);
            logger.info(message);
        }
        return map;
    }

    //更新数据
    //还有state数据，和是否生成同名账号数据未处理
    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,Object> update(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();

        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String role = request.getParameter("role");
        String job = request.getParameter("job");
        String departments = request.getParameter("departments");
        Workers workers = workersRepository.findByCode(code);
        if (workers!=null) {
            workers.setName(name);
            workers.setRole(Integer.parseInt(role));
            workers.setJob(job);
            workers.setDepartments(departments);
            workers.setUpdateTime(new Date());
            workers =  workersRepository.save(workers);
            if(workers!=null){
                String message ="成功更改"+name+"的信息";
                map.put("result",1);
                map.put("message",message);
                logger.info(message);
            }
            else{
                String message = "更改"+name+"的信息失败！请检查数据";
                map.put("result",0);
                map.put("message",message);
                logger.info(message);
            }
        }
        else{
            String message = "更改"+name+"的信息失败！请检查数据";
            map.put("result",0);
            map.put("message",message);
            logger.info(message);
        }
        return map;
    }

    //添加数据
    /**
     *@Author: Ricardo
     *@Description:
     *@Date: 23:43 2018/6/9
     *@param:
     **/
    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String,Object> add(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String role = request.getParameter("role");
        String job = request.getParameter("job");
        String departments = request.getParameter("departments");

        Workers workers = workersRepository.findByCode(code);
        if (workers==null) {
            workers = new Workers();
            workers.setName(name);
            workers.setRole(Integer.parseInt(role));
            workers.setJob(job);
            workers.setDepartments(departments);
            workers.setState("正常");
            //
            workers.setAsUser("是");
            workersRepository.save(workers);
            String message ="成功添加"+name+"的信息";
            map.put("result",1);
            map.put("message",message);
            logger.info(message);
        }
        else{
            String message = "添加失败！工号"+code+"已存在，请检查数据！";
            map.put("result",0);
            map.put("message",message);
            logger.info(message);
        }
        return map;
    }
}
