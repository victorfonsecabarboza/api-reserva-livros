package br.com.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.biblioteca.entity.Livro;
import br.com.biblioteca.repository.LivroRepository;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;

	/**
	 * Cadastra um novo livro.
	 * 
	 * @param livro O objeto Livro a ser cadastrado.
	 * @return O livro é cadastrado.
	 */
	public Livro cadastrarLivro(Livro livro) {
		return livroRepository.save(livro);
	}

	/**
	 * Lista todos os livros.
	 * 
	 * @return Uma lista de livros.
	 */
	public List<Livro> listarLivros() {
		return livroRepository.findAll();
	}

	/**
	 * Obtém um livro pelo seu ID.
	 * 
	 * @param id O ID do livro a ser obtido.
	 * @return Um Optional contendo  o livro, ser encontrado.
	 */
	public Optional<Livro> obterLivroPorId(Integer id) {
		return livroRepository.findById(id);
	}

	/**
	 * Atualiza as informações de um livro existente.
	 * 
	 * @param id O ID do livro a ser atualizado.
	 * @param livroAtualizado O objeto Livro com as informações atualizadas.
	 * @return O livro atualizado, ou null se o livro não for encontrado.
	 */
	public Livro atualizarLivro(Integer id, Livro livroAtualizado) {
		Livro livro = livroRepository.findById(id).orElse(null);
		if (livro != null) {
			// Atualiza os campos do livro existente com os valores do livro atualizado.
			livro.setTitulo(livroAtualizado.getTitulo());
			livro.setAutor(livroAtualizado.getAutor());
			livro.setDisponivel(livroAtualizado.isDisponivel());
			livro.setReservado(livroAtualizado.isReservado());
			return livroRepository.save(livro);
		}
		return null;
	}

	/**
	 * Deleta um livro pelo seu ID.
	 * 
	 * @param id O ID do livro a ser deletado.
	 */
	public void deletarLivro(Integer id) {
		livroRepository.deleteById(id);
	}

}
