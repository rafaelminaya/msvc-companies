package com.rminaya.reportms.helpers;

import com.rminaya.reportms.models.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ReportHelper {
    @Value("${report.template}")
    private String reportTemplate;

    // Retorna la cadena (de lo obtenido del "archivo de propiedades" del repositorio remoto) pero reemplazando cada valor por los obtenido del argumento
    public String readTemplate(Company company) {

        return this.reportTemplate
                .replace("{company}", company.getName())
                .replace("{foundation_date}", company.getFoundationDate().toString())
                .replace("{founder}", company.getFounder())
                .replace("{web_sites}", company.getWebSites().toString());
    }

    // Método para extraer y conviertir en un arreglo de String, cada palabra de la cadena recibida
    public List<String> getPlaceHoldersFromTemplate(String template) {
        // Almacena en un arreglo de String, cada cadena que empiece con la llave de apertura "{"
        String[] arrayCaracteres = template.split("\\{");
        // Iteramos el arreglo para obtener solamente las palarbas entre llaves "{" y "}"
        return Arrays.stream(arrayCaracteres)
                .filter(cadena -> !cadena.isEmpty()) // filtrado de las lineas que no esten vacías
                .map(linea -> {
                    // Obtenemos el indice de cada arreglo cuyo valor termine con la llave de cierre "}"
                    int index = linea.indexOf("}");
                    // Asignamos a cada linea, solo el valor entre las llaves "{" y "}"
                    return linea.substring(0, index);
                })
                .collect(Collectors.toList());
    }
}
