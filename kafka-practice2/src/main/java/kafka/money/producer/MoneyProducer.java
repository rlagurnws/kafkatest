package kafka.money.producer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MoneyProducer {
	private final KafkaTemplate<String, String> userKafkaTemplate;

	@Autowired
	public MoneyProducer(KafkaTemplate kafkaTemplate) {
		userKafkaTemplate = kafkaTemplate;
	}

	@Value(value = "signUpResultByMoney")
	private String topicName;

	public void signUpPublish(String string) {
		ListenableFuture<SendResult<String, String>> future;
		future = userKafkaTemplate.send(topicName, string);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				System.out.println("발행 성공?");
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("발행 실패?");
			}
		});
	}
}
