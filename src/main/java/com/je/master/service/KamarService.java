package com.je.master.service;


import java.util.List;
import java.util.Map;

import com.je.master.model.Kamar;

public interface KamarService {
	
	 public List<Kamar> getAll(int start, int limit, String order, Map<String, String> params);
	 public Kamar getById(String id);
	 public long insert(Kamar kamar);
	 public long update(Kamar kamar);
	 public long delete(Kamar kamar);
}
