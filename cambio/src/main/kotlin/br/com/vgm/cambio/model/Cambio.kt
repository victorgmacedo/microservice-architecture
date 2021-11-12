package br.com.vgm.cambio.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
class Cambio(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "from_currency", nullable = false, length = 3)
    val from: String? = null,
    @Column(name = "to_currency", nullable = false, length = 3)
    val to: String? = null,
    @Column(nullable = false)
    val conversionFactor: BigDecimal? = null,

    @Transient
    var convertedValue: BigDecimal? = null,
    @Transient
    val environment: String? = null
) {
}