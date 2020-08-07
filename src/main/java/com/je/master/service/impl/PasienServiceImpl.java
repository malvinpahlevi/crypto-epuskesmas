package com.je.master.service.impl;

import com.je.master.dao.PasienDao;
import com.je.master.model.Pasien;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.je.master.service.PasienService;
import java.util.ArrayList;

@Service("pasienService")
@Transactional
public class PasienServiceImpl implements PasienService{

    @Autowired
    private PasienDao pasienDao;

    public List<Pasien> getAll(int start, int limit, String order, Map<String, String> params) {
        return pasienDao.getAll(start, limit, order, params);
    }

    public Pasien getById(int id_pasien) {
        return pasienDao.getById(id_pasien);
    }

    public long insert(Pasien pasien) {
        return pasienDao.insert(pasien);
    }

    public long update(Pasien pasien) {
        return pasienDao.update(pasien);
    }

    public long delete(Pasien pasien) {
        return pasienDao.delete(pasien);
    }

    public long count() {
       return pasienDao.count();
    }

    public List<Pasien> searchKeyword(String keyword) {
        List<Pasien> listPasien = new ArrayList();
        listPasien = pasienDao.searchData(keyword);
        
        return listPasien;
    }
   
}
