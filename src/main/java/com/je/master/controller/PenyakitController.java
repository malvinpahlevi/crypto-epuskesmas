/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.controller;

import com.api.nexmedia.util.AES256;
import com.je.master.model.Penyakit;
import com.je.master.service.PenyakitService;
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
public class PenyakitController {
    
    private static final String PENYAKIT_KEY = "penyakit";
    
    @Autowired
    private PenyakitService penyakitService;
    
    @RequestMapping(value = "/masterdata/penyakit", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getAll(@RequestParam(required = false) Map<String, String> params) {
         	
        int activePage = NumberUtils.toInt(params.get(Constants.ACTIVE_PAGE));
        int start = (activePage - 1) * Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        int limit = Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        String orderBy = (String) params.get(Constants.ORDER);
        Map<String, String> assocParams = Utils.getAssocParams(params);
        System.out.println("Ini active page" + activePage);
        System.out.println("Ini order by" + orderBy);
        
        List<Penyakit> list = penyakitService.getAll(start, limit, orderBy, assocParams);
        List baru = new ArrayList();
        
        //membuka list penyakit -> object baru
        for (Penyakit pl : list) {
            Penyakit png = new Penyakit();
            
                //Variable decrypt
                String namaPenyakit = pl.getPenyakit_nama();
                String deskripsiPenyakit = pl.getPenyakit_deskripsi();
                String jenisPenyakit = pl.getPenyakit_jenis();
                
                png.setId_penyakit(pl.getId_penyakit());
                png.setPenyakit_nama(AES256.decrypt(namaPenyakit));
                png.setPenyakit_deskripsi(AES256.decrypt(deskripsiPenyakit));
                png.setPenyakit_jenis(AES256.decrypt(jenisPenyakit));
                
            baru.add(png);
        }
        

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.LIST, baru);
        responseMap.put(Constants.TOTAL, responseMap.size());
        
        long count = penyakitService.count();
        responseMap.put(Constants.TOTAL, count);
        System.out.println("Success PenyakitController.getAll()" + responseMap);

        return responseMap;
    }
    
    @RequestMapping(value = "/masterdata/penyakit/{id_penyakit}", method = RequestMethod.DELETE, produces = "application/json")
    public @ResponseBody Map<String, Object> delete(@PathVariable("id_penyakit") final int id_penyakit){
       
        Penyakit penyakit = new Penyakit();
        penyakit.setId_penyakit(id_penyakit);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (penyakitService.delete(penyakit) != 0) ? Constants.OK : Constants.ERROR);
        System.out.println("Success PenyakitController.delete(): " + responseMap);

        return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/penyakit", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> insert(@RequestBody final Map<String, Object> params) throws ParseException {

   	Map<String, Object> penyakitMap = (Map<String, Object>) params.get(Constants.PENYAKIT_KEY);
        Penyakit penyakit = new Penyakit();
        
        //Variabel untuk encrypt
        String namaPenyakit = (String) penyakitMap.get("penyakit_nama");
        String deskripsiPenyakit = (String) penyakitMap.get("penyakit_deskripsi");
        String jenisPenyakit = (String) penyakitMap.get("penyakit_jenis");
        
        penyakit.setPenyakit_nama((String) AES256.encrypt(namaPenyakit));
        penyakit.setPenyakit_deskripsi((String) AES256.encrypt(deskripsiPenyakit));
        penyakit.setPenyakit_jenis((String) AES256.encrypt(jenisPenyakit));
                
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (penyakitService.insert(penyakit) != 0) ? Constants.OK : Constants.ERROR);
        System.out.println("Success PenyakitController.insert()" + responseMap);
        

        return responseMap;
    }
    
    @RequestMapping(value = "/masterdata/penyakit/{id_penyakit}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> edit(@PathVariable("id_penyakit") int id_penyakit) {

    	Penyakit penyakit = penyakitService.getById(id_penyakit);
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(PENYAKIT_KEY, penyakit);
        System.out.println("Success PenyakitController.getById(): " + responseMap);
    	
        return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/penyakit/{id_penyakit}", method = RequestMethod.PUT, produces = "application/json")
    public Map<String, Object> update(@PathVariable("id_penyakit") final int id_penyakit,
        @RequestBody final Map<String, Object> params) throws ParseException {

    	Map<String, Object> penyakitMap = (Map<String, Object>) params.get(PENYAKIT_KEY);
        Penyakit penyakit = new Penyakit();
        
        //Variabel untuk encrypt
        String namaPenyakit = (String) penyakitMap.get("penyakit_nama");
        String deskripsiPenyakit = (String) penyakitMap.get("penyakit_deskripsi");
        String jenisPenyakit = (String) penyakitMap.get("penyakit_jenis");
        
        penyakit.setId_penyakit(id_penyakit);
        penyakit.setPenyakit_nama((String) AES256.encrypt(namaPenyakit));
        penyakit.setPenyakit_deskripsi((String) AES256.encrypt(deskripsiPenyakit));
        penyakit.setPenyakit_jenis((String) AES256.encrypt(jenisPenyakit));
                

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (penyakitService.update(penyakit) != 0) ? Constants.OK : Constants.ERROR);
        System.out.println("Success PenyakitController.update(): " + responseMap);
        
        return responseMap;
    }
}
