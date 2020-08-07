/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.dao.impl;



import com.je.master.dao.UserDAO;
import com.je.master.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author T39UH
 */

@Repository("userDao")
public class UserDAOImpl implements UserDAO {
    
    private static final String SQL_SELECT_USER_ALL = "SELECT id,username,password,level,email,devisi FROM user";
    private static final String SQL_SELECT_USER_BYID = "SELECT username, password,level,email,devisi from user WHERE id = ?";
    private static final String SQL_COUNT_USER= "SELECT COUNT(*) FROM user ";
    private static final String SQL_INSERT_USER= "INSERT INTO user (id,username,password,level,email,devisi) values(?,?,?,?,?,?)";
    private static final String SQL_UPDATE_USER = "UPDATE user SET username=?, password=?, level=?, email=?, devisi=? WHERE id=?"; 
    private static final String SQL_DELETE_USER= "DELETE FROM user WHERE id=?";
    
    @Autowired
    private JdbcTemplate jdbcTemplate; 
     
   
    
    public List<User> getALL() {
        List<User> userList = null;
        
        try{
                    userList = jdbcTemplate.query(SQL_SELECT_USER_ALL, new Object[]{},new BeanPropertyRowMapper(User.class));
        
        }catch(Exception e){
                e.printStackTrace();
        
        }
        return userList;
        
    }

    
    public User getById(String id) {
     User result = null;
     try{
         result = (User) jdbcTemplate.queryForObject(SQL_SELECT_USER_BYID, 
                 new Object[]{id}, new RowMapper<User>(){
             public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                 User user = new User();
                 user.setId(rs.getString("id"));
                 user.setUsername(rs.getString("username"));
                 user.setPassword(rs.getString("password"));
                 user.setLevel(rs.getString("level"));
                 user.setEmail(rs.getString("email"));
                 user.setDevisi(rs.getString("devisi"));
                 
                 return user;
             }
            
         
         
         
         }
         );
     
     
     
     }catch(Exception e){
         e.printStackTrace();
     
     }
     
     return result;
    }

  
    public long insert(User user) {
        long result = 0;
        try{
            
            result = jdbcTemplate.update(SQL_INSERT_USER, new Object[]{user.getId(),user.getUsername(),user.getPassword(),user.getLevel(),user.getEmail(),user.getDevisi()});
        
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }

    
    public long update(User user) {
       long result = 0;
       try{
           result = jdbcTemplate.update(SQL_UPDATE_USER, new Object[]{user.getUsername(),user.getPassword(),user.getLevel(),user.getEmail(),user.getDevisi(),user.getId()});
       
       
       
       }catch(Exception e){
           e.printStackTrace();
       }
       return result;
    }

  
    public long delete(User user) {
         long result = 0;
         try{
             
             result = jdbcTemplate.update(SQL_DELETE_USER, new Object[]{user.getId()});
         
         
         
         }catch(Exception e){
             e.printStackTrace();
         
         }
         return result;
    }

    
    public long count() {
        long count = 0;
        
        try{
            count = jdbcTemplate.queryForObject(SQL_COUNT_USER, null, Long.class);
        
        
        
        }catch(Exception e){
        e.printStackTrace();
        
        }
        return count;
    }
    
}
