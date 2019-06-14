package br.com.devdojo.springbootessentials.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotfoundException extends RuntimeException{

	private static final long serialVersionUID = -6175290711300280908L;

	public ResourceNotfoundException(String message) {
		super(message);
	}
}
