/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaction.service.impl;

import com.je.transaction.dao.RekamMedisDao;
import com.je.transaction.model.RekamMedis;
import com.je.transaction.service.RekamMedisService;
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
@Service("rekamMedisService")
@Transactional
public class RekamMedisServiceImpl implements RekamMedisService {
    
    @Autowired
    RekamMedisDao rekamMedisDao;

    public List<RekamMedis> getAll() {
        return rekamMedisDao.getAll();
    }

    public RekamMedis getById(String rekammedis_id) {
        return rekamMedisDao.getById(rekammedis_id);
    }

    public long insert(RekamMedis rekamMedis) {
        return rekamMedisDao.insert(rekamMedis);
    }

    public long update(RekamMedis rekamMedis) {
        return rekamMedisDao.update(rekamMedis);
    }

    public long delete(RekamMedis rekamMedis) {
        return rekamMedisDao.delete(rekamMedis);
    }

    public long count() {
        return rekamMedisDao.count();
    }

    public List<RekamMedis> searchKeyword(String keyword) {
        List<RekamMedis> listRekamMedis = new ArrayList();
        listRekamMedis = rekamMedisDao.searchData(keyword);
        
        return listRekamMedis;
    }
    
}
