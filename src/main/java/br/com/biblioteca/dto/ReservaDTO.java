package br.com.biblioteca.dto;

import java.time.LocalDate;

import br.com.biblioteca.entity.Livro;
import lombok.Data;

@Data
public class ReservaDTO {
	private Integer id;
	private Livro livro;
	private String reservadoPor;
	private LocalDate dataReserva;
}
