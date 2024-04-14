package com.rminaya.reportms.repositories;

import com.rminaya.reportms.models.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
@Slf4j
public class CompaniesFallbackRepository {
    private final WebClient webClient;
    private final String uri;

    /*
     * Constructor manual ya que la "inyección por dependencia" del atributo "uri" será con la anotación "@Value"
     *
     * ${fallback.uri}
     * Es obtenida del archivo de propiedades "report-ms.yml" en GitHub del "servidor de configuraciones"
     */
    public CompaniesFallbackRepository(WebClient webClient, @Value("${fallback.uri}") String uri) {
        this.webClient = webClient;
        this.uri = uri;
    }

    // Método que hal ser invocada hará el consumo el API
    public Company getByName(String name) {

        log.warn("Calling companies fallback {}", this.uri);

        return this.webClient
                .get() // indicamos que es un método http GET
                .uri(this.uri, name) // Primer parámetro es el endpoint y el segundo el "path variable" en caso se requiera
                .accept(MediaType.APPLICATION_JSON) // indicamos que recibiremos un JSON en el BODY
                .retrieve() // Con esto realizamos la petición
                .bodyToMono(Company.class) // Indicamos que se recibirá un solo objeto, es decir un "Mono", y se va a  mapear/convertir a una clase "Company"
                .log() // Ayuda a llevar toda la traza a logs
                .block(); // Indicamos que se trabajará de forma normal y NO reactiva

    }

}
