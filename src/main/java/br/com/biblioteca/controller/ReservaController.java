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

import br.com.biblioteca.dto.ReservaDTO;
import br.com.biblioteca.entity.Reserva;
import br.com.biblioteca.service.ReservaService;

@RestController
@RequestMapping("reservas")
public class ReservaController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ReservaService reservaService;

	@PostMapping
	public ResponseEntity<ReservaDTO> cadastrarReserva(@RequestBody ReservaDTO reservaDTO) {
		Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
		Reserva reservaCriada = reservaService.cadastrarReserva(reserva);
		ReservaDTO reservaCriadaDTO = modelMapper.map(reservaCriada, ReservaDTO.class);
		return ResponseEntity.ok().body(reservaCriadaDTO);
	}

	@GetMapping
	public ResponseEntity<List<ReservaDTO>> listarReservas() {
		List<Reserva> listarTodasReservas = reservaService.listarReservas();
		List<ReservaDTO> listarTodasReservasDTO = listarTodasReservas.stream()
				.map(reserva -> modelMapper.map(reserva, ReservaDTO.class)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listarTodasReservasDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReservaDTO> obterReservaPorId(@PathVariable Integer id) {
		return reservaService.obterReservaPorId(id).map(reserva -> {
			ReservaDTO listarReservaPorIdDTO = modelMapper.map(reserva, ReservaDTO.class);
			return ResponseEntity.ok().body(listarReservaPorIdDTO);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ReservaDTO> atualizarReserva(@PathVariable Integer id, @RequestBody ReservaDTO reservaDTO) {
		Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
		Reserva reservaAtualizada = reservaService.AtaulizarReserva(id, reserva);
		ReservaDTO reservaAtualizadaDTO = modelMapper.map(reservaAtualizada, ReservaDTO.class);
		return ResponseEntity.ok().body(reservaAtualizadaDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarReserva(@PathVariable Integer id) {
		reservaService.deletarReserva(id);
		return ResponseEntity.noContent().build();
	}

}
