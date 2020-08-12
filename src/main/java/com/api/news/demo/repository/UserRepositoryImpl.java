package com.api.news.demo.repository;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.utils.Constants;
import com.api.news.demo.model.User;
import com.api.news.demo.utils.Utils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.crypto.password.PasswordEncoder;

@Repository
@Log4j
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${user.default.password:null}")
    private String defaultPassword;

    @Override
    public ResultDTO createOrUpdateUser(User user) {
        ResultDTO resultDTO = new ResultDTO();

        boolean check = isUserExist(user);
        if (check) {
            resultDTO.setMessage("This user is already exists, try another email/phone number");
            return resultDTO;
        }
        if (user.getId() == null) {
            user.setCreateTime(new Date());
            user.setPassword(passwordEncoder.encode(Utils.isStringNullOrEmpty(user.getPassword()) ? defaultPassword : user.getPassword()));
        }

        User userCreate = entityManager.merge(user);
        if (userCreate != null && !Utils.isLongNullOrZero(userCreate.getId())) {
            resultDTO.setObject(userCreate);
            if (Utils.isLongNullOrZero(user.getId())) {
                resultDTO.setId(userCreate.getId().toString());
            }
            resultDTO.setKey(Constants.RESULT.SUCCESS);
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
        }
        return resultDTO;
    }

    @Override
    public ResultDTO searchUser(User user) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
            resultDTO.setKey(Constants.RESULT.SUCCESS);
            StringBuilder hql = new StringBuilder("select t.* from User t where 1=1");

            if (!Utils.isStringNullOrEmpty(user.getEmail())) {
                hql.append(" and lower(t.email) = lower(:p_email) ");
            }

            Query query = entityManager.createNativeQuery(hql.toString(), User.class);
            if (!Utils.isStringNullOrEmpty(user.getEmail())) {
                query.setParameter("p_email", user.getEmail());
            }

            List<User> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                resultDTO.setObject(lst.get(0));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resultDTO.setMessage(e.getCause().getMessage());
            resultDTO.setKey(Constants.RESULT.ERROR);
        }
        return resultDTO;
    }

    private boolean isUserExist(User user) {
        String hql = "select t from " + User.class.getSimpleName() + " t WHERE (t.email = :p_email OR t.phone = :p_phone) ";
        if (user.getId() != null) {
            hql += " and t.id <> :p_id ";
        }
        Query query = entityManager.createQuery(hql);
        query.setParameter("p_email", user.getEmail())
                .setParameter("p_phone", user.getPhone());
        if (user.getId() != null) {
            query.setParameter("p_id", user.getId());
        }

        long l = query.getResultStream().count();
        if (l == 0) {
            return false;
        }
        return true;
    }

    @Override
    public ResultDTO findUserById(List<Long> id) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            String sql = "SELECT u FROM User u WHERE id IN (:p_id) ";
            Query createQuery = entityManager.createQuery(sql, User.class).setParameter("p_id", id);
            List<User> resultList = createQuery.getResultList();
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
            resultDTO.setKey(Constants.RESULT.SUCCESS);
            if (resultList != null && !resultList.isEmpty()) {
                resultDTO.setLst(resultList);
            }
        } catch (Exception e) {
            log.error(e.getCause().getMessage());
            resultDTO.setMessage(e.getCause().getMessage());
            resultDTO.setKey(Constants.RESULT.ERROR);
        }
        return resultDTO;
    }

    @Override
    public List<User> searchUserByNameAndEmail(User user) {
        try {
            StringBuilder hql = new StringBuilder("select t.* from User t where 1=1 ");
            if (!Utils.isStringNullOrEmpty(user.getEmail())) {
                hql.append(" and ( lower(t.email) like lower(:p_email) escape '\\\\'  or lower(t.name) like lower(:p_name) escape '\\\\' ) ");
            }

            Query query = entityManager.createNativeQuery(hql.toString(), User.class);
            if (!Utils.isStringNullOrEmpty(user.getEmail())) {
                query.setParameter("p_email", "%" + Utils.escapeStringSQL(user.getEmail()) + "%");
                query.setParameter("p_name", "%" + Utils.escapeStringSQL(user.getEmail()) + "%");
            }

            return query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }
}
