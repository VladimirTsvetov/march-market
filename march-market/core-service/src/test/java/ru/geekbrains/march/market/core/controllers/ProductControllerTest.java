package ru.geekbrains.march.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.core.SpringBootTestBase;
import ru.geekbrains.march.market.core.converters.ProductConverter;
import ru.geekbrains.march.market.core.entities.Product;
import ru.geekbrains.march.market.core.exceptions.ResourceNotFoundException;
import ru.geekbrains.march.market.core.repositories.ProductRepository;
import ru.geekbrains.march.market.core.services.CategoryService;
import ru.geekbrains.march.market.core.services.ProductService;

import java.math.BigDecimal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.geekbrains.march.market.core.converters.ProductConverter;
import ru.geekbrains.march.market.core.services.ProductService;

import static io.jsonwebtoken.Header.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.testcontainers.shaded.com.google.common.net.HttpHeaders.ACCEPT;

@RequiredArgsConstructor
class ProductControllerTest extends SpringBootTestBase {
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final WebTestClient webTestClient;
    private final CategoryService categoryService;
    private final ProductRepository productRepository;


    @Test
    void findProducts() {

        //готовим тестовую спецификацию и изучаем полученный список дотшек
        Specification<Product> spec = productService.createSpecByFilters(10, 100, "Milk");
        Assertions.assertNotNull(spec);

        List<ProductDto> existingProductList = productService.findAll(spec,1)
                .map(productConverter::entityToDto)
                .getContent();

        //мы точно знаем, что такие продукты есть, поэтому:
        Assertions.assertNotNull(existingProductList);
        Assertions.assertNotEquals(existingProductList.isEmpty(),true);

    }

    @Test
    void getProductById() {
        //тестовый продукт
        Product product = new Product();
        product.setCategory(categoryService.findByTitle("Еда").orElseThrow(
                () -> new ResourceNotFoundException("Категория с названием: " + "Еда" + " не найдена")));
        product.setTitle("Булка");
        product.setPrice(BigDecimal.valueOf(15.50));
        product.setId(777L);
        ProductDto testProductDto = productConverter.entityToDto(product);

        ProductDto productDtoByHttp = webTestClient.get()
                .uri("/product/" + testProductDto.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(testProductDto.getId(), productDtoByHttp.getId());
        Assertions.assertEquals(testProductDto.getTitle(), productDtoByHttp.getTitle());
        Assertions.assertEquals(testProductDto.getCategoryTitle(),productDtoByHttp.getCategoryTitle());
        Assertions.assertEquals(testProductDto.getPrice(),productDtoByHttp.getPrice());
    }

    @Test
    void createNewProducts() {
        ProductDto testProductDto = productConverter.entityToDto(productRepository.getById(777L));

        var testDtoByHttp = webTestClient
                .post()
                .uri("/product/")
                .body(Mono.just(testProductDto), ProductDto.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(CREATED)
                .returnResult(Void.class);

    }

    @Test
    void deleteProductById() {
        productService.deleteById(1L);

        webTestClient.get()
                .uri("/product/1")
                .exchange()
                .expectStatus().isNotFound();
    }
}