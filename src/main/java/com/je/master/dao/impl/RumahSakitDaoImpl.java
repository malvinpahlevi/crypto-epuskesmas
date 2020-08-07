/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.dao.impl;


import com.je.master.dao.RumahSakitDao;
import com.je.master.model.RumahSakit;
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




@Repository("rumahSakitDao")
public class RumahSakitDaoImpl  implements RumahSakitDao{

    private static final String SQL_SELECT_RUMAH_SAKIT= "SELECT KODE,NAMA,KOORDINAT,ALAMAT,EMAIL,TELP,KECAMATAN,KABUPATEN,PROPINSI,TYPE,KEPEMILIKAN,JENIS FROM RUMAH_SAKIT ";
    private static final String SQL_COUNT_RUMAH_SAKIT = "SELECT COUNT(*) FROM RUMAH_SAKIT ";
    private static final String SQL_SELECT_RUMAH_SAKIT_BY_ID = "SELECT KODE,NAMA,KOORDINAT,ALAMAT,EMAIL,TELP,KECAMATAN,KABUPATEN,PROPINSI,TYPE,KEPEMILIKAN,JENIS FROM RUMAH_SAKIT a WHERE a.KODE = ?";
    private static final String SQL_DELETE_RUMAH_SAKIT = "DELETE FROM RUMAH_SAKIT WHERE KODE = ?";
    private static final String SQL_INSERT_RUMAH_SAKIT = "INSERT INTO RUMAH_SAKIT (KODE,NAMA,KOORDINAT,ALAMAT,EMAIL,TELP,KECAMATAN,KABUPATEN,PROPINSI,TYPE,KEPEMILIKAN,JENIS) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_RUMAH_SAKIT = "UPDATE RUMAH_SAKIT SET NAMA=?, KOORDINAT=?, ALAMAT=?, EMAIL=?, TELP=?, KECAMATAN=?, KABUPATEN=?, PROPINSI=?, TYPE=?, KEPEMILIKAN=?, JENIS=? WHERE KODE=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    

    public List<RumahSakit> getAll(int start, int limit, String order, Map<String, String> params) {
        
    	List<RumahSakit> result = null;
    	try{
    		
            String where = Utils.getClauseWhere(params);
            String orderBy = Utils.getOrderBy(order);
            if (start > -1 && limit > 0) {
            	result =  jdbcTemplate.query(SQL_SELECT_RUMAH_SAKIT + where + " ORDER BY " + orderBy + "  LIMIT ?,?", new Object[]{start, limit}, new BeanPropertyRowMapper(RumahSakit.class));    
            } else {
            	result = jdbcTemplate.query(SQL_SELECT_RUMAH_SAKIT + where + " ORDER BY " + orderBy, new Object[]{}, new BeanPropertyRowMapper(RumahSakit.class));      
            }
            
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
        
        return result;
    }

    public RumahSakit getById(String id) {
    	
    	RumahSakit result = null;
    	try{
    		
    		  
    		result = (RumahSakit) jdbcTemplate.queryForObject(SQL_SELECT_RUMAH_SAKIT_BY_ID,
	        		new Object[]{id},new RowMapper<RumahSakit>() {
	        			public RumahSakit mapRow(ResultSet rs, int rowNum)
	        					throws SQLException {
	        				RumahSakit rumahsakit = new RumahSakit();
	        				rumahsakit.setKode(rs.getString("kode"));
	        				rumahsakit.setNama(rs.getString("nama"));
	        				rumahsakit.setAlamat(rs.getString("alamat"));
	        				rumahsakit.setKoordinat(rs.getString("koordinat"));
	        				rumahsakit.setKecamatan(rs.getString("kecamatan"));
	        				rumahsakit.setKabupaten(rs.getString("kabupaten"));
	        				rumahsakit.setPropinsi(rs.getString("propinsi"));
	        				rumahsakit.setEmail(rs.getString("email"));
	        				rumahsakit.setTelp(rs.getString("telp"));
	        				rumahsakit.setType(rs.getString("type"));
	        				rumahsakit.setKepemilikan(rs.getString("kepemilikan"));
	        				rumahsakit.setJenis(rs.getString("jenis"));
	        				return rumahsakit;
	        			}
	        	    	}
    			);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return result;
    	
    }
    

    public long create(RumahSakit rumahSakit) {
    	
    	 long result = 0;
    	 try{
    		 result = jdbcTemplate.update(SQL_INSERT_RUMAH_SAKIT, new Object[]{rumahSakit.getKode(),rumahSakit.getNama(),rumahSakit.getKoordinat(),rumahSakit.getAlamat(),rumahSakit.getEmail(),rumahSakit.getTelp(),rumahSakit.getKecamatan(),rumahSakit.getKabupaten(),rumahSakit.getPropinsi(),rumahSakit.getType(),rumahSakit.getKepemilikan(),rumahSakit.getJenis()});  	
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
    	 return result;
    }

    public long update(RumahSakit rumahSakit){
    	
    	 long result = 0;
    	 try{
    		 result = jdbcTemplate.update(SQL_UPDATE_RUMAH_SAKIT, new Object[]{rumahSakit.getNama(),rumahSakit.getKoordinat(),rumahSakit.getAlamat(),rumahSakit.getEmail(),rumahSakit.getTelp(),rumahSakit.getKecamatan(),rumahSakit.getKabupaten(),rumahSakit.getPropinsi(),rumahSakit.getType(),rumahSakit.getKepemilikan(),rumahSakit.getJenis(),rumahSakit.getKode()}); 	
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
    	 return result;

    }
    
    public long remove(RumahSakit rumahSakit) {
        
	   	 long result = 0;
		 try{
			 result = jdbcTemplate.update(SQL_DELETE_RUMAH_SAKIT, new Object[]{rumahSakit.getKode()});
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return result;
    }

	public long count(Map<String, String> params) {
    	
      	 long result = 0;
   		 try{
   			 
   			 String where = Utils.getClauseWhere(params);
   			 result = jdbcTemplate.queryForObject(SQL_COUNT_RUMAH_SAKIT + where, null,Long.class);
   	 
   		 }catch(Exception e){
   			 e.printStackTrace();
   		 }
   		 return result;
  
    }
}
