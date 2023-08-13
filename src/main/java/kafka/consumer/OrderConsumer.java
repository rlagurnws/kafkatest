package kafka.consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import kafka.model.OrderEntity;
import kafka.model.UserEntity;
import kafka.repository.OrderRepository;
import kafka.repository.UserRepository;

@Component
public class OrderConsumer {
	
	@Autowired
	private UserRepository uRepository;
	@Autowired
	private OrderRepository oRepository;
	
	@KafkaListener(topics = "${kafka.topic.request-topic}")
	@SendTo
	public OrderEntity listen(OrderEntity request) throws InterruptedException {
		
		UserEntity user = uRepository.findById(request.getUserId()).get();
		if(user.getMoney() < request.getPaid()) {
			request.setResult("Failure");
		}else {
			user.setMoney(user.getMoney()-request.getPaid());
			request.setResult("Success");
		}
		uRepository.save(user);
		oRepository.save(request);
		return request;
	}
}