/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.service.impl;


import com.je.master.dao.UserDAO;
import com.je.master.model.User;
import com.je.master.service.UserSERVICE;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author T39UH
 */
@Service("userService")
@Transactional
public class UserSERVICEImpl implements UserSERVICE {

    
    @Autowired
    UserDAO userdao;
    
    @Override
    public List<User> getALL() {
            return userdao.getALL();
    }

    @Override
    public User getById(String id) {
       return userdao.getById(id);
    }

    @Override
    public long insert(User user) {
        return userdao.insert(user);
    }

    @Override
    public long update(User user) {
        return userdao.update(user);
    }

    @Override
    public long delete(User user) {
        return userdao.delete(user);
    }

    @Override
    public long count() {
        return userdao.count();
    }
    
}
