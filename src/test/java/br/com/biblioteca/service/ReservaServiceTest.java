package br.com.biblioteca.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.entity.Livro;
import br.com.biblioteca.entity.Reserva;
import br.com.biblioteca.repository.LivroRepository;
import br.com.biblioteca.repository.ReservaRepository;

class ReservaServiceTest {

	// Cria uma instância mock do repositório de reservas
	@Mock
	private ReservaRepository reservaRepository;

	// Cria uma instância mock do repositório de livros
	@Mock
	private LivroRepository livroRepository;

	// Cria uma instância do serviço de reserva e injeta os mocks dos repositórios
	@InjectMocks
	private ReservaService reservaService;

	// Instâncias de reserva e livro usadas nos testes
	private Reserva reserva;
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

		reserva = new Reserva();
		reserva.setId(1);
		reserva.setLivro(livro);
		reserva.setReservadoPor("Usuário Teste");
		reserva.setDataReserva(LocalDate.now());
	}

	/**
	 * Testa o cadastro de uma reserva.
	 */
	@Test
	public void testCadastrarReserva() {
		// Mock para retorno do livro e do método save
		when(livroRepository.findById(livro.getId())).thenReturn(Optional.of(livro));
		when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);
		
		// Executa o método de cadastrar reserva do service
		Reserva reservaCadastrada = reservaService.cadastrarReserva(reserva);
		
		// Verifica se a reserva foi cadastrada corretamente
		assertNotNull(reservaCadastrada);
		assertEquals("Usuário Teste", reservaCadastrada.getReservadoPor());
		assertEquals(livro, reservaCadastrada.getLivro());
		assertFalse(livro.isDisponivel()); // Livro deve estar reservado
		assertTrue(livro.isReservado());
		
		// Verifica chamadas dos métodos mockados
		verify(livroRepository, times(1)).findById(livro.getId());
		verify(livroRepository, times(1)).save(livro);
		verify(reservaRepository, times(1)).save(reserva);
	}

	/**
	 * Testa a listagem de todas as reservas.
	 */
	@Test
	public void testListarReservas() {
		List<Reserva> reservas = Arrays.asList(reserva);
		
		// Mock para retorno da lista de reservas
		when(reservaRepository.findAll()).thenReturn(reservas);
		
		// Executa o método de listar todas as reserva do service
		List<Reserva> listaReservas = reservaService.listarReservas();
		
		// Verifica se a lista contém uma reserva
		assertEquals(1, listaReservas.size());
		
		// Verifica chamada do método mockado
		verify(reservaRepository, times(1)).findAll();
	}

	/**
	 * Testa a obtenção de uma reserva pelo ID.
	 */
	@Test
	public void testObterReservaPorId() {
		// Mock para retorno da reserva pelo ID
		when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));
		
		// Executa o método de listar uma reserva pelo ID do service
		Optional<Reserva> reservaEncontrada = reservaService.obterReservaPorId(1);
		
		// Verifica se a reserva foi encontrada
		assertTrue(reservaEncontrada.isPresent());
		assertEquals("Usuário Teste", reservaEncontrada.get().getReservadoPor());
		
		// Verifica chamada do método mockado
		verify(reservaRepository, times(1)).findById(1);
	}

	/**
	 * Testa a atualização de uma reserva existente.
	 */
	@Test
	public void testAtualizarReserva() {
	    // Preparando um novo livro e reserva atualizada
	    Livro novoLivro = new Livro();
	    novoLivro.setId(2);
	    novoLivro.setTitulo("Novo Livro de Teste");
	    novoLivro.setAutor("Novo Autor Teste");
	    novoLivro.setDisponivel(false);
	    novoLivro.setReservado(true); 
	    
	    Reserva reservaAtualizada = new Reserva();
	    reservaAtualizada.setLivro(novoLivro);
	    reservaAtualizada.setReservadoPor("Usuário Atualizado");
	    reservaAtualizada.setDataReserva(LocalDate.now().plusDays(1));

	    // Mock para retorno dos métodos utilizados na atualização
	    when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));
	    when(livroRepository.findById(1)).thenReturn(Optional.of(livro));
	    when(livroRepository.findById(2)).thenReturn(Optional.of(novoLivro));
	    when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaAtualizada);

		// Executa o método de atualizar uma reserva pelo ID do service
	    Reserva reservaSalva = reservaService.AtualizarReserva(1, reservaAtualizada);
	    
	    // Verifica se a reserva foi atualizada corretamente
	    assertNotNull(reservaSalva);
	    assertEquals("Usuário Atualizado", reservaSalva.getReservadoPor());
	    assertEquals(novoLivro, reservaSalva.getLivro());

	    // Verifica as mudanças de estado dos livros
	    assertTrue(livro.isDisponivel()); // Livro antigo deve estar disponível
	    assertFalse(livro.isReservado());  // Livro antigo não deve estar reservado
	    assertFalse(novoLivro.isDisponivel()); // Novo livro deve estar indisponível
	    assertTrue(novoLivro.isReservado()); // Novo livro deve estar reservado

	    // Verifica chamadas dos métodos mockados
	    verify(reservaRepository, times(1)).findById(1);
	    verify(livroRepository, times(1)).findById(2);
	    verify(reservaRepository, times(1)).save(any(Reserva.class));
	}

	/**
	 * Testa a exclusão de uma reserva pelo seu ID.
	 */
	@Test
	public void testDeletarReserva() {
		// Mock para exclusão sem ação
		doNothing().when(reservaRepository).deleteById(1);
		
		// Executa o método de deletar uma reserva pelo ID do service
		reservaService.deletarReserva(1);
		
		// Verifica chamada do método de exclusão
		verify(reservaRepository, times(1)).deleteById(1);
	}

}
