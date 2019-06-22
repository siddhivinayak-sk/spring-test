package com.rps.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rps.dtos.CustomerDTO;

@RestController
@RequestMapping("/hateos")
public class HateosController {

	List<CustomerDTO> list;
	
	@ModelAttribute
	public List<CustomerDTO> setupCustomerDTO() {
		List<CustomerDTO> list = new ArrayList<CustomerDTO>();
		list.add(new CustomerDTO(101L, "A 101", "Address 101", 101.00f));
		list.add(new CustomerDTO(102L, "A 102", "Address 102", 1.00f));
		list.add(new CustomerDTO(103L, "A 103", "Address 103", 11.00f));
		list.add(new CustomerDTO(104L, "A 104", "Address 104", 81.00f));
		list.add(new CustomerDTO(105L, "A 105", "Address 105", 19.00f));
		this.list = list;
		return list;
	}

	@GetMapping("/list")
	public List<CustomerDTO> getCustomerList() {
		List<CustomerDTO> temp =
				list.stream().map((e) -> {
					e.add(
							ControllerLinkBuilder.linkTo(
									ControllerLinkBuilder.methodOn(HateosController.class)
									.getCustomer(e.getCustomerId())
									).withSelfRel()
							);
					return e;
				}).collect(Collectors.toList());
		return temp;
	}

	@GetMapping("/{customerId}")
	public List<CustomerDTO> getCustomer(@PathVariable("customerId")Long customerId) {
		return list
				.stream()
				.filter(
						e -> 
						e.getCustomerId().equals(customerId)
						)
				.map(
						(e) -> 
						{
							e.add(
									ControllerLinkBuilder.linkTo(
											ControllerLinkBuilder.methodOn(HateosController.class)
											.getCustomerList()
											)
									.withRel("list all")
									);
							return e;
							}
						)
				.collect(Collectors.toList());
	}
	
	
}
