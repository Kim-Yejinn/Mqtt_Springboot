//package com.example.demo.config;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.annotation.Transformer;
//import org.springframework.integration.channel.PublishSubscribeChannel;
//import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
//import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
//import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
//import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.MessageHandler;
//public class MqttConfig {
//// 브로커 연결 및 토픽 설정
//
//    @Value("${mqtt.broker}")
//    private String broker;
//
//    @Value("${mqtt.clientId}")
//    private String clientId;
//
//    @Value("${mqtt.topic}")
//    private String topic;
//
//    @Value("${mqtt.qos}")
//    private int qos;
//
//    @Value("${mqtt.username}")
//    private String username;
//
//    @Value("${mqtt.password}")
//    private String password;
//
//    @Value("${mqtt.completionTimeout}")
//    private int completionTimeout;
//
//    @Bean
//    public MqttConnectOptions mqttConnectOptions() {
//        MqttConnectOptions options = new MqttConnectOptions();
//        options.setCleanSession(true);
//        options.setUserName(username);
//        options.setPassword(password.toCharArray());
//        return options;
//    }
//
//    @Bean
//    public MqttPahoClientFactory mqttClientFactory() {
//        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//        factory.setPersistence(new MemoryPersistence());
//        factory.setConnectionOptions(mqttConnectOptions());
//        return factory;
//    }
//
//    @Bean
//    public MessageChannel mqttInputChannel() {
//        return new PublishSubscribeChannel();
//    }
//
//    @Bean
//    public MessageChannel mqttOutputChannel() {
//        return new PublishSubscribeChannel();
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "mqttInputChannel")
//    public MessageHandler mqttInputHandler() {
//        return new MessageHandler() {
//            @Override
//            public void handleMessage(org.springframework.messaging.Message<?> message) {
//                // MQTT 메시지를 수신하여 처리하는 핸들러 로직 작성 (예: 메시지를 로깅하거나 처리 서비스로 전달)
//            }
//        };
//    }
//
//    @Bean
//    public MqttPahoMessageDrivenChannelAdapter mqttInbound() {
//        String clientIdWithSuffix = clientId + "_" + System.currentTimeMillis();
//        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
//                clientIdWithSuffix,
//                mqttClientFactory(),
//                topic
//        );
//        adapter.setCompletionTimeout(completionTimeout);
//        adapter.setConverter(new DefaultPahoMessageConverter());
//        adapter.setOutputChannel(mqttInputChannel());
//        return adapter;
//    }
//
//    @Bean
//    @Transformer(inputChannel = "mqttOutputChannel", outputChannel = "mqttOutboundChannel")
//    public org.springframework.integration.transformer.Transformer mqttMessageTransformer() {
//        return new org.springframework.integration.transformer.Transformer() {
//            @Override
//            public org.springframework.messaging.Message<?> transform(org.springframework.messaging.Message<?> message) {
//                MqttMessage mqttMessage = new MqttMessage(message.getPayload().toString().getBytes());
//                mqttMessage.setQos(qos);
//                return mqttMessage;
//            }
//        };
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "mqttOutboundChannel")
//    public MessageHandler mqttOutbound() {
//        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
//                clientId,
//                mqttClientFactory()
//        );
//        messageHandler.setAsync(true);
//        messageHandler.setDefaultTopic(topic);
//        return messageHandler;
//    }
//}
