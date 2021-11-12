package br.com.vgm.cambio.repository

import br.com.vgm.cambio.model.Cambio
import org.springframework.data.jpa.repository.JpaRepository

interface CambioRepository : JpaRepository<Cambio, Long> {

    fun findByFromAndTo(from: String, to: String): Cambio

}