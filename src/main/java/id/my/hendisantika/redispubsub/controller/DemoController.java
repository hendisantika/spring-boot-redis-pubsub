package id.my.hendisantika.redispubsub.controller;

import id.my.hendisantika.redispubsub.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-redis-pubsub
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/19/24
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Order", description = "Endpoint for managing Tutorial")
public class DemoController {

    private final OrderService orderService;
}
