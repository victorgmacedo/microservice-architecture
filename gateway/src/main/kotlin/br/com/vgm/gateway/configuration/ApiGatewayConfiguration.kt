package br.com.vgm.gateway.configuration

import br.com.vgm.gateway.configuration.props.RouteProps
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ApiGatewayConfiguration(val applicationProps: RouteProps) {

    private val log = LoggerFactory.getLogger(ApiGatewayConfiguration::class.java)

    @Bean
    fun gatewayRouter(routeLocatorBuilder: RouteLocatorBuilder): RouteLocator {
        log.info("props: {}", applicationProps)
        val routes = routeLocatorBuilder.routes()
        applicationProps.routes.forEach { routePropertie ->
            routes.route {
                it.path(routePropertie.from).filters { filter ->
                    filter.addRequestHeader("databasetoken", "default")
                }
                    .uri(routePropertie.to)
            }
        }
        return routes.build()
    }

}