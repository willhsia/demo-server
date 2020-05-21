package com.example.demo.rabbitmq.server;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class RabbitProducer {
    private static final String EXCHANGE_NAME_DIRECT = "exchange_direct";
    private static final String ROUTING_KEY_DIRECT = "routingkey_direct";
    private static final String QUEUE_NAME_DIRECT = "queue_direct";
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 5672;
    private static ConnectionFactory factory = null;
    private static  Connection connection = null;
    static {
        factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("root");
        factory.setPassword("root");

        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException {
//        fanout();
//        topic();
//        mandatory();

//        alternate_ex();
        ttl_dlx();
        connection.close();

    }

    public static void ttl_dlx() throws IOException, TimeoutException{

        Channel channel = connection.createChannel();
        channel.exchangeDeclare("ex_dlx", "direct", true);
        channel.exchangeDeclare("ex_ttl", "topic", true);

        HashMap<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 10000);
        args.put("x-dead-letter-exchange", "ex_dlx");
        args.put("x-dead-letter-routing-key", "key.dlx");

        channel.queueDeclare("q_ttl", true, false, false, args);
        channel.queueBind("q_ttl", "ex_ttl", "key.*");

        channel.queueDeclare("q_dlx", true, false, false, null);
        channel.queueBind("q_dlx", "ex_dlx", "key.dlx");
        String message = "hello ttl";
        channel.basicPublish("ex_ttl", "key.ttl", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        channel.close();
    }

    public static void alternate_ex() throws IOException, TimeoutException {
        Channel channel = connection.createChannel();
        HashMap<String, Object> args = new HashMap<>();
        args.put("alternate-exchange", "ex_alternate");
        channel.exchangeDeclare("ex_normal", "direct", true, false, args);
        channel.exchangeDeclare("ex_alternate", "fanout", true, false, null);
        channel.queueDeclare("q_normal", true, false, false, null);
        channel.queueBind("q_normal", "ex_normal", "key_normal");
        channel.queueDeclare("q_alternate", true, false, false, null);
        channel.queueBind("q_alternate", "ex_alternate", "");

        String message = "hello alternate";
        channel.basicPublish("ex_normal", "key", true, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

        channel.close();
    }

    public static void mandatory() throws IOException, TimeoutException {
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("ex_mandatory", "direct", true);
        channel.queueDeclare("q_mandatory", true, false, false, null);
        channel.queueBind("q_mandatory", "ex_mandatory", "key_mandatory1");
        String message = "hello mandatory";
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int i, String s, String s1, String s2, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                String message = new String(bytes);
                System.out.println("Basic.Return 返回结果：" + message);
            }
        });
        channel.basicPublish("ex_mandatory", "", true, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
//        channel.close();
    }

    public static void topic() throws IOException, TimeoutException {
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("ex_topic", "topic", true, false, null);
        channel.queueDeclare("q_topic", true, false, false, null);
        channel.queueBind("q_topic", "ex_topic", "key.*");
        String message = "hello topic";
        channel.basicPublish("ex_topic", "key.topic", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        channel.close();
    }


    public static void fanout() throws IOException, TimeoutException {
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("ex_fanout", "fanout", true, false, null);
        channel.queueDeclare("q_fanout", true, false, false, null);
        channel.queueBind("q_fanout", "ex_fanout", "key_fanout2");
        String message = "hello fanout";
        channel.basicPublish("ex_fanout", "key_fanout", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        channel.close();
    }

    public static void direct() throws IOException, TimeoutException{

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME_DIRECT, "direct", true, false, null);
        channel.queueDeclare(QUEUE_NAME_DIRECT, true, false, false, null);
        channel.queueBind(QUEUE_NAME_DIRECT, EXCHANGE_NAME_DIRECT, ROUTING_KEY_DIRECT);
        String message = "Hello World";
        channel.basicPublish(EXCHANGE_NAME_DIRECT, ROUTING_KEY_DIRECT, MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        channel.close();
//        connection.close();
    }
}
