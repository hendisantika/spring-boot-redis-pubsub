package id.my.hendisantika.redispubsub.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-redis-pubsub
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/19/24
 * Time: 11:14
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@AllArgsConstructor
public class RedisSubscriberConfiguration {

    private final MessageListener messageListener;
    private final RedisConnectionFactory connectionFactory;
}
