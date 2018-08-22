package llcweb.controller;

import llcweb.domain.User;
import llcweb.domain.models.Departments;
import llcweb.domain.models.Users;
import llcweb.service.UsersService;
import llcweb.tools.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/users")
public class UsersController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();
        Users users = new Users();
        users.setUsername("chen");
        map.put("page",usersService.getPage(new PageParam(1,10),users));
        return map;
    }
    //获取当前登录用户
    @RequestMapping(value = "/getCurrentUser",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getCurrentUser(){
        Map<String,Object> map =new HashMap<String,Object>();
        Users users = usersService.getCurrentUser();
        map.put("data",users);
//        { "data":{ "accountNonExpired":true, "accountNonLocked":true,
//                "authorities":[ { "authority":"0" } ],
//            "credentialsNonExpired":true, "enabled":true,
//                    "id":1, "mail":"", "password":"admin",
//                    "phone":"13557453450", "role":0,
//                    "roles":[ { "flag":"0", "id":1, "name":"绠＄悊鍛�" } ],
//            "state":0, "username":"admin" } }
        return map;
    }
    //获取当前用户管理的工段列表
    @RequestMapping(value = "/getUserManageSections",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getUserManageSections(){
        Map<String,Object> map =new HashMap<String,Object>();
        Users users = usersService.getCurrentUser();
        List<Departments>sectionsList =  usersService.getSections(users);
        map.put("data",sectionsList);
        return map;
    }
}
