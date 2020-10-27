package br.com.stefanini.maratonadev.dao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.stefanini.maratonadev.model.Todo;

@RequestScoped
public class TodoDao {
	
	@Inject
	EntityManager em;

	public void inserir(Todo todo) {
		Todo.persist(todo);
	}
	
	public List<Todo> listar() {
		//return Todo.listAll();
		List<Todo> listaRetorno;
		String nomeConsulta = "CONSULTAR_TODO";
		TypedQuery<Todo> query = em.createNamedQuery(nomeConsulta, Todo.class);
		
		try {
			listaRetorno = query.getResultList();
		} catch (Exception e) {
			listaRetorno = new ArrayList<Todo>();
		}
		
		return listaRetorno;
	}
	
}
