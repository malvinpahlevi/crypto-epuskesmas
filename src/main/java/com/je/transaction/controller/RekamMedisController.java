/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaction.controller;

import com.api.nexmedia.util.AES256;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.je.master.model.Dokter;
import com.je.master.model.Pasien;
import com.je.master.model.Penyakit;
import com.je.master.service.DokterService;
import com.je.master.service.PasienService;
import com.je.master.service.PenyakitService;
import com.je.transaction.model.RekamMedis;
import com.je.transaction.service.RekamMedisService;
import com.je.util.Constants;
import com.je.util.Utils;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author AJ.P
 */
@Controller
public class RekamMedisController {
    
    private static final String REKAMMEDIS_KEY = "rekamMedis";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private Gson gson = new GsonBuilder().create();
    
    @Autowired
    private RekamMedisService rekamMedisService;
    
    @Autowired
    private DokterService dokterService;
    
    @Autowired
    private PasienService pasienService;
    
    @Autowired
    private PenyakitService penyakitService;
    
    @RequestMapping(value = "/transaction/rekammedis", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getAll() {
    	
        List<RekamMedis> list = rekamMedisService.getAll();
        List baru = new ArrayList();
        
        // membuka list RekamMedis -> object baru
        for (RekamMedis pl : list) {
            RekamMedis png = new RekamMedis();
            
            // Variabel decrypt
            String idRekamMedis = pl.getRekammedis_id();
            String keluhanRekamMedis = pl.getRekammedis_keluhan();
            String diagnosaRekamMedis = pl.getRekammedis_diagnosa();
            String tindakanRekamMedis = pl.getRekammedis_tindakan();
            
            png.setRekammedis_id(idRekamMedis);
            png.setRekammedis_keluhan(AES256.decrypt(keluhanRekamMedis));
            png.setRekammedis_diagnosa(AES256.decrypt(diagnosaRekamMedis));
            png.setRekammedis_tindakan(AES256.decrypt(tindakanRekamMedis));
            png.setDokter(pl.getDokter());
            png.setPasien(pl.getPasien());
            png.setPenyakit(pl.getPenyakit());
            
            baru.add(png);
        }
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.LIST, baru);
        responseMap.put(Constants.TOTAL, responseMap.size());
        
        long count = rekamMedisService.count();
        responseMap.put(Constants.TOTAL, count);
        
        System.out.println("ini object baru RM" + gson.toJson(baru));
        
        return responseMap;
    }
    
    @RequestMapping(value = "/transaction/rekammedis/{rekammedis_id}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> edit(@PathVariable("rekammedis_id") String rekammedis_id) {

    	
    	RekamMedis rekamMedis = rekamMedisService.getById(rekammedis_id);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(REKAMMEDIS_KEY, rekamMedis);

        return responseMap;
    }
    
    @RequestMapping(value = "/transaction/rekammedis/{rekammedis_id}", method = RequestMethod.DELETE, produces = "application/json")
    public Map<String, Object> delete(@PathVariable("rekammedis_id") final String rekammedis_id) {
    	
    	
    	RekamMedis rekamMedis = new RekamMedis();
        rekamMedis.setRekammedis_id(rekammedis_id);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (rekamMedisService.delete(rekamMedis) != 0) ? Constants.OK : Constants.ERROR);

        return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/transaction/rekammedis", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> insert(@RequestBody final Map<String, Object> params) throws ParseException {
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
    	
    	Map<String, Object> rekamMedisMap = (Map<String, Object>) params.get(REKAMMEDIS_KEY);
        RekamMedis rekamMedis = new RekamMedis();
        
        Date tanggalKunjungan = null;
        if(rekamMedisMap.get("rekammedis_tglkunjungan") instanceof String){
        	 String tanggalKunjunganString = (String)rekamMedisMap.get("rekammedis_tglkunjungan");
        	 tanggalKunjungan = formatter.parse(tanggalKunjunganString);
        }else{
        	 Long tanggalKunjunganLong = (Long)rekamMedisMap.get("rekammedis_tglkunjungan");
                 tanggalKunjungan = new Date(tanggalKunjunganLong);
        }
        
        Map<String, Object> dokterMap = (Map<String, Object>) rekamMedisMap.get("dokter");
        Map<String, Object> pasienMap = (Map<String, Object>) rekamMedisMap.get("pasien");
        Map<String, Object> penyakitMap = (Map<String, Object>) rekamMedisMap.get("penyakit");
        
        // Variabel untuk encrypt
        String keluhanRekammedis = (String) rekamMedisMap.get("rekammedis_keluhan");
        String diagnosaRekammedis = (String) rekamMedisMap.get("rekammedis_diagnosa");
        String tindakanRekammedis = (String) rekamMedisMap.get("rekammedis_tindakan");
        
        rekamMedis.setRekammedis_id((String) rekamMedisMap.get("rekammedis_id"));
        
        Dokter dokter = dokterService.getById((Integer) dokterMap.get("id_dokter"));
        if(dokter == null){
            responseMap.put(Constants.ERROR, "Dokter" + (Integer) dokterMap.get("id_dokter") + "tidak ada di database");
            return responseMap;
        }
        
        Pasien pasien = pasienService.getById((Integer) pasienMap.get("id_pasien"));
        if(pasien == null){
            responseMap.put(Constants.ERROR, "Pasien" + (Integer) pasienMap.get("id_pasien") + "tidak ada di database");
            return responseMap;
        }
        
        Penyakit penyakit = penyakitService.getById((Integer) penyakitMap.get("id_penyakit"));
        if(penyakit == null){
            responseMap.put(Constants.ERROR, "Penyakit" + (Integer) penyakitMap.get("id_penyakit") + "tidak ada di database");
            return responseMap;
        }
        
      
        
        rekamMedis.setRekammedis_tglkunjungan(new Timestamp(tanggalKunjungan.getTime()));
        rekamMedis.setRekammedis_keluhan((String) AES256.encrypt(keluhanRekammedis));
        rekamMedis.setRekammedis_diagnosa((String) AES256.encrypt(diagnosaRekammedis));
        rekamMedis.setRekammedis_tindakan((String) AES256.encrypt(tindakanRekammedis));
        rekamMedis.setDokter(dokter);
        rekamMedis.setPasien(pasien);
        rekamMedis.setPenyakit(penyakit);
        
        responseMap.put(Constants.STATUS, (rekamMedisService.insert(rekamMedis) != 0) ? Constants.OK : Constants.ERROR);

        return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/transaction/rekammedis/{rekammedis_id}", method = RequestMethod.PUT, produces = "application/json")
    public Map<String, Object> update(@PathVariable("rekammedis_id") final String rekammedis_id,
            @RequestBody final Map<String, Object> params) throws ParseException{
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        
        Map<String, Object> rekamMedisMap = (Map<String, Object>) params.get(REKAMMEDIS_KEY);
        RekamMedis rekamMedis = new RekamMedis();
        
        Date tanggalKunjungan = null;
        if(rekamMedisMap.get("rekammedis_tglkunjungan") instanceof String){
        	 String tanggalKunjunganString = (String)rekamMedisMap.get("rekammedis_tglkunjungan");
        	 tanggalKunjungan = formatter.parse(tanggalKunjunganString);
        }else{
        	 Long tanggalKunjunganLong = (Long)rekamMedisMap.get("rekammedis_tglkunjungan");
                 tanggalKunjungan = new Date(tanggalKunjunganLong);
        }
        
        Map<String, Object> dokterMap = (Map<String, Object>) rekamMedisMap.get("dokter");
        Map<String, Object> pasienMap = (Map<String, Object>) rekamMedisMap.get("pasien");
        Map<String, Object> penyakitMap = (Map<String, Object>) rekamMedisMap.get("penyakit");
        
        // Variabel untuk encrypt
        String idRekammedis = (String) rekamMedisMap.get("rekammedis_id");
        String keluhanRekammedis = (String) rekamMedisMap.get("rekammedis_keluhan");
        String diagnosaRekammedis = (String) rekamMedisMap.get("rekammedis_diagnosa");
        String tindakanRekammedis = (String) rekamMedisMap.get("rekammedis_tindakan");
        
        
        Dokter dokter = dokterService.getById((Integer) dokterMap.get("id_dokter"));
        if(dokter == null){
            responseMap.put(Constants.ERROR, "Dokter" + (Integer) dokterMap.get("id_dokter") + "tidak ada di database");
            return responseMap;
        }
        
        Pasien pasien = pasienService.getById((Integer) pasienMap.get("id_pasien"));
        if(pasien == null){
            responseMap.put(Constants.ERROR, "Pasien" + (Integer) pasienMap.get("id_pasien") + "tidak ada di database");
            return responseMap;
        }
        
        Penyakit penyakit = penyakitService.getById((Integer) penyakitMap.get("id_penyakit"));
        if(penyakit == null){
            responseMap.put(Constants.ERROR, "Penyakit" + (Integer) penyakitMap.get("id_penyakit") + "tidak ada di database");
            return responseMap;
        }
        
        rekamMedis.setRekammedis_id(rekammedis_id);
        rekamMedis.setRekammedis_tglkunjungan(new Timestamp(tanggalKunjungan.getTime()));
        rekamMedis.setRekammedis_keluhan((String) AES256.encrypt(keluhanRekammedis));
        rekamMedis.setRekammedis_diagnosa((String) AES256.encrypt(diagnosaRekammedis));
        rekamMedis.setRekammedis_tindakan((String) AES256.encrypt(tindakanRekammedis));        
        rekamMedis.setDokter(dokter);
        rekamMedis.setPasien(pasien);
        rekamMedis.setPenyakit(penyakit);
        
        responseMap.put(Constants.STATUS, (rekamMedisService.update(rekamMedis) != 0) ? Constants.OK : Constants.ERROR);
        System.out.println("INI RESPONSE MAP" + gson.toJson(responseMap));
        return responseMap;
    }
    
}
