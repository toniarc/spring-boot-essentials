package br.com.devdojo.springbootessentials;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.devdojo.springbootessentials.model.Student;
import br.com.devdojo.springbootessentials.repository.StudentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentEndpointTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@MockBean
	private StudentRepository studentRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@TestConfiguration
	static class Config {
		@Bean
		public RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder().basicAuthentication("antonio.junior", "123456");
		}
	}
	
	@Test
	public void listStudentsWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401() {
		restTemplate = restTemplate.withBasicAuth("1", "21");
		ResponseEntity<String> entity = restTemplate.getForEntity("/students", String.class);
		Assertions.assertThat(entity.getStatusCodeValue()).isEqualTo(401);
	}
	
	@Test
	public void getStudentsByIdWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401() {
		restTemplate = restTemplate.withBasicAuth("1", "21");
		ResponseEntity<String> entity = restTemplate.getForEntity("/students/1", String.class);
		Assertions.assertThat(entity.getStatusCodeValue()).isEqualTo(401);
	}
	
	@Test
	public void listStudentsWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() {
		
		List<Student> students = Arrays.asList(new Student(1, "teste1", "teste1@teste.com"),
				new Student(2, "teste2", "teste2@teste.com"),
				new Student(3, "teste3", "teste3@teste.com"));
		
		BDDMockito.when(studentRepository.findAll()).thenReturn(students);
		
		ResponseEntity<Student[]> entity = restTemplate.getForEntity("/students", Student[].class);
		Assertions.assertThat(entity.getStatusCodeValue()).isEqualTo(200);
		Assertions.assertThat(students).extracting("name").contains("teste1", "teste2", "teste3");
	}
	
	@Test
	public void getStudentsShouldReturnStatusCode200() {
		
		Optional<Student> student = Optional.ofNullable(new Student(1, "teste1", "teste1@teste.com"));
		
		BDDMockito.when(studentRepository.findById(1)).thenReturn(student);
		
		ResponseEntity<Student> entity = restTemplate.getForEntity("/students/{id}", Student.class, 1);
		Assertions.assertThat(entity.getStatusCodeValue()).isEqualTo(200);
		Assertions.assertThat(student).isPresent();
		Assertions.assertThat(student.get().getName()).isEqualTo("teste1");
	}
}
