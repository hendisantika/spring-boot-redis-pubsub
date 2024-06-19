package id.my.hendisantika.redispubsub.controller;

import id.my.hendisantika.redispubsub.model.OrderEvent;
import id.my.hendisantika.redispubsub.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/publish")
    @Operation(
            summary = "Add New Order Data",
            description = "Add New Order Data.",
            tags = {"Order"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            String.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public String publish(@RequestBody OrderEvent orderEvent) {
        orderService.publish(orderEvent);
        return "Success";
    }
}
