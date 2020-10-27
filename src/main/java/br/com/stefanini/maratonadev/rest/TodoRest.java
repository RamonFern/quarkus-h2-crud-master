package br.com.stefanini.maratonadev.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import br.com.stefanini.maratonadev.model.Todo;
import br.com.stefanini.maratonadev.service.TodoService;

@Path("todo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoRest {
	
	@Inject
	TodoService service;
	
	@GET
	@Path("")
	@Operation(summary = "Listar listas a fazer",
	description = "Retorna uma lista de Todo.class")
	@APIResponse(responseCode = "200", description = "lista de tarefas",
	content = {
			@Content(mediaType = "application/json", 
					schema = @Schema(implementation = Todo.class, type = SchemaType.ARRAY))
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
					schema = @Schema(implementation = Todo.class))
	})
	public Response incluir(Todo todo) {
		service.inserir(todo);
		return Response
				.status(Response.Status.CREATED)
				.build();
	}
	

}
