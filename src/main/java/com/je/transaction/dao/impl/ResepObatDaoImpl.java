
package com.je.transaction.dao.impl;

import com.api.nexmedia.util.AES256;
import com.google.gson.Gson;
import com.je.master.model.Dokter;
import com.je.master.model.Obat;
import com.je.master.model.Pasien;
import com.je.master.model.Penyakit;
import com.je.transaction.dao.ResepObatDao;
import com.je.transaction.model.RekamMedis;
import com.je.transaction.model.ResepObat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("resepObatDao")
public class ResepObatDaoImpl implements ResepObatDao{

    private static final String SQL_SELECT_RESEPOBAT = "SELECT * FROM _resepobat ro, _rekammedis rm, _dokter d, _pasien ps, _penyakit p, _obat o WHERE ro.rekammedis_id = rm.rekammedis_id AND rm.id_dokter = d.id_dokter AND rm.id_pasien = ps.id_pasien AND rm.id_penyakit = p.id_penyakit AND ro.id_obat = o.id_obat";
    private static final String SQL_SELECT_RESEPOBAT_BY_ID = "SELECT * FROM _resepobat ro, _rekammedis rm, _dokter d, _pasien ps, _penyakit p, _obat o WHERE ro.rekammedis_id = rm.rekammedis_id AND rm.id_dokter = d.id_dokter AND rm.id_pasien = ps.id_pasien AND rm.id_penyakit = p.id_penyakit AND ro.id_obat = o.id_obat AND ro.id_resepobat =?";
    private static final String SQL_INSERT_RESEPOBAT = "INSERT INTO _resepobat ( id_resepobat, rekammedis_id, id_obat, resepobat_jumlah ) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE_RESEPOBAT = "UPDATE _resepobat SET rekammedis_id=?, resepobat_jumlah=?, id_obat=? WHERE id_resepobat=?";
    private static final String SQL_DELETE_RESEPOBAT_BY_ID = "DELETE FROM _resepobat WHERE id_resepobat=?";
    private static final String SQL_COUNT_RESEPOBAT = "SELECT COUNT(*) FROM _resepobat";
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public List<ResepObat> getAll() {
        List<ResepObat> result = null;
        Gson gson = new Gson();
        result = jdbcTemplate.query(SQL_SELECT_RESEPOBAT, new Object[]{}, new RowMapper<ResepObat>(){
            public ResepObat mapRow(ResultSet rs, int i) throws SQLException {
                Gson gson = new Gson();
                
                System.out.println("ini resepobat :" + rs.getInt("id_resepobat"));
                
                ResepObat resepObat = new ResepObat();
                resepObat.setId_resepobat(rs.getInt("id_resepobat"));
                resepObat.setResepobat_jumlah(rs.getString("resepobat_jumlah"));
                
                RekamMedis rekamMedis = new RekamMedis();
                rekamMedis.setRekammedis_id(rs.getString("rekammedis_id"));
                rekamMedis.setRekammedis_tglkunjungan(rs.getTimestamp("rekammedis_tglkunjungan"));
                
                
                Dokter dokter = new Dokter();
                //variabel untuk decrypt
                String namaDokter = rs.getString("dokter_nama");
                dokter.setId_dokter(rs.getInt("id_dokter"));
                dokter.setDokter_nama(AES256.decrypt(namaDokter));
                rekamMedis.setDokter(dokter);
                               
                Pasien pasien = new Pasien();
                //variabel untuk decrypt
                String namaPasien = rs.getString("pasien_nama");
                pasien.setId_pasien(rs.getInt("id_pasien"));
                pasien.setPasien_nama(AES256.decrypt(namaPasien));
                rekamMedis.setPasien(pasien);
                
                Penyakit penyakit = new Penyakit();
                //variabel untuk decrypt
                String namaPenyakit = rs.getString("penyakit_nama");
                penyakit.setId_penyakit(rs.getInt("id_penyakit"));
                penyakit.setPenyakit_nama(AES256.decrypt(namaPenyakit));
                rekamMedis.setPenyakit(penyakit);
                resepObat.setRekamMedis(rekamMedis);
                
                Obat obat = new Obat();
                //variabel untuk decrypt
                String kdObat = rs.getString("kd_obat");
                String namaObat = rs.getString("obat_nama");
                String jenisObat = rs.getString("obat_jenis");
                obat.setId_obat(rs.getInt("id_obat"));
                obat.setKd_obat(AES256.decrypt(kdObat));
                obat.setObat_nama(AES256.decrypt(namaObat));
                obat.setObat_jenis(AES256.decrypt(jenisObat));
                resepObat.setObat(obat);
                
                               
                return resepObat;
            }
        });
        return result;
    }

    @Override
    public ResepObat getById(int id_resepobat) {
        ResepObat result = new ResepObat();
        Gson gson = new Gson();
        try {
            result = jdbcTemplate.queryForObject(SQL_SELECT_RESEPOBAT_BY_ID, new Object[]{id_resepobat}, new RowMapper<ResepObat>() {
                @Override
                public ResepObat mapRow(ResultSet rs, int i) throws SQLException {
                    Gson gson = new Gson();
                    System.out.println("ini id resepobat : " + rs.getInt("id_resepobat"));
                    
                ResepObat resepObat = new ResepObat();
                //variable untuk decrypt
                String jumlahResepobat = rs.getString("resepobat_jumlah");
                resepObat.setId_resepobat(rs.getInt("id_resepobat"));
                resepObat.setResepobat_jumlah(AES256.decrypt(jumlahResepobat));
                
                RekamMedis rekamMedis = new RekamMedis();
                rekamMedis.setRekammedis_id(rs.getString("rekammedis_id"));
                rekamMedis.setRekammedis_tglkunjungan(rs.getTimestamp("rekammedis_tglkunjungan"));
                
                Dokter dokter = new Dokter();
                //variabel untuk decrypt
                String namaDokter = rs.getString("dokter_nama");
                dokter.setId_dokter(rs.getInt("id_dokter"));
                dokter.setDokter_nama(AES256.decrypt(namaDokter));
                rekamMedis.setDokter(dokter);
                               
                Pasien pasien = new Pasien();
                //variable untuk decrypt
                String namaPasien = rs.getString("pasien_nama");
                pasien.setId_pasien(rs.getInt("id_pasien"));
                pasien.setPasien_nama(AES256.decrypt(namaPasien));
                rekamMedis.setPasien(pasien);
                
                Penyakit penyakit = new Penyakit();
                //variabel untuk decrypt
                String namaPenyakit = rs.getString("penyakit_nama");
                penyakit.setId_penyakit(rs.getInt("id_penyakit"));
                penyakit.setPenyakit_nama(AES256.decrypt(namaPenyakit));
                rekamMedis.setPenyakit(penyakit);
                resepObat.setRekamMedis(rekamMedis);
                
                Obat obat = new Obat();
                //variabel untuk decrypt
                String kdObat = rs.getString("kd_obat");
                String namaObat = rs.getString("obat_nama");
                String jenisObat = rs.getString("obat_jenis");
                obat.setId_obat(rs.getInt("id_obat"));
                obat.setKd_obat(AES256.decrypt(kdObat));
                obat.setObat_nama(AES256.decrypt(namaObat));
                obat.setObat_jenis(AES256.decrypt(jenisObat));
                resepObat.setObat(obat);
                    
                    System.out.println("ini getById DaoImpl RO" + gson.toJson(resepObat));
                    return resepObat;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public long insert(ResepObat resepObat) {
        long result = 0;
        Gson gson = new Gson();
        try {
            result = jdbcTemplate.update(SQL_INSERT_RESEPOBAT, new Object[]{
                resepObat.getId_resepobat(),
                resepObat.getRekamMedis().getRekammedis_id(),
                resepObat.getObat().getId_obat(),
                resepObat.getResepobat_jumlah()
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public long update(ResepObat resepObat) {
        long result = 0;
        try {
            result = jdbcTemplate.update(SQL_UPDATE_RESEPOBAT, new Object[]{
                resepObat.getRekamMedis().getRekammedis_id(),
                resepObat.getResepobat_jumlah(),
                resepObat.getObat().getId_obat(),
                resepObat.getId_resepobat()
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public long delete(ResepObat resepObat) {
        long result = 0;
        try {
            result = jdbcTemplate.update(SQL_DELETE_RESEPOBAT_BY_ID, new Object[]{
                resepObat.getId_resepobat()
            });
        } catch (Exception e) {
        }
        return result;
    }

    public long count() {
        long count = 0;
        try {
            count = jdbcTemplate.queryForObject(SQL_COUNT_RESEPOBAT, null, Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<ResepObat> searchData(String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
