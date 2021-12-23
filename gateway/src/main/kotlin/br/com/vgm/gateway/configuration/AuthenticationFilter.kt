package br.com.vgm.gateway.configuration

import br.com.vgm.gateway.jwt.JwtTokenProvider
import io.jsonwebtoken.Claims
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthenticationFilter(val jwtTokenProvider: JwtTokenProvider, val routerValidator: RouterValidator) :
    GlobalFilter, Ordered {

    private val log = LoggerFactory.getLogger(AuthenticationFilter::class.java)

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request: ServerHttpRequest = exchange.request

        log.info("Request from {}", request.uri.host)

        if (routerValidator.isSecured.test(request)) {
            if (isAuthMissing(request))
                return onError(
                    exchange,
                    "Authorization header is missing in request",
                    HttpStatus.UNAUTHORIZED
                )
            val token = getAuthHeader(request)
            if (!jwtTokenProvider.isValidToken(token) && isValidHost(token, request.uri.host)) return onError(
                exchange,
                "Token in Authorization header is invalid",
                HttpStatus.UNAUTHORIZED
            )
            populateRequestWithHeaders(exchange, token)
        }
        return chain.filter(exchange)
    }

    fun onError(exchange: ServerWebExchange, err: String, httpStatus: HttpStatus): Mono<Void> {
        val response: ServerHttpResponse = exchange.response
        response.statusCode = httpStatus
        return response.setComplete()
    }

    fun getAuthHeader(request: ServerHttpRequest): String {
        return request.headers.getOrEmpty("Authorization").first().orEmpty()
    }

    fun isAuthMissing(request: ServerHttpRequest): Boolean {
        return !request.headers.containsKey("Authorization")
    }

    fun isValidHost(token: String, domainFromRequest: String): Boolean{
        return true
        val claims: Claims = jwtTokenProvider.getAllClaimsFromToken(token)
        val domains = claims["domains"]
        if(domains is List<*>){
            return domains.contains(domainFromRequest)
        }
        return false
    }

    fun populateRequestWithHeaders(exchange: ServerWebExchange, token: String) {
        val claims: Claims = jwtTokenProvider.getAllClaimsFromToken(token)
        log.info("User {}, request to {}", claims.subject, exchange.request.uri)
        exchange.request.mutate()
            .header("role", claims["role"].toString())
            .build()
    }

    override fun getOrder(): Int {
        return -1
    }
}