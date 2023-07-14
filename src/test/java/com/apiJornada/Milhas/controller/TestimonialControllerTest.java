package com.apiJornada.Milhas.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.apiJornada.Milhas.domain.testimonial.CreateTestimonialDto;
import com.apiJornada.Milhas.domain.testimonial.TestimonialRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class TestimonialControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private JacksonTester<CreateTestimonialDto> createTestimonialDto;

  @MockBean
  private TestimonialRepository repositoryTestimonial;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Test
  @DisplayName("Deveria devolver código http 415 quando não enviado o Media Type correto")
  void testCreateTestimonial_01() throws Exception {
    var response = mvc.perform(post("/depoimentos")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 400 quando o Media Type enviado estiver correto, mas os dados estiverem errado")
  void testCreateTestimonial_02() throws Exception {
    var response = mvc.perform(post("/depoimentos")
        .contentType(MediaType.MULTIPART_FORM_DATA))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 201 quando o Media Type e os dados enviados estiverem corretos")
  void testCreateTestimonial_03() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
        "text",
        "teste.txt",
        MediaType.TEXT_PLAIN_VALUE,
        "Hello, World!".getBytes());

    var createDto = new CreateTestimonialDto("Nofghdfghf", "Qualquer coisa");

    var response = mvc.perform(post("/depoimentos")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .content(createTestimonialDto.write(createDto).getJson()))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
  }

  // @Test
  // @DisplayName("Deveria devolver código http 201 quando o Media Type e os dados
  // enviados estiverem corretos")
  // void testCreateTestimonial_03() throws Exception {
  // byte[] inputArray = "Test Testimonial".getBytes();
  // MockMultipartFile mockMultipartFile = new
  // MockMultipartFile("fileName",inputArray);
  // var createDto = new CreateTestimonialDto("Teste", mockMultipartFile, "Teste
  // T");

  // var response = mvc.perform(post("/depoimentos")
  // .contentType(MediaType.MULTIPART_FORM_DATA)
  // .content(createTestimonialJson.write(createDto).getJson()))
  // .andReturn().getResponse();

  // assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
  // }

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
