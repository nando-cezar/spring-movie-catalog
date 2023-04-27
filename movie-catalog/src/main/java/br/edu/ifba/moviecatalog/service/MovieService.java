package br.edu.ifba.moviecatalog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifba.moviecatalog.domain.dto.request.MovieRequestDTO;
import br.edu.ifba.moviecatalog.domain.dto.response.MovieResponseDTO;
import br.edu.ifba.moviecatalog.repository.MovieRepository;

@Service
public class MovieService {
    
    @Autowired
    private MovieRepository repository;

    public MovieResponseDTO save(MovieRequestDTO entity) {
        var dataSaved = repository.save(entity.toEntity());
        return MovieResponseDTO.toDto(dataSaved);
    }

    public Optional<List<MovieResponseDTO>> find(String name, Pageable pageable) {
        if(name == null){
            var data = MovieResponseDTO.toListDTO(repository.findAll(pageable).toList());
            return Optional.of(data);
        }
        var data = MovieResponseDTO.toListDTO(repository.findByNameContains(name, pageable));
        return Optional.of(data);
    }

    public Optional<MovieResponseDTO> findById(Long id) {
        return repository.findById(id).map(MovieResponseDTO::new);
    }

    public MovieResponseDTO update(Long id, MovieRequestDTO entity) {
        var data = entity.toEntity();
        data.setId(id);
        var dataUpdated = repository.save(data);
        return MovieResponseDTO.toDto(dataUpdated);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}    
