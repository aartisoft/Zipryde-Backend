
package com.trivecta.zipryde.view.response;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "userId", "userType", "firstName", "lastName", "mobileNumber", "alternateNumber", "emailId",
		"driverProfileId", "licenseNo", "licenseIssuedOn", "licenseValidUntil","defaultPercentageAccepted", "isOnline", "isEnable",
		"cancellationCount","statusCode", "status", "comments", "restriction", "vehicleNumber","bookingId","accessToken",
		"licensePlateNumber,insuranceCompany","insuranceValidUntil","insuranceNo","make","model",
		"licenseFrontImage", "licenseBackImage", "userImage" })

public class UserResponse {

	@JsonProperty("userId")
	private Number userId;

	@JsonProperty("userType")
	private String userType;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("mobileNumber")
	private String mobileNumber;

	@JsonProperty("alternateNumber")
	private String alternateNumber;

	@JsonProperty("emailId")
	private String emailId;

	@JsonProperty("driverProfileId")
	private Number driverProfileId;

	@JsonProperty("licenseNo")
	private String licenseNo;

	@JsonProperty("licenseIssuedOn")
	private String licenseIssuedOn;

	// Format : MM-dd-YYYY
	@JsonProperty("licenseValidUntil")
	private String licenseValidUntil;

	@JsonProperty("defaultPercentageAccepted")
	private Number defaultPercentageAccepted;

	@JsonProperty("isOnline")
	private Integer isOnline;

	@JsonProperty("isEnable")
	private Number isEnable;

	@JsonProperty("statusCode")
	private String statusCode;

	@JsonProperty("status")
	private String status;

	@JsonProperty("comments")
	private String comments;

	@JsonProperty("restriction")
	private String restriction;

	@JsonProperty("licenseFrontImage")
	String licenseFrontImage;

	@JsonProperty("licenseBackImage")
	String licenseBackImage;

	@JsonProperty("userImage")
	String userImage;
	
	@JsonProperty("cancellationCount")
	private Integer cancellationCount;
	
	@JsonProperty("vehicleNumber")
	private String vehicleNumber;
	
	@JsonProperty("bookingId")
	private Integer bookingId;
	
	@JsonProperty("accessToken")
	private String accessToken;
	
	/* MAIL Changes : ZipRyde App Changes to be compliant with TX State Requirements */
	private String licensePlateNumber;
	
	private String insuranceCompany;
	
	private String insuranceNo;
	
	private String insuranceValidUntil;
	
	private String make;
	
	private String model;

	public Number getUserId() {
		return userId;
	}

	public void setUserId(Number userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Number getDriverProfileId() {
		return driverProfileId;
	}

	public void setDriverProfileId(Number driverProfileId) {
		this.driverProfileId = driverProfileId;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getLicenseValidUntil() {
		return licenseValidUntil;
	}

	public void setLicenseValidUntil(String licenseValidUntil) {
		this.licenseValidUntil = licenseValidUntil;
	}

	public Number getDefaultPercentageAccepted() {
		return defaultPercentageAccepted;
	}

	public void setDefaultPercentageAccepted(Number defaultPercentageAccepted) {
		this.defaultPercentageAccepted = defaultPercentageAccepted;
	}

	public String getAlternateNumber() {
		return alternateNumber;
	}

	public void setAlternateNumber(String alternateNumber) {
		this.alternateNumber = alternateNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getLicenseIssuedOn() {
		return licenseIssuedOn;
	}

	public void setLicenseIssuedOn(String licenseIssuedOn) {
		this.licenseIssuedOn = licenseIssuedOn;
	}

	public Number getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Number isEnable) {
		this.isEnable = isEnable;
	}

	public String getRestriction() {
		return restriction;
	}

	public void setRestriction(String restriction) {
		this.restriction = restriction;
	}

	public String getLicenseFrontImage() {
		return licenseFrontImage;
	}

	public void setLicenseFrontImage(String licenseFrontImage) {
		this.licenseFrontImage = licenseFrontImage;
	}

	public String getLicenseBackImage() {
		return licenseBackImage;
	}

	public void setLicenseBackImage(String licenseBackImage) {
		this.licenseBackImage = licenseBackImage;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public Integer getCancellationCount() {
		return cancellationCount;
	}

	public void setCancellationCount(Integer cancellationCount) {
		this.cancellationCount = cancellationCount;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getInsuranceCompany() {
		return insuranceCompany;
	}

	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}

	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getInsuranceValidUntil() {
		return insuranceValidUntil;
	}

	public void setInsuranceValidUntil(String insuranceValidUntil) {
		this.insuranceValidUntil = insuranceValidUntil;
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

	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

}