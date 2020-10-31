package br.com.stefanini.maratonadev;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class TodoRestTest {

	
	@Test
	@Order(1)
	public void testListarTodo() {
		
		 given()
		 .auth()
		 .preemptive()
		 .basic("ramonfernandes9@gmail.com", "sophiaraul")
         .when().get("/api/hello")
         .then()
            .statusCode(200)
            .body(is("em Vinda a Semana #3 :D")); 
            			
	}
	
	@Test
	@Order(2)
	public void testarIncluirTodoComSucesso() {

		String body = "{\r\n"
				+ " \"nome\": \"string\"\"\r\n"
				+ "}";
		 given()
		 .auth()
		 .preemptive()
		 .basic("ramonfernandes9@gmail.com", "sophiaraul")
		 .contentType(ContentType.JSON)
		 .body(body)
         .when()
         .post("/api/todo")
         .then()
           .statusCode(201);
		
	}
}
