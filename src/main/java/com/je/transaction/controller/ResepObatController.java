package com.je.transaction.controller;

import com.api.nexmedia.util.AES256;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.je.master.model.Dokter;
import com.je.master.model.Pasien;
import com.je.master.model.Penyakit;
import com.je.master.model.Obat;
import com.je.master.service.DokterService;
import com.je.master.service.ObatService;
import com.je.master.service.PasienService;
import com.je.master.service.PenyakitService;
import com.je.transaction.model.RekamMedis;
import com.je.transaction.model.ResepObat;
import com.je.transaction.service.RekamMedisService;
import com.je.transaction.service.ResepObatService;
import com.je.util.Constants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ResepObatController {
    private static final String RESEPOBAT_KEY = "resepObat";
    private static final String REKAMMEDIS_KEY = "rekamMedis";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private Gson gson = new GsonBuilder().create();
    
    @Autowired
    private ResepObatService resepObatService;
    
    @Autowired
    private RekamMedisService rekamMedisService;
    
    @Autowired
    private DokterService dokterService;
    
    @Autowired
    private PasienService pasienService;
    
    @Autowired
    private PenyakitService penyakitService;
    
    @Autowired
    private ObatService obatService;
    
    @RequestMapping(value = "/transaction/resepobat", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getAll(){
        
        List<ResepObat> list = resepObatService.getAll();
        List baru = new ArrayList();
        
        // membuka list ResepObat -> object baru
        for (ResepObat pl : list) {
            ResepObat png = new ResepObat();
            
            //variable decrypt
            String jumlahResepObat = pl.getResepobat_jumlah();
            
            png.setId_resepobat(pl.getId_resepobat());
            png.setResepobat_jumlah(AES256.decrypt(jumlahResepObat));
            png.setRekamMedis(pl.getRekamMedis());
            png.setObat(pl.getObat());
            
            baru.add(png);
            System.out.println("ini getAll Controller RO : " + gson.toJson(baru)); 
        }
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.LIST, baru);
        responseMap.put(Constants.TOTAL, responseMap.size());
        
        long count = resepObatService.count();
        responseMap.put(Constants.TOTAL, count);
        
        return responseMap;
    }
    
    @RequestMapping(value = "/transaction/resepobat/{id_resepobat}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> edit(@PathVariable("id_resepobat") int id_resepobat){
        
        ResepObat resepObat = resepObatService.getById(id_resepobat);
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(RESEPOBAT_KEY, resepObat);
        
        return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/transaction/resepobat", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> insert (@RequestBody final Map<String, Object> params) throws ParseException {
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        
        Map<String, Object> resepObatMap = (Map<String, Object>) params.get(RESEPOBAT_KEY);
        
        ResepObat resepObat = new ResepObat();
        
        Map<String, Object> rekamMedisMap = (Map<String, Object>) resepObatMap.get("rekamMedis");
        Map<String, Object> obatMap = (Map<String, Object>) resepObatMap.get("obat");
        
        System.out.println("Isi Object resepObat : " + gson.toJson(resepObat));
        
        RekamMedis rekamMedis = rekamMedisService.getById((String) rekamMedisMap.get("rekammedis_id"));
        System.out.println("Isi Object rekamMedis : " + gson.toJson(rekamMedis));
        if(rekamMedis == null){
            responseMap.put(Constants.ERROR, "Rekammedis" + (String) rekamMedisMap.get("rekammedis_id") + "tidak ada di database");
            return responseMap;
        }
                
        Obat obat = obatService.getById((Integer) obatMap.get("id_obat"));
        if(obat == null){
            responseMap.put(Constants.ERROR, "Obat" + (Integer) obatMap.get("id_obat") + "tidak ada di database");
            return responseMap;
        }
        
        //Variabel untuk encrypt
        String jumlahResepObat = (String) resepObatMap.get("resepobat_jumlah");
        
        resepObat.setResepobat_jumlah((String) AES256.encrypt(jumlahResepObat));
        resepObat.setRekamMedis(rekamMedis);
        resepObat.setObat(obat);
        System.out.println("Isi Object resepObat : " + gson.toJson(resepObat));
        responseMap.put(Constants.STATUS, (resepObatService.insert(resepObat) !=0) ? Constants.OK : Constants.ERROR);
        
        return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/transaction/resepobat/{id_resepobat}", method = RequestMethod.PUT, produces = "application/json")
    public Map<String, Object> update(@PathVariable("id_resepobat") final int id_resepobat,
            @RequestBody final Map<String, Object> params) throws ParseException{
    
        Map<String, Object> responseMap = new HashMap<String, Object>();
        
        Map<String, Object> resepObatMap = (Map<String, Object>) params.get(RESEPOBAT_KEY);
        ResepObat resepObat = new ResepObat();
        
        Map<String, Object> rekamMedisMap = (Map<String, Object>) resepObatMap.get("rekamMedis");
        Map<String, Object> obatMap = (Map<String, Object>) resepObatMap.get("obat");
        
        RekamMedis rekamMedis = rekamMedisService.getById((String) rekamMedisMap.get("rekammedis_id"));
        System.out.println("Isi Object rekamMedis : " + gson.toJson(rekamMedis));
        if(rekamMedis == null){
            responseMap.put(Constants.ERROR, "Rekammedis" + (String) rekamMedisMap.get("rekammedis_id") + "tidak ada di database");
            return responseMap;
        }
        
        Obat obat = obatService.getById((Integer) obatMap.get("id_obat"));
        if(obat == null){
            responseMap.put(Constants.ERROR, "Obat" + (Integer) obatMap.get("id_obat") + "tidak ada di database");
            return responseMap;
        }
        
        //variabel untuk encrypt
        String jumlahResepObat = (String) resepObatMap.get("resepobat_jumlah");
        
        resepObat.setId_resepobat(id_resepobat);
        resepObat.setRekamMedis(rekamMedis);
        resepObat.setResepobat_jumlah((String) AES256.encrypt(jumlahResepObat));
        resepObat.setObat(obat);
        
        responseMap.put(Constants.STATUS, (resepObatService.update(resepObat) != 0) ? Constants.OK : Constants.ERROR);
        System.out.println("INI RESPONSEMAP OBAT UPDATE" + gson.toJson(responseMap));
                
        return responseMap;
    }
    
    @RequestMapping(value = "/transaction/resepobat/{id_resepobat}", method = RequestMethod.DELETE, produces = "application/json")
    public Map<String, Object> delete(@PathVariable("id_resepobat") final int id_resepobat){
    
        ResepObat resepObat = new ResepObat();
        resepObat.setId_resepobat(id_resepobat);
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (resepObatService.delete(resepObat) != 0) ? Constants.OK : Constants.ERROR);
        
        return responseMap;
    }
    
}
