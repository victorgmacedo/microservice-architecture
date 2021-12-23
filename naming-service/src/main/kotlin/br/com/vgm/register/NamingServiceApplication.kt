package br.com.vgm.register

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class NamingServiceApplication

fun main(args: Array<String>) {
	runApplication<NamingServiceApplication>(*args)
}
