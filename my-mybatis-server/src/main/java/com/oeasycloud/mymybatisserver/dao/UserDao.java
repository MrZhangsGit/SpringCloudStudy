package com.oeasycloud.mymybatisserver.dao;

import com.oeasycloud.mymybatisserver.domain.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/2/27
 */
public interface UserDao{
    void saveUser(UserEntity user);

    UserEntity findUserByUserName(String userName);

    void updateUser(UserEntity user);

    void deleteUserById(Long id);
}
