package com.ecommerce.api_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomRouteFilter extends AbstractGatewayFilterFactory<Object> {
    public CustomRouteFilter(){
        super(Object.class);
    }
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            System.out.println("Route Filter:Before Routing");
            ServerWebExchange modifiedExchange =
                    exchange.mutate()
                            .request(
                                    exchange.getRequest()
                                            .mutate()
                                            .header("X-Route-Header",
                                                    "Added-By-Gateway")
                                            .build())
                            .build();

            return chain.filter(modifiedExchange).then(Mono.fromRunnable(()->{
                System.out.println("Route Filter:After Routing");
            }));
        };
    }
}
