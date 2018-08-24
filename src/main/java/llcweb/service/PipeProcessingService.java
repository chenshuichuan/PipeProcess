package llcweb.service;

import llcweb.domain.models.PipeProcessing;
import llcweb.domain.models.PipeTable;
import llcweb.domain.models.Workers;
import llcweb.tools.PageParam;
import org.springframework.data.domain.Page;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */

public interface PipeProcessingService {

    /*
     *添加信息
     */
    void add();
    /*
    *添加信息
    */
    int add(PipeTable pipeTable, int processState, int processIndex, int processPlace);
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
    Page<PipeProcessing> getPage(PageParam pageParam, PipeProcessing example);
}
