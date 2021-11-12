package br.com.vgm.cambio.controller

import br.com.vgm.cambio.model.Cambio
import br.com.vgm.cambio.repository.CambioRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("cambio-service")
class CambioController(
    private val cambioRepository: CambioRepository
) {

    @GetMapping("/{amount}/{from}/{to}")
    fun getCambio(
        @PathVariable("amount") amount: BigDecimal,
        @PathVariable("from") from: String,
        @PathVariable("to") to: String
    ): Cambio {
        return cambioRepository.findByFromAndTo(from, to).apply {
            convertedValue = conversionFactor?.multiply(amount)
        }
    }

}