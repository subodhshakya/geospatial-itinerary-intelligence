/**
 * 
 */
package com.uah.geospatialitineraryintelligence.datamodel;

/**
 * @author Archana
 *
 */
public class CUser {

	private long userId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String password;
	private String street;
	private String city;
	private int zipCode;
	private String state;
	private String country;
	private int noOfTraveler;

	public CUser(String firstName, String middleName, String lastName,
			String email, String password, String street, String city,
			String state, String country, int zipCode, int noOfTraveler) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		this.state = state;
		this.country = country;
		this.noOfTraveler = noOfTraveler;
	}

	public CUser(long userId, String firstName, String middleName,
			String lastName, String email, String password, String street,
			String city, String state, String country, int zipCode,
			int noOfTraveler) {
		this.userId = userId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		this.state = state;
		this.country = country;
		this.noOfTraveler = noOfTraveler;
	}

//	public long getUserId() {
//		return userId;
//	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getNoOfTraveler() {
		return noOfTraveler;
	}

	public void setNoOfTraveler(int noOfTraveler) {
		this.noOfTraveler = noOfTraveler;
	}

}

