/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.controller;



import com.je.master.model.User;

import com.je.master.service.UserSERVICE;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.je.util.Constants;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


/**
 *
 * @author T39UH
 */
@Controller
public class UserController {
       private static final String User_KEY = "DataUser";

   
    
    @Autowired
    private UserSERVICE userService;
    
       
     @RequestMapping(value = "/masterdata/user", method = RequestMethod.GET, produces = "application/json")
     public @ResponseBody Map<String, Object> getAll(){
         
     Map<String, Object> response = new HashMap<String, Object>();
     
     
         
         List <User> userlist = userService.getALL();
         long count = userService.count();
         
         
        
         // response.put(Constants.TOTAL,userlist);
          response.put(Constants.LIST, userlist);
          
          response.put(Constants.OK, count);
         
     
     
   
     return response;
     
     }
      @RequestMapping(value = "/masterdata/user/{kode}", method = RequestMethod.DELETE, produces = "application/json")
    public Map<String, Object> delete(@PathVariable("kode") final String kode) {
        
        User user = new User();
        user.setId(kode);
         Map<String, Object> respMap = new HashMap<String, Object>();
         respMap.put(Constants.STATUS, (userService.delete(user) != 0) ? Constants.OK : Constants.ERROR);
     
        return respMap;
    }
    
    
        @RequestMapping(value = "/masterdata/user/{kode}", method = RequestMethod.GET, produces = "application/json")
        public Map<String, Object> edit(@PathVariable("kode") String kode) {
      
    	 User user = userService.getById(kode);

         Map<String, Object> respMap = new HashMap<String, Object>();
         respMap.put(User_KEY, user);

         return respMap;
      
      
      }
        
        @SuppressWarnings("unchecked")
        @RequestMapping(value = "/masterdata/user", method = RequestMethod.POST, produces = "application/json")
        public Map<String, Object> create(@RequestBody final Map<String, Object> params){
            
        Map<String, Object> userMap = (Map<String, Object>) params.get(User_KEY);
                     User user = new User();
                     user.setId((String) userMap.get("id"));
                     user.setUsername((String)userMap.get("username"));
                     user.setPassword((String) userMap.get("password"));
                     user.setLevel((String) userMap.get("level"));
                     user.setEmail((String) userMap.get("email"));
                     user.setDevisi((String) userMap.get("devisi"));
                 
                     Map<String, Object> respMap = new HashMap<String, Object>();
                     respMap.put(Constants.STATUS, (userService.insert(user) != 0) ? Constants.OK : Constants.ERROR);

                        return respMap;
        
        }
        
         @SuppressWarnings("unchecked")
          @RequestMapping(value = "/masterdata/user/{kode}", method = RequestMethod.PUT, produces = "application/json")
           public Map<String, Object> update(@PathVariable("kode") final String kode,
           @RequestBody final Map<String, Object> params){
           
               Map<String, Object> userMap = (Map<String, Object>) params.get(User_KEY);
               
               User user = new User();
               user.setId(kode);
               user.setUsername((String)userMap.get("username"));
               user.setPassword((String)userMap.get("password"));
               user.setLevel((String)userMap.get("level"));
               user.setEmail((String)userMap.get("email"));
               user.setDevisi((String)userMap.get("devisi"));
               
               Map<String, Object> respMap = new HashMap<String, Object>();
               respMap.put(Constants.STATUS, ( userService.update(user) != 0) ? Constants.OK : Constants.ERROR);
               
               return userMap;
               
           
           
           }
           

}
