package br.com.vgm.book.proxy

import br.com.vgm.book.response.Cambio
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.math.BigDecimal

@FeignClient(name = "cambio-service", url = "localhost:8000")
interface CambioProxy {

    @GetMapping("/cambio-service/{amount}/{from}/{to}")
    fun getCambio(
        @PathVariable("amount") amount: BigDecimal,
        @PathVariable("from") from: String,
        @PathVariable("to") to: String
    ): Cambio

}