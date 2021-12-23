package br.com.vgm.gateway.configuration

import br.com.vgm.gateway.configuration.props.OpenEndpointsProps
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import java.util.function.Predicate

@Component
class RouterValidator(val openEndpointsProps: OpenEndpointsProps) {

    var isSecured: Predicate<ServerHttpRequest> = Predicate<ServerHttpRequest> { request ->
        openEndpointsProps.openEndpoints
            .stream()
            .noneMatch { uri: String ->
                request.uri.path.contains(uri)
            }
    }

}