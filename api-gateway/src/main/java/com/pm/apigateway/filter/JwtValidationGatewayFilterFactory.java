package com.pm.apigateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
/// JwtValidation - tells spring the name of our filter, will match the filter we add to the application.yaml routes
/// GatewayFilterFactory - tells Spring this is a filter class
@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final WebClient webClient;

    public JwtValidationGatewayFilterFactory (WebClient.Builder webClinetBuilder
            ,@Value("${auth.service.url}") String authServiceUrl) {

        this.webClient = webClinetBuilder.baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return ((exchange, chain) ->{
           String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if(token  == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return webClient.get()
                    .uri("/validate")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve()
                    .toBodilessEntity()
                    .then(chain.filter(exchange));

        });
    }
}
