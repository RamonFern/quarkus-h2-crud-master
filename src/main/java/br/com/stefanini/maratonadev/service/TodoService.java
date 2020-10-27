package br.com.stefanini.maratonadev.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import br.com.stefanini.maratonadev.dao.TodoDao;
import br.com.stefanini.maratonadev.model.Todo;


//@RequestScoped
@ApplicationScoped
public class TodoService {

	@Inject	TodoDao dao;
	
	private void validar(Todo todo) {
		//validar regra de negocio
		if(todo.getNome() == null) {
			throw new NotFoundException();
		}
	}
	
	@Transactional(rollbackOn = Exception.class)
	public void inserir(Todo todo) {
		//validacao
		validar(todo);
		//chamada do dao
		dao.inserir(todo);
		//Todo.persist(todo);
		//return this.listar();
	}
	
	public List<Todo> listar() {
		return dao.listar();
	}

}
