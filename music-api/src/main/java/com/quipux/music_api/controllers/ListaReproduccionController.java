package com.quipux.music_api.controllers;

import com.quipux.music_api.entities.ListaReproduccion;
import com.quipux.music_api.services.ListaReproduccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.net.URLEncoder;

@RestController
@RequestMapping("/lists")
@CrossOrigin(origins = "http://localhost:4200")
public class ListaReproduccionController {


    @Autowired
    private ListaReproduccionService listaService;

    @PostMapping
    public ResponseEntity<ListaReproduccion> crearLista(@RequestBody ListaReproduccion lista){
        try {
            if (lista.getNombre() == null || lista.getNombre().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            ListaReproduccion nuevaLista = listaService.crearLista(lista);

            String encodedNombre = URLEncoder.encode(nuevaLista.getNombre(), "UTF-8");
            URI location = URI.create("/lists/" + encodedNombre);

            return ResponseEntity.created(location).body(nuevaLista);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping
    public ResponseEntity<List<ListaReproduccion>> obtenerListas(){
        List<ListaReproduccion> listas = listaService.obtenerListas();
        if (listas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(listas);
    }

    @GetMapping("/{listName}")
    public ResponseEntity<ListaReproduccion> obtenerLista(@PathVariable String listName){
        Optional<ListaReproduccion> lista = listaService.obtenerListaPorNombre(listName);
        if (lista.isPresent()) {
            return ResponseEntity.ok(lista.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> eliminarLista(@PathVariable String listName){

        if (listaService.obtenerListaPorNombre(listName).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        listaService.eliminarLista(listName);
        return ResponseEntity.noContent().build();
    }
}
