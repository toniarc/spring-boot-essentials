package br.com.devdojo.springbootessentials;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.devdojo.springbootessentials.model.Student;
import br.com.devdojo.springbootessentials.repository.StudentRepository;

@DataJpaTest
@RunWith(SpringRunner.class)
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {

	@Autowired
	private StudentRepository repo;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void createShouldPersistData() {
		Student student = new Student("Antonio Junior", "antonio.junior@prodepa.pa.gov.br");
		Student student2 = repo.save(student);
		Assertions.assertThat(student2.getId()).isNotNull();
		Assertions.assertThat(student2.getName()).isEqualTo("Antonio Junior");
		Assertions.assertThat(student2.getEmail()).isEqualTo("antonio.junior@prodepa.pa.gov.br");
	}
	
	@Test
	public void deleteShouldRemoveData() {
		Student student = new Student("Antonio Junior", "antonio.junior@prodepa.pa.gov.br");
		Student student2 = repo.save(student);
		repo.delete(student2);
		
		Assertions.assertThat(repo.findById(student2.getId())).isNotPresent();
	}
	
	@Test
	public void updateShouldPersistAndChangeData() {
		Student student = new Student("Antonio Junior", "antonio.junior@prodepa.pa.gov.br");
		repo.save(student);
		
		student.setName("Antonio R C Junior");
		Student student2 = repo.save(student);
		
		Assertions.assertThat(student2.getId()).isNotNull();
		Assertions.assertThat(student2.getName()).isEqualTo("Antonio R C Junior");
		Assertions.assertThat(student2.getEmail()).isEqualTo("antonio.junior@prodepa.pa.gov.br");
	}
	
	@Test
	public void searchShouldReadAllData() {
		
		Iterable<Student> students = repo.findAll();
		
		Assertions.assertThat(students).isNotNull();
		Assertions.assertThat(students).size().isEqualTo(100);
	}
	
	@Test
	public void searchShouldReadData() {
		Student student1 = new Student("Antonio Junior", "antonio.junior@prodepa.pa.gov.br");
		Student student2 = new Student("ANTONIO Junior", "antonio.junior@prodepa.pa.gov.br");
		
		repo.save(student1);
		repo.save(student2);
		
		List<Student> students = repo.findByNameContainingIgnoreCase("Antonio");
		
		Assertions.assertThat(students).isNotNull();
		Assertions.assertThat(students.size()).isEqualTo(2);
	}
	
	@Test
	public void createWhenNameIsNullShouldThrowConstraintViolationException() {
		
		thrown.expect(ConstraintViolationException.class);
		
		Student student = new Student(null, "antonio.junior@prodepa.pa.gov.br");
		repo.save(student);

	}
	
	@Test
	public void createWhenEmailIsNullShouldThrowConstraintViolationException() {
		
		thrown.expect(ConstraintViolationException.class);
		
		Student student = new Student("Antonio Junior", null);
		repo.save(student);

	}
	
	@Test
	public void createWhenEmailIsNotValidShouldThrowConstraintViolationException() {
		
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("must be a well-formed email address");
		
		Student student = new Student("Antonio Junior", "antonio.junior");
		repo.save(student);

	}
}
