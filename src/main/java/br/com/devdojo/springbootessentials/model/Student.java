package br.com.devdojo.springbootessentials.model;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Student extends AbstractEntity{

	private static final long serialVersionUID = -7758951475213521717L;
	
	@NotEmpty
	private String name;
	
	@Email
	@NotEmpty
	private String email;
	
	public Student() {
	}

	public Student(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	public Student(Integer id, String name, String email) {
		this.setId(id);
		this.name = name;
		this.email = email;
	}
	
}
