package llcweb.service;

import llcweb.domain.models.Workers;
import llcweb.tools.PageParam;
import org.springframework.data.domain.Page;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */

public interface ProcessOrderService {

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

    //根据跟定的加工工序id，以及当前工序id，获得下一工序id
    //加工工序就是由一系列工序id组成的字符串，并包含未开始、已完工两个标志工序
    int nextStage(int processOrderId, int currentStage);
    int nextStage(String processOrderList, int currentStage);
    int currentStageIndex(int processOrderId, int currentStage);
    //根据加工工序id列表 1,,2,3,4 等解析其名字列表 下料,弯管,校管
    String getProcessOrderString(String orderList);
    //根据加工工序id列表 1,,2,3,4 等解析其名字列表 去掉10,11两个工序
    String getProcessOrderString(int processOrderId);
}
