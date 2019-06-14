package br.com.devdojo.springbootessentials.error;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ResourceNotFoundDetails extends ErrorDetail {

	public ResourceNotFoundDetails(String title, int status, String detail, Long timesamp, String developerMessage) {
		super(title, status, detail, timesamp, developerMessage);
	}

}
