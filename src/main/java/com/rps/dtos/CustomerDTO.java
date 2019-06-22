package com.rps.dtos;
import org.springframework.hateoas.ResourceSupport;

public class CustomerDTO extends ResourceSupport {

	private Long customerId;
	private String name;
	private String address;
	private Float balance;
	
	
	
	
	
	public CustomerDTO(Long id, String name, String address, Float balance) {
		super();
		this.customerId = id;
		this.name = name;
		this.address = address;
		this.balance = balance;
	}
	

	
	public Long getCustomerId() {
		return customerId;
	}



	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Float getBalance() {
		return balance;
	}
	public void setBalance(Float balance) {
		this.balance = balance;
	}
	
	
}
