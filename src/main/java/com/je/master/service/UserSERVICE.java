/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.service;


import com.je.master.model.User;
import java.util.List;

/**
 *
 * @author T39UH
 */
public interface UserSERVICE {
    public List<User> getALL();
    public User getById (String id);
    public long insert(User user);
    public long update(User user);
    public long delete (User user);
    public long count();
}
