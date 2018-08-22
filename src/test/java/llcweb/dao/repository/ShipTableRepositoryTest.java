package llcweb.dao.repository;

import llcweb.domain.models.ShipTable;
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
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/8/22
 * Time: 11:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShipTableRepositoryTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ShipTableRepository  shipTableRepository;
    @Test
    public void findFinishedShip() throws Exception {
        List<ShipTable> shipTableList = shipTableRepository.findFinishedShip();
        logger.info("size= "+shipTableList.size());
        Assert.assertThat(shipTableList.size(),greaterThan(1));
    }

    @Test
    public void findUnFinishedShip() throws Exception {
        List<ShipTable> shipTableList = shipTableRepository.findUnfinishedShip();
        logger.info("size= "+shipTableList.size());
        Assert.assertThat(shipTableList.size(),greaterThan(1));
    }
}