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

import com.apiJornada.Milhas.domain.testimonial.CreateTestimonialDto;
import com.apiJornada.Milhas.domain.testimonial.DetailTestimonialDto;
import com.apiJornada.Milhas.domain.testimonial.ListTestimonialDto;
import com.apiJornada.Milhas.domain.testimonial.Testimonial;
import com.apiJornada.Milhas.domain.testimonial.TestimonialRepository;
import com.apiJornada.Milhas.domain.testimonial.UpdateTestimonialDto;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("depoimentos")
@CrossOrigin("http://127.0.0.1:5500")
public class TestimonialController {

  @Autowired
  private TestimonialRepository repositoryTestimonial;

  @PostMapping(consumes = { "multipart/form-data" })
  @Transactional
  public ResponseEntity<URI> createTestimonial(@Valid CreateTestimonialDto createDto, UriComponentsBuilder uriBuilder)
      throws IOException {
    var testimonial = new Testimonial(createDto);
    repositoryTestimonial.save(testimonial);

    var uri = uriBuilder.path("/depoimentos/{id}").buildAndExpand(testimonial.getId()).toUri();

    return ResponseEntity.created(uri).build();
  }

  @GetMapping
  public ResponseEntity<Page<ListTestimonialDto>> listTestimonial(
      @PageableDefault(size = 10, sort = { "name" }) Pageable pagination) {
    var dto = repositoryTestimonial.findAllByActiveTrue(pagination);

    return ResponseEntity.ok(dto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ListTestimonialDto> searchTestimonialById(@PathVariable Long id) {
    var entity = repositoryTestimonial.findByIdAndActiveTrue(id);

    return ResponseEntity.ok(new ListTestimonialDto(entity));
  }

  @GetMapping("/detail")
  public ResponseEntity<Page<DetailTestimonialDto>> detailTestimonial(
      @PageableDefault(size = 10, sort = { "name" }) Pageable pagination) {
    var dto = repositoryTestimonial.findAll(pagination).map(DetailTestimonialDto::new);

    return ResponseEntity.ok(dto);
  }

  @PutMapping(consumes = { "multipart/form-data" })
  @Transactional
  public ResponseEntity<ListTestimonialDto> updateTestimonial(@Valid UpdateTestimonialDto updateDto)
      throws IOException {
    Testimonial testimonial = repositoryTestimonial.findById(updateDto.id()).get();
    testimonial.update(updateDto);

    return ResponseEntity.ok().body(new ListTestimonialDto(testimonial));
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<?> deleteTestimonial(@PathVariable Long id) throws IOException {
    Testimonial testimonial = repositoryTestimonial.findById(id).get();
    testimonial.delete();

    return ResponseEntity.noContent().build();
  }
}
