package com.bushengshi.miaosha.service;

import com.bushengshi.miaosha.dao.UserDao;
import com.bushengshi.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx() {
        User user1 = new User();
        user1.setId(2);
        user1.setName("许文强");
        userDao.insert(user1);

        User user2 = new User();
        user2.setId(1);
        user2.setName("丁力");
        userDao.insert(user1);

        return true;
    }
}
