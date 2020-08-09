package com.api.news.demo.repository;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.News;
import com.api.news.demo.utils.Constants;
import com.api.news.demo.utils.StringUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Log4j
public class NewsRepositoryImpl implements NewsRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public ResultDTO createOrUpdateNews(News news) {
        ResultDTO resultDTO = new ResultDTO();

        if (StringUtils.isLongNullOrZero(news.getCategoryId())) {
            resultDTO.setMessage("Category name is required !");
            return resultDTO;
        }
        
        if (StringUtils.isStringNullOrEmpty(news.getTitle())) {
            resultDTO.setMessage("Title is required !");
            return resultDTO;
        }

        if (StringUtils.isStringNullOrEmpty(news.getContent())) {
            resultDTO.setMessage("Content is required !");
            return resultDTO;
        }

        News newsClone = news.clone();
        newsClone.setCategoryId(null);
        ResultDTO checkExisted = search(newsClone);
        if (checkExisted.getLst() != null && !checkExisted.getLst().isEmpty()) {
            resultDTO.setMessage("News is existed !");
            return resultDTO;
        }

        News newsCreate = entityManager.merge(news);
        if (newsCreate != null && !StringUtils.isLongNullOrZero(newsCreate.getId())) {
            if (StringUtils.isLongNullOrZero(news.getId())) {
                resultDTO.setId(newsCreate.getId().toString());
            }
            resultDTO.setKey(Constants.RESULT.SUCCESS);
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
        }
        return resultDTO;
    }

    @Override
    public ResultDTO findAllNews() {
        ResultDTO resultDTO = new ResultDTO();
        try {
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
            resultDTO.setKey(Constants.RESULT.SUCCESS);
            List<News> lst = entityManager.createQuery("select t from " + News.class.getSimpleName() + " t where t.active = 1").getResultList();
            if (lst != null && !lst.isEmpty()) {
                resultDTO.setLst(lst);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resultDTO.setMessage(e.getCause().getMessage());
            resultDTO.setKey(Constants.RESULT.ERROR);
        }
        return resultDTO;
    }

    @Override
    public ResultDTO search(News news) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
            resultDTO.setKey(Constants.RESULT.SUCCESS);
            StringBuilder hql = new StringBuilder("select t.* from News t join Category c on t.category_id = c.id and c.active = 1 where 1=1 ");
            if (!StringUtils.isStringNullOrEmpty(news.getTitle())) {
                hql.append(" and t.title like :p_title ");
            }
            if (news.getActive() != null) {
                hql.append(" and t.active = :p_active ");
            }
            if (news.getCategoryId() != null) {
                hql.append(" and t.category_id = :p_category_id ");
            }
            //use for check existed
            if (news.getId() != null) {
                hql.append(" and t.id <> :p_id ");
            }
            hql.append(" order by t.title ");

            Query query = entityManager.createNativeQuery(hql.toString(), News.class);
            if (!StringUtils.isStringNullOrEmpty(news.getTitle())) {
                query.setParameter("p_title", "%" + StringUtils.escapeStringSQL(news.getTitle()) + "%");
            }
            if (news.getActive() != null) {
                query.setParameter("p_active", news.getActive());
            }
            if (news.getCategoryId() != null) {
                query.setParameter("p_category_id", news.getCategoryId());
            }

            //use for check existed
            if (news.getId() != null) {
                query.setParameter("p_id", news.getId());
            }

            List<News> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                resultDTO.setLst(lst);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resultDTO.setMessage(e.getCause().getMessage());
            resultDTO.setKey(Constants.RESULT.ERROR);
        }
        return resultDTO;
    }

    @Override
    public ResultDTO findNewsById(Long id) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
            resultDTO.setKey(Constants.RESULT.SUCCESS);
            News s = entityManager.find(News.class, id);
            if (s != null) {
                resultDTO.setObject(s);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resultDTO.setMessage(e.getCause().getMessage());
            resultDTO.setKey(Constants.RESULT.ERROR);
        }
        return resultDTO;
    }

    @Override
    public ResultDTO deleteNews(Long id) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
            resultDTO.setKey(Constants.RESULT.SUCCESS);
            News s = entityManager.find(News.class, id);
            if (s != null) {
                entityManager.remove(s);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resultDTO.setMessage(e.getCause().getMessage());
            resultDTO.setKey(Constants.RESULT.ERROR);
        }
        return resultDTO;
    }

    @Override
    public ResultDTO findAllNewsByCategoryId(Long categoryId) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
            resultDTO.setKey(Constants.RESULT.SUCCESS);
            List<News> lst = entityManager.createQuery("from " + News.class.getSimpleName() + " where categoryId = :p_category ").setParameter("p_category", categoryId).getResultList();
            if (lst != null && !lst.isEmpty()) {
                resultDTO.setLst(lst);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resultDTO.setMessage(e.getCause().getMessage());
            resultDTO.setKey(Constants.RESULT.ERROR);
        }
        return resultDTO;
    }
}
