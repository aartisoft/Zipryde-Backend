package com.trivecta.zipryde.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the commission database table.
 * 
 */
@Entity
@Table(name="COMMISSION")
@NamedQueries({
	@NamedQuery(name="Commission.findAll", query="SELECT c FROM Commission c"),
	@NamedQuery(name="Commission.getLatest",query="SELECT c FROM Commission c where c.user.id = :driverId and c.calculatedDate IS NULL"),
	@NamedQuery(name="Commission.getCommissionAmountByStatus",query="SELECT SUM(c.commisionAmount) FROM Commission c where c.status = :status"),
	@NamedQuery(name="Commission.getByDriverIdAndStatus",query="SELECT c FROM Commission c where c.user.id = :driverId and c.status = :status ORDER BY c.id DESC")
})
public class Commission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date calculatedDate;

	private BigDecimal commisionAmount;

	private BigDecimal noOfMiles;

	private Integer noOfTrips;

	@Temporal(TemporalType.TIMESTAMP)
	private Date paidDate;

	private String status;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="driverId")
	private User user;

	public Commission() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCalculatedDate() {
		return this.calculatedDate;
	}

	public void setCalculatedDate(Date calculatedDate) {
		this.calculatedDate = calculatedDate;
	}

	public BigDecimal getCommisionAmount() {
		return this.commisionAmount;
	}

	public void setCommisionAmount(BigDecimal commisionAmount) {
		this.commisionAmount = commisionAmount;
	}

	public BigDecimal getNoOfMiles() {
		return this.noOfMiles;
	}

	public void setNoOfMiles(BigDecimal noOfMiles) {
		this.noOfMiles = noOfMiles;
	}

	public Integer getNoOfTrips() {
		return this.noOfTrips;
	}

	public void setNoOfTrips(Integer noOfTrips) {
		this.noOfTrips = noOfTrips;
	}

	public Date getPaidDate() {
		return this.paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}