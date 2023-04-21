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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping(path = "/movies")
@Tag(name = "Movies")
public class MovieController {
    
    @Autowired
    private MovieService service;

    @PostMapping
    @Operation(summary = "Save only one movie")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201", 
                description = "Saved with success", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MovieResponseDTO.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "406", 
                description = "Not Acceptable", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MovieResponseDTO.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<MovieResponseDTO> save(@Parameter(description = "New movie body content to be created") @RequestBody MovieRequestDTO data){
        var dataConverted = service.save(data);
        return new ResponseEntity<MovieResponseDTO>(dataConverted, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retrieve all movies with or without filter")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Retrieval of successful movies", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MovieResponseDTO.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MovieResponseDTO.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<List<MovieResponseDTO>> find(@Parameter(description = "Title for movie to be found (optional)") @RequestParam(required = false) String name){
        var data = service.find(name).get();
        var isExists = data.isEmpty();
        return isExists ? 
            ResponseEntity.notFound().build() : 
            ResponseEntity.ok().body(data);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve movie by id")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Retrieval of successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MovieResponseDTO.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MovieResponseDTO.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<MovieResponseDTO> findById(@Parameter(description = "Movie Id to be searched") @PathVariable Long id){
        return service.findById(id)
            .map(record ->  ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
	@Transactional
    @Operation(summary = "Update only one movie")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Updated with successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MovieResponseDTO.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MovieResponseDTO.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<MovieResponseDTO> update(@Parameter(description = "Movie Id to be updated") @PathVariable Long id, @Parameter(description = "Movie Elements/Body Content to be updated") @RequestBody MovieRequestDTO data){

        return service.findById(id)
        .map(record -> {
            var dataSaved = service.update(id, data);
            return ResponseEntity.ok().body(dataSaved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
	@Transactional
    @Operation(summary = "Delete only one movie")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Deleted with successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MovieResponseDTO.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MovieResponseDTO.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<MovieResponseDTO> delete(@Parameter(description = "Movie Id to be deleted") @PathVariable Long id){

        return service.findById(id)
        .map(record -> {
            var data = record;
            service.deleteById(id);
            return ResponseEntity.ok().body(data);
        }).orElse(ResponseEntity.notFound().build());
    }
    
}
