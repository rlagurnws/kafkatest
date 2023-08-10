package kafka.consumer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import kafka.money.Entity.MoneyEntity;
import kafka.money.producer.MoneyProducer;
import kafka.repository.MoneyRepository;

@Service
public class SignUpConsumer {
	
	@Autowired
	private MoneyRepository repository;
	
	@Autowired
	private MoneyProducer mp;
	
	@KafkaListener(topics = "signUp")
    public void signUpListener(String str) {
		System.out.println("*******************************************"+str);
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String,Object> map = mapper.readValue(str,Map.class);
			repository.save(new MoneyEntity(map.get("id").toString(), Integer.parseInt(map.get("money").toString())));
			Map<String,String> result = new HashMap<>();
			result.put("result", "success");
			mp.signUpPublish(mapper.writeValueAsString(map));
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("TestConsumer Error");
		}
    }
}
