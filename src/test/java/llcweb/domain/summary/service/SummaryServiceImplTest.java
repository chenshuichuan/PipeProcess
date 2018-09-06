package llcweb.domain.summary.service;

import llcweb.dao.repository.*;
import llcweb.domain.models.Departments;
import llcweb.domain.summary.SummaryDay;
import llcweb.domain.summary.repository.SummaryDayRepository;
import llcweb.service.*;
import llcweb.tools.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/9/2
 * Time: 17:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SummaryServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DepartmentsRepository departmentsRepository;
    @Autowired
    private SummaryDayRepository summaryDayRepository;

    @Test
    public void summaryDayFindTest(){
        Date date = DateUtil.StringTodate("2018-08-01","yyyy-MM-dd");
        List<SummaryDay> summaryDayList = summaryDayRepository.findByDayAndType(date,"批次");
        logger.info("record size="+summaryDayList.size());
        Assert.assertThat(summaryDayList.size(),greaterThan(1));
    }

    @Test
    public void addSummaryDay() throws Exception {
    }

    @Test
    public void addSummaryWorkerDay() throws Exception {
    }

    @Test
    public void addSummaryMonth() throws Exception {
    }

    @Test
    public void calSummaryDay() throws Exception {
    }

    @Test
    public void calSummaryMonth() throws Exception {
    }

    @Test
    public void calSummaryWorkerDay() throws Exception {
    }

    @Test
    public void generateData() throws Exception {
        //取2018年到今几个月的月列表
        //取各个工位
        List<Departments> departmentsList = departmentsRepository.findByLevel(2);
        //取各个类型
        String []type ={"批次","单元","管件"};//
        Random rand = new Random();
        // 取各个月各天
        List<String> dayList = new ArrayList<>();
        for(int i=6;i<8;i++){
            System.out.println("month = "+(i+1));
            dayList = DateUtil.getDayByMonth(2018,i);

            //生成summaryDay 数据
            for(String day: dayList){
                Date date = DateUtil.StringTodate(day,"yyyy-MM-dd");
                for(Departments departments: departmentsList){
                    for(int j=0;j<3;j++){
                        SummaryDay summaryDay =
                                summaryDayRepository.findByDepartmentIdAndDayAndType(departments.getId(),date,type[j]);
                        if(summaryDay==null){
                            summaryDay = new SummaryDay(departments.getId(),date,type[j]);
                        }
                        //非下料 没有批次级别
                        if(departments.getStageId()!=1){//
                            if(j==1){
                                summaryDay.setPlanning(rand.nextInt(2));
                                summaryDay.setFinished(rand.nextInt(2));
                            }
                            else if(j==2){
                                summaryDay.setPlanning(rand.nextInt(10)+2);
                                summaryDay.setFinished(rand.nextInt(10)+2);
                            }

                            if(j!=0)summaryDayRepository.save(summaryDay);
                        }
                        else{
                            //下料 批
                            if(j==0){
                                summaryDay.setPlanning(rand.nextInt(2));
                                summaryDay.setFinished(rand.nextInt(2));
                            }
                            //下料 单元
                            else if(j==1){
                                summaryDay.setPlanning(rand.nextInt(3));
                                summaryDay.setFinished(rand.nextInt(3));
                            }
                            //下料 管件
                            else {
                                summaryDay.setPlanning(rand.nextInt(20)+10);
                                summaryDay.setFinished(rand.nextInt(20)+10);
                            }
                            summaryDayRepository.save(summaryDay);
                        }
                    }
                }
            }

            //for(String date: dayList)System.out.println(date+",");
        }
    }

    //生成数据之后，统计工序工作量
    @Test
    public void calStageSummary()throws Exception{
        //取各个工序
        List<Departments> departmentsList = departmentsRepository.findByLevel(1);
        for(Departments departments:departmentsList){

            String []type = {"管件","单元","批次"};

            if(departments.getStageId()!=1){//非下料
                for(int j=0;j<type.length-1;j++){
                    //获得工序下的工位
                    List<Departments> workplaceList = departmentsRepository.findByUpDepartment(departments.getId());
                    // 取各个月各天
                    List<String> dayList = new ArrayList<>();
                    for(int i=6;i<8;i++) {
                        System.out.println("month = " + (i + 1));
                        dayList = DateUtil.getDayByMonth(2018, i);
                        //对每天统计工序下当天所有工位的加工情况
                        for (String day : dayList){
                            Date date = DateUtil.StringTodate(day,"yyyy-MM-dd");
                            SummaryDay stageSummary =
                                    summaryDayRepository.findByDepartmentIdAndDayAndType(departments.getId(),date,type[j]);
                            if(stageSummary==null){
                                stageSummary = new SummaryDay(departments.getId(),date,type[j]);
                            }
                            int planning = 0;
                            int finished =0;
                            //取当天各个工位加工情况
                            for (Departments workpalce: workplaceList){
                                SummaryDay summaryDay =
                                        summaryDayRepository.findByDepartmentIdAndDayAndType(workpalce.getId(),date,type[j]);
                                if(summaryDay!=null){
                                    planning+=summaryDay.getPlanning();
                                    finished+=summaryDay.getFinished();
                                    logger.info("planning="+summaryDay.getPlanning()+",finished="+summaryDay.getFinished());
                                }
                            }
                            stageSummary.setPlanning(planning);
                            stageSummary.setFinished(finished);
                            summaryDayRepository.save(stageSummary);
                            logger.info("planning="+planning+",finished="+finished);
                        }
                    }
                }

            }
            else{//下料

                for(int j=0;j<type.length;j++){
                    //获得工序下的工位
                    List<Departments> workplaceList = departmentsRepository.findByUpDepartment(departments.getId());
                    // 取各个月各天
                    List<String> dayList = new ArrayList<>();
                    for(int i=6;i<8;i++) {
                        System.out.println("month = " + (i + 1));
                        dayList = DateUtil.getDayByMonth(2018, i);
                        //对每天统计工序下当天所有工位的加工情况
                        for (String day : dayList){
                            Date date = DateUtil.StringTodate(day,"yyyy-MM-dd");
                            SummaryDay stageSummary =
                                    summaryDayRepository.findByDepartmentIdAndDayAndType(departments.getId(),date,type[j]);
                            if(stageSummary==null){
                                stageSummary = new SummaryDay(departments.getId(),date,type[j]);
                            }
                            int planning = 0;
                            int finished =0;
                            //取当天各个工位加工情况
                            for (Departments workpalce: workplaceList){
                                SummaryDay summaryDay =
                                        summaryDayRepository.findByDepartmentIdAndDayAndType(workpalce.getId(),date,type[j]);
                                if(summaryDay!=null){
                                    planning+=summaryDay.getPlanning();
                                    finished+=summaryDay.getFinished();
                                    logger.info("planning="+summaryDay.getPlanning()+",finished="+summaryDay.getFinished());
                                }
                            }
                            stageSummary.setPlanning(planning);
                            stageSummary.setFinished(finished);
                            summaryDayRepository.save(stageSummary);
                            logger.info("aaplanning="+planning+",aafinished="+finished);
                        }
                    }
                }
            }
        }
    }
    //生成数据之后，统计工段工作量
    @Test
    public void calSectionSummary()throws Exception{
        //取各个工段
        List<Departments> departmentsList = departmentsRepository.findByLevel(0);
        for(Departments section:departmentsList){

            String []type = {"管件","单元","批次"};
            for(int j=0;j<type.length-1;j++){
                //获得工段下的工序
                List<Departments> stageList = departmentsRepository.findByUpDepartment(section.getId());
                // 取各个月各天
                List<String> dayList = new ArrayList<>();
                for(int i=6;i<8;i++) {
                    System.out.println("month = " + (i + 1));
                    dayList = DateUtil.getDayByMonth(2018, i);
                    //对每天统计工序下当天所有工位的加工情况
                    for (String day : dayList){
                        Date date = DateUtil.StringTodate(day,"yyyy-MM-dd");
                        SummaryDay sectionSummary =
                                summaryDayRepository.findByDepartmentIdAndDayAndType(section.getId(),date,type[j]);
                        if(sectionSummary==null){
                            sectionSummary = new SummaryDay(section.getId(),date,type[j]);
                        }
                        int planning = 0;
                        int finished =0;
                        //取当天各个工序加工情况
                        for (Departments stage: stageList){
                            SummaryDay summaryDay =
                                    summaryDayRepository.findByDepartmentIdAndDayAndType(stage.getId(),date,type[j]);
                            if(summaryDay!=null){//有的工序没有批次的记录
                                planning+=summaryDay.getPlanning();
                                finished+=summaryDay.getFinished();
                                logger.info("planning="+summaryDay.getPlanning()+",finished="+summaryDay.getFinished());
                            }
                        }
                        sectionSummary.setPlanning(planning);
                        sectionSummary.setFinished(finished);
                        summaryDayRepository.save(sectionSummary);
                        logger.info("planning="+planning+",finished="+finished);
                    }
                }
            }
        }
    }
}