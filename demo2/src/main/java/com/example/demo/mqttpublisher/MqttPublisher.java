package com.example.demo.mqttpublisher;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttPublisher {
    
    // 브로커에게 메시지 전송하기 위함
    // 1. 브로커 접속
    // 2. 브로커로 메시지 전송
    // pub과 sub의 역할을 하기 위한 기능을 가질 것임.
    private MqttClient client;

    private String broker = "tcp://192.168.25.61:1883";
    private String clientId = "test";


    public MqttPublisher() {
        try {
            // 클라이언트 생성
            client = new MqttClient(broker, clientId);
            // 브로커 접속
            client.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public boolean send(String topic, String msg)  {
        // 전송할 mqtt 메시지 만든다

        try {
        MqttMessage message = new MqttMessage();
        message.setPayload(msg.getBytes()); // 실제 전송할 메시지
            client.publish(topic, message);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
//        message.setQos(qos);

        return true;
    }
    
    // 접속을 끊기
    public void close(){
        try {
            if(client !=null ){
                client.disconnect();
                client.close();
            }
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        MqttPublisher sender = new MqttPublisher();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=1;
                String msg="";
                while(true){
                    if(i==5){
                        break;
                    }else{
                        if(i%2==1){
                            msg="led_on";
                        }else{
                            msg="led_off";
                        }
                    }
                    i++;
                    sender.send("glory",msg);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                sender.close();
            }
        }).start();
    }


}
