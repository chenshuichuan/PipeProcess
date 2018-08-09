package llcweb.service.impl;

import llcweb.dao.repository.UsersRepository;
import llcweb.domain.models.Users;
import llcweb.service.UsersService;
import llcweb.tools.PageParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/8/6
 * Time: 23:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersServiceImplTest {

    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired

    @Test
    public void getUsersPage() throws Exception {

        Page<Users> users = usersService.getUsersPage(new PageParam(1,10),"chen");
        //for (Users user: users. )
    }

}