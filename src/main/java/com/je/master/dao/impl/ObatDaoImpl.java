/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.dao.impl;

import com.api.nexmedia.util.AES256;
import com.je.master.dao.ObatDao;
import com.je.master.model.Obat;
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
@Repository("obatDao")
public class ObatDaoImpl implements ObatDao {
    
    private static final String SQL_SELECT_OBAT = "SELECT id_obat,"
            + "kd_obat,"
            + "obat_nama,"
            + "obat_jenis,"
            + "obat_kadaluarsa,"
            + "obat_produsen FROM _obat";
    private static final String SQL_SELECT_OBAT_BY_ID = "SELECT id_obat,"
            + "kd_obat,"
            + "obat_nama,"
            + "obat_jenis,"
            + "obat_kadaluarsa,"
            + "obat_produsen FROM _obat WHERE id_obat = ?";
    private static final String SQL_DELETE_OBAT_BY_ID = "DELETE FROM _obat WHERE id_obat = ?";
    private static final String SQL_INSERT_OBAT = "INSERT INTO _obat ("
            + "id_obat,"
            + "kd_obat,"
            + "obat_nama,"
            + "obat_jenis,"
            + "obat_kadaluarsa,"
            + "obat_produsen) VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE_OBAT = "UPDATE _obat SET kd_obat=?,"
            + "obat_nama=?,"
            + "obat_jenis=?,"
            + "obat_kadaluarsa=?,"
            + "obat_produsen=? WHERE id_obat=?";
    private static final String SQL_COUNT_OBAT = "SELECT COUNT(*) FROM _obat";
    private static final String SQL_SEARCH = "SELECT * FROM _obat WHERE kd_obat like ?";
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<Obat> getAll(int start, int limit, String order, Map<String, String> params) {
        List<Obat> result = null;
        String where = Utils.getClauseWhere(params);
        String orderBy = Utils.getOrderBy(order);
         if (start > -1 && limit > 0) {
            result =  jdbcTemplate.query(SQL_SELECT_OBAT + where + " ORDER BY " + orderBy + "  LIMIT ?,?", new Object[]{start, limit}, new BeanPropertyRowMapper<Obat>(Obat.class));
        } else {
            result =  jdbcTemplate.query(SQL_SELECT_OBAT, new BeanPropertyRowMapper<Obat>(Obat.class));
          
        }
         
        result = jdbcTemplate.query(SQL_SELECT_OBAT, new Object[]{}, new RowMapper<Obat>(){
            @Override
            public Obat mapRow(ResultSet rs, int rowNum) throws SQLException {
                Obat obat = new Obat();
                obat.setId_obat(rs.getInt("id_obat"));
                obat.setKd_obat(rs.getString("kd_obat"));
                obat.setObat_nama(rs.getString("obat_nama"));
                obat.setObat_jenis(rs.getString("obat_jenis"));
                obat.setObat_kadaluarsa(rs.getString("obat_kadaluarsa"));
                obat.setObat_produsen(rs.getString("obat_produsen"));
                
                return obat;
            } 
        });
        return result;
    }

    @Override
    public Obat getById(int id_obat) {
        Obat result = null;
        try {
            result = (Obat) jdbcTemplate.queryForObject(SQL_SELECT_OBAT_BY_ID, new Object[]{id_obat}, new RowMapper<Obat>() {
                @Override
                public Obat mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Obat obat = new Obat();
                    
                    // Variabel untuk decrypt
                    String kodeObat = rs.getString("kd_obat");
                    String namaObat = rs.getString("obat_nama");
                    String jenisObat = rs.getString("obat_jenis");
                    String kadaluarsaObat = rs.getString("obat_kadaluarsa");
                    String produsenObat = rs.getString("obat_produsen");
                    
                    obat.setId_obat(rs.getInt("id_obat"));
                    obat.setKd_obat(AES256.decrypt(kodeObat));
                    obat.setObat_nama(AES256.decrypt(namaObat));
                    obat.setObat_jenis(AES256.decrypt(jenisObat));
                    obat.setObat_kadaluarsa(AES256.decrypt(kadaluarsaObat));
                    obat.setObat_produsen(AES256.decrypt(produsenObat));
                    
                    return obat;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public long insert(Obat obat) {
        long result = 0;
        try {
            result = jdbcTemplate.update(SQL_INSERT_OBAT, new Object[]{
               obat.getId_obat(),
               obat.getKd_obat(),
               obat.getObat_nama(),
               obat.getObat_jenis(),
               obat.getObat_kadaluarsa(),
               obat.getObat_produsen()
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public long update(Obat obat) {
        long result = 0;
        try {
            result = jdbcTemplate.update(SQL_UPDATE_OBAT, new Object[]{
               obat.getKd_obat(),
               obat.getObat_nama(),
               obat.getObat_jenis(),
               obat.getObat_kadaluarsa(),
               obat.getObat_produsen(),
               obat.getId_obat()
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public long delete(Obat obat) {
        long result = 0;
        
        result = jdbcTemplate.update(SQL_DELETE_OBAT_BY_ID, new Object[]{
            obat.getId_obat()
        });
        return result;
    }

    @Override
    public long count() {
        long count = 0;
        try {
            count = jdbcTemplate.queryForObject(SQL_COUNT_OBAT, null, Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Obat> searchData(String keyword) {
        String searchKeyword = "%" + keyword + "%";
        List<Obat> listObat = null;
        listObat = jdbcTemplate.query(SQL_SEARCH, new Object[]{searchKeyword}, new BeanPropertyRowMapper(Obat.class));
        
        return listObat;
    }
    
}
