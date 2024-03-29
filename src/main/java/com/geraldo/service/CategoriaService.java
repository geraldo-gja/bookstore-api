package com.geraldo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.geraldo.domain.Categoria;
import com.geraldo.dtos.CategoriaDTO;
import com.geraldo.repositories.CategoriaRepository;
import com.geraldo.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	public Categoria findById( Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElseThrow( () -> new ObjectNotFoundException(
				"Objeto não encontrato! Id: " + id + ", Tipo: " + Categoria.class.getName() ) );
	}
	
	public List<Categoria> findAll() {
		return repository.findAll();
	}
	
	public Categoria create(Categoria obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Categoria update(Integer id, CategoriaDTO objDto) {
		
		Categoria obj = findById(id);
		obj.setNome(objDto.getNome());
		obj.setDescricao(objDto.getDescricao());
		
		return repository.save(obj);
	}

	public void delete(Integer id) {
		
		findById(id);
			
		try {
			repository.deleteById(id);
		} catch ( DataIntegrityViolationException e) {
			throw new com.geraldo.service.exceptions.DataIntegrityViolationException
			( "Categoria não pode ser deletado! Possue livros associados." );
		}
	}
	
}
