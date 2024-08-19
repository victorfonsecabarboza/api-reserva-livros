package br.com.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.biblioteca.entity.Livro;
import br.com.biblioteca.entity.Reserva;
import br.com.biblioteca.repository.LivroRepository;
import br.com.biblioteca.repository.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ReservaService {

	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private LivroRepository livroRepository;

	/**
	 * Cadastra uma nova reserva.
	 * 
	 * @param reserva O objeto Reserva a ser cadastrado.
	 * @return A reserva cadastrada.
	 * @throws EntityNotFoundException Se o livro associado à reserva não for encontrado.
	 */
	public Reserva cadastrarReserva(Reserva reserva) {
		// Verifica se o livro associado à reserva existe.
	    Livro livro = livroRepository.findById(reserva.getLivro().getId())
	        .orElseThrow(() -> new EntityNotFoundException("Livro com ID " + reserva.getLivro().getId() + " não encontrado"));
	    // Associa o livro à reserva.
	    reserva.setLivro(livro);
	    return reservaRepository.save(reserva);
	}

	/**
	 * Lista todas as reservas.
	 * 
	 * @return Uma lista de reservas.
	 */
	public List<Reserva> listarReservas() {
		return reservaRepository.findAll();
	}

	/**
	 * Obtém uma reserva pelo seu ID.
	 * 
	 * @param id O ID da reserva a ser obtida.
	 * @return Um Optional contendo a reserva, se encontrada.
	 */
	public Optional<Reserva> obterReservaPorId(Integer id) {
		return reservaRepository.findById(id);
	}

	/**
	 * Atualiza as informações de uma reserva existente.
	 * 
	 * @param id O ID da reserva a ser atualizada.
	 * @param reservaAtualizada O objeto Reserva com as informações atualizadas.
	 * @return A reserva atualizada, ou null se a reserva não for encontrada.
	 * @throws EntityNotFoundException Se o livro associado à reserva atualizada não for encontrado.
	 */
	public Reserva AtaulizarReserva(Integer id, Reserva reservaAtualizada) {
		Reserva reserva = reservaRepository.findById(id).orElse(null);
		if (reserva != null) {
			// Verifica se o livro associado à reserva atualizada existe.
			Livro livro = livroRepository.findById(reservaAtualizada.getLivro().getId()).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
			// Atualiza os campos da reserva existente.
			reserva.setReservadoPor(reservaAtualizada.getReservadoPor());
			reserva.setDataReserva(reservaAtualizada.getDataReserva());
			reserva.setLivro(livro);
			return reservaRepository.save(reserva);
		}
		return null;
	}

	/**
	 * Deleta uma reserva pelo seu ID.
	 * 
	 * @param id O ID da reserva a ser deletada.
	 */
	public void deletarReserva(Integer id) {
		reservaRepository.deleteById(id);
	}

}
