package llcweb.service;

import llcweb.domain.models.PipeTable;
import llcweb.domain.models.UnitTable;
import llcweb.domain.models.Workstage;
import llcweb.tools.PageParam;
import org.springframework.data.domain.Page;

import java.nio.channels.Pipe;
import java.util.List;

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

    Page<PipeTable> getPage(PageParam pageParam, PipeTable pipeTable,List<UnitTable> unitTableList);
    void turnUnit(int startPage, String batchName);

    //解析管件的加工工序,传入所有工序，防止单元调用批次调用时，每次重新读取数据库，
    //仅计算，不保存到pipeTable
    String calPipeProcessOrder(PipeTable pipeTable, Workstage underStart, Workstage cut,
                               Workstage bend, Workstage proofread, Workstage weld,
                               Workstage polish, Workstage surface, Workstage finished,boolean saveToDataBase);
}
