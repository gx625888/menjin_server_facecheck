package com.threey.guard.manage.domain;

import java.io.Serializable;

public class BandCard implements Serializable {

    private static final long serialVersionUID = 9024076787082379687L;

    private String personId;
    private String personName;
    private String personPhone;
    private String house;
    private String cardId;
    private String bandStatus;
    
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPersonPhone() {
		return personPhone;
	}
	public void setPersonPhone(String personPhone) {
		this.personPhone = personPhone;
	}
	public String getHouse() {
		return house;
	}
	public void setHouse(String house) {
		this.house = house;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getBandStatus() {
		return bandStatus;
	}
	public void setBandStatus(String bandStatus) {
		this.bandStatus = bandStatus;
	}
}
