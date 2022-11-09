package it.uniroma3.idd.web4.domain.repository;

import it.uniroma3.idd.web4.domain.model.XPathExpressionsFamily;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XPathExpressionsFamilyRepository extends CrudRepository<XPathExpressionsFamily, Long> {
}
