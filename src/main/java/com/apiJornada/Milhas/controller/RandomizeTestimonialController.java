package com.apiJornada.Milhas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiJornada.Milhas.domain.testimonial.ListTestimonialDto;
import com.apiJornada.Milhas.domain.testimonial.TestimonialRepository;

@RestController
@RequestMapping("depoimentos-home")
@CrossOrigin("http://127.0.0.1:5500")
public class RandomizeTestimonialController {

  @Autowired
  private TestimonialRepository repositoryTestimonial;

  @GetMapping
  public ResponseEntity<List<ListTestimonialDto>> randomTestimonial() {
    var dto = repositoryTestimonial.findRandomTestimonial();

    return ResponseEntity.ok(dto);
  }
}
