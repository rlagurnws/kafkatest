package kafka.user.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class UserProducer {

	private final KafkaTemplate<String, String> userKafkaTemplate;

	@Autowired
	public UserProducer(KafkaTemplate kafkaTemplate) {
		userKafkaTemplate = kafkaTemplate;
	}

	@Value(value = "signUp")
	private String topicName;
	
	public void loginPublish(String string) {
		System.out.println(string + "loginPublish"+" "+topicName);
		ListenableFuture<SendResult<String, String>> future = userKafkaTemplate.send(topicName, string);
		
		RequestReplyFuture<String,String,String> rrf = new RequestReplyFuture<>();
		
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
