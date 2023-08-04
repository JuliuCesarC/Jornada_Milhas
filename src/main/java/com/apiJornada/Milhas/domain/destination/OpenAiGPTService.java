package com.apiJornada.Milhas.domain.destination;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

@Service
public class OpenAiGPTService {

  @Value("${openai.api.token.secret}")
  private String apiKey;

  private final String model = "gpt-3.5-turbo";

  private final String PROMPT = "Faça um resumo sobre CITY_NAME enfatizando os principais motivos para visitar este lugar. Utilize uma linguagem informal e o resumo deve conter no máximo 300 caracteres. Não comece o texto com o nome CITY_NAME e evite escrever em primeira pessoa. Não utilize palavras de baixo calão ou com teor sexual.";

  public ChatCompletionChoice createDestinationDescription(String cityName) {
    String promptWithDestination = PROMPT.replace("CITY_NAME", cityName);
    
    OpenAiService service = new OpenAiService(apiKey);

    List<ChatMessage> list = new ArrayList<>();
    var message = new ChatMessage("user", promptWithDestination);
    list.add(message);

    ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
        .model(model)
        .messages(list)
        .maxTokens(130)
        // .temperature(0.4)
        // .topP(0.5)
        .build();

    return service.createChatCompletion(completionRequest).getChoices().get(0);
  }
}
