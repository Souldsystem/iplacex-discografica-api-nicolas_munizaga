package org.iplacex.proyectos.discografia.discos;
import org.iplacex.proyectos.discografia.artistas.Artista;
import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
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
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepo;
    
    @Autowired
    private IArtistaRepository artistaRepo;

    @PostMapping(value = "/disco", 
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Disco> HandlePostDiscoRequest(@RequestBody Disco disco) {
     
Optional<Artista> verif = artistaRepo.findById(disco.idArtista); 

    if(!verif.isPresent()){
        return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);
       }
        Disco temp = discoRepo.insert(disco);
        return new ResponseEntity<>(temp, null, HttpStatus.CREATED);
    }

    @GetMapping(value = "/discos",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        List<Disco> temp = discoRepo.findAll();
        return new ResponseEntity<>(temp, null, HttpStatus.OK);
    }

    @GetMapping(value = "/disco/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandleGetDiscoRequest(@PathVariable String id) {
        Optional<Disco> temp = discoRepo.findById(id);

        if (!temp.isPresent()) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(temp.get(), null, HttpStatus.OK);
    }

    @GetMapping(value = "/artista/{id}/discos",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {
        List<Disco> temp = discoRepo.findDiscosByIdArtista(id);
        return new ResponseEntity<>(temp, null, HttpStatus.OK);
    }

    @PutMapping(value = "/disco/{id}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandleUpdateDiscoRequest(@PathVariable String id, @RequestBody Disco disco) {
        if (!discoRepo.existsById(id)) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        } else {
            disco._id = id; 
            Disco temp = discoRepo.save(disco);
            return new ResponseEntity<>(temp, null, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/disco/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Disco> HandleDeleteDiscoRequest(@PathVariable("id") String id) {
        if (!discoRepo.existsById(id)) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        } else {
            Disco temp = discoRepo.findById(id).get();
            discoRepo.deleteById(id);
            return new ResponseEntity<>(temp, null, HttpStatus.OK);
        }
    }
}
