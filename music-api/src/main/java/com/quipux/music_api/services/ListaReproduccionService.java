package com.quipux.music_api.services;

import com.quipux.music_api.entities.ListaReproduccion;
import com.quipux.music_api.repositories.ListaReproduccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListaReproduccionService {

    @Autowired
    private ListaReproduccionRepository listaRepo;

    public ListaReproduccion crearLista(ListaReproduccion lista){
        try {
            return listaRepo.save(lista);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la lista: " + e.getMessage(), e);
        }
    }

    public List<ListaReproduccion> obtenerListas(){
        return listaRepo.findAll();
    }

    public Optional<ListaReproduccion> obtenerListaPorNombre(String nombre){
        return listaRepo.findByNombre(nombre);
    }

    public void eliminarLista(String nombre){
        listaRepo.findByNombre(nombre).ifPresent(listaRepo::delete);
    }
}
