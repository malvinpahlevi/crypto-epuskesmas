/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.controller;

import com.je.util.AES256;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.je.master.model.Dokter;
import com.je.master.service.DokterService;
import com.je.util.Constants;
import com.je.util.Utils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author AJ.P
 */
@Controller
public class DokterController {
    
    private Gson gson = new GsonBuilder().create(); 	
    private static final String DOKTER_KEY = "dokter";
    
    @Autowired
    private DokterService dokterService;
    
    @RequestMapping(value = "/masterdata/dokter", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getAll(@RequestParam(required = false) Map<String, String> params) {
        
        int activePage = NumberUtils.toInt(params.get(Constants.ACTIVE_PAGE));
        int start = (activePage - 1) * Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        int limit = Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        String orderBy = (String) params.get(Constants.ORDER);
        Map<String, String> assocParams = Utils.getAssocParams(params);
        System.out.println("Ini active page" + activePage);
        System.out.println("Ini order by" + orderBy);
        
        List<Dokter> list = dokterService.getAll(start, limit, orderBy, assocParams);
        List baru = new ArrayList();     
        
         System.out.println("Ctrl dokter list obj" + gson.toJson(list));
        
        
        // membuka list dokter -> object baru
        for (Dokter pl : list) {
            Dokter png = new Dokter();
            
               //Variabel decrypt
               String nipDokter = pl.getDokter_nip();
               String namaDokter = pl.getDokter_nama();
               String jenkelDokter = pl.getDokter_jenkel();
               String spesialisDokter = pl.getDokter_spesialis();
               
               png.setId_dokter(pl.getId_dokter());              
               png.setDokter_nip(AES256.decrypt(nipDokter));
               png.setDokter_nama(AES256.decrypt(namaDokter));
               png.setDokter_jenkel(AES256.decrypt(jenkelDokter));
               png.setDokter_spesialis(AES256.decrypt(spesialisDokter));

           baru.add(png);
             System.out.println("Ctrl dokter baru obj" + gson.toJson(baru));
        }
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.LIST, baru);
        responseMap.put(Constants.TOTAL, responseMap.size());
        
        long count = dokterService.count();
        responseMap.put(Constants.TOTAL, count);
        System.out.println("Success DokterController.getAll(): " + responseMap);

        return responseMap;
    }
    
    @RequestMapping(value = "/masterdata/dokter/{id_dokter}", method = RequestMethod.DELETE, produces = "application/json")
    public @ResponseBody Map<String, Object> delete(@PathVariable("id_dokter") final int id_dokter){
       
        Dokter dokter = new Dokter();
        dokter.setId_dokter(id_dokter);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (dokterService.delete(dokter) != 0) ? Constants.OK : Constants.ERROR);
        System.out.println("Success DokterController.delete(): " + responseMap);
        
        return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/dokter", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> insert(@RequestBody final Map<String, Object> params) throws ParseException {

   	Map<String, Object> dokterMap = (Map<String, Object>) params.get(Constants.DOKTER_KEY);
        Dokter dokter = new Dokter();
        
        // Variabel untuk encrypt
        String nip =  (String) dokterMap.get("dokter_nip");
        String namaDokter = (String) dokterMap.get("dokter_nama");
        String jenkelDokter = (String) dokterMap.get("dokter_jenkel");
        String spesialisDokter = (String) dokterMap.get("dokter_spesialis");
        
        dokter.setDokter_nip((String) AES256.encrypt(nip));
        dokter.setDokter_nama((String) AES256.encrypt(namaDokter));
        dokter.setDokter_jenkel((String) AES256.encrypt(jenkelDokter));
        dokter.setDokter_spesialis((String) AES256.encrypt(spesialisDokter));
        
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (dokterService.insert(dokter) != 0) ? Constants.OK : Constants.ERROR);
        System.out.println("Success DokterController.insert(): " + responseMap);
        
        return responseMap;
    }
    
    @RequestMapping(value = "/masterdata/dokter/{id_dokter}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> edit(@PathVariable("id_dokter") int id_dokter) {

    	Dokter dokter = dokterService.getById(id_dokter);
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(DOKTER_KEY, dokter);
        System.out.println("Success DokterController.getById(): " + gson.toJson(responseMap));
    	
        return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/dokter/{id_dokter}", method = RequestMethod.PUT, produces = "application/json")
    public Map<String, Object> update(@PathVariable("id_dokter") final int id_dokter,
        @RequestBody final Map<String, Object> params) throws ParseException {

    	Map<String, Object> dokterMap = (Map<String, Object>) params.get(DOKTER_KEY);
        Dokter dokter = new Dokter();
        
        String nip = (String) dokterMap.get("dokter_nip");
        String namaDokter = (String) dokterMap.get("dokter_nama");
        String jenkelDokter = (String) dokterMap.get("dokter_jenkel");
        String spesialisDokter = (String) dokterMap.get("dokter_spesialis");
        
        dokter.setId_dokter(id_dokter);
        dokter.setDokter_nip((String) AES256.encrypt(nip));
        dokter.setDokter_nama((String) AES256.encrypt(namaDokter));
        dokter.setDokter_jenkel((String) AES256.encrypt(jenkelDokter));
        dokter.setDokter_spesialis((String) AES256.encrypt(spesialisDokter));

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (dokterService.update(dokter) != 0) ? Constants.OK : Constants.ERROR);
        System.out.println("Success DokterController.update(): " + responseMap);

        return responseMap;
    }
    
}
