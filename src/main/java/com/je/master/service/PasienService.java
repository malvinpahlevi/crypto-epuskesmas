package com.je.master.service;

import java.util.List;
import java.util.Map;

import com.je.master.model.Pasien;

public interface PasienService {
	
	public List<Pasien> getAll(int start, int limit, String order, Map<String, String> params);
	public Pasien getById(int id_pasien);
	public long insert(Pasien pasien);
	public long update(Pasien pasien);
	public long delete(Pasien pasien);
        public long count();
        public List<Pasien> searchKeyword(String keyword);
}

