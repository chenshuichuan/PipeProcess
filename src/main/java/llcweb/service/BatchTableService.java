package llcweb.service;

import llcweb.domain.entities.BatchProcessInfo;
import llcweb.domain.entities.ProcessInfo;
import llcweb.domain.models.BatchTable;
import llcweb.domain.models.ShipTable;
import llcweb.domain.models.Workers;
import llcweb.tools.PageParam;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */

public interface BatchTableService {

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
    /*
  * 根据分页参数以及各字段示例查找信息
  * example 为字段可能包含的值
  * */
    Page<BatchTable> getPage(PageParam pageParam, BatchTable example);

    //统计某船 的单元加工情况
    ProcessInfo calUnitProcessOfShip(String shipCode);
    // 统计某船所有批次加工情况
    List<BatchProcessInfo> getBatchProcessInfo(ShipTable shipTable,int isfinished);
}
