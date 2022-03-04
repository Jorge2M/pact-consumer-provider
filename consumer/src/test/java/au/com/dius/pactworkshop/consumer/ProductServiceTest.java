package au.com.dius.pactworkshop.consumer;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductServiceTest {

    private WireMockServer wireMockServer;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(options().dynamicPort());

        wireMockServer.start();

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(wireMockServer.baseUrl())
                .build();

        productService = new ProductService(restTemplate);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    private final static String PRODUCT_17044002 = 
    		"{" + 
        		"\"id\":\"17044002\"," + 
        		"\"name\":\"Floral print bikini\"," + 
        		"\"gender\":\"M\"," + 
        		"\"brand\":\"MANGO\"," +
        	    "\"families\": [" +
        	    	"{" + 
        	        	"\"id\": 325," +
        	        	"\"name\": \"Abrigos\"" +
        	        "}," +
        	        "{" +
        	        	"\"id\": 335," +
        	        	"\"name\": \"Chaquetas\"" +
        	        "}" +
        	    "]" +                      		
            "}";
    
    @Test
    void getAllProducts() {
        wireMockServer.stubFor(get(urlPathEqualTo("/products"))
        		.withQueryParam("countryId", WireMock.equalTo("001"))
        		.withQueryParam("languageId", WireMock.equalTo("ES"))
        		.withQueryParam("channelId", WireMock.equalTo("shop"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[" + PRODUCT_17044002 + "]")));

        List<Product> expected = Arrays.asList(
        		new Product(
        				"17044002", "Floral print bikini", "M", "MANGO", 
        				Arrays.asList(
        						new Family(325, "Abrigos"),
        						new Family(335, "Chaquetas"))));

        List<Product> products = productService.getAllProducts(new FiltersProduct("001", "ES", "shop"));

        assertEquals(expected, products);
    }

    @Test
    void getProductById() {
        wireMockServer.stubFor(get(urlPathEqualTo("/products/17044002"))
        		.withQueryParam("countryId", WireMock.equalTo("001"))
        		.withQueryParam("languageId", WireMock.equalTo("ES"))
        		.withQueryParam("channelId", WireMock.equalTo("shop"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(PRODUCT_17044002)));

        Product expected = new Product(
				"17044002", "Floral print bikini", "M", "MANGO", 
				Arrays.asList(
						new Family(325, "Abrigos"),
						new Family(335, "Chaquetas")));

        Product product = productService.getProduct("17044002", new FiltersProduct("001", "ES", "shop"));

        assertEquals(expected, product);
    }
}
