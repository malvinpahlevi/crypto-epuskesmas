/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.je.master.dao.RumahSakitDao;
import com.je.master.model.RumahSakit;
import com.je.master.service.RumahSakitService;


@Service("rumahSakitService")
@Transactional
public class RumahSakitServiceImpl implements RumahSakitService{

    @Autowired
    private RumahSakitDao rumahSakitDao;

    public List<RumahSakit> getAll(int start, int limit, String order, Map<String, String> params) {
        return rumahSakitDao.getAll(start, limit, order, params);
    }

    public RumahSakit getById(String kode) {
        return rumahSakitDao.getById(kode);
    }

    public long count(Map<String, String> params) {
        return rumahSakitDao.count(params);
    }

    public long update(final RumahSakit rumahSakit) {

        return rumahSakitDao.update(rumahSakit);
    }

    public long create(final RumahSakit rumahSakit) {
        return rumahSakitDao.create(rumahSakit);
    }

    public long remove(final RumahSakit rumahSakit) {
        return rumahSakitDao.remove(rumahSakit);
    }
}
