/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaction.controller;

//import com.je.rumahsakit.master.model.Kamar;
//import com.je.rumahsakit.master.model.Pasien;
//import com.je.rumahsakit.master.service.KamarService;
//import com.je.rumahsakit.master.service.PasienService;
import com.je.transaction.model.RawatKamarPasien;
import com.je.transaction.service.RawatKamarPasienService;
import com.je.util.Constants;
import com.je.util.Utils;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RawatKamarPasienController {
//     private static final String RAWAT_KAMAR_PASIEN_KEY = "rawatKamarPasien";
//     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//
//    @Autowired
//    private RawatKamarPasienService rawatKamarPasienService;
//    
//    @Autowired
//    private KamarService kamarService;
//    
//    @Autowired
//    private PasienService pasienService;
//
//    @RequestMapping(value = "/transaction/rawatkamarpasien", method = RequestMethod.GET, produces = "application/json")
//    public Map<String, Object> getAll(@RequestParam(required = false) Map<String, String> params) {
//    	
//        int activePage = NumberUtils.toInt(params.get(Constants.ACTIVE_PAGE));
//        int start = (activePage - 1) * Constants.GRIDBOX_MAX_ROW_PER_PAGE;
//        int limit = Constants.GRIDBOX_MAX_ROW_PER_PAGE;
//        String orderBy = (String) params.get(Constants.ORDER);
//        Map<String, String> assocParams = Utils.getAssocParams(params);
//
//        Map<String, Object> responseMap = new HashMap<String, Object>();
//        responseMap.put(Constants.LIST, rawatKamarPasienService.getAll(start, limit, orderBy, assocParams));
//        responseMap.put(Constants.TOTAL, responseMap.size());
//        return responseMap;
//
//    }
//    
//    
//    @RequestMapping(value = "/transaction/rawatkamarpasien/{kode}", method = RequestMethod.GET, produces = "application/json")
//    public Map<String, Object> edit(@PathVariable("kode") String kode) {
//
//    	
//    	 RawatKamarPasien rawatKamarPasien = rawatKamarPasienService.getById(kode);
//
//         Map<String, Object> responseMap = new HashMap<String, Object>();
//         responseMap.put(RAWAT_KAMAR_PASIEN_KEY, rawatKamarPasien);
//
//         return responseMap;
//    }
//   
//    @RequestMapping(value = "/transaction/rawatkamarpasien/{kode}", method = RequestMethod.DELETE, produces = "application/json")
//    public Map<String, Object> delete(@PathVariable("kode") final String kode) {
//    	
//    	
//    	 RawatKamarPasien rawatKamarPasien = new RawatKamarPasien();
//         rawatKamarPasien.setKode(kode);
//
//         Map<String, Object> responseMap = new HashMap<String, Object>();
//         responseMap.put(Constants.STATUS, (rawatKamarPasienService.delete(rawatKamarPasien) != 0) ? Constants.OK : Constants.ERROR);
//
//         return responseMap;
//
//    }
//    
//    @SuppressWarnings("unchecked")
//    @RequestMapping(value = "/transaction/rawatkamarpasien", method = RequestMethod.POST, produces = "application/json")
//    public Map<String, Object> insert(@RequestBody final Map<String, Object> params) throws ParseException {
//
//        Date waktu = new Date();
//        SimpleDateFormat frmWaktuKode = new SimpleDateFormat("yyyyMMddHHmmss");
//	String kode = "RKP"+frmWaktuKode.format(waktu);
//        
//        Map<String, Object> responseMap = new HashMap<String, Object>();
//    	
//    	Map<String, Object> rawatKamarPasienMap = (Map<String, Object>) params.get(RAWAT_KAMAR_PASIEN_KEY);
//        RawatKamarPasien rawatKamarPasien = new RawatKamarPasien();
//        
//        Date tanggalMasuk = null;
//        if(rawatKamarPasienMap.get("tanggalMasuk") instanceof String){
//        	 String tanggalMasukString = (String)rawatKamarPasienMap.get("tanggalMasuk");
//        	 tanggalMasuk = formatter.parse(tanggalMasukString);
//        }else{
//        	 Long tanggalMasukLong = (Long)rawatKamarPasienMap.get("tanggalMasuk");
//                 tanggalMasuk = new Date(tanggalMasukLong);
//        }
//        
//        Date tanggalKeluar = null;
//        if(rawatKamarPasienMap.get("tanggalKeluar") instanceof String){
//        	 String tanggalKeluarString = (String)rawatKamarPasienMap.get("tanggalKeluar");
//        	 tanggalKeluar = formatter.parse(tanggalKeluarString);
//        }else{
//        	 Long tanggalKeluarLong = (Long)rawatKamarPasienMap.get("tanggalKeluar");
//                 tanggalKeluar = new Date(tanggalKeluarLong);
//        }
//        
//        Map<String, Object> kamarMap = (Map<String, Object>) rawatKamarPasienMap.get("kamar");
//        Map<String, Object> pasienMap = (Map<String, Object>) rawatKamarPasienMap.get("pasien");
//
//       // Validasi
//       Kamar kamar= kamarService.getById((String)kamarMap.get("kode"));
//       if(kamar == null){
//           responseMap.put(Constants.ERROR, "Kamar "+(String)kamarMap.get("kode")+" Tidak Ada Di Database");
//           return responseMap;
//       }
//       
//        Pasien pasien= pasienService.getById((String)pasienMap.get("kode"));
//       if(pasien == null){
//           responseMap.put(Constants.ERROR, "Pasien "+(String)pasienMap.get("kode")+" Tidak Ada Di Database");
//           return responseMap;
//       }
//       
//
//        rawatKamarPasien.setKode(kode);
//        rawatKamarPasien.setTanggalMasuk(new Timestamp(tanggalMasuk.getTime()));
//        rawatKamarPasien.setTanggalKeluar(new Timestamp(tanggalKeluar.getTime()));
//        rawatKamarPasien.setKeterangan((String) rawatKamarPasienMap.get("keterangan"));
//        rawatKamarPasien.setKamar(kamar);
//        rawatKamarPasien.setPasien(pasien);
//        
//        
//       
//        responseMap.put(Constants.STATUS, (rawatKamarPasienService.insert(rawatKamarPasien) != 0) ? Constants.OK : Constants.ERROR);
//
//        return responseMap;
//
//
//    }
//
//    
//    @SuppressWarnings("unchecked")
//    @RequestMapping(value = "/transaction/rawatkamarpasien/{kode}", method = RequestMethod.PUT, produces = "application/json")
//    public Map<String, Object> update(@PathVariable("kode") final String kode,
//            @RequestBody final Map<String, Object> params) throws ParseException {
//        
//        Map<String, Object> responseMap = new HashMap<String, Object>();
//    	
//    	Map<String, Object> rawatKamarPasienMap = (Map<String, Object>) params.get(RAWAT_KAMAR_PASIEN_KEY);
//        RawatKamarPasien rawatKamarPasien = new RawatKamarPasien();
//        
//        Date tanggalMasuk = null;
//        if(rawatKamarPasienMap.get("tanggalMasuk") instanceof String){
//        	 String tanggalMasukString = (String)rawatKamarPasienMap.get("tanggalMasuk");
//        	 tanggalMasuk = formatter.parse(tanggalMasukString);
//        }else{
//        	 Long tanggalMasukLong = (Long)rawatKamarPasienMap.get("tanggalMasuk");
//                 tanggalMasuk = new Date(tanggalMasukLong);
//        }
//        
//        Date tanggalKeluar = null;
//        if(rawatKamarPasienMap.get("tanggalKeluar") instanceof String){
//        	 String tanggalKeluarString = (String)rawatKamarPasienMap.get("tanggalKeluar");
//        	 tanggalKeluar = formatter.parse(tanggalKeluarString);
//        }else{
//        	 Long tanggalKeluarLong = (Long)rawatKamarPasienMap.get("tanggalKeluar");
//                 tanggalKeluar = new Date(tanggalKeluarLong);
//        }
//        
//        Map<String, Object> kamarMap = (Map<String, Object>) rawatKamarPasienMap.get("kamar");
//        Map<String, Object> pasienMap = (Map<String, Object>) rawatKamarPasienMap.get("pasien");
//
//       // Validasi
//       Kamar kamar= kamarService.getById((String)kamarMap.get("kode"));
//       if(kamar == null){
//           responseMap.put(Constants.ERROR, "Kamar "+(String)kamarMap.get("kode")+" Tidak Ada Di Database");
//           return responseMap;
//       }
//       
//        Pasien pasien= pasienService.getById((String)pasienMap.get("kode"));
//       if(pasien == null){
//           responseMap.put(Constants.ERROR, "Pasien "+(String)pasienMap.get("kode")+" Tidak Ada Di Database");
//           return responseMap;
//       }
//
//        rawatKamarPasien.setKode(kode);
//        rawatKamarPasien.setTanggalMasuk(new Timestamp(tanggalMasuk.getTime()));
//        rawatKamarPasien.setTanggalKeluar(new Timestamp(tanggalKeluar.getTime()));
//        rawatKamarPasien.setKeterangan((String) rawatKamarPasienMap.get("keterangan"));
//        rawatKamarPasien.setKamar(kamar);
//        rawatKamarPasien.setPasien(pasien);
//        
//        
//       
//        responseMap.put(Constants.STATUS, (rawatKamarPasienService.update(rawatKamarPasien) != 0) ? Constants.OK : Constants.ERROR);
//
//        return responseMap;
//    
//    }

    
}
