package com.example.sdw24.adapters.out;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.sdw24.domain.ports.GenerativeAiService;

import feign.FeignException;
import feign.RequestInterceptor;


@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "gemini", matchIfMissing = true)
@FeignClient(name = "geminiApi", url = "${gemini.base-url}", configuration = GoogleGeminiService.Config.class)
public interface GoogleGeminiService extends GenerativeAiService {
	
	@PostMapping("/v1beta/models/gemini-2.0-flash:generateContent")
	GoogleGeminiResp textOnlyInput(GoogleGeminiReq req);
	
    @Override
    default String generateContent(String objective, String context) {
    	String prompt = """
    			%s
    			%s
    			""".formatted(objective, context);

    	GoogleGeminiReq req = new GoogleGeminiReq(
    			List.of(new Content(List.of(new Part(prompt))))
    			);
    	try {	
    		GoogleGeminiResp resp = textOnlyInput(req);
    		return resp.candidates().getFirst().content().parts().getFirst().text();
    	} catch (FeignException httpErrors) {
    		System.out.println(httpErrors.getMessage());
    		return "Erro de comunicação com a api do Google Gemini.";
    	} catch (Exception unexpectedError) {
    		return "Erro, o retorno da API nao contém os dados esperados.";
    	}
    }
    record GoogleGeminiReq(List<Content> contents) {}
    record Content(List<Part> parts) {}
    record Part(String text) {}
    
    record GoogleGeminiResp(List<Candidate> candidates) {}
    record Candidate(Content content) {}
    
    
    class Config {
    	@Bean
    	public RequestInterceptor apiKeyRequestInterceptor(@Value("${gemini.api-key}") String apiKey) {
    		return requestTemplate -> requestTemplate.query("key", apiKey);
    	}
    }
}
	


/*
     --- Estruturas de Requisição e Resposta para Gemini ---

     Requisição para a API Gemini (generateContent)
     Exemplo:
     {
       "contents": [
         {
           "parts": [
             { "text": "Explique como funciona a IA." }
           ]
         }
       ]
     }
  
     Resposta da API Gemini (generateContent)
     Exemplo:
     {
       "candidates": [
         {
           "content": {
             "parts": [
               { "text": "A IA funciona..." }
             ],
             "role": "model"
           },
           "finishReason": "STOP",
           ...
         }
       ],
       ...
     }
*/