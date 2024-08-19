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

@RestController
@RequestMapping("livros")
public class LivroController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LivroService livroService;

	@PostMapping
	public ResponseEntity<LivroDTO> cadastrarLivro(@RequestBody LivroDTO livroDTO) {
		Livro livro = modelMapper.map(livroDTO, Livro.class);
		Livro livroCriado = livroService.cadastrarLivro(livro);
		LivroDTO livroCriadoDTO = modelMapper.map(livroCriado, LivroDTO.class);
		return ResponseEntity.ok().body(livroCriadoDTO);
	}

	@GetMapping
	public ResponseEntity<List<LivroDTO>> listarLivros() {
		List<Livro> listarTodosLivros = livroService.listarLivros();
		List<LivroDTO> listarTodosLivrosDTO = listarTodosLivros.stream()
				.map(livro -> modelMapper.map(livro, LivroDTO.class)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listarTodosLivrosDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LivroDTO> obterLivroPorId(@PathVariable Integer id) {
		return livroService.obterLivroPorId(id).map(livro -> {
			LivroDTO listarLivroIdDTO = modelMapper.map(livro, LivroDTO.class);
			return ResponseEntity.ok().body(listarLivroIdDTO);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<LivroDTO> atualizarLivro(@PathVariable Integer id, @RequestBody LivroDTO livroDTO) {
		Livro livro = modelMapper.map(livroDTO, Livro.class);
		Livro livroAtualizado = livroService.atualizarLivro(id, livro);
		LivroDTO livroAtualizadoDTO = modelMapper.map(livroAtualizado, LivroDTO.class);
		return ResponseEntity.ok().body(livroAtualizadoDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarLivro(@PathVariable Integer id) {
		livroService.deletarLivro(id);
		return ResponseEntity.noContent().build();
	}

}
