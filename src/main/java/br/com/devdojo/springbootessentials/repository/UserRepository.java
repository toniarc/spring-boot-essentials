package br.com.devdojo.springbootessentials.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.devdojo.springbootessentials.model.UserCA;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserCA, Integer>{

	UserCA findByLogin(String login);
}
