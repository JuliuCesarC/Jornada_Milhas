package com.apiJornada.Milhas.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.apiJornada.Milhas.domain.destination.Destination;
import com.apiJornada.Milhas.domain.destination.DestinationRepository;
import com.apiJornada.Milhas.domain.destination.ListDestinationDto;
import com.apiJornada.Milhas.domain.testimonial.ListTestimonialDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class DestinationControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private JacksonTester<List<ListDestinationDto>> listDestinationJson;

  @MockBean
  private DestinationRepository repositoryDestination;

  private final String URL = "/destinos";
  private final String URL_SEARCH = "/destinos/buscar";

  private final String id = "111";
  private final String name = "paris";
  private final double price = 1111.11;
  private final String idField = "id";
  private final String nameField = "name";
  private final String priceField = "price";
  private final String pictureField = "picture";

  @Test
  @DisplayName("Deveria devolver código http 415 quando não enviado o Media Type correto.")
  void testCreateDestination_01() throws Exception {
    var response = mvc.perform(post(URL)).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 400 quando o Media Type enviado estiver correto, mas os dados estiverem errados.")
  void testCreateDestination_02() throws Exception {
    var response = mvc.perform(post(URL)
        .contentType(MediaType.MULTIPART_FORM_DATA))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 201 quando o Media Type e os dados enviados estiverem corretos.")
  void testCreateDestination_03() throws Exception {

    var response = mvc.perform(multipart(URL).file(createMockMultipartFile())
        .param(this.nameField, this.name)
        .param(this.priceField, String.valueOf(this.price)))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 200 quando efetuado a requisição.")
  void testListDestination_01() throws Exception {
    var response = mvc.perform(get(URL)).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 400 quando não enviado o query param.")
  void testSearchByNameDestination_01() throws Exception {
    var response = mvc.perform(get(URL_SEARCH)).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 404 quando a busca por nome não encontrar nenhum resultado.")
  void testSearchByNameDestination_02()
      throws Exception {
    final String QUERY_PARAM = "?name=";
    final String URL_WHIT_QUERY_PARAM = URL_SEARCH + QUERY_PARAM + name;

    PageImpl<ListDestinationDto> pagedResponse = new PageImpl<>(new ArrayList<>());
    when(repositoryDestination.findAllByNameLikeAndActiveTrue(any(), any())).thenReturn(pagedResponse);

    var response = mvc.perform(get(URL_WHIT_QUERY_PARAM)).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 200 quando a busca por nome encontrar um resultado.")
  void testSearchByNameDestination_03() throws Exception {
    final String QUERY_PARAM = "?name=";
    final String URL_WHIT_QUERY_PARAM = URL_SEARCH + QUERY_PARAM + name;

    PageImpl<ListDestinationDto> pagedResponse = new PageImpl<>(
        List.of(new ListDestinationDto(createDestinationEntity())));
    when(repositoryDestination.findAllByNameLikeAndActiveTrue(any(), any())).thenReturn(pagedResponse);

    var response = mvc.perform(get(URL_WHIT_QUERY_PARAM)).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());


    var expectedJson = listDestinationJson.write(List.of(new ListDestinationDto(createDestinationEntity()))).getJson();


    ObjectMapper mapper = new ObjectMapper();
    // var obj = mapper.readValue(response.getContentAsString(), Pageable.class);
    
    // var responseJson = listDestinationJson.read(response.getContentAsString());
    // assertThat(obj.getContent()).isEqualTo(expectedJson);
  }

  @Test
  void testSearchDestinationById() {

  }

  @Test
  void testUpdateDestination() {

  }

  @Test
  void testDeleteDestination() {

  }

  MockMultipartFile createMockMultipartFile() {
    return new MockMultipartFile(
        this.pictureField,
        "teste.png",
        MediaType.IMAGE_PNG_VALUE,
        "Hello, World!".getBytes());
  }

  Destination createDestinationEntity() throws NumberFormatException, IOException {
    return new Destination(Long.valueOf(this.id), createMockMultipartFile().getBytes(), this.name,
        BigDecimal.valueOf(this.price), true);
  }
}
