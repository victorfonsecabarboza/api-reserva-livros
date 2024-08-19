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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("reservas")
@Tag(name = "Reservas", description = "Operações relacionadas às reservas de livros")
public class ReservaController {

	@Autowired
	private ModelMapper modelMapper; // Injeção do ModelMapper para conversão entre DTOs e entidades.

	@Autowired
	private ReservaService reservaService; // Injeção do serviço de reservas.

	/**
	 * Cadastra uma nova reserva.
	 *
	 * @param reservaDTO Objeto de transferência de dados da reserva a ser cadastrada.
	 * @return Resposta contendo o DTO da reserva cadastrada.
	 */
	@Operation(summary = "Cadastra uma nova reserva")
	@ApiResponse(responseCode = "200", description = "Reserva cadastrada com sucesso")
	@PostMapping
	public ResponseEntity<ReservaDTO> cadastrarReserva(@RequestBody ReservaDTO reservaDTO) {
		// Converte o DTO para a entidade Reserva.
		Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
		// Cadastra a reserva e obtém a reserva criada.
		Reserva reservaCriada = reservaService.cadastrarReserva(reserva);
		// Converte a reserva criada para DTO.
		ReservaDTO reservaCriadaDTO = modelMapper.map(reservaCriada, ReservaDTO.class);
		// Retorna o DTO da reserva criada.
		return ResponseEntity.ok().body(reservaCriadaDTO);
	}

	/**
	 * Lista todas as reservas.
	 *
	 * @return Resposta contendo uma lista de DTOs das reservas.
	 */
	@Operation(summary = "Lista todas as reservas")
	@ApiResponse(responseCode = "200", description = "Lista de reservas retornada com sucesso")
	@GetMapping
	public ResponseEntity<List<ReservaDTO>> listarReservas() {
		// Obtém a lista de reservas.
		List<Reserva> listarTodasReservas = reservaService.listarReservas();
		// Converte a lista de reservas para uma lista de DTOs.
		List<ReservaDTO> listarTodasReservasDTO = listarTodasReservas.stream()
				.map(reserva -> modelMapper.map(reserva, ReservaDTO.class)).collect(Collectors.toList());
		// Retorna a lista de DTOs.
		return ResponseEntity.ok().body(listarTodasReservasDTO);
	}

	/**
	 * Obtém uma reserva pelo seu ID.
	 *
	 * @param id ID da reserva a ser obtida.
	 * @return Resposta contendo o DTO da reserva se encontrado, ou Not Found se não encontrado.
	 */
	@Operation(summary = "Obtém uma reserva por ID")
	@ApiResponse(responseCode = "200", description = "Reserva encontrada com sucesso")
	@ApiResponse(responseCode = "404", description = "Reserva não encontrada")
	@GetMapping("/{id}")
	public ResponseEntity<ReservaDTO> obterReservaPorId(@PathVariable Integer id) {
		// Obtém a reserva pelo ID.
		return reservaService.obterReservaPorId(id).map(reserva -> {
			// Converte a reserva para DTO.
			ReservaDTO listarReservaPorIdDTO = modelMapper.map(reserva, ReservaDTO.class);
			// Retorna o DTO da reserva.
			return ResponseEntity.ok().body(listarReservaPorIdDTO);
		// Retorna Not Found se a reserva não for encontrada.
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
	@ApiResponse(responseCode = "200", description = "Reserva atualizada com sucesso")
	@ApiResponse(responseCode = "404", description = "Reserva não encontrada")
	@PutMapping("/{id}")
	public ResponseEntity<ReservaDTO> atualizarReserva(@PathVariable Integer id, @RequestBody ReservaDTO reservaDTO) {
		// Converte o DTO para a entidade Reserva.
		Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
		// Atualiza a reserva e obtém a reserva atualizada.
		Reserva reservaAtualizada = reservaService.AtaulizarReserva(id, reserva);
		// Converte a reserva atualizada para DTO.
		ReservaDTO reservaAtualizadaDTO = modelMapper.map(reservaAtualizada, ReservaDTO.class);
		// Retorna o DTO da reserva atualizada.
		return ResponseEntity.ok().body(reservaAtualizadaDTO);
	}

	/**
	 * Deleta uma reserva pelo seu ID.
	 *
	 * @param id ID da reserva a ser deletada.
	 * @return Resposta com status No Content após a exclusão.
	 */
	@Operation(summary = "Deleta uma reserva pelo ID")
	@ApiResponse(responseCode = "204", description = "Reserva deletada com sucesso")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarReserva(@PathVariable Integer id) {
		// Deleta a reserva.
		reservaService.deletarReserva(id);
		// Retorna No Content.
		return ResponseEntity.noContent().build();
	}
}
