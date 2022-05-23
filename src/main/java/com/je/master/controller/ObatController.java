/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.controller;

import com.je.util.AES256;
import com.je.master.model.Obat;
import com.je.master.service.ObatService;
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
public class ObatController {
    
    private static final String OBAT_KEY = "obat";
    
    @Autowired
    private ObatService obatService;
    
    @RequestMapping(value = "/masterdata/obat", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getAll(@RequestParam(required = false) Map<String, String> params) {
         	
        int activePage = NumberUtils.toInt(params.get(Constants.ACTIVE_PAGE));
        int start = (activePage - 1) * Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        int limit = Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        String orderBy = (String) params.get(Constants.ORDER);
        Map<String, String> assocParams = Utils.getAssocParams(params);
        System.out.println("Ini active page" + activePage);
        System.out.println("Ini order by" + orderBy);
        
        List<Obat> list = obatService.getAll(start, limit, orderBy, assocParams);
        List baru = new ArrayList();
        
        for (Obat pl : list) {
            Obat png = new Obat();
            
            //Variabel decrypt
            String kodeObat = pl.getKd_obat();
            String namaObat = pl.getObat_nama();
            String jenisObat = pl.getObat_jenis();
            String kadaluarsaObat = pl.getObat_kadaluarsa();
            String produsenObat = pl.getObat_produsen();
            
            png.setId_obat(pl.getId_obat());
            png.setKd_obat(AES256.decrypt(kodeObat));
            png.setObat_nama(AES256.decrypt(namaObat));
            png.setObat_jenis(AES256.decrypt(jenisObat));
            png.setObat_kadaluarsa(AES256.decrypt(kadaluarsaObat));
            png.setObat_produsen(AES256.decrypt(produsenObat));
            
            baru.add(png);
        }

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.LIST, baru);
        responseMap.put(Constants.TOTAL, responseMap.size());
        // (count)total isi field OBAT
        long count = obatService.count();
        responseMap.put(Constants.TOTAL, count);
        System.out.println("Success ObatController.getALL(): " + responseMap);

        return responseMap;
    }
    
    @RequestMapping(value = "/masterdata/obat/{id_obat}", method = RequestMethod.DELETE, produces = "application/json")
    public @ResponseBody Map<String, Object> delete(@PathVariable("id_obat") final int id_obat){
       
        Obat obat = new Obat();
        obat.setId_obat(id_obat);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (obatService.delete(obat) != 0) ? Constants.OK : Constants.ERROR);
        System.out.println("Success ObatController.delete(): " + responseMap);

        return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/obat", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> insert(@RequestBody final Map<String, Object> params) throws ParseException {

   	Map<String, Object> obatMap = (Map<String, Object>) params.get(Constants.OBAT_KEY);
        Obat obat = new Obat();
        
        //Variabel untuk encrypt 
        String kodeObat = (String) obatMap.get("kd_obat");
        String namaObat = (String) obatMap.get("obat_nama");
        String jenisObat = (String) obatMap.get("obat_jenis");
        String kadaluarsaObat = (String) obatMap.get("obat_kadaluarsa");
        String produsenObat = (String) obatMap.get("obat_produsen");
        
        obat.setKd_obat((String) AES256.encrypt(kodeObat));
        obat.setObat_nama((String) AES256.encrypt(namaObat));
        obat.setObat_jenis((String) AES256.encrypt(jenisObat));
        obat.setObat_kadaluarsa((String) AES256.encrypt(kadaluarsaObat));
        obat.setObat_produsen((String) AES256.encrypt(produsenObat));
                
        Map<String, Object> responseMap = new HashMap<String, Object>();
        // ubah obatService.update => obatService.insert
        responseMap.put(Constants.STATUS, (obatService.insert(obat) != 0) ? Constants.OK : Constants.ERROR);
        System.out.println("Success ObatController.insert(): " + responseMap);

        return responseMap;
    }
    
    @RequestMapping(value = "/masterdata/obat/{id_obat}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> edit(@PathVariable("id_obat") int id_obat) {

    	Obat obat = obatService.getById(id_obat);
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(OBAT_KEY, obat);
        System.out.println("Success ObatController.getById(): " + responseMap);
    	
        return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/obat/{id_obat}", method = RequestMethod.PUT, produces = "application/json")
    public Map<String, Object> update(@PathVariable("id_obat") final int id_obat,
        @RequestBody final Map<String, Object> params) throws ParseException {

    	Map<String, Object> obatMap = (Map<String, Object>) params.get(OBAT_KEY);
        Obat obat = new Obat();
        
        //Variabel untuk encrypt        
        String kodeObat = (String) obatMap.get("kd_obat");
        String namaObat = (String) obatMap.get("obat_nama");
        String jenisObat = (String) obatMap.get("obat_jenis");
        String kadaluarsaObat = (String) obatMap.get("obat_kadaluarsa");
        String produsenObat = (String) obatMap.get("obat_produsen");
        
        obat.setId_obat(id_obat);
        obat.setKd_obat((String) AES256.encrypt(kodeObat));
        obat.setObat_nama((String) AES256.encrypt(namaObat));
        obat.setObat_jenis((String) AES256.encrypt(jenisObat));
        obat.setObat_kadaluarsa((String) AES256.encrypt(kadaluarsaObat));
        obat.setObat_produsen((String) AES256.encrypt(produsenObat));

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (obatService.update(obat) != 0) ? Constants.OK : Constants.ERROR);
        System.out.println("Success ObatController.update(): " + responseMap);

        return responseMap;
    }
}
