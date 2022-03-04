package au.com.dius.pactworkshop.provider;

import java.util.List;
import java.util.Objects;

public class Product {

    private String id;
    private String name;
    private String gender;
    private String brand;
    private List<Family> families;

    public Product() {
    }

    public Product(String id,
    			   String name,
                   String gender,
                   String brand,
                   List<Family> families) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.brand = brand;
        this.families = families;
    }

    public String getId() {
        return id;
    }
    
    public String getName() {
    	return name;
    }
    
    public String getGender() {
    	return gender;
    }
    
    public String getBrand() {
    	return brand;
    }
    
    public List<Family> getFamilies() {
    	return families;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return 
        		Objects.equals(id, product.id) &&
                Objects.equals(gender, product.gender) &&
                Objects.equals(brand, product.brand) &&
                Objects.equals(name, product.name) &&
                Objects.equals(families, product.families);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gender, brand, name);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", gender='" + gender + '\'' +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", families='" + families + '\'' +
                '}';
    }
}
