package br.com.biblioteca.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.biblioteca.dto.LivroDTO;
import br.com.biblioteca.entity.Livro;
import br.com.biblioteca.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("livros")
@Tag(name = "Livros", description = "Operações relacionadas aos livros")
public class LivroController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LivroService livroService;

	/**
	 * Cadastra um novo livro.
	 *
	 * @param livroDTO Objeto de transferência de dados do livro a ser cadastrado.
	 * @return Resposta contendo o DTO do livro cadastrado.
	 */
	@Operation(summary = "Cadastra um novo livro")
	@ApiResponse(responseCode = "200", description = "Livro cadastrado com sucesso")
	@PostMapping
	public ResponseEntity<LivroDTO> cadastrarLivro(@RequestBody LivroDTO livroDTO) {
		Livro livro = modelMapper.map(livroDTO, Livro.class);
		Livro livroCriado = livroService.cadastrarLivro(livro);
		LivroDTO livroCriadoDTO = modelMapper.map(livroCriado, LivroDTO.class);
		return ResponseEntity.ok().body(livroCriadoDTO);
	}

	/**
	 * Lista todos os livros.
	 *
	 * @return Resposta contendo uma lista de DTOs dos livros.
	 */
	@Operation(summary = "Lista todos os livros")
	@ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
	@GetMapping
	public ResponseEntity<List<LivroDTO>> listarLivros() {
		List<Livro> listarTodosLivros = livroService.listarLivros();
		List<LivroDTO> listarTodosLivrosDTO = listarTodosLivros.stream()
				.map(livro -> modelMapper.map(livro, LivroDTO.class)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listarTodosLivrosDTO);
	}

	/**
	 * Obtém um livro pelo seu ID.
	 *
	 * @param id ID do livro a ser obtido.
	 * @return Resposta contendo o DTO do livro se encontrado, ou Not Found se não encontrado.
	 */
	@Operation(summary = "Obtém um livro por ID")
	@ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso")
	@ApiResponse(responseCode = "404", description = "Livro não encontrado")
	@GetMapping("/{id}")
	public ResponseEntity<LivroDTO> obterLivroPorId(@PathVariable Integer id) {
		return livroService.obterLivroPorId(id).map(livro -> {
			LivroDTO listarLivroIdDTO = modelMapper.map(livro, LivroDTO.class);
			return ResponseEntity.ok().body(listarLivroIdDTO);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * Atualiza um livro existente.
	 *
	 * @param id ID do livro a ser atualizado.
	 * @param livroDTO Objeto de transferência de dados com as informações atualizadas.
	 * @return Resposta contendo o DTO do livro atualizado.
	 */
	@Operation(summary = "Atualiza um livro existente")
	@ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso")
	@ApiResponse(responseCode = "404", description = "Livro não encontrado")
	@PutMapping("/{id}")
	public ResponseEntity<LivroDTO> atualizarLivro(@PathVariable Integer id, @RequestBody LivroDTO livroDTO) {
		Livro livro = modelMapper.map(livroDTO, Livro.class);
		Livro livroAtualizado = livroService.atualizarLivro(id, livro);
		LivroDTO livroAtualizadoDTO = modelMapper.map(livroAtualizado, LivroDTO.class);
		return ResponseEntity.ok().body(livroAtualizadoDTO);
	}

	/**
	 * Deleta um livro pelo seu ID.
	 *
	 * @param id ID do livro a ser deletado.
	 * @return Resposta com status No Content após a exclusão.
	 */
	@Operation(summary = "Deleta um livro pelo ID", description = "Remove um livro do sistema pelo seu ID")
	@ApiResponse(responseCode = "204", description = "Livro deletado com sucesso") 
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarLivro(@PathVariable Integer id) {
		livroService.deletarLivro(id);
		return ResponseEntity.noContent().build();
	}

}
