package com.apiJornada.Milhas.controller;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.apiJornada.Milhas.domain.destination.CreateDestinationDto;
import com.apiJornada.Milhas.domain.destination.Destination;
import com.apiJornada.Milhas.domain.destination.DestinationRepository;
import com.apiJornada.Milhas.domain.destination.DetailDestinationDto;
import com.apiJornada.Milhas.domain.destination.ListDestinationDto;
import com.apiJornada.Milhas.domain.destination.UpdateDestinationDto;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("destinos")
@CrossOrigin("http://127.0.0.1:5500")
public class DestinationController {

  @Autowired
  private DestinationRepository repositoryDestination;

  @PostMapping(consumes = { "multipart/form-data" })
  @Transactional
  public ResponseEntity<String> createDestination(@Valid CreateDestinationDto dto, UriComponentsBuilder uriBuilder)
      throws IOException {
    var destination = new Destination(dto);
    repositoryDestination.save(destination);

    var uri = uriBuilder.path("/destinos/{id}").buildAndExpand(destination.getId()).toUri();

    return ResponseEntity.created(uri).build();
  }

  // @GetMapping
  // public ResponseEntity<Page<ListDestinationDto>> filterListDestination(
  //     @PageableDefault(size = 10, sort = { "name" }) Pageable pagination) {
  //   var dto = repositoryDestination.findAllByActiveTrue(pagination);

  //   return ResponseEntity.ok(dto);
  // }
  @GetMapping
  public ResponseEntity<Page<ListDestinationDto>> listDestination(
      @PageableDefault(size = 10, sort = { "name" }) Pageable pagination) {
    var dto = repositoryDestination.findAllByActiveTrue(pagination);

    return ResponseEntity.ok(dto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ListDestinationDto> searchDestinationById(@PathVariable Long id) {
    var entity = repositoryDestination.findByIdAndActiveTrue(id);

    return ResponseEntity.ok(new ListDestinationDto(entity));
  }

  @GetMapping("/detail")
  // @Secured({ "ROLE_ADMIN", "ROLE_MANAGER" })
  public ResponseEntity<Page<DetailDestinationDto>> detailDestination(
      @PageableDefault(size = 10, sort = { "name" }) Pageable pagination) {
    var dto = repositoryDestination.findAll(pagination).map(DetailDestinationDto::new);

    return ResponseEntity.ok(dto);
  }

    @PutMapping(consumes = { "multipart/form-data" })
  @Transactional
  public ResponseEntity<ListDestinationDto> updateDestination(@Valid UpdateDestinationDto updateDto)
  throws IOException {
    var destination = repositoryDestination.findById(updateDto.id()).get();
    destination.update(updateDto);
    
    return ResponseEntity.ok().body(new ListDestinationDto(destination));
  }

  @DeleteMapping("/{id}")
  // @Secured({ "ROLE_ADMIN", "ROLE_MANAGER" })
  @Transactional
  public ResponseEntity<String> deleteDestination(@PathVariable Long id) throws IOException {
    var destination = repositoryDestination.findById(id).get();
    destination.disable();

    return ResponseEntity.noContent().build();
  }

}
