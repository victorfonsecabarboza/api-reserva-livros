package br.com.biblioteca.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import br.com.biblioteca.repository.ReservaRepository;

class ReservaServiceTest {

	@Mock
	private ReservaRepository reservaRepository; // Mock do repositório de reservas.

	@InjectMocks
	private ReservaService reservaService; // Serviço a ser testado.

	private Reserva reserva; // Objeto de reserva para testes.
	private Livro livro; // Objeto de livro associado às reservas.

	/**
	 * Configura o ambiente de teste antes de cada método de teste.
	 * Inicializa os objetos e mocks necessários.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // Inicializa os mocks
		livro = new Livro();
		livro.setId(1);
		livro.setTitulo("Livro de Teste");
		livro.setAutor("Autor Teste");

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
		// Define o comportamento esperado do mock.
		when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);		
		// Executa o método a ser testado.
		Reserva reservaCadastrada = reservaService.cadastrarReserva(reserva);		
		// Verifica se a reserva foi cadastrada corretamente.
		assertNotNull(reservaCadastrada);
		assertEquals("Usuário Teste", reservaCadastrada.getReservadoPor());		
		// Verifica se o método save do repositório foi chamado uma vez.
		verify(reservaRepository, times(1)).save(reserva);
	}

	/**
	 * Testa a listagem de todas as reservas.
	 */
	@Test
	public void testListarReservas() {
		List<Reserva> reservas = Arrays.asList(reserva);
		// Define o comportamento esperado do mock.
		when(reservaRepository.findAll()).thenReturn(reservas);		
		// Executa o método a ser testado.
		List<Reserva> listaReservas = reservaService.listarReservas();		
		// Verifica se a lista retornada contém o número correto de reservas.
		assertEquals(1, listaReservas.size());		
		// Verifica se o método findAll do repositório foi chamado uma vez.
		verify(reservaRepository, times(1)).findAll();
	}

	/**
	 * Testa a obtenção de uma reserva pelo seu ID.
	 */
	@Test
	public void testObterReservaPorId() {
		// Define o comportamento esperado do mock.
		when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));	
		// Executa o método a ser testado.
		Optional<Reserva> reservaEncontrada = reservaService.obterReservaPorId(1);		
		// Verifica se a reserva foi encontrada e contém os dados esperados.
		assertTrue(reservaEncontrada.isPresent());
		assertEquals("Usuário Teste", reservaEncontrada.get().getReservadoPor());		
		// Verifica se o método findById do repositório foi chamado uma vez.
		verify(reservaRepository, times(1)).findById(1);
	}

	/**
	 * Testa a atualização de uma reserva existente.
	 */
	@Test
	public void testAtualizarReserva() {
		Reserva reservaAtualizada = new Reserva();
		reservaAtualizada.setLivro(livro);
		reservaAtualizada.setReservadoPor("Usuário Atualizado");
		reservaAtualizada.setDataReserva(LocalDate.now().plusDays(1));	
		// Define o comportamento esperado dos mocks.
		when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));
		when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaAtualizada);		
		// Executa o método a ser testado.
		Reserva reservaSalva = reservaService.AtaulizarReserva(1, reservaAtualizada);		
		// Verifica se a reserva foi atualizada corretamente.
		assertNotNull(reservaSalva);
		assertEquals("Usuário Atualizado", reservaSalva.getReservadoPor());		
		// Verifica se os métodos findById e save do repositório foram chamados corretamente.
		verify(reservaRepository, times(1)).findById(1);
		verify(reservaRepository, times(1)).save(reserva);
	}

	/**
	 * Testa a exclusão de uma reserva pelo seu ID.
	 */
	@Test
	public void testDeletarReserva() {
		// Define o comportamento esperado do mock.
		doNothing().when(reservaRepository).deleteById(1);		
		// Executa o método a ser testado.
		reservaService.deletarReserva(1);		
		// Verifica se o método deleteById do repositório foi chamado uma vez.
		verify(reservaRepository, times(1)).deleteById(1);
	}

}
