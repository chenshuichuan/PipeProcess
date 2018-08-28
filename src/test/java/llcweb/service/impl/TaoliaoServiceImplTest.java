package llcweb.service.impl;

import llcweb.dao.repository.ArrangeTableRepository;
import llcweb.dao.repository.PipeTableRepository;
import llcweb.dao.repository.PlanTableRepository;
import llcweb.dao.repository.TaoliaoRepository;
import llcweb.domain.models.PlanTable;
import llcweb.domain.models.Taoliao;
import llcweb.service.TaoliaoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/8/28
 * Time: 20:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaoliaoServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PlanTableRepository planTableRepository;
    @Autowired
    private TaoliaoService taoliaoService;
    @Autowired
    private TaoliaoRepository taoliaoRepository;

    //@Transactional
    @Test
    public void generateTaoliao() throws Exception {
        int planId =879; //887;//882,879
        PlanTable planTable = planTableRepository.findOne(planId);

        taoliaoService.generateTaoliao(planTable);
        List<Taoliao> taoliaoList = taoliaoRepository.findByPlanId(planId);
        logger.info("解析的加工顺序："+taoliaoList.size());
        Assert.assertThat(taoliaoList.size(),greaterThan(0));

    }

}