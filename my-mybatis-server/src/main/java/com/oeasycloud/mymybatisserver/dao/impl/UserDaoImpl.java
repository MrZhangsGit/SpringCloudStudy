package com.oeasycloud.mymybatisserver.dao.impl;

import com.oeasycloud.mymybatisserver.dao.UserDao;
import com.oeasycloud.mymybatisserver.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/3/1
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveUser(UserEntity user) {
        mongoTemplate.save(user);
    }

    @Override
    public UserEntity findUserByUserName(String userName) {
        Query query = new Query(Criteria.where("userName").is(userName));
        UserEntity userEntity = mongoTemplate.findOne(query, UserEntity.class);
        return userEntity;
    }

    @Override
    public void updateUser(UserEntity user) {
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("userName", user.getUserName()).set("passWord", user.getPassWord());

        /**
         * 更新查询返回结果集的第一条
         */
        mongoTemplate.updateFirst(query, update, UserEntity.class);
        /**
         * 更新查询返回结果集的所有
         * mongoTemplate.updateMulti(query, update, UserEntity.class);
         */
    }

    @Override
    public void deleteUserById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, UserEntity.class);
    }
}
