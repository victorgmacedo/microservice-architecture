package br.com.vgm.book.response

import java.math.BigDecimal
import javax.persistence.*

class Cambio(
    val id: Long,
    val from: String,
    val to: String,
    val conversionFactor: BigDecimal,
    var convertedValue: BigDecimal,
) {
}