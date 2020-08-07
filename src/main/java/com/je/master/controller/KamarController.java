package com.je.master.controller;





import com.je.master.model.Kamar;
import com.je.master.service.KamarService;
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

import com.je.util.Constants;
import com.je.util.Utils;


@Controller
public class KamarController {
	
	
    private static final String KAMAR_KEY = "kamar";

    @Autowired
    private KamarService kamarService;

    @RequestMapping(value = "/masterdata/kamar", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getAll(@RequestParam(required = false) Map<String, String> params) {
    	
        int activePage = NumberUtils.toInt(params.get(Constants.ACTIVE_PAGE));
        int start = (activePage - 1) * Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        int limit = Constants.GRIDBOX_MAX_ROW_PER_PAGE;
        String orderBy = (String) params.get(Constants.ORDER);
        Map<String, String> assocParams = Utils.getAssocParams(params);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.LIST, kamarService.getAll(start, limit, orderBy, assocParams));
        responseMap.put(Constants.TOTAL, responseMap.size());
        return responseMap;
    	
    	
    	
    }
    
    @RequestMapping(value = "/masterdata/kamar/{kode}", method = RequestMethod.DELETE, produces = "application/json")
    public Map<String, Object> delete(@PathVariable("kode") final String kode) {
    	
    	
    	 Kamar kamar = new Kamar();
         kamar.setKode(kode);

         Map<String, Object> responseMap = new HashMap<String, Object>();
         responseMap.put(Constants.STATUS, (kamarService.delete(kamar) != 0) ? Constants.OK : Constants.ERROR);

         return responseMap;

    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/kamar", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> insert(@RequestBody final Map<String, Object> params) {

    	
    	Map<String, Object> kamarMap = (Map<String, Object>) params.get(KAMAR_KEY);
        Kamar kamar = new Kamar();

        kamar.setKode((String) kamarMap.get("kode"));
        kamar.setNama((String) kamarMap.get("nama"));
        kamar.setKeterangan((String) kamarMap.get("keterangan"));
        
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constants.STATUS, (kamarService.insert(kamar) != 0) ? Constants.OK : Constants.ERROR);

        return responseMap;


    }

    @RequestMapping(value = "/masterdata/kamar/{kode}", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> edit(@PathVariable("kode") String kode) {

    	
    	 Kamar kamar = kamarService.getById(kode);

         Map<String, Object> responseMap = new HashMap<String, Object>();
         responseMap.put(KAMAR_KEY, kamar);

         return responseMap;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/masterdata/kamar/{kode}", method = RequestMethod.PUT, produces = "application/json")
    public Map<String, Object> update(@PathVariable("kode") final String kode,
            @RequestBody final Map<String, Object> params) {

    	Map<String, Object> kamarMap = (Map<String, Object>) params.get(KAMAR_KEY);
        Kamar kamar = new Kamar();

        kamar.setKode((String) kamarMap.get("kode"));
        kamar.setNama((String) kamarMap.get("nama"));
        kamar.setKeterangan((String) kamarMap.get("keterangan"));

         Map<String, Object> responseMap = new HashMap<String, Object>();
         responseMap.put(Constants.STATUS, (kamarService.update(kamar) != 0) ? Constants.OK : Constants.ERROR);

         return responseMap;


    }


}
