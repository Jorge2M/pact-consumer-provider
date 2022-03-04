package au.com.dius.pactworkshop.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonArrayMinLike;
import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(PactConsumerTestExt.class)
public class ProductConsumerPactTest {

	
    @Pact(consumer = "consumer-api-prods", provider = "api-prods")
    RequestResponsePact getProductById_productExists(PactDslWithProvider builder) {
        return builder.given("Product with ID 17044002 exists")
                .uponReceiving("get product with ID 17044002")
                .method("GET")
                .path("/products/17044002")
                .query("countryId=001&languageId=ES&channelId=shop")
                .willRespondWith()
                .status(200)
                .headers(headers())
                .body(newJsonBody((body) -> {
                    body.stringType("id", "17044002")
                    	.stringType("name", "Floral print bikini")
                    	.stringType("gender", "M")
                    	.stringType("brand", "MANGO")
                    	.array("families", object -> {
	                    	object.object((ao) -> {
	                    		ao.numberType("id", 325)
	                    		  .stringType("name", "Abrigos");
	                    	});
	                    	
	                    	object.object((ao) -> {
	                    		ao.numberType("id", 335)
	                    		  .stringType("name", "Chaquetas");
	                    	});
                    	});
                }).build())
                .toPact();      
    }
    
    private Map<String, String> headers() {
    	Map<String, String> headers = new HashMap<>();
    	headers.put("Content-Type", "application/json; charset=utf-8");
    	return headers;
    }

    @Pact(consumer = "consumer-api-prods", provider = "api-prods")
    RequestResponsePact getProductById_productNotExists(PactDslWithProvider builder) {
        return builder.given("Product with ID 99044002 doesn't exists")
                .uponReceiving("get product with ID 99044002")
                .method("GET")
                .path("/products/99044002")
                .query("countryId=001&languageId=ES&channelId=shop")
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getProductById_productExists")
    void getProductById_productExists(MockServer mockServer) {
    	
        Product expected = new Product(
				"17044002", "Floral print bikini", "M", "MANGO", 
				Arrays.asList(
						new Family(325, "Abrigos"),
						new Family(335, "Chaquetas")));

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();
        
        Product product = new ProductService(restTemplate).getProduct(
        		"17044002",
        		new FiltersProduct("001", "ES", "shop"));

        assertEquals(expected, product);
    }

    @Test
    @PactTestFor(pactMethod = "getProductById_productNotExists")
    void getProductById_productDoesNotExist(MockServer mockServer) {
    	
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();

        HttpClientErrorException e = assertThrows(HttpClientErrorException.class,
                () -> new ProductService(restTemplate).getProduct(
                		"99044002",
                		new FiltersProduct("001", "ES", "shop")));
                
        assertEquals(404, e.getRawStatusCode());
    }

}
