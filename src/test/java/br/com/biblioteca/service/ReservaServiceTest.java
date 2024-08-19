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
	private ReservaRepository reservaRepository;

	@InjectMocks
	private ReservaService reservaService;

	private Reserva reserva;
	private Livro livro;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
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

	@Test
	public void testCadastrarReserva() {
		when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);
		Reserva reservaCadastrada = reservaService.cadastrarReserva(reserva);
		assertNotNull(reservaCadastrada);
		assertEquals("Usuário Teste", reservaCadastrada.getReservadoPor());
		verify(reservaRepository, times(1)).save(reserva);
	}

	@Test
	public void testListarReservas() {
		List<Reserva> reservas = Arrays.asList(reserva);
		when(reservaRepository.findAll()).thenReturn(reservas);
		List<Reserva> listaReservas = reservaService.listarReservas();
		assertEquals(1, listaReservas.size());
		verify(reservaRepository, times(1)).findAll();
	}

	@Test
	public void testObterReservaPorId() {
		when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));
		Optional<Reserva> reservaEncontrada = reservaService.obterReservaPorId(1);
		assertTrue(reservaEncontrada.isPresent());
		assertEquals("Usuário Teste", reservaEncontrada.get().getReservadoPor());
		verify(reservaRepository, times(1)).findById(1);
	}

	@Test
	public void testAtualizarReserva() {
		Reserva reservaAtualizada = new Reserva();
		reservaAtualizada.setLivro(livro);
		reservaAtualizada.setReservadoPor("Usuário Atualizado");
		reservaAtualizada.setDataReserva(LocalDate.now().plusDays(1));
		when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));
		when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaAtualizada);
		Reserva reservaSalva = reservaService.AtaulizarReserva(1, reservaAtualizada);
		assertNotNull(reservaSalva);
		assertEquals("Usuário Atualizado", reservaSalva.getReservadoPor());
		verify(reservaRepository, times(1)).findById(1);
		verify(reservaRepository, times(1)).save(reserva);
	}

	@Test
	public void testDeletarReserva() {
		doNothing().when(reservaRepository).deleteById(1);
		reservaService.deletarReserva(1);
		verify(reservaRepository, times(1)).deleteById(1);
	}

}
