package id.my.hendisantika.redispubsub.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
public class OrderEventListener implements MessageListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("pubsubRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
}
