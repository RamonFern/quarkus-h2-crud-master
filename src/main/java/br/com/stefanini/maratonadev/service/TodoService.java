package br.com.stefanini.maratonadev.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.stefanini.maratonadev.dao.TodoDao;
import br.com.stefanini.maratonadev.model.Todo;


//@RequestScoped
@ApplicationScoped
public class TodoService {

	@Inject
	TodoDao dao;
	
	private void validar() {
		//validar regra de negocio
	}
	
	@Transactional(rollbackOn = Exception.class)
	public List<Todo> inserir(Todo todo) {
		Todo.persist(todo);
		return this.listar();
	}
	
	public List<Todo> listar() {
		return dao.listar();
	}

}
