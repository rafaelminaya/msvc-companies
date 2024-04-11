package com.rminaya.reportms.services;

public interface ReportService {

    // Método para crear el reporte buscando el nombre de la compañia
    String makeReport(String name);
    // Método para grabar el reporte, recibiendo el nombre del reporte
    String saveReport(String report);
    // Elimina el reporte a partir del nombre dado
    void deleteReport(String name);
}
