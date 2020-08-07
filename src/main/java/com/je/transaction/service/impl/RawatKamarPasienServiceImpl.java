/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.transaction.service.impl;

import com.je.transaction.model.RawatKamarPasien;
import com.je.transaction.dao.RawatKamarPasienDao;
import com.je.transaction.service.RawatKamarPasienService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("rawatKamarPasienService")
@Transactional
public class RawatKamarPasienServiceImpl implements RawatKamarPasienService{
    
    @Autowired
    RawatKamarPasienDao rawatKamarPasienDao;

    public List<RawatKamarPasien> getAll(int start, int limit, String order, Map<String, String> params) {
        return rawatKamarPasienDao.getAll(start, limit, order, params);
    }

    public RawatKamarPasien getById(String id) {
       return rawatKamarPasienDao.getById(id);
    }

    public long insert(RawatKamarPasien rawatKamarPasien) {
        return rawatKamarPasienDao.insert(rawatKamarPasien);
    }

    public long update(RawatKamarPasien rawatKamarPasien) {
        return rawatKamarPasienDao.update(rawatKamarPasien);
    }

    public long delete(RawatKamarPasien rawatKamarPasien) {
        return rawatKamarPasienDao.delete(rawatKamarPasien);
    }
    
}
