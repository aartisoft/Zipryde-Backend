package com.trivecta.zipryde.model.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.trivecta.zipryde.model.entity.CabType;
import com.trivecta.zipryde.model.entity.Make;
import com.trivecta.zipryde.model.entity.Model;
import com.trivecta.zipryde.model.entity.Nyop;
import com.trivecta.zipryde.model.entity.UserType;

public interface AdminService {

	public List<Make> getAllMake();
	
	public List<Model> getAllModelByMakeId(int makeId);
	
	public List<CabType> getAllCabTypes();
	
	public List<UserType> getAllUserTypes();
	
	public List<Nyop> getAllNyopList();
	
	public Map<Integer,BigDecimal> getAllNYOPByCabTypeAndDistance(int NoOfMiles, int cabTypeId);
}
