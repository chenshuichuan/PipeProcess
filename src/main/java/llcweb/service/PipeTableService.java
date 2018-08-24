package llcweb.service;

import llcweb.domain.models.PipeTable;
import llcweb.tools.PageParam;
import org.springframework.data.domain.Page;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 15:59
 */

public interface PipeTableService {

    /*
     *添加信息
     */
    void add();
    /*
    *更新信息
    */
    void updateById(int id);

    int updatePipeToNextStage(PipeTable pipeTable,int processState, int processIndex);
    /*
    *根据id查找
    */
    void findById(int id);
    /*
    *删除
    * */
    void deleteById(int id);

    Page<PipeTable> getPage(PageParam pageParam, Integer batchId);
    void turnUnit(int startPage, String batchName);

}
