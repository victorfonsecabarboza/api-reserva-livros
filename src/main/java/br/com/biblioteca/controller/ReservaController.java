
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("reservas")
@Tag(name = "Reservas", description = "Operações relacionadas às reservas de livros")
public class ReservaController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ReservaService reservaService;

	/**
	 * Cadastra uma nova reserva.
	 *
	 * @param reservaDTO Objeto de transferência de dados da reserva a ser cadastrada.
	 * @return Resposta contendo o DTO da reserva cadastrada.
	 */
	@Operation(summary = "Cadastra uma nova reserva")
	@PostMapping
	public ResponseEntity<ReservaDTO> cadastrarReserva(@RequestBody ReservaDTO reservaDTO) {
		Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
		Reserva reservaCriada = reservaService.cadastrarReserva(reserva);
		ReservaDTO reservaCriadaDTO = modelMapper.map(reservaCriada, ReservaDTO.class);
		return ResponseEntity.ok().body(reservaCriadaDTO);
	}

	/**
	 * Lista todas as reservas.
	 *
	 * @return Resposta contendo uma lista de DTOs das reservas.
	 */
	@Operation(summary = "Lista todas as reservas")
	@GetMapping
	public ResponseEntity<List<ReservaDTO>> listarReservas() {
		List<Reserva> listarTodasReservas = reservaService.listarReservas();
		List<ReservaDTO> listarTodasReservasDTO = listarTodasReservas.stream()
				.map(reserva -> modelMapper.map(reserva, ReservaDTO.class)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listarTodasReservasDTO);
	}

	/**
	 * Obtém uma reserva pelo seu ID.
	 *
	 * @param id ID da reserva a ser obtida.
	 * @return Resposta contendo o DTO da reserva se encontrado, ou Not Found se não encontrado.
	 */
	@Operation(summary = "Obtém uma reserva por ID")
	@GetMapping("/{id}")
	public ResponseEntity<ReservaDTO> obterReservaPorId(@PathVariable Integer id) {
		return reservaService.obterReservaPorId(id).map(reserva -> {
			ReservaDTO listarReservaPorIdDTO = modelMapper.map(reserva, ReservaDTO.class);
			return ResponseEntity.ok().body(listarReservaPorIdDTO);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * Atualiza uma reserva existente.
	 *
	 * @param id ID da reserva a ser atualizada.
	 * @param reservaDTO Objeto de transferência de dados com as informações atualizadas.
	 * @return Resposta contendo o DTO da reserva atualizada.
	 */
	@Operation(summary = "Atualiza uma reserva existente")
	@PutMapping("/{id}")
	public ResponseEntity<ReservaDTO> atualizarReserva(@PathVariable Integer id, @RequestBody ReservaDTO reservaDTO) {
		Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
		Reserva reservaAtualizada = reservaService.AtualizarReserva(id, reserva);
		ReservaDTO reservaAtualizadaDTO = modelMapper.map(reservaAtualizada, ReservaDTO.class);
		return ResponseEntity.ok().body(reservaAtualizadaDTO);
	}

	/**
	 * Deleta uma reserva pelo seu ID.
	 *
	 * @param id ID da reserva a ser deletada.
	 * @return Resposta com status No Content após a exclusão.
	 */
	@Operation(summary = "Deleta uma reserva pelo ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarReserva(@PathVariable Integer id) {
		reservaService.deletarReserva(id);
		return ResponseEntity.noContent().build();
	}
}
