package com.api.news.demo.repository;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.Category;
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
public class CategoryRepositoryImpl implements CategoryRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public ResultDTO createOrUpdateCategory(Category category) {
        ResultDTO resultDTO = new ResultDTO();

        if (StringUtils.isStringNullOrEmpty(category.getName())) {
            resultDTO.setMessage("Category name is required !");
            return resultDTO;
        }

        Category categoryClone = category.clone();
        categoryClone.setActive(null);
        ResultDTO checkExisted = search(categoryClone);
        if (checkExisted.getLst() != null && !checkExisted.getLst().isEmpty()) {
            resultDTO.setMessage("Category is existed !");
            return resultDTO;
        }

        Category categoryCreate = entityManager.merge(category);
        if (categoryCreate != null && !StringUtils.isLongNullOrZero(categoryCreate.getId())) {
            if (StringUtils.isLongNullOrZero(category.getId())) {
                resultDTO.setId(categoryCreate.getId().toString());
            }
            resultDTO.setKey(Constants.RESULT.SUCCESS);
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
        }
        return resultDTO;
    }

    @Override
    public ResultDTO findAllCategory() {
        ResultDTO resultDTO = new ResultDTO();
        try {
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
            resultDTO.setKey(Constants.RESULT.SUCCESS);
            List<Category> lst = entityManager.createQuery("select t from " + Category.class.getSimpleName() + " t order by t.name ").getResultList();
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
    public ResultDTO search(Category category) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
            resultDTO.setKey(Constants.RESULT.SUCCESS);
            StringBuilder hql = new StringBuilder("select t from Category t where 1=1 ");
            if (!StringUtils.isStringNullOrEmpty(category.getName())) {
                hql.append(" and t.name like :p_name ");
            }
            if (category.getActive() != null) {
                hql.append(" and t.active = :p_active ");
            }
            //use for check existed
            if (category.getId() != null) {
                hql.append(" and t.id <> :p_id ");
            }
            hql.append(" order by t.name ");

            Query query = entityManager.createQuery(hql.toString());
            if (!StringUtils.isStringNullOrEmpty(category.getName())) {
                query.setParameter("p_name", "%" + StringUtils.escapeStringSQL(category.getName()) + "%");
            }
            if (category.getActive() != null) {
                query.setParameter("p_active", category.getActive());
            }

            //use for check existed
            if (category.getId() != null) {
                query.setParameter("p_id", category.getId());
            }

            List<Category> lst = query.getResultList();
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
    public ResultDTO findCategoryById(Long id) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
            resultDTO.setKey(Constants.RESULT.SUCCESS);
            Category s = entityManager.find(Category.class, id);
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
    public ResultDTO deleteCategory(Long id) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
            resultDTO.setKey(Constants.RESULT.SUCCESS);
            Category s = entityManager.find(Category.class, id);
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
}
