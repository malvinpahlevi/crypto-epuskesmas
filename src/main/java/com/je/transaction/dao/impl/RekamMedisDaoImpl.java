/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaction.dao.impl;

import com.api.nexmedia.util.AES256;
import com.google.gson.Gson;
import com.je.master.model.Dokter;
import com.je.master.model.Pasien;
import com.je.master.model.Penyakit;
import com.je.transaction.dao.RekamMedisDao;
import com.je.transaction.model.RekamMedis;
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
@Repository("rekamMedisDao")
public class RekamMedisDaoImpl implements RekamMedisDao {
    
    private static final String SQL_SELECT_REKAMMEDIS = "SELECT * FROM _rekammedis rm, _dokter d , _pasien ps , _penyakit p "
            + "WHERE rm.id_dokter = d.id_dokter "
            + "and rm.id_pasien = ps.id_pasien "
            + "and rm.id_penyakit = p.id_penyakit";
    
    private static final String SQL_SELECT_REKAMMEDIS_BY_ID = "SELECT * FROM _rekammedis rm, _dokter d , _pasien ps , _penyakit p "
            + "WHERE rm.id_dokter = d.id_dokter "
            + "and rm.id_pasien = ps.id_pasien "
            + "and rm.id_penyakit = p.id_penyakit "
            + "and rm.rekammedis_id=?";
    private static final String SQL_DELETE_REKAMMEDIS_BY_ID = "DELETE FROM _rekammedis WHERE rekammedis_id = ?";
    private static final String SQL_INSERT_REKAMMEDIS = "INSERT INTO _rekammedis ("
            + "rekammedis_id,"
            + "id_dokter,"
            + "id_pasien,"
            + "id_penyakit,"
            + "rekammedis_tglkunjungan,"
            + "rekammedis_keluhan,"
            + "rekammedis_diagnosa,"
            + "rekammedis_tindakan) VALUES (?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_REKAMMEDIS = "UPDATE _rekammedis SET rekammedis_tglkunjungan =?, rekammedis_keluhan =?, rekammedis_diagnosa =?, rekammedis_tindakan =?, id_dokter =?, id_pasien =?, id_penyakit =? WHERE rekammedis_id =?";
    private static final String SQL_COUNT_REKAMMEDIS = "SELECT COUNT(*) FROM _rekammedis";
    private static final String SQL_SEARCH = "SELECT * FROM _rekammedis WHERE rekammedis_id like ?";
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<RekamMedis> getAll() {
        List<RekamMedis> result = null;
        Gson gson = new Gson();
        result = jdbcTemplate.query(SQL_SELECT_REKAMMEDIS, new Object[]{}, new RowMapper<RekamMedis>(){

            public RekamMedis mapRow(ResultSet rs, int i) throws SQLException {
                Gson gson = new Gson();
                 
                RekamMedis rekamMedis = new RekamMedis();               
                rekamMedis.setRekammedis_id(rs.getString("rekammedis_id"));
                rekamMedis.setRekammedis_tglkunjungan(rs.getTimestamp("rekammedis_tglkunjungan"));
                rekamMedis.setRekammedis_keluhan(rs.getString("rekammedis_keluhan"));
                rekamMedis.setRekammedis_diagnosa(rs.getString("rekammedis_diagnosa"));
                rekamMedis.setRekammedis_tindakan(rs.getString("rekammedis_tindakan"));
                               
                Dokter dokter = new Dokter();
                //variable decrypt
                String namaDokter = rs.getString("dokter_nama");
                dokter.setId_dokter(rs.getInt("id_dokter"));
                dokter.setDokter_nama(AES256.decrypt(namaDokter));
                rekamMedis.setDokter(dokter);
                             
                Pasien pasien = new Pasien();
                //variable decrypt
                String ktpPasien = rs.getString("pasien_ktp");
                String namaPasien = rs.getString("pasien_nama");
                String jenkelPasien = rs.getString("pasien_jenkel");
                pasien.setId_pasien(rs.getInt("id_pasien"));
                pasien.setPasien_ktp(AES256.decrypt(ktpPasien));
                pasien.setPasien_nama(AES256.decrypt(namaPasien));
                pasien.setPasien_tgllahir(rs.getTimestamp("pasien_tgllahir"));
                pasien.setPasien_jenkel(AES256.decrypt(jenkelPasien));
                rekamMedis.setPasien(pasien);
                          
                Penyakit penyakit = new Penyakit();
                //variabel decrypt
                String namaPenyakit = rs.getString("penyakit_nama");
                penyakit.setId_penyakit(rs.getInt("id_penyakit"));
                penyakit.setPenyakit_nama(AES256.decrypt(namaPenyakit));
                rekamMedis.setPenyakit(penyakit);
                
                System.out.println("ini object getAll() rekammedis" + gson.toJson(rekamMedis));
                return rekamMedis; 
            }
        });
        return result;
    }

    @Override
    public RekamMedis getById(String rekammedis_id) {
        RekamMedis result = new RekamMedis();
        Gson gson = new Gson();
        try {
            result = jdbcTemplate.queryForObject(SQL_SELECT_REKAMMEDIS_BY_ID, new Object[]{rekammedis_id}, new RowMapper<RekamMedis>(){
                @Override
                public RekamMedis mapRow(ResultSet rs, int i) throws SQLException {
                Gson gson = new Gson();
                 
                RekamMedis rekamMedis = new RekamMedis();
                //variabel untuk decrypt
                String idRekamMedis = rs.getString("rekammedis_id");
                String keluhanRekamMedis = rs.getString("rekammedis_keluhan");
                String diagnosaRekamMedis = rs.getString("rekammedis_diagnosa");
                String tindakanRekamMedis = rs.getString("rekammedis_tindakan");
                
                rekamMedis.setRekammedis_id(idRekamMedis);
                rekamMedis.setRekammedis_tglkunjungan(rs.getTimestamp("rekammedis_tglkunjungan"));
                rekamMedis.setRekammedis_keluhan(AES256.decrypt(keluhanRekamMedis));
                rekamMedis.setRekammedis_diagnosa(AES256.decrypt(diagnosaRekamMedis));
                rekamMedis.setRekammedis_tindakan(AES256.decrypt(tindakanRekamMedis));
                               
                Dokter dokter = new Dokter();
                //variable untuk decrypt
                String namaDokter = rs.getString("dokter_nama");
                
                dokter.setId_dokter(rs.getInt("id_dokter"));
                dokter.setDokter_nama(AES256.decrypt(namaDokter));
                rekamMedis.setDokter(dokter);
                             
                Pasien pasien = new Pasien();
                //variabel untuk decrypt
                String ktpPasien = rs.getString("pasien_ktp");
                String namaPasien = rs.getString("pasien_nama");
                String jenkelPasien = rs.getString("pasien_jenkel");
                
                pasien.setId_pasien(rs.getInt("id_pasien"));
                pasien.setPasien_ktp(AES256.decrypt(ktpPasien));
                pasien.setPasien_nama(AES256.decrypt(namaPasien));
                pasien.setPasien_tgllahir(rs.getTimestamp("pasien_tgllahir"));
                pasien.setPasien_jenkel(AES256.decrypt(jenkelPasien));
                rekamMedis.setPasien(pasien);
                          
                Penyakit penyakit = new Penyakit();
                //variabel untuk decrypt
                String namaPenyakit = rs.getString("penyakit_nama");
                
                penyakit.setId_penyakit(rs.getInt("id_penyakit"));
                penyakit.setPenyakit_nama(AES256.decrypt(namaPenyakit));
                rekamMedis.setPenyakit(penyakit);
                
                System.out.println("DAOIMPL GETBYID() rekammedis" + gson.toJson(rekamMedis));
                return rekamMedis;
                }

        });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public long insert(RekamMedis rekamMedis) {
        long result = 0;
        Gson gson = new Gson();
        try {

            result = jdbcTemplate.update(SQL_INSERT_REKAMMEDIS, new Object[]{
               rekamMedis.getRekammedis_id(),
               rekamMedis.getDokter().getId_dokter(),
               rekamMedis.getPasien().getId_pasien(),
               rekamMedis.getPenyakit().getId_penyakit(),
               rekamMedis.getRekammedis_tglkunjungan(),
               rekamMedis.getRekammedis_keluhan(),
               rekamMedis.getRekammedis_diagnosa(),
               rekamMedis.getRekammedis_tindakan()
                    
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ini Result" + gson.toJson(result));
        return result;
    }

    @Override
    public long update(RekamMedis rekamMedis) {
        long result = 0;
        try {
            result = jdbcTemplate.update(SQL_UPDATE_REKAMMEDIS, new Object[]{
                rekamMedis.getRekammedis_tglkunjungan(),
                rekamMedis.getRekammedis_keluhan(),
                rekamMedis.getRekammedis_diagnosa(),
                rekamMedis.getRekammedis_tindakan(),
                rekamMedis.getDokter().getId_dokter(),
                rekamMedis.getPasien().getId_pasien(),
                rekamMedis.getPenyakit().getId_penyakit(),
                rekamMedis.getRekammedis_id()
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public long delete(RekamMedis rekamMedis) {
        long result = 0;
        try {
            result = jdbcTemplate.update(SQL_DELETE_REKAMMEDIS_BY_ID, new Object[]{
                rekamMedis.getRekammedis_id()
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public long count() {
        long count = 0;
        try {
            count = jdbcTemplate.queryForObject(SQL_COUNT_REKAMMEDIS, null, Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<RekamMedis> searchData(String keyword) {
        String searchKeyword = "%" + keyword + "%";
        List<RekamMedis> listRekamMedis = null;
        listRekamMedis = jdbcTemplate.query(SQL_SEARCH, new Object[]{searchKeyword}, new BeanPropertyRowMapper(RekamMedis.class));
        
        return listRekamMedis;
    }

}
