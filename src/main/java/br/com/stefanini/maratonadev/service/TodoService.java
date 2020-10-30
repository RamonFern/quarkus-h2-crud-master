package br.com.stefanini.maratonadev.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.opentracing.Traced;

import br.com.stefanini.maratonadev.dao.TodoDao;
import br.com.stefanini.maratonadev.dto.TodoDto;
import br.com.stefanini.maratonadev.model.Todo;
import br.com.stefanini.maratonadev.model.dominio.StatusEnum;
import br.com.stefanini.maratonadev.model.parser.TodoParser;


@RequestScoped
//@ApplicationScoped
@Traced
public class TodoService {

	@Inject	TodoDao dao;
	
	@Inject
	TodoStatusService statusService;
	
	private void validar(Todo todo) {
		//validar regra de negocio
		
		if(dao.isNomeRepetido(todo.getNome())) {
			throw new NotFoundException();
		}
	}
	
	/*REGRA DE CRIAÇÃO:
	 * Toda tarefa criada vem por padrão na lista TODO e com a data corrente.
	 * */
	
	@Transactional(rollbackOn = Exception.class)
	public void inserir(@Valid TodoDto todoDto, String emailLogado) {
		//validacao
		Todo todo = TodoParser.get().entidade(todoDto);
		validar(todo);
	
		//chamada do dao
		Long id = dao.inserir(todo);
		statusService.inserir(id, StatusEnum.TODO, emailLogado);
	}
	
	
	public List<TodoDto> listar() {
		return dao.listar()
				  .stream()
				  .map(TodoParser.get()::dto)
				  .collect(Collectors.toList());
	}
	
	public void excluir(Long id) {
		//DESAFIO: Validar se id é válido
		if(dao.buscarPorId(id) == null) {
			throw new NotFoundException();
		}
		dao.excuir(id);
	}

	public TodoDto buscar(Long id) {
		return TodoParser.get().dto(buscarPorId(id));
	}

	@Transactional(rollbackOn = Exception.class)
	public void atualizar(Long id, TodoDto dto, String emailLogado) {
		Todo todo = TodoParser
						.get()
						.entidade(dto);
		Todo todoBanco = buscarPorId(id);
		todoBanco.setNome(todo.getNome());
		dao.atualizar(todoBanco);
		statusService.atualizar(id, dto.getStatus(), emailLogado);
	}
	
	private Todo buscarPorId(Long id) {
		Todo todo = dao.buscarPorId(id);
		if(todo == null) {
			throw new NotFoundException();
		}
		return todo;
	}

}
