package br.edu.ifba.moviecatalog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifba.moviecatalog.domain.dto.request.MovieRequestDTO;
import br.edu.ifba.moviecatalog.domain.dto.response.MovieResponseDTO;
import br.edu.ifba.moviecatalog.repository.MovieRepository;

@Service
public class MovieService {
    
    @Autowired
    private MovieRepository repository;

    public MovieResponseDTO save(MovieRequestDTO entity) {
        return MovieResponseDTO.toDto(repository.save(entity.toEntity()));
    }

    public MovieResponseDTO save(Long id, MovieRequestDTO entity) {
        var data = entity.toEntity();
        data.setId(id);
        return MovieResponseDTO.toDto(repository.save(data));
    }

    public List<MovieResponseDTO> find(String name) {
        if(name == null){
            return  MovieResponseDTO.toListDTO(repository.findAll());
        }
        return MovieResponseDTO.toListDTO(repository.findByNameContains(name));
    }

    public Optional<MovieResponseDTO> findById(Long id) {
        return repository.findById(id).map(MovieResponseDTO::new);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}    
