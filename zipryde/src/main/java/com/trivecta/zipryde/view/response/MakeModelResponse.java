package com.trivecta.zipryde.view.response;

public class MakeModelResponse {

	private Integer makeModelId;
	
	private Integer makeId;
	
	private Integer isEnable;

	private String make;
	
	private String model;

	public Integer getMakeModelId() {
		return makeModelId;
	}

	public void setMakeModelId(Integer makeModelId) {
		this.makeModelId = makeModelId;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	
	public Integer getMakeId() {
		return makeId;
	}

	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}
}
