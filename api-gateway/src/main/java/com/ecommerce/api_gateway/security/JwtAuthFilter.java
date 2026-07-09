package com.ecommerce.api_gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    @Autowired
    public JwtUtil jwtUtil;
    public List<String> publicUrls=List.of("/api/users/register","/api/users/login");
    public static final Map<String,Map<String,List<String>>> ROLE_API_PERMISSIONS=
            Map.of("ADMIN",Map.of("GET",List.of("/api/products","/api/categories"),
                    "POST",List.of("/api/products","/api/categories"),
                    "PUT",List.of("/api/products","/api/categories"),
                    "DELETE",List.of("/api/products","/api/categories")),
                    "USER",Map.of("GET",List.of("/api/products","/api/categories")));

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("hii from jwtauthfilter");
        String path=exchange.getRequest().getURI().getPath();
        System.out.println("path " + path);
        String method=exchange.getRequest().getMethod().name();
        if(path.endsWith("/api/users/register")
                || path.endsWith("/api/users/login") || path.endsWith("/payment/api/payments/webhook/razorpay")) {
            return chain.filter(exchange);
        }

        String authHeader=exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return unauthorized(exchange);
        }

        String token=authHeader.substring(7);
        String userId=jwtUtil.getUserIdFromToken(token);
        String userRole=jwtUtil.getRole(token);
        System.out.println("user role"+userRole);
        if(!jwtUtil.validateToken(token)){
            return unauthorized(exchange);
        }

//        if (!isAuthorized(userRole,method,path)){
//            return forbidden(exchange);
//        }


        ServerHttpRequest modifiedRequest=exchange.getRequest().mutate().header("X-User-Id",userId).header("X-User-Role",userRole).build();


        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange){
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> forbidden(ServerWebExchange exchange){
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isAuthorized(String role,String method,String path){
        if(!ROLE_API_PERMISSIONS.containsKey(role))
            return false;
        if(!ROLE_API_PERMISSIONS.get(role).containsKey(method))
            return false;

        return ROLE_API_PERMISSIONS.get(role).get(method).stream().anyMatch(x->x.endsWith(path));
    }
}
