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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.apiJornada.Milhas.domain.destination.Destination;
import com.apiJornada.Milhas.domain.destination.DestinationRepository;
import com.apiJornada.Milhas.domain.destination.DetailDestinationDto;
import com.apiJornada.Milhas.domain.destination.ListDestinationDto;
import com.apiJornada.Milhas.domain.destination.OpenAiGPTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatMessage;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class DestinationControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private JacksonTester<ListDestinationDto> listDestinationJson;

  @Autowired
  private JacksonTester<DetailDestinationDto> detailDestinationJson;

  @MockBean
  private DestinationRepository repositoryDestination;
  
  @MockBean
  private OpenAiGPTService serviceGpt;

  private final String URL = "/destinos";
  private final String URL_GENERATE = "/destinos/gerar-descricao";
  private final String URL_SEARCH = "/destinos/buscar";
  private final String URL_ROUTE_ID = "/destinos/";

  private final String id = "111";
  private final String name = "paris";
  private final String target = "Lorem ipsum dolor";
  private final String destinationDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
  private final String price = "200.8";
  private final String chatUser = "assistant";
  private final String idField = "id";
  private final String nameField = "name";
  private final String targetField = "target";
  private final String imageField1 = "imageOne";
  private final String imageField2 = "imageTwo";
  private final String descriptionField = "destinationDescription";
  private final String priceField = "price";

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

    var response = mvc.perform(multipart(URL)
        .file(createMockMultipartFile_01())
        .file(createMockMultipartFile_02())
        .param(this.nameField, this.name)
        .param(this.targetField, this.target)
        .param(this.descriptionField, this.destinationDescription)
        .param(this.priceField, this.price)
        )
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
  }



  @Test
  @DisplayName("Deveria devolver código http 400 quando não enviado informação de destino.")
  void testGenerateDescription_01() throws Exception {
    var response = mvc.perform(post(URL_GENERATE)).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }
  @Test
  @DisplayName("Deveria devolver código http 200 quando enviado a informação de destino.")
  void testGenerateDescription_02() throws Exception {
    ChatMessage message = new ChatMessage(chatUser, destinationDescription);
    var chatCompletion = new ChatCompletionChoice();
    chatCompletion.setIndex(0);
    chatCompletion.setMessage(message);
    chatCompletion.setFinishReason("stop");
    when(serviceGpt.createDestinationDescription(any())).thenReturn(chatCompletion);

    var destinationNameJson = "{\"name\": \"1111\"}";
    var response = mvc.perform(post(URL_GENERATE).contentType(MediaType.APPLICATION_JSON).content(destinationNameJson)).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    ObjectMapper objectMapper = new ObjectMapper();
    var responseJson = objectMapper.readValue(response.getContentAsString(), ChatCompletionChoice.class);
    assertThat(responseJson).isEqualTo(chatCompletion);
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

    Page<ListDestinationDto> pagedResponse = new PageImpl<>(
        List.of(new ListDestinationDto(createDestinationEntity())));
    when(repositoryDestination.findAllByNameLikeAndActiveTrue(any(), any())).thenReturn(pagedResponse);

    var response = mvc.perform(get(URL_WHIT_QUERY_PARAM)).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonPage = objectMapper.writeValueAsString(pagedResponse);
    // Serializa o objeto "pagedResponse" para comparar com o retorno da rota. Não
    // utilizamos a propriedade "JacksonTester<>" como
    // em alguns outros métodos, pois a ordem dos campos na serializadão do
    // "ObjectMapper" e do "JacksonTester.write().getJson()" são diferentes,
    // o que acaba criando um erro na assertiva, mesmo que ambos tenham todos os
    // campos no JSON.

    assertThat(response.getContentAsString()).isEqualTo(jsonPage);
  }

  @Test
  @DisplayName("Deveria devolver código http 404 quando não enviado o id.")
  void testSearchDestinationById_01() throws Exception {
    var response = mvc.perform(get(URL_ROUTE_ID)).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 200 quando id informado estiver correto.")
  void testSearchDestinationById_02() throws Exception {
    Destination destination = createDestinationEntity();
    var detailDestination = new DetailDestinationDto(destination);

    when(repositoryDestination.findByIdAndActiveTrue(any())).thenReturn(destination);

    var response = mvc.perform(get(URL_ROUTE_ID + id)).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    var expectedJson = detailDestinationJson.write(detailDestination).getJson();
    assertThat(response.getContentAsString()).isEqualTo(expectedJson);
  }

  @Test
  @DisplayName("Deveria devolver código http 415 quando não enviado o Media Type correto")
  void testUpdateDestination_01() throws Exception {
    var response = mvc.perform(put(URL)).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 400 quando o Media Type enviado estiver correto, mas os dados estiverem errados.")
  void testUpdateDestination_02() throws Exception {
    var response = mvc.perform(put(URL)
        .contentType(MediaType.MULTIPART_FORM_DATA))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 200 quando todas informações enviadas estiverem corretas.")
  void testUpdateDestination_03() throws Exception {

    Optional<Destination> testimonial = Optional.of(createDestinationEntity());
    var listTestimonial = new ListDestinationDto(createDestinationEntity());

    when(repositoryDestination.findById(any())).thenReturn(testimonial);

    MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(URL);
    builder.with(new RequestPostProcessor() {
      @Override
      public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
        request.setMethod("PUT");
        return request;
      }
    });
    var response = mvc.perform(builder
        .file(createMockMultipartFile_01())
        .file(createMockMultipartFile_02())
        .param(this.idField, this.id)
        .param(this.nameField, this.name)
        .param(this.targetField, this.target)
        .param(this.descriptionField, this.destinationDescription)
        .param(this.priceField, this.price))
        .andReturn().getResponse();
    ;

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    var expectedJson = listDestinationJson.write(listTestimonial).getJson();
    assertThat(response.getContentAsString()).isEqualTo(expectedJson);
  }

  @Test
  @DisplayName("Deveria devolver código http 404 quando não enviado o id.")
  void testDeleteDestination_01() throws Exception {
    var response = mvc.perform(delete(URL_ROUTE_ID)).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 204 quando id informado estiver correto.")
  void testDeleteDestination_02() throws Exception {

    Optional<Destination> destination = Optional.of(createDestinationEntity());
    when(repositoryDestination.findById(any())).thenReturn(destination);

    var response = mvc.perform(delete(URL_ROUTE_ID + id)).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
  }

  MockMultipartFile createMockMultipartFile_01() {
    return new MockMultipartFile(
        this.imageField1,
        "teste.png",
        MediaType.IMAGE_PNG_VALUE,
        "Hello, World!".getBytes());
  }

  MockMultipartFile createMockMultipartFile_02() {
    return new MockMultipartFile(
        this.imageField2,
        "image.png",
        MediaType.IMAGE_PNG_VALUE,
        "Hello World".getBytes());
  }

  Destination createDestinationEntity() throws NumberFormatException, IOException {
    return new Destination(Long.valueOf(this.id), createMockMultipartFile_01().getBytes(),
        createMockMultipartFile_02().getBytes(), this.name,
        this.target, this.destinationDescription, new BigDecimal(this.price), true);
  }
}
