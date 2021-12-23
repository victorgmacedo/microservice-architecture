package br.com.vgm.gateway.configuration.props

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("config")
@RefreshScope
class RouteProps {
    lateinit var routes: List<RouterProperties>

    class RouterProperties {
        lateinit var from: String
        lateinit var to: String

        override fun toString(): String {
            return "RouterProperties(from='$from', to='$to')"
        }
    }

    override fun toString(): String {
        return "ApplicationProps(routes=$routes)"
    }
}