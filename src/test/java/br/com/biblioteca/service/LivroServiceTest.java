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

class LivroServiceTest {

	@Mock
	private LivroRepository livroRepository; // Mock do repositório de livros.

	@InjectMocks
	private LivroService livroService; // Serviço a ser testado.

	private Livro livro; // Objeto de livro para testes.+
	
	/**
	 * Configura o ambiente de teste antes de cada método de teste.
	 * Inicializa os objetos e mocks necessários.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // Inicializa os mocks.
		livro = new Livro();
		livro.setId(1);
		livro.setTitulo("Livro de Teste");
		livro.setAutor("Autor Teste");
		livro.setDisponivel(true);
		livro.setReservado(false);
	}

	/**
	 * Testa o cadastro de um livro.
	 */
	@Test
	public void testCadastrarLivro() {
		// Define o comportamento esperado do mock.
		when(livroRepository.save(any(Livro.class))).thenReturn(livro);
		// Executa o método a ser testado.
		Livro livroCadastrado = livroService.cadastrarLivro(livro);
		// Verifica se o livro foi cadastrado corretamente.
		assertNotNull(livroCadastrado);
		assertEquals("Livro de Teste", livroCadastrado.getTitulo());
		assertEquals("Autor Teste", livroCadastrado.getAutor());
		// Verifica se o método save do repositório foi chamado uma vez.
		verify(livroRepository, times(1)).save(livro);
	}

	/**
	 * Testa a listagem de todos os livros.
	 */
	@Test
	public void testListarLivros() {
		List<Livro> livros = Arrays.asList(livro);
		// Define o comportamento esperado do mock.
		when(livroRepository.findAll()).thenReturn(livros);
		// Executa o método a ser testado.
		List<Livro> listarLivros = livroService.listarLivros();		
		// Verifica se a lista retornada contém o número correto de livros.
		assertEquals(1, listarLivros.size());		
		// Verifica se o método findAll do repositório foi chamado uma vez.
		verify(livroRepository, times(1)).findAll();
	}

	/**
	 * Testa a obtenção de um livro pelo seu ID.
	 */
	@Test
	public void testObterLivroPorId() {
		// Define o comportamento esperado do mock.
		when(livroRepository.findById(1)).thenReturn(Optional.of(livro));		
		// Executa o método a ser testado
		Optional<Livro> livroEncontrado = livroService.obterLivroPorId(1);		
		// Verifica se o livro foi encontrado e contém os dados esperados.
		assertTrue(livroEncontrado.isPresent());
		assertEquals("Livro de Teste", livroEncontrado.get().getTitulo());
		assertEquals("Autor Teste", livroEncontrado.get().getAutor());		
		// Verifica se o método findById do repositório foi chamado uma vez.
		verify(livroRepository, times(1)).findById(1);
	}

	/**
	 * Testa a atualização de um livro existente.
	 */
	@Test
	public void testAtualizarLivro() {
		Livro livroAtualizado = new Livro();
		livroAtualizado.setTitulo("Livro Atualizado");
		livroAtualizado.setAutor("Autor Atualizado");
		livroAtualizado.setDisponivel(false);
		livroAtualizado.setReservado(true);
		// Define o comportamento esperado dos mocks.
		when(livroRepository.findById(1)).thenReturn(Optional.of(livro));
		when(livroRepository.save(any(Livro.class))).thenReturn(livroAtualizado);
		// Executa o método a ser testado.
		Livro livroSalvo = livroService.atualizarLivro(1, livroAtualizado);
		// Verifica se o livro foi atualizado corretamente.
		assertNotNull(livroSalvo);
		assertEquals("Livro Atualizado", livroSalvo.getTitulo());
		assertEquals("Autor Atualizado", livroSalvo.getAutor());
		// Verifica se os métodos findById e save do repositório foram chamados corretamente.
		verify(livroRepository, times(1)).findById(1);
		verify(livroRepository, times(1)).save(livro);
	}

	/**
	 * Testa a exclusão de um livro pelo seu ID.
	 */
	@Test
	public void testDeletarLivro() {
		// Define o comportamento esperado do mock.
		doNothing().when(livroRepository).deleteById(1);	
		// Executa o método a ser testado.
		livroService.deletarLivro(1);	
		// Verifica se o método deleteById do repositório foi chamado uma vez.
		verify(livroRepository, times(1)).deleteById(1);
	}

}
