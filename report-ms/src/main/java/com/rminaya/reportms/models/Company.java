package com.rminaya.reportms.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
// Permite crear un constructor sin argumentos, recomendado para la correcta deserializarse de un JSON
@AllArgsConstructor // @Builder requiere que haya un constructor con todos los argumentos
public class Company implements Serializable {

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

    private List<WebSite> webSites;

}
