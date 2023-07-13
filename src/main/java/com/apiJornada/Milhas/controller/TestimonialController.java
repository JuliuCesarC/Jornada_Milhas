package com.apiJornada.Milhas.controller;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.apiJornada.Milhas.domain.testimonial.CreateTestimonialDto;
import com.apiJornada.Milhas.domain.testimonial.ListTestimonialDto;
import com.apiJornada.Milhas.domain.testimonial.Testimonial;
import com.apiJornada.Milhas.domain.testimonial.TestimonialRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("depoimentos")
@CrossOrigin("http://127.0.0.1:5500")
public class TestimonialController {

  @Autowired
  private TestimonialRepository repositoryTestimonial;

  @PostMapping(consumes = {"multipart/form-data"})
  @CrossOrigin(exposedHeaders = {"location"})
  @Transactional
  public ResponseEntity<URI> createTestimonial(@Valid CreateTestimonialDto createDto, UriComponentsBuilder uriBuilder) throws IOException {
    var testimonial = new Testimonial(createDto);
    repositoryTestimonial.save(testimonial);

    var uri = uriBuilder.path("/depoimentos/{id}").buildAndExpand(testimonial.getId()).toUri();

    return ResponseEntity.created(uri).build();
  }

  @GetMapping
  public ResponseEntity<Page<ListTestimonialDto>> listTestimonial(
      @PageableDefault(size = 10, sort = { "name" }) Pageable pagination) {      
    var dto = repositoryTestimonial.findAll(pagination).map(ListTestimonialDto::new);

    return ResponseEntity.ok(dto);
  }
}

