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

	// Cria uma instância mock do repositório de livro
	@Mock
	private LivroRepository livroRepository;

	// Cria uma instância do serviço de livro e injeta os mocks dos repositórios
	@InjectMocks
	private LivroService livroService;

	// Instância de livro usada nos testes
	private Livro livro;

	/**
	 * Inicializa objetos e mocks antes de cada teste.
	 */
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

	/**
	 * Testa o cadastro de um livro.
	 */
	@Test
	public void testCadastrarLivro() {
		// Mock do método save do livro
		when(livroRepository.save(any(Livro.class))).thenReturn(livro);

		// Executa o método de cadastrar livro do service
		Livro livroCadastrado = livroService.cadastrarLivro(livro);

		// Verifica se o livro foi cadastrado corretamente
		assertNotNull(livroCadastrado);
		assertEquals("Livro de Teste", livroCadastrado.getTitulo());
		assertEquals("Autor Teste", livroCadastrado.getAutor());

		// Verifica chamadas dos métodos mockados
		verify(livroRepository, times(1)).save(livro);
	}

	/**
	 * Testa a listagem de todos os livros.
	 */
	@Test
	public void testListarLivros() {
		List<Livro> livros = Arrays.asList(livro);

		// Mock para retorno da lista de livros
		when(livroRepository.findAll()).thenReturn(livros);

		// Executa o método de listar todas os livros do service
		List<Livro> listarLivros = livroService.listarLivros();

		// Verifica se a lista contém um livro
		assertEquals(1, listarLivros.size());

		// Verifica chamada do método mockado
		verify(livroRepository, times(1)).findAll();
	}

	/**
	 * Testa a obtenção de um livro pelo seu ID.
	 */
	@Test
	public void testObterLivroPorId() {
		// Mock para retorno do livro pelo ID
		when(livroRepository.findById(1)).thenReturn(Optional.of(livro));

		// Executa o método de listar um livro pelo ID do service
		Optional<Livro> livroEncontrado = livroService.obterLivroPorId(1);

		// Verifica se o livro foi encontrada
		assertTrue(livroEncontrado.isPresent());
		assertEquals("Livro de Teste", livroEncontrado.get().getTitulo());
		assertEquals("Autor Teste", livroEncontrado.get().getAutor());

		// Verifica chamada do método mockado
		verify(livroRepository, times(1)).findById(1);
	}

	/**
	 * Testa a atualização de um livro existente.
	 */
	@Test
	public void testAtualizarLivro() {
		// Preparando um livro atualizado
		Livro livroAtualizado = new Livro();
		livroAtualizado.setTitulo("Livro Atualizado");
		livroAtualizado.setAutor("Autor Atualizado");
		livroAtualizado.setDisponivel(false);
		livroAtualizado.setReservado(true);

		// Mock para retorno dos métodos utilizados na atualização
		when(livroRepository.findById(1)).thenReturn(Optional.of(livro));
		when(livroRepository.save(any(Livro.class))).thenReturn(livroAtualizado);

		// Executa o método de atualizar um livro pelo ID do service
		Livro livroSalvo = livroService.atualizarLivro(1, livroAtualizado);

		// Verifica se o livro foi atualizado corretamente
		assertNotNull(livroSalvo);
		assertEquals("Livro Atualizado", livroSalvo.getTitulo());
		assertEquals("Autor Atualizado", livroSalvo.getAutor());

		// Verifica chamadas dos métodos mockados
		verify(livroRepository, times(1)).findById(1);
		verify(livroRepository, times(1)).save(livro);
	}

	/**
	 * Testa a exclusão de um livro pelo seu ID.
	 */
	@Test
	public void testDeletarLivro() {
		// Mock para exclusão sem ação
		doNothing().when(livroRepository).deleteById(1);

		// Executa o método de deletar um livro pelo ID do service
		livroService.deletarLivro(1);

		// Verifica chamada do método de exclusão
		verify(livroRepository, times(1)).deleteById(1);
	}

}
