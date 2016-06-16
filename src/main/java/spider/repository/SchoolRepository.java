package spider.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import spider.entity.School;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
@Repository
public interface SchoolRepository extends JpaRepository<School,Integer>,JpaSpecificationExecutor<School> {

}
