package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import test.producer.TestConsumer;
import test.producer.TestProducer;

@RestController
public class TestController {
	
	@Autowired
	private TestProducer tp;

	@RequestMapping("/test")
	public String simpleTest(String str) {
		System.out.println("simpleTest Start");

		tp.publishOrder(str);
		
		TestConsumer tc = new TestConsumer();
		String s = tc.orderListener(str);
		
		System.out.println("simpleTest Done");
		return s;
	}
}
