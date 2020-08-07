package com.je.master.controller;





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

import com.je.master.model.RumahSakit;
import com.je.master.service.RumahSakitService;
import com.je.util.Constants;
import com.je.util.Utils;

@Controller
public class RumahSakitController {
	
	
   private static final String RUMAH_SAKIT_KEY = "rumahsakit";

    @Autowired
    private RumahSakitService rumahSakitService;

    @RequestMapping(value = "/masterdata/rumahsakit", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getAll(@RequestParam(required = false) Map<String, String> params) {
    	
        int activePage = NumberUtils.toInt(params.get(Constants.ACTIVE_PAGE));
        int start = (activePage - 1) * Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        int limit = Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        String orderBy = (String) params.get(Constants.ORDER);
        Map<String, String> assocParams = Utils.getAssocParams(params);

        Map<String, Object> respMap = new HashMap<String, Object>();
        respMap.put(Constants.LIST, rumahSakitService.getAll(start, limit, orderBy, assocParams));
        long total = rumahSakitService.count(assocParams);
        respMap.put(Constants.TOTAL, total);
        return respMap;
    	
    	
    	
    }
    
    @RequestMapping(value = "/masterdata/rumahsakit/{kode}", method = RequestMethod.DELETE, produces = "application/json")
    public Map<String, Object> delete(@PathVariable("kode") final String kode) {
    	
    	
    	 RumahSakit rumahSakit = new RumahSakit();
         rumahSakit.setKode(kode);

         Map<String, Object> respMap = new HashMap<String, Object>();
         respMap.put(Constants.STATUS, (rumahSakitService.remove(rumahSakit) != 0) ? Constants.OK : Constants.ERROR);

         return respMap;

    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/rumahsakit", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> create(@RequestBody final Map<String, Object> params) {

    	
    	Map<String, Object> rumahSakitMap = (Map<String, Object>) params.get(RUMAH_SAKIT_KEY);
        RumahSakit rumahSakit = new RumahSakit();
        rumahSakit.setKode((String) rumahSakitMap.get("kode"));
        rumahSakit.setNama((String) rumahSakitMap.get("nama"));
        rumahSakit.setKoordinat((String) rumahSakitMap.get("koordinat"));
        rumahSakit.setAlamat((String) rumahSakitMap.get("alamat"));
        rumahSakit.setEmail((String) rumahSakitMap.get("email"));
        rumahSakit.setTelp((String) rumahSakitMap.get("telp"));
        rumahSakit.setKecamatan((String) rumahSakitMap.get("kecamatan"));
        rumahSakit.setKabupaten((String) rumahSakitMap.get("kabupaten"));
        rumahSakit.setPropinsi((String) rumahSakitMap.get("propinsi"));
        rumahSakit.setType((String) rumahSakitMap.get("type"));
        rumahSakit.setKepemilikan((String) rumahSakitMap.get("kepemilikan"));
        rumahSakit.setJenis((String) rumahSakitMap.get("jenis"));
        
        Map<String, Object> respMap = new HashMap<String, Object>();
        respMap.put(Constants.STATUS, (rumahSakitService.create(rumahSakit) != 0) ? Constants.OK : Constants.ERROR);

        return respMap;


    }

    @RequestMapping(value = "/masterdata/rumahsakit/{kode}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> edit(@PathVariable("kode") String kode) {

    	
    	 RumahSakit rumahSakit = rumahSakitService.getById(kode);

         Map<String, Object> respMap = new HashMap<String, Object>();
         respMap.put(RUMAH_SAKIT_KEY, rumahSakit);

         return respMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/rumahsakit/{kode}", method = RequestMethod.PUT, produces = "application/json")
    public Map<String, Object> update(@PathVariable("kode") final String kode,
            @RequestBody final Map<String, Object> params) {

    	 Map<String, Object> rumahSakitMap = (Map<String, Object>) params.get(RUMAH_SAKIT_KEY);
         RumahSakit rumahSakit = new RumahSakit();
         rumahSakit.setKode(kode);
         rumahSakit.setNama((String) rumahSakitMap.get("nama"));
         rumahSakit.setKoordinat((String) rumahSakitMap.get("koordinat"));
         rumahSakit.setAlamat((String) rumahSakitMap.get("alamat"));
         rumahSakit.setEmail((String) rumahSakitMap.get("email"));
         rumahSakit.setTelp((String) rumahSakitMap.get("telp"));
         rumahSakit.setKecamatan((String) rumahSakitMap.get("kecamatan"));
         rumahSakit.setKabupaten((String) rumahSakitMap.get("kabupaten"));
         rumahSakit.setPropinsi((String) rumahSakitMap.get("propinsi"));
         rumahSakit.setType((String) rumahSakitMap.get("type"));
         rumahSakit.setKepemilikan((String) rumahSakitMap.get("kepemilikan"));
         rumahSakit.setJenis((String) rumahSakitMap.get("jenis"));

         Map<String, Object> respMap = new HashMap<String, Object>();
         respMap.put(Constants.STATUS, (rumahSakitService.update(rumahSakit) != 0) ? Constants.OK : Constants.ERROR);

         return respMap;


    }


}
