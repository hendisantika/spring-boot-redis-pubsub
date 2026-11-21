package id.my.hendisantika.redispubsub;

import id.my.hendisantika.redispubsub.model.OrderEvent;
import id.my.hendisantika.redispubsub.subscriber.OrderEventListener;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
class SpringBootRedisPubsubApplicationTests {

    @Container
    @ServiceConnection(name = "redis")
    static GenericContainer<?> REDIS_CONTAINER =
            new GenericContainer<>(DockerImageName.parse("redis:8-alpine")).withExposedPorts(6379);

    @MockitoBean
    OrderEventListener orderEventListener;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testOnMessage() {
        OrderEvent orderEvent = OrderEvent.builder()
                .orderId("1")
                .userId("12")
                .productName("Mobile")
                .quantity(1)
                .price(42000)
                .build();

        redisTemplate.convertAndSend("order-events", orderEvent);

        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);
        verify(orderEventListener, timeout(5000)).onMessage(argumentCaptor.capture(), ArgumentMatchers.any());

        OrderEvent receivedEvent = objectMapper.readValue(argumentCaptor.getValue().getBody(), OrderEvent.class);

        assertEquals(orderEvent.getOrderId(), receivedEvent.getOrderId());
        assertEquals(orderEvent.getQuantity(), receivedEvent.getQuantity());
        assertEquals(orderEvent.getPrice(), receivedEvent.getPrice());
    }

}
