package br.com.stefanini.maratonadev.rest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import br.com.stefanini.maratonadev.dto.TodoDto;
import br.com.stefanini.maratonadev.service.TodoService;

@Path("todo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"USER","ADMIN"})
public class TodoRest {
	
	@Inject
	TodoService service;
	@Inject
	Validator validator;
	
	@GET
	@Path("")
	@Operation(summary = "Listar listas a fazer",
	description = "Retorna uma lista de Todo.class")
	@APIResponse(responseCode = "200", description = "lista de tarefas",
	content = {
			@Content(mediaType = "application/json", 
					schema = @Schema(implementation = TodoDto.class, type = SchemaType.ARRAY))
	})
	public Response listar() {
		return Response
				.status(Response.Status.OK)
				.entity(service.listar())
				.build();
	}
	
	@POST
	@Path("")
	@Operation(summary = "Incluir uma tarefa",
	description = "Incluir uma tarefa")
	@APIResponse(responseCode = "201", description = "inserir uma tarefas",
	content = {
			@Content(mediaType = "application/json", 
					schema = @Schema(implementation = TodoDto.class))
	})
	public Response incluir(TodoDto todo, @Context SecurityContext securityContext ) {
		Set<ConstraintViolation<TodoDto>> erros = validator.validate(todo);
		
		if(erros.isEmpty()) {
			service.inserir(todo, securityContext.getUserPrincipal().getName());
		}else {
			List<String> listaErros = erros
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.toList());
//			listaErros.forEach(i -> {
//				System.out.println(i);
//			});
			throw new NotFoundException(listaErros.get(0));
		}
		
		return Response
				.status(Response.Status.CREATED)
				.build();
	}
	
	@DELETE
	@Path("/{id}")
	@RolesAllowed({"ADMIN"})
	@Operation(summary = "excluir uma tarefa",
	description = "excluir uma tarefa")
	@APIResponse(responseCode = "200", 
	description = "excluir uma tarefas",
	content = {
			@Content(mediaType = "application/json", 
			schema = @Schema(implementation = TodoDto.class))
	})
	public Response excluir(@PathParam("id") Long id) {
		service.excluir(id);
		return Response
				.status(Response.Status.ACCEPTED)
				.build();
	}
	
	@GET
	@Path("/{id}")
	@Operation(summary = "buscar uma tarefa por id",
	description = "buscar uma tarefa por id")
	@APIResponse(responseCode = "200", 
	description = "burcar tarefas por id",
	content = {
			@Content(mediaType = "application/json", 
			schema = @Schema(implementation = TodoDto.class))
	})
	public Response buscarPorId(@PathParam("id") Long id) {
		return Response.status(Response.Status.OK).entity(service.buscar(id)).build();
	}
	
	@PUT
	@Path("/{id}")
	@Operation(summary = "editar uma tarefa com base no id",
	description = "editar uma tarefa por base no id")
	@APIResponse(responseCode = "200", 
	description = "editar tarefas buscando primeiro pelo id",
	content = {
			@Content(mediaType = "application/json", 
			schema = @Schema(implementation = TodoDto.class))
	})
	public Response atualizar(@PathParam("id") Long id, TodoDto todoDto,
			@Context SecurityContext securityContext) {
		service.atualizar(id, todoDto, securityContext.getUserPrincipal().getName());
		return Response.status(Response.Status.OK).build();
	}
	

}
