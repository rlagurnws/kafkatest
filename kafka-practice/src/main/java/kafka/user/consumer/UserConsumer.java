package kafka.user.consumer;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kafka.user.repository.UserRepository;

@Service
public class UserConsumer {

	@Autowired
	private UserRepository repository;
	
	@KafkaListener(topics = "signUpResultByMoney")
    public boolean signUpListener(String str) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map;
		try {
			map = mapper.readValue(str, Map.class);
			System.out.println(map);
			if(map.get("result").toString().equals("success")) {
				return true;
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return false;
    }
}