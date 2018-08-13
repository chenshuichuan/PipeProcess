package llcweb.service;

import llcweb.domain.models.UnitProcessing;
import llcweb.domain.models.UnitTable;
import llcweb.tools.PageParam;
import org.springframework.data.domain.Page;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */

public interface UnitTableService {

    /*
     *添加信息
     */
    void add();
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

    void turnUnit();

    int countPipeNumberOfUnit(UnitTable unitTable);
    void countPipeNumberOfUnits();

    void judgeBatchUnitPlanId(String batchName);
    void analyzePlanOfUnits();

    /*
    * * 根据分页参数以及各字段示例查找信息
    * * example 为字段可能包含的值
    * * */
    Page<UnitTable> getPage(PageParam pageParam, UnitTable example);
}
