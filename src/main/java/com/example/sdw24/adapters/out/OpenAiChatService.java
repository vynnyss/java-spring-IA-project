package com.example.sdw24.adapters.out;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.sdw24.domain.ports.GenerativeAiService;

import feign.RequestInterceptor;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "OPEN-AI")
@FeignClient(name = "openAiApi", url = "${gemini.base-url}", configuration = OpenAiChatService.Config.class)
public interface OpenAiChatService extends GenerativeAiService {
	
	@PostMapping("/v1/chat/completions")
	OpenAiChatCompletionResp chatCompletion(OpenAiChatCompletionReq req);
	
    @Override
    default String generateContent(String objective, String context) {
    	String model = "gpt-4o-mini";
		List<Message> message = List.of(
				new Message("system", objective),
				new Message("user", context)
				);
		OpenAiChatCompletionReq req = new OpenAiChatCompletionReq(model, message);
		OpenAiChatCompletionResp resp = chatCompletion(req);
        return resp.choices().getFirst().message().content();
    }
    record OpenAiChatCompletionReq(String model, List<Message> messagens) {}
    record Message(String role, String content) {}

    record OpenAiChatCompletionResp(List<Choice> choices) {}
    record Choice(Message message) {}
    
    class Config {
    	public RequestInterceptor apiKeyRequestInterceptor(@Value("${openai.api-key}") String apiKey) {
    		return requestTemplate -> requestTemplate.header(HttpHeaders.AUTHORIZATION,"Bearer %s".formatted(apiKey));
    	}
    }
}
	


/*
'{
    "model": "gpt-4o-mini",
    "messages": [
      {
        "role": "developer",
        "content": "You are a helpful assistant."
      },
      {
        "role": "user",
        "content": "Hello!"
      }
    ]
  }'
  
  
  
  {
  "id": "chatcmpl-B9MBs8CjcvOU2jLn4n570S5qMJKcT",
  "object": "chat.completion",
  "created": 1741569952,
  "model": "gpt-4.1-2025-04-14",
  "choices": [
    {
      "index": 0,
      "message": {
        "role": "assistant",
        "content": "Hello! How can I assist you today?",
        "refusal": null,
        "annotations": []
      },
      "logprobs": null,
      "finish_reason": "stop"
    }
  ],
  "usage": {
    "prompt_tokens": 19,
    "completion_tokens": 10,
    "total_tokens": 29,
    "prompt_tokens_details": {
      "cached_tokens": 0,
      "audio_tokens": 0
    },
    "completion_tokens_details": {
      "reasoning_tokens": 0,
      "audio_tokens": 0,
      "accepted_prediction_tokens": 0,
      "rejected_prediction_tokens": 0
    }
  },
  "service_tier": "default"
}

  
*/