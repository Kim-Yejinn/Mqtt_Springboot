//package com.example.demo.mqttSubscriber;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.messaging.Message;
//public class MessageHandler(Message<byte[]> message){
//
//    // 메시지 처리를 위한 핸들러
//
//    String topic = message.getHeaders().get("mqtt_receivedTopic", String.class);
//    byte[] payload = message.getPayload();
//    String payloadString = new String(payload);
//
//    // 받은 메시지에 대한 로직 처리 (예: 로깅, 디바이스 제어 등)
//        System.out.println("Received message from topic: " + topic);
//        System.out.println("Message payload: " + payloadString);
//}
//}
