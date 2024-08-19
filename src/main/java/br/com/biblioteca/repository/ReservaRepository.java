package br.com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.biblioteca.entity.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

}
