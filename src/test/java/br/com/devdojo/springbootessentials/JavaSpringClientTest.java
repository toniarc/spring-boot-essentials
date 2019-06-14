package br.com.devdojo.springbootessentials;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.devdojo.springbootessentials.model.CustomPageImpl;
import br.com.devdojo.springbootessentials.model.Student;

public class JavaSpringClientTest {

	private RestTemplate rest;
	
	@Before
	public void before() {
		rest = new RestTemplateBuilder().rootUri("http://localhost:8080")
				.basicAuthentication("antonio.junior", "123456")
				.build();
	}
	
	@Test
	public void findOneForObjet() {
		Student student = rest.getForObject("/students/{id}", Student.class, 1);
		System.out.println(student);
	}
	
	@Test
	public void findOneForEntity() {
		ResponseEntity<Student> entity = rest.getForEntity("/students/{id}", Student.class, 1);
		System.out.println(entity);
		System.out.println(entity.getBody());
	}
	
	@Test
	public void listAllExchange() {
		ResponseEntity<CustomPageImpl<Student>> exchange = rest.exchange("/students", HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<Student>>() {});
		System.out.println(exchange.getBody());
	}
	
	@Test
	public void postExchange() {
		Student student = new Student();
		student.setName("Antonio Junior");
		student.setEmail("antonio.junior@prodepa.pa.gov.br");
		
		ResponseEntity<Student> exchange = rest.exchange("/students", HttpMethod.POST, new HttpEntity<>(student), Student.class);
		System.out.println(exchange.getBody());
	}
}
