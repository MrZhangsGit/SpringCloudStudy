package com.oeasycloud.mymybatisserver.Controller;

import com.oeasycloud.mymybatisserver.dao.UserDao;
import com.oeasycloud.mymybatisserver.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/3/1
 */
@RestController
public class UserRestController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/api/saveUser", method = RequestMethod.GET)
    public Integer saveUser(@RequestBody UserEntity user) throws Exception {
        userDao.saveUser(user);
        return 1;
    }

    @RequestMapping(value = "/api/findUserByUserName", method = RequestMethod.GET)
    public UserEntity findUserByUserName(@RequestParam(value = "userName") String userName) throws Exception {
        return userDao.findUserByUserName(userName);
    }

    @RequestMapping(value = "/api/updateUser", method = RequestMethod.GET)
    public Integer updateUser(@RequestBody UserEntity user) throws Exception {
        userDao.updateUser(user);
        return 1;
    }
}
