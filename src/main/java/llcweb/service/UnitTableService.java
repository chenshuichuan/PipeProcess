package llcweb.service;

import llcweb.domain.entities.ProcessInfo;
import llcweb.domain.entities.UnitTableInfo;
import llcweb.domain.entities.Units;
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

    List<UnitTable> findByPlanId(int planId);
    List<Units> findUnitsByPlanId(int planId);
    List<UnitTable> getUnitsByStageId(int stageId);

    //判断单元是否完成
    boolean isFinished(UnitTable unitTable);

    //单元派工时更改单元信息 type==1时，是下料派工，要更改管件信息，其他工序不需要更改管件数量信息
    int updateUnitToNextStage(UnitTable unitTable, Departments workPlace, boolean isCut);

    //计算某单元的加工顺序
    String calUnitProcessOrder(UnitTable unitTable, Workstage underStart, Workstage cut,
                               Workstage bend, Workstage proofread, Workstage weld,
                               Workstage polish, Workstage surface, Workstage finished,boolean saveToDataBase);
    //UnitTable转换为UnitTableInfo
    List<UnitTableInfo> unitToUnitInfo(List<UnitTable> unitTableList,Departments section);

    //统计某船 的管件加工情况
    ProcessInfo calPipeProcessOfShip(String shipCode);
}
