/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.service.impl;

import com.je.master.dao.PenyakitDao;
import com.je.master.model.Penyakit;
import com.je.master.service.PenyakitService;
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
@Service("penyakitService")
@Transactional
public class PenyakitServiceImpl implements PenyakitService {

    @Autowired
    private PenyakitDao penyakitDao;
    
    public List<Penyakit> getAll(int start, int limit, String order, Map<String, String> params) {
        return penyakitDao.getAll(start, limit, order, params);
    }

    public Penyakit getById(int id_penyakit) {
        return penyakitDao.getById(id_penyakit);
    }

    public long insert(Penyakit penyakit) {
        return penyakitDao.insert(penyakit);
    }

    public long update(Penyakit penyakit) {
        return penyakitDao.update(penyakit);
    }

    public long delete(Penyakit penyakit) {
        return penyakitDao.delete(penyakit);
    }

    public long count() {
        return penyakitDao.count();
    }

    public List<Penyakit> searchKeyword(String keyword) {
        List<Penyakit> listPenyakit = new ArrayList();
        listPenyakit = penyakitDao.searchData(keyword);
        
        return listPenyakit;
    }
    
}
