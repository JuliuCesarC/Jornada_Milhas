package com.apiJornada.Milhas.domain.destination;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

@Service
public class OpenAiGPTService {

  @Value("${openai.api.token.secret}")
  private String apiKey;

  private final String model = "gpt-3.5-turbo";

  // private final String PROMPT = "Faça um resumo sobre CITY_NAME enfatizando o porque este lugar é incrível. Utilize uma linguagem informal e até 100 caracteres no máximo em cada parágrafo. Crie 2 parágrafos neste resumo.";
  private final String PROMPT = "Faça um resumo sobre CITY_NAME enfatizando os principais motivos para visitar este lugar. Utilize uma linguagem informal e até 100 caracteres no máximo em cada parágrafo. Crie 2 parágrafos neste resumo.";

  public ChatMessage createDestinationDescription(String cityName) {
    String promptWithDestination = PROMPT.replace("CITY_NAME", cityName);
    OpenAiService service = new OpenAiService(apiKey);

    List<ChatMessage> list = new ArrayList<>();
    var message = new ChatMessage("user", promptWithDestination);
    list.add(message);

    ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
        .model(model)
        .messages(list)
        .maxTokens(200)
        .build();

    return service.createChatCompletion(completionRequest).getChoices().get(0).getMessage();
  }
}
