package com.bushengshi.miaosha.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQConfig {

    public static final String MIAOSHA_QUEUE = "miaosha.queue";
    public static final String QUEUE = "queue";
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String HEADER_QUEUE = "header.queue";
    public static final String TOPIC_EXCHANGE = "topicExchage";
    public static final String FANOUT_EXCHANGE = "fanoutxchage";
    public static final String HEADERS_EXCHANGE = "headersExchage";

    /**
     * 交换机路由类型如下：
     *
     *     Direct Exchange：直接匹配，通过Exchange名称+RoutingKey来发送与接收消息;
     *
     *     Fanout Exchange：广播订阅，向所有消费者发布消息，但只有消费者将队列绑定到该路由才能收到消息，忽略RoutingKey；
     *
     *     Topic Exchange：主题匹配订阅，这里的主题指的是RoutingKey，RoutingKey可以采用通配符，如：*或#，RoutingKey命名采用.来分隔多个词，只有消费者将队列绑定到该路由且指定的RoutingKey符合匹配规则时才能收到消息；
     *
     *     Headers Exchange：消息头订阅，消息发布前，为消息定义一个或多个键值对的消息头，然后消费者接收消息时同样需要定义类似的键值对请求头，里面需要多包含一个匹配模式（有：x-mactch=all,或者x-mactch=any）,只有请求头与消息头相匹配，才能接收到消息，忽略RoutingKey；
     *
     */

    /**
     * Direct模式 交换机Exchange
     */
//    @Bean
//    public Queue queue() {
//        return new Queue(QUEUE, true);
//    }

    /**
     * Topic模式 可带通配符 交换机Exchange
     */
//    @Bean
//    public Queue topicQueue1() {
//        return new Queue(TOPIC_QUEUE1, true);
//    }
//
//    @Bean
//    public Queue topicQueue2() {
//        return new Queue(TOPIC_QUEUE2, true);
//    }
//
//    public TopicExchange topicExchange() {
//        return new TopicExchange(TOPIC_EXCHANGE);
//    }
//
//    @Bean
//    public Binding topicBinding1() {
//        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
//    }
//
//    @Bean
//    public Binding topicBinding2() {
//        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
//    }


//
//    /**
//     * Fanout模式 广播 交换机Exchange
//     */
//    @Bean
//    public FanoutExchange fanoutExchage() {
//        return new FanoutExchange(FANOUT_EXCHANGE);
//    }
//
//    @Bean
//    public Binding FanoutBinding1() {
//        return BindingBuilder.bind(topicQueue1()).to(fanoutExchage());
//    }
//
//    @Bean
//    public Binding FanoutBinding2() {
//        return BindingBuilder.bind(topicQueue2()).to(fanoutExchage());
//    }

    /**
     * Header模式 交换机Exchange
     */
//    @Bean
//    public HeadersExchange headersExchage() {
//        return new HeadersExchange(HEADERS_EXCHANGE);
//    }
//
//    @Bean
//    public Queue headerQueue1() {
//        return new Queue(HEADER_QUEUE, true);
//    }
//
//    @Bean
//    public Binding headerBinding() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("header1", "value1");
//        map.put("header2", "value2");
//        return BindingBuilder.bind(headerQueue1()).to(headersExchage()).whereAll(map).match();
//    }

    @Bean
    public Queue miaoShaQueue(){
        return new Queue(MIAOSHA_QUEUE,true);
    }
}
