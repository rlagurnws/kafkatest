package test.producer;

import org.springframework.kafka.annotation.KafkaListener;

public class TestConsumer {
	
	@KafkaListener(topics = "test", containerFactory = "orderKafkaListenerContainerFactory")
    public String orderListener(String str) {
		try {
			System.out.println("TestConsumer Start");
			System.out.println(str);
			Thread.sleep(3000);
			System.out.println("TestConsumer End");
			throw new Exception();
//			return "이게 된다고?";
		}catch(Exception e) {
			System.out.println("TestConsumer Error");
			return "이게 안된다고?";
		}
    }
}
