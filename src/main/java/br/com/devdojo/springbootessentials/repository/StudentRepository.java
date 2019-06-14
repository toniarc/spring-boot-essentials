package br.com.devdojo.springbootessentials.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.devdojo.springbootessentials.model.Student;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Integer>{

	List<Student> findByNameContainingIgnoreCase(String name);
}
