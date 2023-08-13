package kafka.controller;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kafka.model.OrderEntity;
import kafka.model.OrderEntity;

@RestController
public class OrderController {

	@Autowired
	private ReplyingKafkaTemplate<String, OrderEntity, OrderEntity> kafkaTemplate;
	
	@Value("${kafka.topic.request-topic}")
	private String requestTopic;
	
	@Value("${kafka.topic.requestreply-topic}")
	private String requestReplyTopic;
	
	@PostMapping(value="/order",produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public String sum(@RequestBody OrderEntity request) throws InterruptedException, ExecutionException {
		// create producer record
		ProducerRecord<String, OrderEntity> record = new ProducerRecord<String, OrderEntity>(requestTopic, request);
		// set reply topic in header
		record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));
		// post in kafka topic
		RequestReplyFuture<String, OrderEntity, OrderEntity> sendAndReceive = kafkaTemplate.sendAndReceive(record);

		// confirm if producer produced successfully
		SendResult<String, OrderEntity> sendResult = sendAndReceive.getSendFuture().get();
		
		//print all headers
		sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));
		// get consumer record
		ConsumerRecord<String, OrderEntity> consumerRecord = sendAndReceive.get();
		
		// return consumer value
		if(consumerRecord.value().getResult().equals("Failure")) {
			return "실패했습니다.";			
		}else {
			return "성공했습니다.";
		}
	}
}