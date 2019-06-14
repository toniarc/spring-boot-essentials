package br.com.devdojo.springbootessentials.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="USER_CA")
public class UserCA extends AbstractEntity {

	private static final long serialVersionUID = 6783369847696578287L;

	@NotEmpty
	@Column(unique=true)
	private String login;
	
	@NotEmpty
	@JsonIgnore
	private String password;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private boolean admin;
}
