package com.example.demo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/test")
@RequiredArgsConstructor
public class TestController {
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	private static String TOPIC_NAME = "test";
	
	@PostMapping
	public String sendMessage() {
		String messageData = "이거 돌아가는거 맞는건가";
		kafkaTemplate.send(TOPIC_NAME, messageData);
		return "성공이라고요";
	}
}
