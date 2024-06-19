package id.my.hendisantika.redispubsub.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.redispubsub.model.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-redis-pubsub
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/19/24
 * Time: 11:15
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderEventListener implements MessageListener {

    private final ObjectMapper objectMapper;

    @Qualifier("pubsubRedisTemplate")
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            log.info("New message received: {}", message);
            OrderEvent orderEvent = objectMapper.readValue(message.getBody(), OrderEvent.class);
            redisTemplate.opsForValue().set(orderEvent.getOrderId(), orderEvent);
        } catch (IOException e) {
            log.error("error while parsing message");
        }
    }
}
