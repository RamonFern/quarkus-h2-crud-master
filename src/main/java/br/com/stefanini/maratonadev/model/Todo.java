package br.com.stefanini.maratonadev.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.validation.constraints.PastOrPresent;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "todo")
@NamedNativeQueries({ 
	@NamedNativeQuery(name = "CONSULTAR_TODO", query = ""
		+ "SELECT id, nome, dataCriacao FROM todo", resultClass = Todo.class),
	@NamedNativeQuery(name = "INSERIR_TODO", query = ""
		+ "INSERT INTO todo (nome, dataCriacao) values "
		+ "(:nome, :dataCriacao)"),
	@NamedNativeQuery(name = "EXCLUIR_TODO", query = "DELETE todo WHERE id = :id")
})
public class Todo extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @CreationTimestamp
	@PastOrPresent
	private LocalDateTime dataCriacao;

	@Column(name = "nome", length = 250, nullable = false)
	private String nome;

	public Todo() {

	}

	public Todo(Long id, LocalDateTime dataCriacao, String nome) {
		this.id = id;
		this.dataCriacao = dataCriacao;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
