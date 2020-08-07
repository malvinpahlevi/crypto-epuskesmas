/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.dao.impl;

import com.api.nexmedia.util.AES256;
import com.je.master.dao.PenyakitDao;
import com.je.master.model.Penyakit;
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
@Repository("penyakitDao")
public class PenyakitDaoImpl implements PenyakitDao {
    
    private static final String SQL_SELECT_PENYAKIT = "SELECT id_penyakit,"
            + "penyakit_nama,"
            + "penyakit_deskripsi,"
            + "penyakit_jenis FROM _penyakit";
    private static final String SQL_SELECT_PENYAKIT_BY_ID = "SELECT id_penyakit,"
            + "penyakit_nama,"
            + "penyakit_deskripsi,"
            + "penyakit_jenis FROM _penyakit WHERE id_penyakit=? ";
    private static final String SQL_DELETE_PENYAKIT_BY_ID = "DELETE FROM _penyakit WHERE id_penyakit = ?";
    private static final String SQL_INSERT_PENYAKIT = "INSERT INTO _penyakit ("
            + "id_penyakit,"
            + "penyakit_nama,"
            + "penyakit_deskripsi,"
            + "penyakit_jenis) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE_PENYAKIT = "UPDATE _penyakit SET penyakit_nama=?,"
            + "penyakit_deskripsi=?,"
            + "penyakit_jenis=? WHERE id_penyakit=?";
    private static final String SQL_COUNT_PENYAKIT = "SELECT COUNT(*) FROM _penyakit";
    private static final String SQL_SEARCH = "SELECT * FROM _penyakit WHERE penyakit_nama like ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<Penyakit> getAll(int start, int limit, String order, Map<String, String> params) {
        List<Penyakit> result = null;
        String where = Utils.getClauseWhere(params);
        String orderBy = Utils.getOrderBy(order);
         if (start > -1 && limit > 0) {
            result =  jdbcTemplate.query(SQL_SELECT_PENYAKIT + where + " ORDER BY " + orderBy + "  LIMIT ?,?", new Object[]{start, limit}, new BeanPropertyRowMapper<Penyakit>(Penyakit.class));
        } else {
            result =  jdbcTemplate.query(SQL_SELECT_PENYAKIT, new BeanPropertyRowMapper<Penyakit>(Penyakit.class));
          
        }
         
         result = jdbcTemplate.query(SQL_SELECT_PENYAKIT, new Object[]{}, new RowMapper<Penyakit>(){
            @Override
            public Penyakit mapRow(ResultSet rs, int rowNum) throws SQLException {
                Penyakit penyakit = new Penyakit();
                penyakit.setId_penyakit(rs.getInt("id_penyakit"));
                penyakit.setPenyakit_nama(rs.getString("penyakit_nama"));
                penyakit.setPenyakit_deskripsi(rs.getString("penyakit_deskripsi"));
                penyakit.setPenyakit_jenis(rs.getString("penyakit_jenis"));
                
                return penyakit;
            }
         });
         return result;
    }

    @Override
    public Penyakit getById(int id_penyakit) {
        Penyakit result = null;
        try {
            result = (Penyakit) jdbcTemplate.queryForObject(SQL_SELECT_PENYAKIT_BY_ID, new Object[]{id_penyakit}, new RowMapper<Penyakit>() {
                public Penyakit mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Penyakit penyakit = new Penyakit();
                    
                    //Variabel untuk decrypt
                    String namaPenyakit = rs.getString("penyakit_nama");
                    String deskripsiPenyakit = rs.getString("penyakit_deskripsi");
                    String jenisPenyakit = rs.getString("penyakit_jenis");
                    
                    penyakit.setId_penyakit(rs.getInt("id_penyakit"));
                    penyakit.setPenyakit_nama(AES256.decrypt(namaPenyakit));
                    penyakit.setPenyakit_deskripsi(AES256.decrypt(deskripsiPenyakit));
                    penyakit.setPenyakit_jenis(AES256.decrypt(jenisPenyakit));
                    
                    return penyakit;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public long insert(Penyakit penyakit) {
        long result = 0;
        try {
            result = jdbcTemplate.update(SQL_INSERT_PENYAKIT, new Object[]{
                penyakit.getId_penyakit(),
                penyakit.getPenyakit_nama(),
                penyakit.getPenyakit_deskripsi(),
                penyakit.getPenyakit_jenis()
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public long update(Penyakit penyakit) {
        long result = 0;
        try {
            result = jdbcTemplate.update(SQL_UPDATE_PENYAKIT, new Object[]{
               penyakit.getPenyakit_nama(),
               penyakit.getPenyakit_deskripsi(),
               penyakit.getPenyakit_jenis(),
               penyakit.getId_penyakit()
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public long delete(Penyakit penyakit) {
        long result = 0;
        
        result = jdbcTemplate.update(SQL_DELETE_PENYAKIT_BY_ID, new Object[]{
           penyakit.getId_penyakit()
        });
        return result;
    }

    @Override
    public long count() {
        long count = 0;
        try {
            count = jdbcTemplate.queryForObject(SQL_COUNT_PENYAKIT, null, Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Penyakit> searchData(String keyword) {
        String searchKeyword = "%" + keyword + "%";
        List<Penyakit> listPenyakit = null;
        listPenyakit = jdbcTemplate.query(SQL_SEARCH, new Object[]{searchKeyword}, new BeanPropertyRowMapper(Penyakit.class));
        
        return listPenyakit;
    }
}
