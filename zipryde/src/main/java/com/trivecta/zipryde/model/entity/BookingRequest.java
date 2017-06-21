package com.trivecta.zipryde.model.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the booking_request database table.
 * 
 */
@Entity
@Table(name="BOOKING_REQUEST")
@NamedQuery(name="BookingRequest.findAll", query="SELECT b FROM BookingRequest b")
public class BookingRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to Booking
	@ManyToOne
	@JoinColumn(name="bookingId")
	private Booking booking;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="driverId")
	private User user;

	public BookingRequest() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Booking getBooking() {
		return this.booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}