package ru.geekbrains.march.market.cart.integrations;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.cart.SpringBootTestBase;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/*
@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    //private final RestTemplate restTemplate;
    private final WebClient productServiceWebClient;


    public ProductDto getProductById(Long id){
        return productServiceWebClient.get()
                .uri("api/v1/products/"+id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Товар не найден в продуктовом МС"))
                )
                .bodyToMono(ProductDto.class)
                .block();
    }
}
 */
@RequiredArgsConstructor
class ProductServiceIntegrationTest extends SpringBootTestBase {
    WebTestClient webTestClient;
    @Test
    void getProductById() {

        ProductDto p = new ProductDto();
        ProductDto productDto = new ProductDto();
        productDto.setTitle("Хлебб");
        productDto.setPrice(BigDecimal.valueOf(10));
        productDto.setCategoryTitle("Хлеб");
        productDto.setId(111L);



        ProductDto productDtoByHttp = webTestClient.get()
                .uri("api/v1/products/" + productDto.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(productDto.getId(), productDtoByHttp.getId());
        Assertions.assertEquals(productDto.getTitle(), productDtoByHttp.getTitle());
        Assertions.assertEquals(productDto.getPrice(), productDtoByHttp.getPrice());
        Assertions.assertEquals(productDto.getCategoryTitle(), productDtoByHttp.getCategoryTitle());
    }
}