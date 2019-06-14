package br.com.devdojo.springbootessentials.error;

import java.util.List;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationErrorDetails extends ErrorDetail {

	private List<FieldError> erros;
	
	public ValidationErrorDetails(String title, int status, String detail, Long timesamp, String developerMessage, List<FieldError> erros) {
		super(title, status, detail, timesamp, developerMessage);
		this.erros = erros;
	}

}
