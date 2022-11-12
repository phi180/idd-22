package it.uniroma3.idd.web4.domain.repository;

import it.uniroma3.idd.web4.domain.model.Rule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends CrudRepository<Rule, Long> {

}
