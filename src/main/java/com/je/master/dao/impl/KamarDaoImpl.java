/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.dao.impl;

import com.je.master.dao.KamarDao;
import com.je.master.model.Kamar;
import com.je.util.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("kamarDao")
public class KamarDaoImpl implements KamarDao {

    private static final String SQL_SELECT_KAMAR= "SELECT KODE,NAMA,KETERANGAN FROM KAMAR";
    private static final String SQL_SELECT_KAMAR_BY_ID = "SELECT KODE,NAMA,KETERANGAN FROM KAMAR WHERE KODE = ?";
    private static final String SQL_DELETE_KAMAR_BY_ID = "DELETE FROM KAMAR WHERE KODE = ?";
    private static final String SQL_INSERT_KAMAR = "INSERT INTO KAMAR (KODE,NAMA,KETERANGAN) VALUES (?,?,?)";
    private static final String SQL_UPDATE_KAMAR = "UPDATE KAMAR SET NAMA=?, KETERANGAN=?  WHERE KODE=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Kamar> getAll(int start, int limit, String order, Map<String, String> params) {
        
        List<Kamar> result = null;
        String where = Utils.getClauseWhere(params);
        String orderBy = Utils.getOrderBy(order);
        if (start > -1 && limit > 0) {
            result =  jdbcTemplate.query(SQL_SELECT_KAMAR + where + " ORDER BY " + orderBy + "  LIMIT ?,?", new Object[]{start, limit}, new BeanPropertyRowMapper<Kamar>(Kamar.class));
        } else {
            result =  jdbcTemplate.query(SQL_SELECT_KAMAR + where + " ORDER BY " + orderBy, new Object[]{}, new BeanPropertyRowMapper<Kamar>(Kamar.class));
          
        }
        
        return result;
    }

    public Kamar getById(String id) {
        Kamar result = null;
    	try{
    		
    		  
    		result = (Kamar) jdbcTemplate.queryForObject(SQL_SELECT_KAMAR_BY_ID,
	        		new Object[]{id},new RowMapper<Kamar>() {
	        			public Kamar mapRow(ResultSet rs, int rowNum)
	        					throws SQLException {
	        				Kamar kamar = new Kamar();
	        				kamar.setKode(rs.getString("kode"));
	        				kamar.setNama(rs.getString("nama"));
                                                kamar.setKeterangan(rs.getString("keterangan"));
	        				return kamar;
	        			}
	        	    	}
    			);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return result;
    }

    public long insert(Kamar kamar) {
     long result = 0;
    	 try{
    		 result = jdbcTemplate.update(SQL_INSERT_KAMAR, new Object[]{kamar.getKode(),kamar.getNama(),kamar.getKeterangan()});  	
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
    	 return result;
    }
    public long update(Kamar kamar){
    long result = 0;
    	 try{
    		 result = jdbcTemplate.update(SQL_UPDATE_KAMAR, new Object[]{kamar.getNama(),kamar.getKeterangan(),kamar.getKode()}); 	
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
    	 return result;
    }
    
    public long delete(Kamar kamar) {
    	   	 long result = 0;
		 try{
			 result = jdbcTemplate.update(SQL_DELETE_KAMAR_BY_ID, new Object[]{kamar.getKode()});
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return result;
    }


   
}
