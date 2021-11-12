package br.com.vgm.book.repository

import br.com.vgm.book.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {

    fun findByIdAndCurrency(id: Long, currency: String): Book

}