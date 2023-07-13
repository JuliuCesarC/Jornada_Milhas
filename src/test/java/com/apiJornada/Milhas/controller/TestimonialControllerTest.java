package com.apiJornada.Milhas.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class TestimonialControllerTest {

    @Autowired
  private MockMvc mvc;
  
  @Test
  @DisplayName("Deveria devolver código http 415 quando não enviado o Media Type correto")
  void testCreateTestimonial_01() throws Exception {
    var response = mvc.perform(post("/depoimentos")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
  }

  @Test
  void testDeleteTestimonial() {

  }

  @Test
  void testListTestimonial() {

  }

  @Test
  void testSearchTestimonialById() {

  }

  @Test
  void testUpdateTestimonial() {

  }
}
