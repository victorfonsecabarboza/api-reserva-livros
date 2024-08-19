package br.com.biblioteca.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.entity.Livro;
import br.com.biblioteca.repository.LivroRepository;
import br.com.biblioteca.service.LivroService;

class LivroServiceTest {

	@Mock
	private LivroRepository livroRepository;

	@InjectMocks
	private LivroService livroService;
	private Livro livro;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		livro = new Livro();
		livro.setId(1);
		livro.setTitulo("Livro de Teste");
		livro.setAutor("Autor Teste");
		livro.setDisponivel(true);
		livro.setReservado(false);
	}

	@Test
	public void testCadastrarLivro() {
		when(livroRepository.save(any(Livro.class))).thenReturn(livro);
		Livro livroCadastrado = livroService.cadastrarLivro(livro);
		assertNotNull(livroCadastrado);
		assertEquals("Livro de Teste", livroCadastrado.getTitulo());
		assertEquals("Autor Teste", livroCadastrado.getAutor());
		verify(livroRepository, times(1)).save(livro);
	}

	@Test
	public void testListarLivros() {
		List<Livro> livros = Arrays.asList(livro);
		when(livroRepository.findAll()).thenReturn(livros);
		List<Livro> listarLivros = livroService.listarLivros();
		assertEquals(1, listarLivros.size());
		verify(livroRepository, times(1)).findAll();

	}

	@Test
	public void testObterLivroPorId() {
		when(livroRepository.findById(1)).thenReturn(Optional.of(livro));
		Optional<Livro> livroEncontrado = livroService.obterLivroPorId(1);
		assertTrue(livroEncontrado.isPresent());
		assertEquals("Livro de Teste", livroEncontrado.get().getTitulo());
		assertEquals("Autor Teste", livroEncontrado.get().getAutor());
		verify(livroRepository, times(1)).findById(1);
	}

	@Test
	public void testAtualizarLivro() {
		Livro livroAtualizado = new Livro();
		livroAtualizado.setTitulo("Livro Atualizado");
		livroAtualizado.setAutor("Autor Atualizado");
		livroAtualizado.setDisponivel(false);
		livroAtualizado.setReservado(true);

		when(livroRepository.findById(1)).thenReturn(Optional.of(livro));
		when(livroRepository.save(any(Livro.class))).thenReturn(livroAtualizado);

		Livro livroSalvo = livroService.atualizarLivro(1, livroAtualizado);

		assertNotNull(livroSalvo);
		assertEquals("Livro Atualizado", livroSalvo.getTitulo());
		assertEquals("Autor Atualizado", livroSalvo.getAutor());

		verify(livroRepository, times(1)).findById(1);
		verify(livroRepository, times(1)).save(livro);
	}

	@Test
	public void testDeletarLivro() {
		doNothing().when(livroRepository).deleteById(1);

		livroService.deletarLivro(1);

		verify(livroRepository, times(1)).deleteById(1);
	}

}
