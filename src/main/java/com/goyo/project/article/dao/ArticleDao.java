package com.goyo.project.article.dao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import com.goyo.project.model.Article;
import com.goyo.project.model.UserInfo;
@Transactional
public interface ArticleDao extends PagingAndSortingRepository<Article,String>,JpaSpecificationExecutor<Article> {
//	@Query(value = "SELECT * FROM Article WHERE subjection = ?1",
//		    countQuery = "SELECT count(*) FROM Article WHERE subjection = ?1",
//		    nativeQuery = true)
//	Page<Article> findBySubjection(String subjection, Pageable pageable);
	 //查询党组织数量
    @Query("from Article t where t.typeIds like %?1%")
    public List<Article> findByTypeIds(String typeIds);
    //查询某个组织的某个详细类别的文章数
    @Query("from Article t where t.typeIds like %?1% and t.sourceOrganization like %?2%")
    public List<Article> findByTypeIdsAndSourceOrganization(String typeIds,String sourceOrganization);
}