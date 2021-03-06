package com.trivecta.zipryde.view.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverVehicleAssociationResponse {


	private  Number driverVehicleId;
	
	private  Number cabId;
	
	private String cabType;
	
	private Number cabSeatingCapacity;
	
	private String  vin;
	
	private String vehicleNumber;
	
	private Number driverId;
	
	private String driverName;
	
	private String fromDate;
	
	private String toDate;

	public Number getDriverVehicleId() {
		return driverVehicleId;
	}

	public void setDriverVehicleId(Number driverVehicleId) {
		this.driverVehicleId = driverVehicleId;
	}

	public Number getCabId() {
		return cabId;
	}

	public void setCabId(Number cabId) {
		this.cabId = cabId;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Number getDriverId() {
		return driverId;
	}

	public void setDriverId(Number driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	public String getCabType() {
		return cabType;
	}

	public void setCabType(String cabType) {
		this.cabType = cabType;
	}

	public Number getCabSeatingCapacity() {
		return cabSeatingCapacity;
	}

	public void setCabSeatingCapacity(Number cabSeatingCapacity) {
		this.cabSeatingCapacity = cabSeatingCapacity;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

}
