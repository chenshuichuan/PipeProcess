package llcweb.service.impl;

import llcweb.dao.repository.ArrangeTableRepository;
import llcweb.dao.repository.UnitTableRepository;
import llcweb.service.ArrangeTableService;
import llcweb.service.UnitTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */
@Service
public class UnitTableServiceImpl implements UnitTableService {

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private UnitTableRepository unitTableRepository;

    @Transactional
    @Override
    public void add() {
        logger.info("service add");
    }

    @Transactional
    @Override
    public void updateById(int id) {
        logger.info("service updateById id="+id);
    }

    @Override
    public void findById(int id) {
        logger.info("service findById id="+id);
    }
    @Transactional
    @Override
    public void deleteById(int id) {
        logger.info("service add id="+id);
    }

}