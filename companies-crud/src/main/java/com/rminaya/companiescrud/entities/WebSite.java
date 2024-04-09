package com.rminaya.companiescrud.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class WebSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(columnDefinition = "category") // indicamos qué columna representa
    @Enumerated(value = EnumType.STRING) // permite hacer el mapao de este enum como un String
    private Category category;
}
