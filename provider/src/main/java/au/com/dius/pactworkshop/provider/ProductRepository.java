package au.com.dius.pactworkshop.provider;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final Map<String, Product> PRODUCTS = new HashMap<>();

    public List<Product> fetchAll() {
        initProducts();

        return new ArrayList<>(PRODUCTS.values());
    }

    public Optional<Product> getById(String id) {
        initProducts();
        System.out.println("Getting product " + id);
        Product product = PRODUCTS.get(id);
        if (product==null) {
        	System.out.println("Product " + id + " not obtained");
        } else {
        	System.out.println("Product " + id + " obtained !!!");
        }
        return Optional.ofNullable(PRODUCTS.get(id));
    }

    private void initProducts() {
    	System.out.println("INITIALIZING PRODUCTS...");
        PRODUCTS.put(
        		"17044002",
        		new Product(
        				"17044002", "Floral print bikini", "M", "MANGO", 
        				Arrays.asList(
        						new Family(325, "Abrigos"),
        						new Family(335, "Chaquetas"))));
    }
}
