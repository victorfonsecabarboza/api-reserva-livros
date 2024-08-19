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

	public Livro cadastrarLivro(Livro livro) {
		return livroRepository.save(livro);
	}

	public List<Livro> listarLivros() {
		return livroRepository.findAll();
	}

	public Optional<Livro> obterLivroPorId(Integer id) {
		return livroRepository.findById(id);
	}

	public Livro atualizarLivro(Integer id, Livro livroAtualizado) {
		Livro livro = livroRepository.findById(id).orElse(null);
		if (livro != null) {
			livro.setTitulo(livroAtualizado.getTitulo());
			livro.setAutor(livroAtualizado.getAutor());
			livro.setDisponivel(livroAtualizado.isDisponivel());
			livro.setReservado(livroAtualizado.isReservado());
			return livroRepository.save(livro);
		}
		return null;
	}

	public void deletarLivro(Integer id) {
		livroRepository.deleteById(id);
	}

}
