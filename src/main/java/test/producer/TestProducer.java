package test.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class TestProducer {
	
	private final KafkaTemplate<String, String> orderKafkaTemplate;
    
    @Autowired
    public TestProducer(KafkaTemplate kafkaTemplate) {
    	orderKafkaTemplate = kafkaTemplate;
    }

    @Value(value = "test")
    private String orderTopicName;

    public void publishOrder(String string) {
        System.out.println("======>>>" + string);


        ListenableFuture<SendResult<String, String>> future = orderKafkaTemplate.send(orderTopicName, string);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
            	System.out.println("TestProducer Success");
            }

            @Override
            public void onFailure(Throwable t) {
            	System.out.println("TestProducer Fail");
            }
        });
    }
}
