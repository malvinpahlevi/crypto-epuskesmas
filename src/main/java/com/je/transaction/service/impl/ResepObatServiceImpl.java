
package com.je.transaction.service.impl;


import com.je.transaction.dao.ResepObatDao;
import com.je.transaction.model.ResepObat;
import com.je.transaction.service.ResepObatService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("resepObatService")
@Transactional
public class ResepObatServiceImpl implements ResepObatService{

    @Autowired
    ResepObatDao resepObatDao;

    public List<ResepObat> getAll() {
        return resepObatDao.getAll();
    }

    public ResepObat getById(int id_resepobat) {
        return resepObatDao.getById(id_resepobat);
    }

    public long insert(ResepObat resepObat) {
        return resepObatDao.insert(resepObat);
    }

    public long update(ResepObat resepObat) {
        return  resepObatDao.update(resepObat);
    }

    public long delete(ResepObat resepObat) {
        return resepObatDao.delete(resepObat);
    }

    public long count() {
        return resepObatDao.count();
    }

    public List<ResepObat> searchKeyword(String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
    
    
}
