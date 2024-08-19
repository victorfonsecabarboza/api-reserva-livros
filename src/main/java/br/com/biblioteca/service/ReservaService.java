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

	public Reserva cadastrarReserva(Reserva reserva) {
	    // Verifique se o livro associado realmente existe
	    Livro livro = livroRepository.findById(reserva.getLivro().getId())
	        .orElseThrow(() -> new EntityNotFoundException("Livro com ID " + reserva.getLivro().getId() + " não encontrado"));
	    // Defina o livro na reserva
	    reserva.setLivro(livro);
	    // Salve e retorne a reserva
	    return reservaRepository.save(reserva);
	}

	public List<Reserva> listarReservas() {
		return reservaRepository.findAll();
	}

	public Optional<Reserva> obterReservaPorId(Integer id) {
		return reservaRepository.findById(id);
	}

	public Reserva AtaulizarReserva(Integer id, Reserva reservaAtualizada) {
		Reserva reserva = reservaRepository.findById(id).orElse(null);
		if (reserva != null) {
			Livro livro = livroRepository.findById(reservaAtualizada.getLivro().getId()).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
			reserva.setReservadoPor(reservaAtualizada.getReservadoPor());
			reserva.setDataReserva(reservaAtualizada.getDataReserva());
			reserva.setLivro(livro);
			return reservaRepository.save(reserva);
		}
		return null;
	}

	public void deletarReserva(Integer id) {
		reservaRepository.deleteById(id);
	}

}
