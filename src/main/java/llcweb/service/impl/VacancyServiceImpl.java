//package llcweb.service.impl;
//
//import com.ricardo.workplan.domain.Vacancy;
//import com.ricardo.workplan.repository.VacancyRepository;
//import com.ricardo.workplan.service.VacancyService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * Created by:Ricardo
// * Description:
// * Date: 2018/2/1
// * Time: 15:59
// */
//@Service
//public class VacancyServiceImpl implements VacancyService {
//
//    private  final static Logger logger = LoggerFactory.getLogger(VacancyServiceImpl.class);
//    @Autowired
//    private VacancyRepository vacancyRepository;
//
//    /*
//     *添加一个人员的vacancy信息
//     */
//    @Transactional
//    @Override
//    public void addVacancy(String name,String vacancyStr){
//        logger.info("addVacancy service");
//        vacancyRepository.save(new Vacancy(name,vacancyStr));
//    }
//
//
//    /*
//    *更新一个人员的vacancy信息
//    */
//    @Transactional
//    @Override
//    public void updateVacancyById(int id,String name,String vacancyStr){
//        logger.info("addVacancy service");
//        vacancyRepository.save(new Vacancy(id,name,vacancyStr));
//    }
//
//    /*
//    *添加一个人员的vacancy信息
//    */
//    @Transactional
//    @Override
//    public void deleteVacancyById(int id){
//        logger.info("addVacancy service");
//        vacancyRepository.delete(id);
//    }
//
//    /*
//    * 根据di查找一个vacancy*/
//    @Override
//    public  Vacancy findVacancyById(int id){
//        logger.info("findVacancyById service");
//        return vacancyRepository.findOne(id);
//    }
//    /*
//   * 根据name查找一个vacancy*/
//    @Override
//    public  Vacancy findVacancyByName(String name){
//        logger.info("findVacancyByName service");
//        return vacancyRepository.findByName(name);
//    }
//    /*
//   * 返回所有Vacancy*/
//    @Override
//    public List<Vacancy> getAllVacancy(){
//        logger.info("getAllVacancy service");
//        return vacancyRepository.findAll();
//    }
//
//    /*
//    *根据name删除一个vacancy
//    * */
//    @Override
//    public void deleteVacancyByName(String name) {
//        logger.info("deleteVacancyByName service");
//        vacancyRepository.deleteByName(name);
//    }
//}
