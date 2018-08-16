package llcweb.dao.repository;

import llcweb.domain.entities.Units;
import llcweb.domain.models.UnitTable;
import llcweb.service.UsersService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/8/16
 * Time: 19:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTableRepositoryTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UsersService usersService;
    @Autowired
    private UnitTableRepository unitTableRepository;
    @Test
    public void findAll() throws Exception {
    }

    @Test
    public void findByBatchNameAndUnitName() throws Exception {
    }

    @Test
    public void findByBatchName() throws Exception {
    }

    @Test
    public void findByUnitNameLike() throws Exception {
    }

    @Test
    public void findByBatchNameAndUnitNameLike() throws Exception {
    }

    @Test
    public void findByBatchNameAndPlanIdIsNull() throws Exception {
    }

    @Test
    public void findByPlanId() throws Exception {
        List<UnitTable> unitsList = unitTableRepository.findByPlanId(1);
        Assert.assertThat(unitsList.size(),greaterThan(1));

    }

}