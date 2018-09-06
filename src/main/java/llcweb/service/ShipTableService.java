package llcweb.service;

import llcweb.domain.entities.ShipProcessInfo;
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

public interface ShipTableService {

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
    Page<ShipTable> getPage(PageParam pageParam, ShipTable example);
    List<ShipTable> getAllShipNameByState(int state);

    //统计船的加工情况
    List<ShipProcessInfo> getShipProcessInfo(List<ShipTable> shipTableList);
}
