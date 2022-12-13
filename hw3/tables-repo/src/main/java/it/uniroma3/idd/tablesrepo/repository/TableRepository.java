package it.uniroma3.idd.tablesrepo.repository;

import it.uniroma3.idd.tablesrepo.model.TableMentor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends CrudRepository<TableMentor, Long> {

    @Query("SELECT tm FROM TableMentor tm WHERE tm.oid = ?1")
    List<TableMentor> findByOid(String oid);

}
