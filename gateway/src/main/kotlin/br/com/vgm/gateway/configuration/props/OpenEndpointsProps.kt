package br.com.vgm.gateway.configuration.props

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("config")
@RefreshScope
class OpenEndpointsProps {

    lateinit var openEndpoints: List<String>

}