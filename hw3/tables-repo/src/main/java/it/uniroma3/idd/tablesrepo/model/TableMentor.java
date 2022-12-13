package it.uniroma3.idd.tablesrepo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(indexes = {@Index(name = "oid_index", columnList = "oid")})
public class TableMentor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String oid;

    @Column(columnDefinition = "TEXT")
    private String cells;

}
