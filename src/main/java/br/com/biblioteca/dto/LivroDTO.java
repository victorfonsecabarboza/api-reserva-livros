package br.com.biblioteca.dto;

import lombok.Data;

@Data
public class LivroDTO {
	private Integer id;
	private String titulo;
	private String autor;
	private boolean disponivel = true;
	private boolean reservado = false;
}
