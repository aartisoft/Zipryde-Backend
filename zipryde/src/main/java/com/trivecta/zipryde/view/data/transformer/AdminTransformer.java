package com.trivecta.zipryde.view.data.transformer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trivecta.zipryde.constants.ErrorMessages;
import com.trivecta.zipryde.framework.exception.MandatoryValidationException;
import com.trivecta.zipryde.model.entity.CabType;
import com.trivecta.zipryde.model.entity.Make;
import com.trivecta.zipryde.model.entity.Model;
import com.trivecta.zipryde.model.entity.Nyop;
import com.trivecta.zipryde.model.entity.PricingMstr;
import com.trivecta.zipryde.model.entity.PricingType;
import com.trivecta.zipryde.model.service.AdminService;
import com.trivecta.zipryde.view.request.CommonRequest;
import com.trivecta.zipryde.view.request.PricingMstrRequest;
import com.trivecta.zipryde.view.response.CabTypeResponse;
import com.trivecta.zipryde.view.response.MakeModelResponse;
import com.trivecta.zipryde.view.response.NYOPResponse;
import com.trivecta.zipryde.view.response.PricingMstrResponse;
import com.trivecta.zipryde.view.response.PricingTypeResponse;

@Component
public class AdminTransformer {

	@Autowired
	AdminService adminService;
	
	public List<CabTypeResponse> getAllCabTypes() {
		List<CabTypeResponse> cabTypeResponseList = 
				new ArrayList<CabTypeResponse>();
		
		List<CabType> cabTypes = adminService.getAllCabTypes();
		
		if(cabTypes != null && cabTypes.size() > 0) {
			for(CabType cabType : cabTypes) {
				CabTypeResponse cabTypeResponse = 
						new CabTypeResponse();
				
				cabTypeResponse.setCabTypeId(cabType.getId());
				cabTypeResponse.setEngineType(cabType.getEngineType());
				cabTypeResponse.setIsEnable(cabType.getIsEnable());
				cabTypeResponse.setLevel(cabType.getLevel());
				cabTypeResponse.setType(cabType.getType());
				
				cabTypeResponse.setSeatingCapacity(cabType.getSeatingCapacity());
				
				if(cabType.getPricingMstrs() != null && cabType.getPricingMstrs().size() > 0 ) {
					if(cabType.getPricingMstrs().get(0).getPricePerUnit()  != null) {
						Double pricePerUnit = 
								cabType.getPricingMstrs().get(0).getPricePerUnit().doubleValue();				
						cabTypeResponse.setPricePerUnit(pricePerUnit);
					}
				}
				
				cabTypeResponseList.add(cabTypeResponse);
			}
		}
		return cabTypeResponseList;
	}
	
	public List<MakeModelResponse> getAllMake() {
		List<MakeModelResponse> makeModelResponseList = 
				new ArrayList<MakeModelResponse>();
		
		List<Make> makeList = adminService.getAllMake();
		
		if(makeList != null && makeList.size() > 0) {
			for(Make make : makeList) {
				MakeModelResponse makeModelResponse = 
						new MakeModelResponse();
				makeModelResponse.setMake(make.getMake());
				makeModelResponse.setMakeModelId(make.getId());
				
				makeModelResponseList.add(makeModelResponse);
			}
		}
		return makeModelResponseList;
	}
	
	public List<MakeModelResponse> getAllModelByMakeId(CommonRequest commonRequest) {
		List<MakeModelResponse> makeModelResponseList = 
				new ArrayList<MakeModelResponse>();
		
		List<Model> modelList = adminService.getAllModelByMakeId(commonRequest.getMakeId().intValue());
		
		if(modelList != null && modelList.size() > 0) {
			for(Model model : modelList) {
				MakeModelResponse makeModelResponse = 
						new MakeModelResponse();
				makeModelResponse.setModel(model.getModel());
				makeModelResponse.setMakeModelId(model.getId());
				
				makeModelResponseList.add(makeModelResponse);
			}
		}
		return makeModelResponseList;
	}
	
	public  List<NYOPResponse> getAllNYOPList() {
		List<NYOPResponse> nyopRespList = new ArrayList<NYOPResponse>();
		
		List<Nyop> nyopList = adminService.getAllNyopList();
		
		if(nyopList != null && nyopList.size() > 0){
			for(Nyop nyop : nyopList) {
				NYOPResponse nyopResponse = new NYOPResponse();
				nyopResponse.setPercentage(nyop.getPercentage());
				nyopRespList.add(nyopResponse);
			}
		}
		return nyopRespList;
	}
	
	public List<NYOPResponse> getAllNYOPByCabTypeDistAndNoOfPassenger(CommonRequest commonRequest) throws MandatoryValidationException{
		
		if(commonRequest.getDistanceInMiles() != null && commonRequest.getCabTypeId() != null && 
				commonRequest.getNoOfPassengers() != null) {
			List<NYOPResponse> nyopResponseList = 
					new ArrayList<NYOPResponse>();
			
			
			Map<Integer,BigDecimal> nyopPricingList =
					adminService.getAllNYOPByCabTypeDistanceAndPerson(
							commonRequest.getDistanceInMiles().intValue(), 
							commonRequest.getCabTypeId().intValue() ,
							commonRequest.getNoOfPassengers().intValue());
			
			
			Iterator<Map.Entry<Integer, BigDecimal>> entries = nyopPricingList.entrySet().iterator();
			while (entries.hasNext()) {
			    Map.Entry<Integer, BigDecimal> entry = entries.next();
			    
			    NYOPResponse nyopResponse = new NYOPResponse();
			    nyopResponse.setPercentage(entry.getKey());
			    nyopResponse.setPrice(String.valueOf(entry.getValue()));
			    
			    nyopResponseList.add(nyopResponse);
			}
			
			return nyopResponseList;
		}
		else {
			throw new MandatoryValidationException(ErrorMessages.DISTANCE_CAB_TYPE_PERSON_MANDATORY);
		}	
	}
	
	public List<PricingTypeResponse> getAllEnabledPricingType() {
		List<PricingTypeResponse> pricingTypeResponseList = new ArrayList<PricingTypeResponse>();
		List<PricingType> pricingTypeList = adminService.getAllEnabledPricingType();
		if(pricingTypeList != null && pricingTypeList.size() > 0) {
			for(PricingType pricingType : pricingTypeList) {
				PricingTypeResponse pricingTypeResp = new PricingTypeResponse();
				pricingTypeResp.setPricingTypeId(pricingType.getId());
				pricingTypeResp.setPricingType(pricingType.getType());
				pricingTypeResponseList.add(pricingTypeResp);
			}
		}
		return pricingTypeResponseList;		
	}
	
	public List<PricingMstrResponse> getAllPricingMstrByCabType(CommonRequest commonRequest) throws MandatoryValidationException {
		if(commonRequest.getCabTypeId() == null) {
			throw new MandatoryValidationException(ErrorMessages.CAB_TYPE_REQUIRED);
		}
		List<PricingMstr> pricingMstrList = adminService.getAllPricingMstrByCabType(commonRequest.getCabTypeId().intValue());
		List<PricingMstrResponse> pricingMstrRespList = setPricingMstrResponse(pricingMstrList);
		return pricingMstrRespList;				
	}
	
	public void savePricingMstrs(List<PricingMstrRequest> pricingMstrReqList) {
		List<PricingMstr> pricingMstrs = setPricingMstrListFromRequest(pricingMstrReqList);
		adminService.savePricingMstrs(pricingMstrs);
	}
	
	private List<PricingMstrResponse>  setPricingMstrResponse(List<PricingMstr> pricingMstrList) {
		List<PricingMstrResponse> pricingMstrRespList = new ArrayList<PricingMstrResponse>();
		
		if(pricingMstrList != null && pricingMstrList.size() >0 ) {
			for(PricingMstr pricingMstr : pricingMstrList) {
				PricingMstrResponse pricingMstrResponse  = new PricingMstrResponse();
				
				CabTypeResponse cabTypeResponse = new CabTypeResponse();
				cabTypeResponse.setCabTypeId(pricingMstr.getCabType().getId());
				cabTypeResponse.setType(pricingMstr.getCabType().getType());
				pricingMstrResponse.setCabTypeResponse(cabTypeResponse);
				
				PricingTypeResponse pricingTypeResponse = new PricingTypeResponse();
				pricingTypeResponse.setPricingTypeId(pricingMstr.getPricingType().getId());
				pricingTypeResponse.setPricingType(pricingMstr.getPricingType().getType());
				
				pricingMstrResponse.setPricingTypeResponse(pricingTypeResponse);

				pricingMstrResponse.setIsEnable(pricingMstr.getIsEnable());
				pricingMstrResponse.setPricingMstrId(pricingMstr.getId());
				
				if(pricingMstr.getPrice() != null) {
					pricingMstrResponse.setPrice(pricingMstr.getPrice().doubleValue());
				}
				else {
					pricingMstrResponse.setPrice(pricingMstr.getPricePerUnit().doubleValue());
				}
				pricingMstrRespList.add(pricingMstrResponse);
			}
		}
		return pricingMstrRespList;
	}
	
	private List<PricingMstr> setPricingMstrListFromRequest(List<PricingMstrRequest> pricingMstrReqList) {
		List<PricingMstr> pricingMstrList = null;
		
		if(pricingMstrReqList != null && pricingMstrReqList.size() > 0) {
			pricingMstrList = new ArrayList<PricingMstr>();
			
			for(PricingMstrRequest pricingMstrReq  : pricingMstrReqList) {
				PricingMstr pricingMstr = new PricingMstr();
				CabType cabType = new CabType();
				cabType.setId(pricingMstrReq.getCabTypeId().intValue());
				pricingMstr.setCabType(cabType);
				
				PricingType pricingType = new PricingType();
				pricingType.setId(pricingMstrReq.getPricingTypeId().intValue());
				pricingMstr.setPricingType(pricingType);
				
				if(pricingMstrReq.getPricingMstrId() != null) {
					pricingMstr.setId(pricingMstrReq.getPricingMstrId().intValue());
				}
				pricingMstr.setIsEnable(pricingMstrReq.getIsEnable().intValue());
				pricingMstr.setPrice(new BigDecimal(pricingMstrReq.getPrice()).setScale(2,RoundingMode.CEILING));
				
				pricingMstrList.add(pricingMstr);				
			}
		}
		return pricingMstrList;
	}
}
