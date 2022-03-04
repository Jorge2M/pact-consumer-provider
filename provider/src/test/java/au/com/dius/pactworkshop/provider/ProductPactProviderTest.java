package au.com.dius.pactworkshop.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;

@Provider("api-prods")
//@PactFolder("pacts")
@PactBroker(host = "pact-broker.mangodev.net", port = "80")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductPactProviderTest {

    @LocalServerPort
    int port;

//    @MockBean
//    private ProductRepository productRepository;

    @BeforeEach
    void setUp(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPact(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("Product with ID 99044002 doesn't exists")
    void toNoProductsExistState() {
//        when(productRepository.getById("99044002")).thenReturn(Optional.empty());
    }

    @State("Product with ID 17044002 exists")
    void toProductWithIdTenExistsState() {
//        when(productRepository.getById("17044002")).thenReturn(Optional.of(
//    		new Product(
//				"17044002", "Floral print bikini", "M", "MANGO", 
//				Arrays.asList(
//					new Family(325, "Abrigos"),
//					new Family(335, "Chaquetas")))));
    }
}
