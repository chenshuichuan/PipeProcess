package llcweb.domain.summary.service;

import llcweb.dao.repository.DepartmentsRepository;
import llcweb.dao.repository.UsersRepository;
import llcweb.dao.repository.WorkersRepository;
import llcweb.domain.models.Departments;
import llcweb.domain.models.Users;
import llcweb.domain.models.Workers;
import llcweb.domain.summary.SummaryDay;
import llcweb.domain.summary.SummaryMonth;
import llcweb.domain.summary.SummaryService;
import llcweb.domain.summary.SummaryWorkerDay;
import llcweb.domain.summary.repository.SummaryDayRepository;
import llcweb.domain.summary.repository.SummaryMonthRepository;
import llcweb.domain.summary.repository.SummaryWorkerDayRepository;
import llcweb.service.UsersService;
import llcweb.tools.PageParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */
@Service
public class SummaryServiceImpl implements SummaryService {

    private  final  Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SummaryDayRepository summaryDayRepository;
    @Autowired
    private SummaryWorkerDayRepository summaryWorkerDayRepository;
    @Autowired
    private SummaryMonthRepository summaryMonthRepository;

    @Override
    public void addSummaryDay(int departId,Date day,  String type, int planning ,int finished) {

        SummaryDay temp = summaryDayRepository.findByDepartmentIdAndDayAndType(departId,day,type);
        if(temp!=null){
            logger.error("该数据已存在!departId="+departId+",day="+day.toString()+",type="+type);
        }
        else {
            temp = new SummaryDay(departId,day,type);
            temp.setPlanning(planning);
            temp.setFinished(finished);
            summaryDayRepository.save(temp);
        }
    }
    @Override
    public void addSummaryWorkerDay(int workerId, Date day,int planning,int finished) {
        SummaryWorkerDay temp = summaryWorkerDayRepository.findByWorkerIdAndDay(workerId,day);
        if(temp!=null){
            logger.error("该数据已存在!workerId="+workerId+",day="+day.toString());
        }
        else {
            temp = new SummaryWorkerDay();
            temp.setWorkerId(workerId);
            temp.setDay(day);
            temp.setPlanning(planning);
            temp.setFinished(finished);
            summaryWorkerDayRepository.save(temp);
        }
    }
    @Override
    public void addSummaryMonth(int departId,Date month,  String type, int planning ,int finished) {
        SummaryMonth temp = summaryMonthRepository.findByDepartmentIdAndMonthAndType(departId,month,type);
        if(temp!=null){
            logger.error("该数据已存在!departId="+departId+",month="+month.toString()+",type="+type);
        }
        else {
            temp = new SummaryMonth();
            temp.setDepartmentId(departId);
            temp.setMonth(month);
            temp.setType(type);
            temp.setPlanning(planning);
            temp.setFinished(finished);
            summaryMonthRepository.save(temp);
        }
    }

    /**
     *@Author: Ricardo
     *@Description: 计算某部门某天某类型 的加工情况
     *@Date: 9:50 2018/9/6
     *@param:
     **/
    @Override
    public SummaryDay calSummaryDay(int departId, Date day, String type) {
        //如果是管件
        //取当天完成的pipe Processing 数
        //单元
        //取当天完成的unit processing 数
        //批次
        //去当天完成的batch processing 数
        return null;
    }

    @Override
    public SummaryMonth calSummaryMonth(int departId, Date month, String type) {

        return null;
    }

    @Override
    public SummaryWorkerDay calSummaryWorkerDay(int workerId, Date date) {
        return  null;
    }
    @Override
    public void generateData(){

    }
}
