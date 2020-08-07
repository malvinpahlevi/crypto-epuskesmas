/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.service.impl;

import com.je.master.dao.ObatDao;
import com.je.master.model.Obat;
import com.je.master.service.ObatService;
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
@Service("obatService")
@Transactional
public class ObatServiceImpl implements ObatService {

    @Autowired
    private ObatDao obatDao;
    
    public List<Obat> getAll(int start, int limit, String order, Map<String, String> params) {
        return obatDao.getAll(start, limit, order, params);
    }

    public Obat getById(int id_obat) {
        return obatDao.getById(id_obat);
    }

    public long insert(Obat obat) {
        return obatDao.insert(obat);
    }

    public long update(Obat obat) {
        return obatDao.update(obat);
    }

    public long delete(Obat obat) {
        return obatDao.delete(obat);
    }

    public long count() {
        return obatDao.count();
    }

    public List<Obat> searchKeyword(String keyword) {
        List<Obat> listObat = new ArrayList();
        listObat = obatDao.searchData(keyword);
        
        return listObat;
    }
    
}
