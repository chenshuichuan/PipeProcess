package llcweb.domain.summary;

import llcweb.domain.models.Departments;
import llcweb.domain.models.Users;
import llcweb.tools.PageParam;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 *@Author: Ricardo
 *@Description:
 *@Date: 15:38 2018/9/2
 *@param:
 **/
public interface SummaryService {

    /*
     *添加信息
     */
    void addSummaryDay(int departId,Date day,  String type, int planning ,int finished);
    void addSummaryWorkerDay(int workerId, Date day, int planning, int finished);
    void addSummaryMonth(int departId,Date month,  String type, int planning ,int finished);

    //
    SummaryDay calSummaryDay(int departId,Date day,  String type);
    SummaryMonth calSummaryMonth(int departId,Date month,  String type);
    SummaryWorkerDay calSummaryWorkerDay(int workerId,Date date);

    void generateData();
}
