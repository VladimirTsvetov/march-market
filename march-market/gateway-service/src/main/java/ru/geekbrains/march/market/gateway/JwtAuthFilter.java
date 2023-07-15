package ru.geekbrains.march.market.gateway;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.ObjectInputFilter;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {
    @Autowired
    private JwtUtils jwtUtils;

    public JwtAuthFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config){
        return (exchange, chain) -> {
            org.springframework.http.server.reactive.ServerHttpRequest request = exchange.getRequest();
            if(!isAuthMissing((ServerHttpRequest) request)){
                final String token = getAuthHeader((ServerHttpRequest) request);
                if(jwtUtils.isInvalid(token)){
                    try {
                        return this.onError(exchange,"Auth header is invalid", HttpStatus.UNAUTHORIZED);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                populateRequestWithHeaders(exchange,token);
            }
            return  chain.filter(exchange);
        };

    }
    public static class Config{

    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) throws IOException {
        ServerHttpResponse response = (ServerHttpResponse) exchange.getResponse();
        response.setStatusCode(httpStatus);
        exchange.mutate().response((org.springframework.http.server.reactive.ServerHttpResponse) response).build();

        return exchange.getResponse().setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request){
        return request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
    }

    private boolean isAuthMissing(ServerHttpRequest request){
        if(!request.getHeaders().containsKey("Authorization")){
            return true;
        }
        if(!request.getHeaders().getOrEmpty("Authorization").get(0).startsWith("Bearer")){
            return true;
        }
        return false;
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange,String token){
        Claims claims = jwtUtils.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("username", claims.getSubject())
                .build();
    }
}
