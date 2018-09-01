package llcweb.service;

import llcweb.domain.entities.CutPipeInfo;
import llcweb.domain.models.PipeTable;
import llcweb.domain.models.PlanTable;
import llcweb.domain.models.Taoliao;
import llcweb.domain.models.Workstage;
import llcweb.tools.PageParam;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by:Ricardo
 * Description: 套料管材表
 * Date: 2018/2/1
 * Time: 15:59
 */

public interface TaoliaoService {

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

    Page<Taoliao> getPage(PageParam pageParam, Taoliao taoliao);
    int generateTaoliao(PlanTable planTable);
    List<PipeTable> getPipeTableByTaoliaoId(int taoliaoId);
    Page<PipeTable> getPipeTableByTaoliaoId( PageParam pageParam,int taoliaoId);
    List<CutPipeInfo> turnPipeTableToCutPipeInfo(List<PipeTable> pipeTableList, int taoliaoId);

}
