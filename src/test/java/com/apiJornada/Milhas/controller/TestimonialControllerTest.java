package com.apiJornada.Milhas.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.IOException;
import java.util.Optional;

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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.context.WebApplicationContext;

import com.apiJornada.Milhas.domain.testimonial.CreateTestimonialDto;
import com.apiJornada.Milhas.domain.testimonial.ListTestimonialDto;
import com.apiJornada.Milhas.domain.testimonial.Testimonial;
import com.apiJornada.Milhas.domain.testimonial.TestimonialRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class TestimonialControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private JacksonTester<CreateTestimonialDto> createTestimonialDto;

  @Autowired
  private JacksonTester<ListTestimonialDto> listTestimonialDto;

  @MockBean
  private TestimonialRepository repositoryTestimonial;

  @Autowired
  private WebApplicationContext webApplicationContext;

  private String id = "111";
  private String name = "11111";
  private String testimonial = "11111111";
  private String idField = "id";
  private String pictureField = "picture";
  private String nameField = "name";
  private String testimonialField = "testimonial";

  @Test
  @DisplayName("Deveria devolver código http 415 quando não enviado o Media Type correto.")
  void testCreateTestimonial_01() throws Exception {
    var response = mvc.perform(post("/depoimentos")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 400 quando o Media Type enviado estiver correto, mas os dados estiverem errados.")
  void testCreateTestimonial_02() throws Exception {
    var response = mvc.perform(post("/depoimentos")
        .contentType(MediaType.MULTIPART_FORM_DATA))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 201 quando o Media Type e os dados enviados estiverem corretos.")
  void testCreateTestimonial_03() throws Exception {

    var response = mvc.perform(multipart("/depoimentos").file(createMultiPartFile())
        .param(this.nameField, this.name)
        .param(this.testimonialField, this.testimonial))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 200 quando efetuado a requisição.")
  void testListTestimonial_01() throws Exception {
    var response = mvc.perform(get("/depoimentos")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 404 quando não enviado o id.")
  void testSearchTestimonialById_01() throws Exception {
    var response = mvc.perform(get("/depoimentos/")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 200 quando id informado estiver correto.")
  void testSearchTestimonialById_02() throws Exception {
    Testimonial testimonial = createTestimonial();
    var listTestimonial = new ListTestimonialDto(testimonial);

    when(repositoryTestimonial.findByIdAndActiveTrue(any())).thenReturn(testimonial);

    var response = mvc.perform(get("/depoimentos/111")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    var responseJson = listTestimonialDto.write(listTestimonial).getJson();
    assertThat(response.getContentAsString()).isEqualTo(responseJson);
  }

  @Test
  @DisplayName("Deveria devolver código http 415 quando não enviado o Media Type correto")
  void testUpdateTestimonial_01() throws Exception {
    var response = mvc.perform(put("/depoimentos")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 400 quando o Media Type enviado estiver correto, mas os dados estiverem errados.")
  void testUpdateTestimonial_02() throws Exception {
    var response = mvc.perform(put("/depoimentos")
        .contentType(MediaType.MULTIPART_FORM_DATA))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 200 quando todas informações enviadas estiverem corretas.")
  void testUpdateTestimonial_03() throws Exception {

    Optional<Testimonial> testimonial = Optional.of(createTestimonial());
    var listTestimonial = new ListTestimonialDto(createTestimonial());

    when(repositoryTestimonial.findById(any())).thenReturn(testimonial);

    // CONFIGURANDO A REQUISIÇÃO DO TIPO MULTIPART PARA O VERBO PUT

    // Criamos uma requisição do tipo Multipart para a rota informada.
    MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/depoimentos");
    // Executamos o "builder.with()" para podermos adicionar a interface
    // "RequestPostProcessor", que nos permite manipular a requisição antes de ela
    // ser enviada.
    builder.with(new RequestPostProcessor() {
      @Override
      public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
        // Sobrescrevemos o método "postProcessRequest" para que ele retorne o verbo
        // PUT.
        request.setMethod("PUT");
        return request;
      }
    });
    var response = mvc.perform(builder
        .file(createMultiPartFile())
        .param(this.idField, this.id)
        .param(this.nameField, this.name)
        .param(this.testimonialField, this.testimonial))
        .andReturn().getResponse();
    ;

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    var responseJson = listTestimonialDto.write(listTestimonial).getJson();
    assertThat(response.getContentAsString()).isEqualTo(responseJson);
  }

  @Test
  @DisplayName("Deveria devolver código http 404 quando não enviado o id.")
  void testDeleteTestimonial_01() throws Exception {
    var response = mvc.perform(delete("/depoimentos/")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 204 quando id informado estiver correto.")
  void testDeleteTestimonial_02() throws Exception {

    Optional<Testimonial> testimonial = Optional.of(createTestimonial());
    when(repositoryTestimonial.findById(any())).thenReturn(testimonial);
    
    var response = mvc.perform(delete("/depoimentos/"+this.id)).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
  }

  MockMultipartFile createMultiPartFile() {
    return new MockMultipartFile(
        this.pictureField,
        "teste.txt",
        MediaType.IMAGE_PNG_VALUE,
        "Hello, World!".getBytes());
  }

  Testimonial createTestimonial() throws NumberFormatException, IOException {
    return new Testimonial(Long.valueOf(this.id), this.name, createMultiPartFile().getBytes(), this.testimonial, true);
  }
}
