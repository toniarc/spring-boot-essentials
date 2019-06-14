package br.com.devdojo.springbootessentials.endpoint;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.devdojo.springbootessentials.error.ResourceNotfoundException;
import br.com.devdojo.springbootessentials.model.Student;
import br.com.devdojo.springbootessentials.repository.StudentRepository;

@RestController
@RequestMapping("students")
public class StudentEndpoint {
	
	@Autowired
	private StudentRepository repository;

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> listAll(Pageable pageable, 
									 @RequestParam(name="name", required=false) String name,
									 @AuthenticationPrincipal UserDetails userDetails){
		
		System.out.println(userDetails);
		
		if(name != null) {
			return new ResponseEntity<>(repository.findByNameContainingIgnoreCase(name), HttpStatus.OK);
		}
		return new ResponseEntity<>(repository.findAll(pageable), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping(path="/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable("id") Integer id){
		Optional<Student> findById = repository.findById(id);
		
		if(!findById.isPresent()) {
			throw new ResourceNotfoundException("Student not found for ID: " + id);
		}
		
		return new ResponseEntity<>(findById.get(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	@Transactional
	public ResponseEntity<?> save(@Valid @RequestBody Student student){ 
		repository.save(student);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping 
	public ResponseEntity<?> updateStudent(@RequestBody Student student){
		repository.save(student);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}
	
	@DeleteMapping(path="/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteStudent(@PathVariable("id") Integer id){
		try {
			repository.deleteById(id);
			return new ResponseEntity<>("User deleted", HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotfoundException("Student not found for ID: " + id);
		}
		
	}
}
