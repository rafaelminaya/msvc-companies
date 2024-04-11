package com.rminaya.reportms.services;

import com.rminaya.reportms.helpers.ReportHelper;
import com.rminaya.reportms.models.Company;
import com.rminaya.reportms.models.WebSite;
import com.rminaya.reportms.repositories.CompaniesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final CompaniesRepository companiesRepository;
    private final ReportHelper reportHelper;


    @Override
    public String makeReport(String name) {

        return this.reportHelper.readTemplate(
                this.companiesRepository.getByName(name).orElseThrow()
        );
    }

    @Override
    public String saveReport(String report) {

        // Indicamos que el formato de fechá a usar será  "dd/MM/yyyy"
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Arreglo que cotiene cada palabra que estaba entre las llaves "{" y "}"
        List<String> placeHolders = this.reportHelper.getPlaceHoldersFromTemplate(report);
        // Convirtiendo a un arreglo de websites lo que se encuentra en la posición 3 del arreglo de "placeHolders"
        List<WebSite> webSites = Stream.of(placeHolders.get(3))
                .map(website -> WebSite.builder()
                        .name(website)
                        .build()
                )
                .toList();

        // Creamos el nuevo company
        Company companytoPersist = Company.builder()
                .name(placeHolders.get(0))
                .foundationDate(LocalDate.parse(placeHolders.get(1), format)) // parseamos al tipo "LocalDate" el formato dd/MM/yyyy"
                .founder(placeHolders.get(2))
                .webSites(webSites)
                .build();
        // Creamos un nuevo company con el "web client"
        this.companiesRepository.postByName(companytoPersist);

        return "Saved";
    }

    @Override
    public void deleteReport(String name) {
        this.companiesRepository.deleteByName(name);
    }
}
