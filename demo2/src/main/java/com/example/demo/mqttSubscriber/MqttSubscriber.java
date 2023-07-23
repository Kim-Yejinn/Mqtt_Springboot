package com.example.demo.mqttSubscriber;

import org.eclipse.paho.client.mqttv3.*;

public class MqttSubscriber implements MqttCallback {
    // mqtt 클라이언트 (메시지 전달 받기 위해 구독 신청 후 대기)
    // 메시지 오는 시점이 되었을때 자동 실행
    // MqttCallback 인터페이스 상속
    // MqttCallback 인터 페이스의 abstract 메소드 오버라이딩
    
    // 클라이언트 만들기
    
    // 브로커와 통신하는 역할 - subscriber, publisher의 역할
    private MqttClient mqttclient;
    // MQTT 프로토콜을 이용해서 broker에 연결하면서 연결 정보를 설정할 수 있는 객체
    private MqttConnectOptions mqttOption;
    // 클라이언트 아이디는 broker가 클라이언트를 식별하기 위한 용도, 고유해야 함/
    public MqttSubscriber init(String server, String clientId){
        try {
            mqttOption=new MqttConnectOptions();
            mqttOption.setCleanSession(true);
            mqttOption.setKeepAliveInterval(30);
            // broker에 subscribe 하기 위한 클라이언트 객체 생성
            mqttclient = new MqttClient(server, clientId);
            // 클라이언트 객체에 mqtt callback등록 - 구독 신청 후 적절한 시점에 처리하고 싶은 기능 구현
            // 메소드가 자동으로 그 시점에 호출되도록 할 수 있음.
            mqttclient.setCallback(this);
            mqttclient.connect(mqttOption);
//            mqttclient.connect();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        return this;


    }


    // 커넥션이 종료되면 호출 - 통신 오류로 연결이 끊어지는 경우 호출
    @Override
    public void connectionLost(Throwable cause) {
        
    }
    // 메시지의 배달이 완료되면 호출
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("====메시지 도착===");
        System.out.println(message);
        System.out.println("topic:"+topic+",id"+message.getId()+",payload:"+new String(message.getPayload()));

    }
    // 메시지가 도착하면 호출하는 메소드
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    // 구독 신청
    public boolean subscribe(String topic){
        boolean result =true;
        if(topic!=null) {
            try {
                // topic과 Qos를 전달
                //Qos는 메시지가 도착하기 위한 품질의 값 설정
                // 0(체크 ㄴㄴ), 1, 2(메시지 전송 여부 모두 체크) 설정 가능
                mqttclient.subscribe(topic, 0);
            } catch (MqttException e) {
                e.printStackTrace();
                result=false;
            }
        }
        return result;
    }
    //테스트
    public static void main(String[] args) {
        MqttSubscriber subobj= new MqttSubscriber();
        subobj.init("tcp://192.168.25.61:1883", "test2").subscribe("glory");
    }
}
