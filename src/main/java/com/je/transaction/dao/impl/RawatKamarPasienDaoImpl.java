/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaction.dao.impl;

//import com.je.rumahsakit.master.model.Kamar;
//import com.je.rumahsakit.master.model.Pasien;
import com.je.transaction.dao.RawatKamarPasienDao;
import com.je.transaction.model.RawatKamarPasien;
import com.je.util.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("rawatKamarPasienDao")
public class RawatKamarPasienDaoImpl implements RawatKamarPasienDao{
    
    private static final String SQL_SELECT_RAWAT_KAMAR_PASIEN= "select "
            + "rkp.kode as kode ,"
            + "rkp.tanggal_masuk as tanggal_masuk,"
            + "rkp.tanggal_keluar as tanggal_keluar,"
            + "rkp.keterangan as keterangan,"
            + "k.kode as kode_kamar,"
            + "k.nama as nama_kamar,"
            + "p.kode as kode_pasien,"
            + "p.nama as nama_pasien "
            + "from rawat_kamar_pasien  rkp "
            + "inner join kamar k on k.kode=rkp.kode_kamar "
            + "inner join pasien p on p.kode=rkp.kode_pasien ";
    private static final String SQL_SELECT_RAWAT_KAMAR_PASIEN_BY_ID = "select "
            + "rkp.kode as kode ,"
            + "rkp.tanggal_masuk as tanggal_masuk,"
            + "rkp.tanggal_keluar as tanggal_keluar,"
            + "rkp.keterangan as keterangan,"
            + "k.kode as kode_kamar,"
            + "k.nama as nama_kamar,"
            + "p.kode as kode_pasien,"
            + "p.nama as nama_pasien from rawat_kamar_pasien  rkp "
            + "inner join kamar k on k.kode=rkp.kode_kamar "
            + "inner join pasien p on p.kode=rkp.kode_pasien "
            + "where rkp.kode = ?";
    private static final String SQL_DELETE_RAWAT_KAMAR_PASIEN_BY_ID = "delete from rawat_kamar_pasien where kode = ?";
    private static final String SQL_INSERT_RAWAT_KAMAR_PASIEN = "insert into rawat_kamar_pasien ("
            + "kode,"
            + "kode_kamar,"
            + "kode_pasien,"
            + "tanggal_masuk,"
            + "tanggal_keluar,"
            + "keterangan) VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE_RAWAT_KAMAR_PASIEN = "update rawat_kamar_pasien SET "
            + "tanggal_masuk=?, "
            + "tanggal_keluar=?, "
            + "keterangan=?  where kode=? and kode_kamar=? and kode_pasien=?";
    
    @Autowired
    private JdbcTemplate jdbcTemplate;



    public List<RawatKamarPasien> getAll(int start, int limit, String order, Map<String, String> params) {
//         List<RawatKamarPasien> result = null;
//        String where = Utils.getClauseWhere(params);
//        String orderBy = Utils.getOrderBy(order);
//        if (start > -1 && limit > 0) {
//            result =  jdbcTemplate.query(SQL_SELECT_RAWAT_KAMAR_PASIEN + where + " ORDER BY " + orderBy + "  LIMIT ?,?", new Object[]{start, limit}, getRowMapper());
//        } else {
//            result =  jdbcTemplate.query(SQL_SELECT_RAWAT_KAMAR_PASIEN + where + " ORDER BY " + orderBy, new Object[]{}, getRowMapper());
//          
//        }
        
        return null; //result;
    }
    
    

    public RawatKamarPasien getById(String id) {
          RawatKamarPasien result = null;
    	try{

    		result = (RawatKamarPasien) jdbcTemplate.queryForObject(SQL_SELECT_RAWAT_KAMAR_PASIEN_BY_ID,new Object[]{id},getRowMapper());
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return result;
    }

    public long insert(RawatKamarPasien rawatKamarPasien) {
      long result = 0;
//    	 try{
//    		 result = jdbcTemplate.update(SQL_INSERT_RAWAT_KAMAR_PASIEN, new Object[]{rawatKamarPasien.getKode(),rawatKamarPasien.getKamar().getKode(),rawatKamarPasien.getPasien().getKode(),rawatKamarPasien.getTanggalMasuk(),rawatKamarPasien.getTanggalKeluar(),rawatKamarPasien.getKeterangan()});  	
//    	 }catch(Exception e){
//    		 e.printStackTrace();
//    	 }
    	 return 0; //result;
    }

    public long update(RawatKamarPasien rawatKamarPasien) {
//        long result = 0;
//    	 try{
//    		 result = jdbcTemplate.update(SQL_UPDATE_RAWAT_KAMAR_PASIEN, new Object[]{rawatKamarPasien.getTanggalMasuk(),rawatKamarPasien.getTanggalKeluar(),rawatKamarPasien.getKeterangan(),rawatKamarPasien.getKode(),rawatKamarPasien.getKamar().getKode(),rawatKamarPasien.getPasien().getKode()});  	
//    	 }catch(Exception e){
//    		 e.printStackTrace();
//    	 }
    	 return 0; //result;
    }

    public long delete(RawatKamarPasien rawatKamarPasien) {
      long result = 0;
//		 try{
//			 result = jdbcTemplate.update(SQL_DELETE_RAWAT_KAMAR_PASIEN_BY_ID, new Object[]{rawatKamarPasien.getKode()});
//		 }catch(Exception e){
//			 e.printStackTrace();
//		 }
		 return 0;//result;
    }
    
    
    private RowMapper<RawatKamarPasien> getRowMapper(){
//           return new RowMapper<RawatKamarPasien>(){
//                   @Override
//                   public RawatKamarPasien mapRow(ResultSet rs, int rowNum) throws SQLException{
//                           RawatKamarPasien rawatKamarPasien = new RawatKamarPasien();
//                           rawatKamarPasien.setKode(rs.getString("kode"));
//                           rawatKamarPasien.setTanggalKeluar(rs.getTimestamp("tanggal_keluar"));
//                           rawatKamarPasien.setTanggalMasuk(rs.getTimestamp("tanggal_masuk"));
//                           rawatKamarPasien.setKeterangan(rs.getString("keterangan"));
//
//                           Kamar kamar = new Kamar();
//                           kamar.setKode(rs.getString("kode_kamar"));
//                           kamar.setNama(rs.getString("nama_kamar"));
//                           rawatKamarPasien.setKamar(kamar);
//
//
//                           Pasien pasien = new Pasien();
//                           pasien.setKode(rs.getString("kode_pasien"));
//                           pasien.setNama(rs.getString("nama_pasien"));
//                           rawatKamarPasien.setPasien(pasien);
//
//                           return rawatKamarPasien;
//                   }
//           };
return null;
   }
    
}
