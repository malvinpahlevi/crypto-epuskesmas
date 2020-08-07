/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.service.impl;


import com.je.master.dao.KamarDao;
import com.je.master.model.Kamar;
import com.je.master.service.KamarService;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





@Service("kamarService")
@Transactional
public class KamarServiceImpl implements KamarService{

    @Autowired
    private KamarDao kamarDao;


    public List<Kamar> getAll(int start, int limit, String order, Map<String, String> params) {
       return kamarDao.getAll(start, limit, order, params);
    }

    public Kamar getById(String id) {
       return kamarDao.getById(id);
    }

    public long insert(Kamar kamar) {
        return kamarDao.insert(kamar);
    }

    public long update(Kamar kamar) {
        return kamarDao.update(kamar);
    }

    public long delete(Kamar kamar) {
        return kamarDao.delete(kamar);
    }


    
}
