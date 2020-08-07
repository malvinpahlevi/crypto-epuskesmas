/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.dao.impl;

import com.api.nexmedia.util.AES256;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.je.master.dao.DokterDao;
import com.je.master.model.Dokter;
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

/**
 *
 * @author AJ.P
 */
@Repository("dokterDao")
public class DokterDaoImpl implements DokterDao {
    private Gson gson = new GsonBuilder().create();
    
    private static final String SQL_SELECT_DOKTER = "SELECT id_dokter,"
            + "dokter_nip,"
            + "dokter_nama,"
            + "dokter_jenkel,"
            + "dokter_spesialis FROM _dokter";
    private static final String SQL_SELECT_DOKTER_BY_ID = "SELECT id_dokter,"
            + "dokter_nip,"
            + "dokter_nama,"
            + "dokter_jenkel,"
            + "dokter_spesialis FROM _dokter WHERE id_dokter = ?";
    private static final String SQL_DELETE_DOKTER_BY_ID = "DELETE FROM _dokter WHERE id_dokter = ?";
    private static final String SQL_INSERT_DOKTER = "INSERT INTO _dokter ("
            + "id_dokter,"
            + "dokter_nip,"
            + "dokter_nama,"
            + "dokter_jenkel,"
            + "dokter_spesialis) values (?,?,?,?,?)";
    private static final String SQL_UPDATE_DOKTER = "UPDATE _dokter SET dokter_nip=?,"
            + "dokter_nama=?,"
            + "dokter_jenkel=?,"
            + "dokter_spesialis=? WHERE id_dokter=?";
    private static final String SQL_COUNT_DOKTER = "SELECT COUNT(*) FROM _dokter";
    private static final String SQL_SEARCH = "SELECT * FROM _dokter WHERE dokter_nip like ?";
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Dokter> getAll(int start, int limit, String order, Map<String, String> params) {
        List<Dokter> result = null;
        String where = Utils.getClauseWhere(params);
        String orderBy = Utils.getOrderBy(order);
         if (start > -1 && limit > 0) {
            result =  jdbcTemplate.query(SQL_SELECT_DOKTER + where + " ORDER BY " + orderBy + "  LIMIT ?,?", new Object[]{start, limit}, new BeanPropertyRowMapper<Dokter>(Dokter.class));
        } else {
            result =  jdbcTemplate.query(SQL_SELECT_DOKTER, new BeanPropertyRowMapper<Dokter>(Dokter.class));
          
        }
        
        result = jdbcTemplate.query(SQL_SELECT_DOKTER, new Object[]{}, new RowMapper<Dokter>(){
            @Override
            public Dokter mapRow(ResultSet rs, int rowNum) 
                    throws SQLException {
                Dokter dokter = new Dokter();
                
                String nip = rs.getString("dokter_nip");
                
                dokter.setId_dokter(rs.getInt("id_dokter"));
                dokter.setDokter_nip(nip);
                dokter.setDokter_nama(rs.getString("dokter_nama"));
                dokter.setDokter_jenkel(rs.getString("dokter_jenkel"));
                dokter.setDokter_spesialis(rs.getString("dokter_spesialis"));
                
                
                System.out.println("isi cek dokter" + gson.toJson(dokter));
                return dokter;
            }
           
        });
       return result;
    
    }
    

    public Dokter getById(int id_dokter) {
        Dokter result = null;
        try {
            result = (Dokter) jdbcTemplate.queryForObject(SQL_SELECT_DOKTER_BY_ID, new Object[]{id_dokter}, new RowMapper<Dokter>(){
                @Override
                public Dokter mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Dokter dokter = new Dokter();
                    
                    //Variabel untuk decrypt
                    String nip = rs.getString("dokter_nip");
                    String namaDokter = rs.getString("dokter_nama");
                    String jenkelDokter = rs.getString("dokter_jenkel");
                    String spesialisDokter = rs.getString("dokter_spesialis");
                    
                    
                    dokter.setId_dokter(rs.getInt("id_dokter"));
                    dokter.setDokter_nip(AES256.decrypt(nip));
                    dokter.setDokter_nama(AES256.decrypt(namaDokter));
                    dokter.setDokter_jenkel(AES256.decrypt(jenkelDokter));
                    dokter.setDokter_spesialis(AES256.decrypt(spesialisDokter));
                
                    return dokter;
                }
               
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public long insert(Dokter dokter) {
        long result = 0;
        try {
            result = jdbcTemplate.update(SQL_INSERT_DOKTER, new Object[]{
               dokter.getId_dokter(),
               dokter.getDokter_nip(),
               dokter.getDokter_nama(),
               dokter.getDokter_jenkel(),
               dokter.getDokter_spesialis()
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public long update(Dokter dokter) {
        long result = 0;
        try {
            result = jdbcTemplate.update(SQL_UPDATE_DOKTER, new Object[]{
                dokter.getDokter_nip(),
                dokter.getDokter_nama(),
                dokter.getDokter_jenkel(),
                dokter.getDokter_spesialis(),
                dokter.getId_dokter()
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public long delete(Dokter dokter) {
        long result = 0;
        
        result = jdbcTemplate.update(SQL_DELETE_DOKTER_BY_ID, new Object[]{
            dokter.getId_dokter()
        });
        return result;
    }

    @Override
    public long count() {
        long count = 0;
        try {
            count = jdbcTemplate.queryForObject(SQL_COUNT_DOKTER, null, Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Dokter> searchData(String keyword) {
        String searchKeyword = "%" + keyword + "%";
        List<Dokter> listDokter = null;
        listDokter = jdbcTemplate.query(SQL_SEARCH, new Object[]{searchKeyword}, new BeanPropertyRowMapper(Dokter.class));
        
        return listDokter;
    }
        
}
