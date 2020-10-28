package br.com.stefanini.maratonadev.dao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.eclipse.microprofile.opentracing.Traced;

import br.com.stefanini.maratonadev.model.Todo;

@RequestScoped
@Traced
public class TodoDao {
	
	@PersistenceContext
	EntityManager em;

	@Transactional
	public void inserir(Todo todo) {
		String nomeSql = "INSERIR_TODO";
		Query query = em.createNamedQuery(nomeSql);
		
		//query.setParameter("id", todo.getId());
		query.setParameter("nome", todo.getNome());
		query.setParameter("dataCriacao", todo.getDataCriacao());
		
		query.executeUpdate();
	}
	
	public List<Todo> listar() {
		//return Todo.listAll();
		List<Todo> listaRetorno;
		String nomeConsulta = "CONSULTAR_TODO";
		TypedQuery<Todo> query = em.createNamedQuery(nomeConsulta, Todo.class);
		try {
			listaRetorno = query.getResultList();
		} catch (Exception e) {
			listaRetorno = new ArrayList<>();
		}
		return listaRetorno;
	}
	
	@Transactional
	public void excuir(Long id) {
		String nomeSql = "EXCLUIR_TODO";
		Query query = em.createNamedQuery(nomeSql);
		query.setParameter("id", id);		
		query.executeUpdate();
	}
	
	public Boolean isNomeRepetido(String nome) {
		String nomeSql = "CONSULTAR_NOME_REPETIDO_TODO";
		Boolean nomeRepetido = Boolean.FALSE;
		TypedQuery<Todo> query = em.createNamedQuery(nomeSql, Todo.class);
		query.setParameter("nome", nome);
		nomeRepetido = query.getResultList().size() > 0;
		return nomeRepetido;
	}
	
	public Todo buscarPorId(Long id) {
		String nomeSql = "CONSULTAR_TODO_ID";
		Todo todo;
		TypedQuery<Todo> query = em.createNamedQuery(nomeSql, Todo.class);
		query.setParameter("id", id);
		try {
			todo = query.getSingleResult();
		} catch (NoResultException e) {
			todo = null;
		}
		return todo;
	}
	
}
