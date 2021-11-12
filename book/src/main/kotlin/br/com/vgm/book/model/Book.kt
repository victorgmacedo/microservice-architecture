package br.com.vgm.book.model

import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val author: String,
    @Temporal(TemporalType.DATE)
    val launchDate: Date,
    var price: BigDecimal,
    val title: String,
    val currency: String,

) {
}