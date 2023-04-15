package br.edu.ifba.moviecatalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.moviecatalog.domain.dto.request.MovieRequestDTO;
import br.edu.ifba.moviecatalog.domain.dto.response.MovieResponseDTO;
import br.edu.ifba.moviecatalog.service.MovieService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping(path = "/movies")
public class MovieController {
    
    @Autowired
    private MovieService service;

    @PostMapping
    public ResponseEntity<MovieResponseDTO> save(@RequestBody MovieRequestDTO data){
        var dataConverted = service.save(data);
        return new ResponseEntity<MovieResponseDTO>(dataConverted, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> find(@RequestParam(required = false) String name){

        var list = service.find(name);
        
        if(list.isEmpty()){
            return new ResponseEntity<List<MovieResponseDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<MovieResponseDTO>>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> findById(@PathVariable Long id){
        return service.findById(id)
        .map(record -> {
            return ResponseEntity.ok().body(record);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<MovieResponseDTO> udpate(@PathVariable Long id, @RequestBody MovieRequestDTO data){

        return service.findById(id)
        .map(record -> {
            var dataSaved = service.save(id, data);
            return ResponseEntity.ok().body(dataSaved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity<MovieResponseDTO> delete(@PathVariable Long id){

        return service.findById(id)
        .map(record -> {
            var data = record;
            service.deleteById(id);
            return ResponseEntity.ok().body(data);
        }).orElse(ResponseEntity.notFound().build());
    }
    
}
