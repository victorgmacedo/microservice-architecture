package br.com.vgm.book.controller

import br.com.vgm.book.model.Book
import br.com.vgm.book.proxy.CambioProxy
import br.com.vgm.book.repository.BookRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("book-service")
class BookController(
    private val bookRepository: BookRepository,
    private val cambioProxy: CambioProxy
) {

    @GetMapping("/{id}/{currency}")
    fun findBook(
        @PathVariable("id") id: Long,
        @PathVariable("currency") currency: String
    ): Book? {
        var book = bookRepository.getById(id)

        val cambio = cambioProxy.getCambio(book.price, "USD", currency)

//        val param = mapOf("amount" to book.price, "from" to "USD", "to" to currency)
//        val cambio = RestTemplate().getForEntity(
//            "http://localhost:8000/cambio-service/{amount}/{from}/{to}",
//            Cambio::class.java,
//            param
//        ).body

        book.price = cambio!!.convertedValue

        return book;
    }
}