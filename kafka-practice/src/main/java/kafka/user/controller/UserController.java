package kafka.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kafka.user.consumer.UserConsumer;
import kafka.user.domain.UserEntity;
import kafka.user.producer.UserProducer;
import kafka.user.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository repository;
	@Autowired
	UserProducer up;
	@Autowired
	UserConsumer uc;
	@PostMapping("/new")
	public String signUp(@RequestBody Map<String, Object> map) {
//		System.out.println(map);
		ObjectMapper mapper = new ObjectMapper();
		repository.save(new UserEntity(map.get("id").toString(),map.get("password").toString()));
		try {
			up.loginPublish(mapper.writeValueAsString(map));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "성공!";
	}
}
