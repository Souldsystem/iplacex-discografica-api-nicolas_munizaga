package org.iplacex.proyectos.discografia.artistas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepo;

    @PostMapping(value = "/artista", 
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista temp = artistaRepo.insert(artista);

        return new ResponseEntity<>(temp, null, HttpStatus.CREATED);
    }

    @GetMapping(value = "/artistas",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {
        List<Artista> temp = artistaRepo.findAll();

   
        return new ResponseEntity<>(temp, null , HttpStatus.OK);
        
    }

    @GetMapping(value = "/artista/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandleGetArtistaRequest(@PathVariable String id) {
    Optional<Artista> temp = artistaRepo.findById(id); 

    if(!temp.isPresent()){

return new ResponseEntity<>(null , HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(temp.get(), null , HttpStatus.OK);
    }

    @PutMapping(value = "/artista/{id}",
     consumes = MediaType.APPLICATION_JSON_VALUE,
     produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Artista> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista artista) {
    
    return artistaRepo.findById(id)
    .map(existingArtista -> {
        existingArtista.setNombre(artista.getNombre());
        existingArtista.setEstilos(artista.getEstilos());
        existingArtista.setAnioFundacion(artista.getAnioFundacion());
        existingArtista.setEstaActivo(artista.isEstaActivo());
        Artista updatedArtista = artistaRepo.save(existingArtista);
        return new ResponseEntity<>(updatedArtista, HttpStatus.OK);
    })
    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
}


    @DeleteMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleDeleteArtistaRequest(@PathVariable("id") String id) {
        if (!artistaRepo.existsById(id)) {
            return new ResponseEntity<>(null,null , HttpStatus.NOT_FOUND);
       
        } else {

            Artista temp = artistaRepo.findById(id).get();         

            artistaRepo.deleteById(id);
            return new ResponseEntity<>(temp, null , HttpStatus.OK);
        }
    }
}
