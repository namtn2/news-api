package com.api.news.demo.repository;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.utils.Constants;
import com.api.news.demo.model.User;
import com.api.news.demo.utils.StringUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

@Repository
@Log4j
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResultDTO login(User user) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            List<User> lst = entityManager.createQuery("select t from " + User.class.getSimpleName() + " t WHERE t.email = :p_email")
                    .setParameter("p_email", user.getEmail())
                    .getResultList();
            if (lst != null && !lst.isEmpty()) {
                User u = lst.get(0);
                if (!StringUtils.isStringNullOrEmpty(user.getPassword())) {
                    if (!passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                        resultDTO.setMessage("Login failed, please check your email or password");
                        return resultDTO;
                    }
                }

                resultDTO.setObject(u);
                resultDTO.setMessage(Constants.RESULT.SUCCESS);
                resultDTO.setKey(Constants.RESULT.SUCCESS);
            } else {
                resultDTO.setMessage("Login failed, please check your email or password");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resultDTO.setMessage(e.getCause().getMessage());
            resultDTO.setKey(Constants.RESULT.ERROR);
        }
        return resultDTO;
    }

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
            if (!StringUtils.isStringNullOrEmpty(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }

        User userCreate = entityManager.merge(user);
        if (userCreate != null && !StringUtils.isLongNullOrZero(userCreate.getId())) {
            resultDTO.setObject(userCreate);
            if (StringUtils.isLongNullOrZero(user.getId())) {
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

            if (!StringUtils.isStringNullOrEmpty(user.getEmail())) {
                hql.append(" and lower(t.email) = lower(:p_email) ");
            }

            Query query = entityManager.createNativeQuery(hql.toString(), User.class);
            if (!StringUtils.isStringNullOrEmpty(user.getEmail())) {
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
    public ResultDTO findUserById(Long id) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            resultDTO.setMessage(Constants.RESULT.SUCCESS);
            resultDTO.setKey(Constants.RESULT.SUCCESS);
            User s = entityManager.find(User.class, id);
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
}
