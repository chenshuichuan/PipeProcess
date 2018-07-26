package llcweb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */

public interface VacancyService {

    /*
     *添加一个人员的vacancy信息
     */
    void addVacancy(String name, String vacancyStr);


    /*
    *更新一个人员的vacancy信息
    */
    void updateVacancyById(int id, String name, String vacancyStr);
    /*
    *添加一个人员的vacancy信息
    */
    void deleteVacancyById(int id);

    /*
    *根据name删除一个vacancy
    * */
    void deleteVacancyByName(String name);
}
