package br.com.devdojo.springbootessentials.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Getter(value=AccessLevel.PUBLIC)
@SuperBuilder
public class ErrorDetail {

	private String title;
	private int status;
	private String detail;
	private Long timesamp;
	private String developerMessage;
}
