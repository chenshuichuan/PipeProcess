package llcweb.dao.repository;

import llcweb.domain.models.PipeProcessing;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 13:36
 */
 //Integer 是id 的类型
public interface PipeProcessingRepository extends JpaRepository<PipeProcessing,Integer>{

}