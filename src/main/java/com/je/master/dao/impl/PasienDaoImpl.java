/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.dao.impl;

import com.api.nexmedia.util.AES256;
import com.je.master.dao.PasienDao;
import com.je.master.model.Pasien;
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


@Repository("pasienDao")
public class PasienDaoImpl implements PasienDao {

    private static final String SQL_SELECT_PASIEN = "SELECT id_pasien,"
            + "pasien_ktp,"
            + "pasien_nama,"
            + "pasien_alamat,"
            + "pasien_tempatlahir,"
            + "pasien_tgllahir,"
            + "pasien_telepon,"
            + "pasien_jenkel,"
            + "pasien_agama,"
            + "pasien_noasuransi,"
            + "pasien_jenisasuransi,"
            + "pasien_goldar,"
            + "pasien_kelurahan,"
            + "pasien_kecamatan,"
            + "pasien_kotakabupaten,"
            + "pasien_provinsi,"
            + "pasien_nokk FROM _pasien";
    private static final String SQL_SELECT_PASIEN_BY_ID = "SELECT id_pasien,"
            + "pasien_ktp,"
            + "pasien_nama,"
            + "pasien_alamat,"
            + "pasien_tempatlahir,"
            + "pasien_tgllahir,"
            + "pasien_telepon,"
            + "pasien_jenkel,"
            + "pasien_agama,"
            + "pasien_noasuransi,"
            + "pasien_jenisasuransi,"
            + "pasien_goldar,"
            + "pasien_kelurahan,"
            + "pasien_kecamatan,"
            + "pasien_kotakabupaten,"
            + "pasien_provinsi,"
            + "pasien_nokk"
            + " FROM _pasien WHERE id_pasien = ?";
    private static final String SQL_DELETE_PASIEN_BY_ID = "DELETE FROM _pasien WHERE id_pasien = ?";
    private static final String SQL_INSERT_PASIEN = "INSERT INTO _pasien ("
            + "id_pasien,"
            + "pasien_ktp,"
            + "pasien_nama,"
            + "pasien_alamat,"
            + "pasien_tempatlahir,"
            + "pasien_tgllahir,"
            + "pasien_telepon,"
            + "pasien_jenkel,"
            + "pasien_agama,"
            + "pasien_noasuransi,"
            + "pasien_jenisasuransi,"
            + "pasien_goldar,"
            + "pasien_kelurahan,"
            + "pasien_kecamatan,"
            + "pasien_kotakabupaten,"
            + "pasien_provinsi,"
            + "pasien_nokk) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_PASIEN = "UPDATE _pasien SET pasien_ktp=?, "
            + "pasien_nama=?, "
            + "pasien_alamat=?, "
            + "pasien_tempatlahir=?, "
            + "pasien_tgllahir=?, "
            + "pasien_telepon=?, "
            + "pasien_jenkel=?, "
            + "pasien_agama=?, "
            + "pasien_noasuransi=?, "
            + "pasien_jenisasuransi=?, "
            + "pasien_goldar=?, "
            + "pasien_kelurahan=?, "
            + "pasien_kecamatan=?, "
            + "pasien_kotakabupaten=?, "
            + "pasien_provinsi=?, "
            + "pasien_nokk=? WHERE id_pasien=?";
    private static final String SQL_COUNT_PASIEN = "SELECT COUNT(*) FROM _pasien";
    private static final String SQL_SEARCH = "SELECT * FROM _pasien WHERE pasien_ktp like ?";
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<Pasien> getAll(int start, int limit, String order, Map<String, String> params) {
        List<Pasien> result = null;
        String where = Utils.getClauseWhere(params);
        String orderBy = Utils.getOrderBy(order);
         if (start > -1 && limit > 0) {
            result =  jdbcTemplate.query(SQL_SELECT_PASIEN + where + " ORDER BY " + orderBy + "  LIMIT ?,?", new Object[]{start, limit}, new BeanPropertyRowMapper<Pasien>(Pasien.class));
        } else {
            result =  jdbcTemplate.query(SQL_SELECT_PASIEN , new BeanPropertyRowMapper<Pasien>(Pasien.class));
          
        }
        
       
     result = jdbcTemplate.query(SQL_SELECT_PASIEN, new Object[]{}, new RowMapper<Pasien>() {
			@Override
			public Pasien mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Pasien pasien = new Pasien();
                                pasien.setId_pasien(rs.getInt("id_pasien"));
                                pasien.setPasien_ktp(rs.getString("pasien_ktp"));
                                pasien.setPasien_nama(rs.getString("pasien_nama"));
                                pasien.setPasien_alamat(rs.getString("pasien_alamat"));
                                pasien.setPasien_tempatlahir(rs.getString("pasien_tempatlahir"));
                                pasien.setPasien_tgllahir(rs.getTimestamp("pasien_tgllahir"));
                                pasien.setPasien_telepon(rs.getString("pasien_telepon"));
                                pasien.setPasien_jenkel(rs.getString("pasien_jenkel"));
                                pasien.setPasien_agama(rs.getString("pasien_agama"));
                                pasien.setPasien_noasuransi(rs.getString("pasien_noasuransi"));
                                pasien.setPasien_jenisasuransi(rs.getString("pasien_jenisasuransi"));
                                pasien.setPasien_goldar(rs.getString("pasien_goldar"));
                                pasien.setPasien_kelurahan(rs.getString("pasien_kelurahan"));
                                pasien.setPasien_kecamatan(rs.getString("pasien_kecamatan"));
                                pasien.setPasien_kotakabupaten(rs.getString("pasien_kotakabupaten"));
                                pasien.setPasien_provinsi(rs.getString("pasien_provinsi"));
                                pasien.setPasien_nokk(rs.getString("pasien_nokk"));
                                
                                return pasien;
			}
		});  
        
        return result;
    }

    @Override
    public Pasien getById(int id_pasien) {
        Pasien result = null;
        try {
            result = (Pasien) jdbcTemplate.queryForObject(SQL_SELECT_PASIEN_BY_ID, new Object[]{id_pasien}, new RowMapper<Pasien>() {
                @Override
                public Pasien mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Pasien pasien = new Pasien();
                    
                    //Variabel untuk decrypt
                    String ktpPasien = rs.getString("pasien_ktp");
                    String namaPasien = rs.getString("pasien_nama");
                    String alamatPasien = rs.getString("pasien_alamat");
                    String tempatLahirPasien = rs.getString("pasien_tempatlahir");
                    String teleponPasien = rs.getString("pasien_telepon");
                    String jenkelPasien = rs.getString("pasien_jenkel");
                    String agamaPasien = rs.getString("pasien_agama");
                    String noAsuransiPasien = rs.getString("pasien_noasuransi");
                    String jenisAsuransiPasien = rs.getString("pasien_jenisasuransi");
                    String goldarPasien = rs.getString("pasien_goldar");
                    String kelurahanPasien = rs.getString("pasien_kelurahan");
                    String kecamatanPasien = rs.getString("pasien_kecamatan");
                    String kotaKabupatenPasien = rs.getString("pasien_kotakabupaten");
                    String provinsiPasien = rs.getString("pasien_provinsi");
                    String noKkPasien = rs.getString("pasien_nokk");
                    
                    pasien.setId_pasien(rs.getInt("id_pasien"));
                    pasien.setPasien_ktp(AES256.decrypt(ktpPasien));
                    pasien.setPasien_nama(AES256.decrypt(namaPasien));
                    pasien.setPasien_alamat(AES256.decrypt(alamatPasien));
                    pasien.setPasien_tempatlahir(AES256.decrypt(tempatLahirPasien));
                    pasien.setPasien_tgllahir(rs.getTimestamp("pasien_tgllahir"));
                    pasien.setPasien_telepon(AES256.decrypt(teleponPasien));
                    pasien.setPasien_jenkel(AES256.decrypt(jenkelPasien));
                    pasien.setPasien_agama(AES256.decrypt(agamaPasien));
                    pasien.setPasien_noasuransi(AES256.decrypt(noAsuransiPasien));
                    pasien.setPasien_jenisasuransi(AES256.decrypt(jenisAsuransiPasien));
                    pasien.setPasien_goldar(AES256.decrypt(goldarPasien));
                    pasien.setPasien_kelurahan(AES256.decrypt(kelurahanPasien));
                    pasien.setPasien_kecamatan(AES256.decrypt(kecamatanPasien));
                    pasien.setPasien_kotakabupaten(AES256.decrypt(kotaKabupatenPasien));
                    pasien.setPasien_provinsi(AES256.decrypt(provinsiPasien));
                    pasien.setPasien_nokk(AES256.decrypt(noKkPasien));
                    
                    return pasien;
                }
            });
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }

    @Override
    public long insert(Pasien pasien) {
        long result = 0;
        try {
            result =jdbcTemplate.update(SQL_INSERT_PASIEN, new Object[]{
                pasien.getId_pasien(),
                pasien.getPasien_ktp(),
                pasien.getPasien_nama(),
                pasien.getPasien_alamat(),
                pasien.getPasien_tempatlahir(),
                pasien.getPasien_tgllahir(),
                pasien.getPasien_telepon(),
                pasien.getPasien_jenkel(),
                pasien.getPasien_agama(),
                pasien.getPasien_noasuransi(),
                pasien.getPasien_jenisasuransi(),
                pasien.getPasien_goldar(),
                pasien.getPasien_kelurahan(),
                pasien.getPasien_kecamatan(),
                pasien.getPasien_kotakabupaten(),
                pasien.getPasien_provinsi(),
                pasien.getPasien_nokk()
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public long update(Pasien pasien) {
       long result = 0;
        try {
            result = jdbcTemplate.update(SQL_UPDATE_PASIEN, new Object[]{
                pasien.getPasien_ktp(),
                pasien.getPasien_nama(),
                pasien.getPasien_alamat(),
                pasien.getPasien_tempatlahir(),
                pasien.getPasien_tgllahir(),
                pasien.getPasien_telepon(),
                pasien.getPasien_jenkel(),
                pasien.getPasien_agama(),
                pasien.getPasien_noasuransi(),
                pasien.getPasien_jenisasuransi(),
                pasien.getPasien_goldar(),
                pasien.getPasien_kelurahan(),
                pasien.getPasien_kecamatan(),
                pasien.getPasien_provinsi(),
                pasien.getPasien_kotakabupaten(),
                pasien.getPasien_nokk(),
                pasien.getId_pasien()
            });            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
        
    }

    @Override
    public long delete(Pasien pasien) {
        long result = 0;
        
        result = jdbcTemplate.update(SQL_DELETE_PASIEN_BY_ID, new Object[]{
           pasien.getId_pasien()
        });
        return result;
    }

    @Override
    public long count() {
        long count = 0;
        try {
            count = jdbcTemplate.queryForObject(SQL_COUNT_PASIEN, null, Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Pasien> searchData(String keyword) {
        String searchKeyword = "%" + keyword + "%";
        List<Pasien> listPasien = null;
        listPasien = jdbcTemplate.query(SQL_SEARCH, new Object[]{searchKeyword}, new BeanPropertyRowMapper(Pasien.class));
        
        return listPasien;
    }
}
