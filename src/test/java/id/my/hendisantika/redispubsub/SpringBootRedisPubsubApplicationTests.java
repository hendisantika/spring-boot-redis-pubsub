package id.my.hendisantika.redispubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import id.my.hendisantika.redispubsub.model.OrderEvent;
import id.my.hendisantika.redispubsub.subscriber.OrderEventListener;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
class SpringBootRedisPubsubApplicationTests {

    @Container
    static RedisContainer REDIS_CONTAINER =
            new RedisContainer(DockerImageName.parse("redis:7-alpine3.16")).withExposedPorts(6379);

    @MockBean
    OrderEventListener orderEventListener;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    private static void registerRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379).toString());
    }

    @Test
    public void testOnMessage() throws Exception {
        OrderEvent orderEvent = OrderEvent.builder()
                .orderId("1")
                .userId("12")
                .productName("Mobile")
                .quantity(1)
                .price(42000)
                .build();

        redisTemplate.convertAndSend("order-events", orderEvent);

        Thread.sleep(1000);

        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);
        Mockito.verify(orderEventListener).onMessage(argumentCaptor.capture(), ArgumentMatchers.any());

        OrderEvent receivedEvent = objectMapper.readValue(argumentCaptor.getValue().getBody(),
                OrderEvent.class);

        assertEquals(receivedEvent.getOrderId(), orderEvent.getOrderId());
        assertEquals(receivedEvent.getQuantity(), orderEvent.getQuantity());
        assertEquals(receivedEvent.getPrice(), orderEvent.getPrice());
    }

}
