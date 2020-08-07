/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.service.impl;

import com.je.master.dao.DokterDao;
import com.je.master.model.Dokter;
import com.je.master.service.DokterService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author AJ.P
 */
@Service("dokterService")
@Transactional
public class DokterServiceImpl implements DokterService {
    
    @Autowired
    private DokterDao dokterDao;

    public List<Dokter> getAll(int start, int limit, String order, Map<String, String> params) {
        return dokterDao.getAll(start, limit, order, params);
    }

    public Dokter getById(int id_dokter) {
        return dokterDao.getById(id_dokter);
    }

    public long insert(Dokter dokter) {
        return dokterDao.insert(dokter);
    }

    public long update(Dokter dokter) {
        return dokterDao.update(dokter);
    }

    public long delete(Dokter dokter) {
        return dokterDao.delete(dokter);
    }

    public long count() {
        return dokterDao.count();
    }

    public List<Dokter> seacrhKeyword(String keyword) {
        List<Dokter> listDokter = new ArrayList();
        listDokter = dokterDao.searchData(keyword);
        
        return listDokter;
    }
    
}
