package br.com.devdojo.springbootessentials.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomPageImpl<T> {

	private List<T> content;
	private Pageable pageable;

	private int totalPages;
	private long totalElements;
	private boolean last;
	private boolean first;
	private Sort sort;
	
	private int numberOfElements;
	private int size;
	private int number;
    private boolean empty;


    public CustomPageImpl() {
	}
}

@Getter
@Setter
class Pageable {
	private Sort sort;
	private int pageSize;
	private int pageNumber;
	private int offset;
	private boolean unpaged;
	private boolean paged;
	
	public Pageable() {
	}
}

@Getter
@Setter
class Sort {

	private boolean sorted;
	private boolean unsorted;
	private boolean empty;
	
	public Sort() {
	}
}