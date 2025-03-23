package com.quipux.music_api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ListaReproduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Column(nullable = false, unique = true)
    private String nombre;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private String descripcion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cancion> canciones;
}
