package com.rminaya.companiescrud.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String founder;
    private String logo;
    /*
     * Mapeo para que la fecha recibida de formato "dd/MM/yyyy" y de tipo String se transforme a "LocalDate"
     *
     * JsonFormat.Shape.STRING
     * Tipo de dato de la propiedad del request
     *
     * pattern = "dd/MM/yyyy"
     * Formato esperado de la propiedad
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate foundationDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "id_company", referencedColumnName = "id") // id_company es el nombre del campo en la BD y "id" es el atributo de "WebSite"
    private List<WebSite> webSites;

}
