package au.com.dius.pactworkshop.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductService {

    private final RestTemplate restTemplate;

    @Autowired
    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> getAllProducts(FiltersProduct filter) {
        return restTemplate.exchange("/products?" + filter.getQueryParams(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>(){}).getBody();
    }

    public Product getProduct(String id, FiltersProduct filter) {
        return restTemplate.getForEntity("/products/{id}?" + filter.getQueryParams(), Product.class, id).getBody();
    }
}
