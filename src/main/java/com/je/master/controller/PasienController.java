package com.je.master.controller;

import com.api.nexmedia.util.AES256;
import com.je.master.model.Pasien;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.je.master.service.PasienService;
import com.je.util.Constants;
import com.je.util.Utils;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PasienController {
    
    private static final String PASIEN_KEY = "pasien";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

    @Autowired
    private PasienService pasienService;

    
    @RequestMapping(value = "/masterdata/pasien", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getAll(@RequestParam(required = false) Map<String, String> params) {
         	
        int activePage = NumberUtils.toInt(params.get(Constants.ACTIVE_PAGE));
        int start = (activePage - 1) * Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        int limit = Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        String orderBy = (String) params.get(Constants.ORDER);
        Map<String, String> assocParams = Utils.getAssocParams(params);
        System.out.println("Ini active page" + activePage);
        System.out.println("Ini order by" + orderBy);
        
        List<Pasien> list = pasienService.getAll(start, limit, orderBy, assocParams);
        List baru = new ArrayList();
        
        // membuka list Pasien -> object baru
        for (Pasien pl : list) {
            Pasien png = new Pasien();
            
            //Variabel Decrypt
            String ktpPasien = pl.getPasien_ktp();
            String namaPasien = pl.getPasien_nama();
            String alamatPasien = pl.getPasien_alamat();
            String tempatLahirPasien = pl.getPasien_tempatlahir();
            String teleponPasien = pl.getPasien_telepon();
            String jenkelPasien = pl.getPasien_jenkel();
            String agamaPasien = pl.getPasien_agama();
            String noAsuransiPasien = pl.getPasien_noasuransi();
            String jenisAsuransiPasien = pl.getPasien_jenisasuransi();
            String goldarPasien = pl.getPasien_goldar();
            String kelurahanPasien = pl.getPasien_kelurahan();
            String kecamatanPasien = pl.getPasien_kecamatan();
            String kotaKabupatenPasien = pl.getPasien_kotakabupaten();
            String provinsiPasien = pl.getPasien_provinsi();
            String noKkPasien = pl.getPasien_nokk();
            
            png.setId_pasien(pl.getId_pasien());
            png.setPasien_ktp(AES256.decrypt(ktpPasien));
            png.setPasien_nama(AES256.decrypt(namaPasien));
            png.setPasien_alamat(AES256.decrypt(alamatPasien));
            png.setPasien_tempatlahir(AES256.decrypt(tempatLahirPasien));
            png.setPasien_tgllahir(pl.getPasien_tgllahir());
            png.setPasien_telepon(AES256.decrypt(teleponPasien));
            png.setPasien_jenkel(AES256.decrypt(jenkelPasien));
            png.setPasien_agama(AES256.decrypt(agamaPasien));
            png.setPasien_noasuransi(AES256.decrypt(noAsuransiPasien));
            png.setPasien_jenisasuransi(AES256.decrypt(jenisAsuransiPasien));
            png.setPasien_goldar(AES256.decrypt(goldarPasien));
            png.setPasien_kelurahan(AES256.decrypt(kelurahanPasien));
            png.setPasien_kecamatan(AES256.decrypt(kecamatanPasien));
            png.setPasien_kotakabupaten(AES256.decrypt(kotaKabupatenPasien));
            png.setPasien_provinsi(AES256.decrypt(provinsiPasien));
            png.setPasien_nokk(AES256.decrypt(noKkPasien));
            
            baru.add(png);
        }

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.LIST, baru);
        responseMap.put(Constants.TOTAL, responseMap.size());
        long count = pasienService.count();
        responseMap.put(Constants.TOTAL, count);
        System.out.println("Success PasienController.getALL(): " + responseMap);

        return responseMap;
    }
    
    @RequestMapping(value = "/masterdata/pasien/{id_pasien}", method = RequestMethod.DELETE, produces = "application/json")
    public @ResponseBody Map<String, Object> delete(@PathVariable("id_pasien") final int id_pasien){
       
        Pasien pasien = new Pasien();
        pasien.setId_pasien(id_pasien);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (pasienService.delete(pasien) != 0) ? Constants.OK : Constants.ERROR);
        System.out.println("Success PasienController.delete(): " + responseMap);
        
        return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/pasien", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> insert(@RequestBody final Map<String, Object> params) throws ParseException {

   	Map<String, Object> pasienMap = (Map<String, Object>) params.get(Constants.PASIEN_KEY);
        Pasien pasien = new Pasien();
        
        Date pasien_tgllahir = null;
        if(pasienMap.get("pasien_tgllahir") instanceof String){
        	 String pasien_tgllahirString = (String)pasienMap.get("pasien_tgllahir");
        	 pasien_tgllahir = formatter.parse(pasien_tgllahirString);
        }else{
               Long pasien_tgllahirLong = (Long)pasienMap.get("pasien_tgllahir");
               pasien_tgllahir = new Date(pasien_tgllahirLong);
        }

        //Variabel untk encrypt
        String ktpPasien = (String) pasienMap.get("pasien_ktp");
        String namaPasien = (String) pasienMap.get("pasien_nama");
        String alamatPasien = (String) pasienMap.get("pasien_alamat");
        String tempatLahirPasien = (String) pasienMap.get("pasien_tempatlahir");
        String teleponPasien = (String) pasienMap.get("pasien_telepon");
        String jenkelPasien = (String) pasienMap.get("pasien_jenkel");
        String agamaPasien = (String) pasienMap.get("pasien_agama");
        String noAsuransiPasien = (String) pasienMap.get("pasien_noasuransi");
        String jenisAsuransiPasien = (String) pasienMap.get("pasien_jenisasuransi");
        String goldarPasien = (String) pasienMap.get("pasien_goldar");
        String kelurahanPasien = (String) pasienMap.get("pasien_kelurahan");
        String kecamatanPasien = (String) pasienMap.get("pasien_kecamatan");
        String kotaKabupatenPasien = (String) pasienMap.get("pasien_kotakabupaten");
        String provinsiPasien = (String) pasienMap.get("pasien_provinsi");
        String noKkPasien = (String) pasienMap.get("pasien_nokk");
        
        pasien.setPasien_ktp((String) AES256.encrypt(ktpPasien));
        pasien.setPasien_nama((String) AES256.encrypt(namaPasien));
        pasien.setPasien_alamat((String) AES256.encrypt(alamatPasien));
        pasien.setPasien_tempatlahir((String) AES256.encrypt(tempatLahirPasien));
        pasien.setPasien_tgllahir(new Timestamp(pasien_tgllahir.getTime()));
        pasien.setPasien_telepon((String) AES256.encrypt(teleponPasien));
        pasien.setPasien_jenkel((String) AES256.encrypt(jenkelPasien));
        pasien.setPasien_agama((String) AES256.encrypt(agamaPasien));
        pasien.setPasien_noasuransi((String) AES256.encrypt(noAsuransiPasien));
        pasien.setPasien_jenisasuransi((String) AES256.encrypt(jenisAsuransiPasien));
        pasien.setPasien_goldar((String) AES256.encrypt(goldarPasien));
        pasien.setPasien_kelurahan((String) AES256.encrypt(kelurahanPasien));
        pasien.setPasien_kecamatan((String) AES256.encrypt(kecamatanPasien));
        pasien.setPasien_kotakabupaten((String) AES256.encrypt(kotaKabupatenPasien));
        pasien.setPasien_provinsi((String) AES256.encrypt(provinsiPasien));
        pasien.setPasien_nokk((String) AES256.encrypt(noKkPasien));
        
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (pasienService.insert(pasien) != 0) ? Constants.OK : Constants.ERROR);
        System.out.println("Success PasienController.insert(): " + responseMap);
        
        return responseMap;
    }

    @RequestMapping(value = "/masterdata/pasien/{id_pasien}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> edit(@PathVariable("id_pasien") int id_pasien) {

    	
    	 Pasien pasien = pasienService.getById(id_pasien);

         Map<String, Object> responseMap = new HashMap<String, Object>();
         responseMap.put(PASIEN_KEY, pasien);
         System.out.println("Success PasienController.getById(): " + responseMap);

         return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/pasien/{id_pasien}", method = RequestMethod.PUT, produces = "application/json")
    public Map<String, Object> update(@PathVariable("id_pasien") final int id_pasien,
        @RequestBody final Map<String, Object> params) throws ParseException {

    	Map<String, Object> pasienMap = (Map<String, Object>) params.get(PASIEN_KEY);
        Pasien pasien = new Pasien();

        Date pasien_tgllahir = null;
        if(pasienMap.get("pasien_tgllahir") instanceof String){
        	 String pasien_tgllahirString = (String)pasienMap.get("pasien_tgllahir");
        	 pasien_tgllahir = formatter.parse(pasien_tgllahirString);
        }else{
               Long pasien_tgllahirLong = (Long)pasienMap.get("pasien_tgllahir");
               pasien_tgllahir = new Date(pasien_tgllahirLong);
        }
        
        //Variabel untk encrypt
        String ktpPasien = (String) pasienMap.get("pasien_ktp");
        String namaPasien = (String) pasienMap.get("pasien_nama");
        String alamatPasien = (String) pasienMap.get("pasien_alamat");
        String tempatLahirPasien = (String) pasienMap.get("pasien_tempatlahir");
        String teleponPasien = (String) pasienMap.get("pasien_telepon");
        String jenkelPasien = (String) pasienMap.get("pasien_jenkel");
        String agamaPasien = (String) pasienMap.get("pasien_agama");
        String noAsuransiPasien = (String) pasienMap.get("pasien_noasuransi");
        String jenisAsuransiPasien = (String) pasienMap.get("pasien_jenisasuransi");
        String goldarPasien = (String) pasienMap.get("pasien_goldar");
        String kelurahanPasien = (String) pasienMap.get("pasien_kelurahan");
        String kecamatanPasien = (String) pasienMap.get("pasien_kecamatan");
        String kotaKabupatenPasien = (String) pasienMap.get("pasien_kotakabupaten");
        String provinsiPasien = (String) pasienMap.get("pasien_provinsi");
        String noKkPasien = (String) pasienMap.get("pasien_nokk");
        
        pasien.setId_pasien(id_pasien);
        pasien.setPasien_ktp((String) AES256.encrypt(ktpPasien));
        pasien.setPasien_nama((String) AES256.encrypt(namaPasien));
        pasien.setPasien_alamat((String) AES256.encrypt(alamatPasien));
        pasien.setPasien_tempatlahir((String) AES256.encrypt(tempatLahirPasien));
        pasien.setPasien_tgllahir(new Timestamp(pasien_tgllahir.getTime()));
        pasien.setPasien_telepon((String) AES256.encrypt(teleponPasien));
        pasien.setPasien_jenkel((String) AES256.encrypt(jenkelPasien));
        pasien.setPasien_agama((String) AES256.encrypt(agamaPasien));
        pasien.setPasien_noasuransi((String) AES256.encrypt(noAsuransiPasien));
        pasien.setPasien_jenisasuransi((String) AES256.encrypt(jenisAsuransiPasien));
        pasien.setPasien_goldar((String) AES256.encrypt(goldarPasien));
        pasien.setPasien_kelurahan((String) AES256.encrypt(kelurahanPasien));
        pasien.setPasien_kecamatan((String) AES256.encrypt(kecamatanPasien));
        pasien.setPasien_kotakabupaten((String) AES256.encrypt(kotaKabupatenPasien));
        pasien.setPasien_provinsi((String) AES256.encrypt(provinsiPasien));
        pasien.setPasien_nokk((String) AES256.encrypt(noKkPasien));

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (pasienService.update(pasien) != 0) ? Constants.OK : Constants.ERROR);
        System.out.println("Success PasienController.update(): " + responseMap);

        return responseMap;


    }


}
