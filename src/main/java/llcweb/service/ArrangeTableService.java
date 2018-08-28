package llcweb.service;

import llcweb.domain.entities.ArrangeRecord;
import llcweb.domain.models.*;
import llcweb.tools.PageParam;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */

public interface ArrangeTableService {

    /*
     *添加信息
     */
    void add();
    /*
     *添加信息
     */
    int add(Workers arranger, PlanTable planTable, Departments workPlace);

    int add(Workers arranger, UnitTable unitTable, Departments workPlace);
    /*
    *更新信息
    */
    void updateById(int id);
    /*
    *根据id查找
    */
    void findById(int id);
    /*
    *删除
    * */
    void deleteById(int id);

    /*
  * 根据分页参数以及各字段示例查找信息
  * example 为字段可能包含的值
  * */
    Page<ArrangeTable> getPage(PageParam pageParam, ArrangeTable example);

    /*
   *将arrangeTable封装为arrangeRecord
   * */
    ArrangeRecord getRecord(ArrangeTable arrangeTable);
    List<ArrangeRecord> arrangeTableToArrangeRecord(List<ArrangeTable> arrangeTableList);
    List<ArrangeTable> findArrangeByWorkplace(String section,String stage,String workplace,int isFinished);
    boolean isWorkpalceVacancy(Departments departments);

    //plan批次派工到工位,出错返回0，正常返回1
    int arrangeBatchToWorkPlace(Workers arranger, PlanTable planTable, Departments workPlace);

    //除下料外其他的工序的单元派工
    int arrangeUnitToWorkPlace(Workers arranger,UnitTable unitTable, Departments workPlace);

    //下料的单元派工，不需要产生派工记录
    int arrangeBatchUnitCut(UnitTable unitTable, Departments workPlace,int arrangeId);

    //管件派工到下一个工序
    int arrangePipe(PipeTable pipeTable, Departments workPlace,int arrangeId);



    /**
     *@Author: Ricardo
     *@Description: //根据当前登录用户获取其可以查看的派工记录
     *@Date: 14:32 2018/8/27
     *@param:
     **/
    List<ArrangeTable> getUsersArrangeTable(int arrangeType,int isFinished);
}
