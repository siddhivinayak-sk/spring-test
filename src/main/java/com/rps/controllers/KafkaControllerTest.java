package com.rps.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import org.springframework.kafka.core.KafkaTemplate;



@RestController
@RequestMapping("kafka")
public class KafkaControllerTest {

	//@Autowired
	//private KafkaTemplate<String, String> kafkaTemplate;
	
	@GetMapping("/topic1")
	public String pushMessageToTopic1(@RequestParam("message")String message) {
		//kafkaTemplate.send("kafkatopic1", message);
		return "Success!";
	}
}
