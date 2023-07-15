package ru.geekbrains.march.market.core.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.core.SpringBootTestBase;
import ru.geekbrains.march.market.core.entities.Product;
import ru.geekbrains.march.market.core.exceptions.ResourceNotFoundException;
import ru.geekbrains.march.market.core.repositories.ProductRepository;
import ru.geekbrains.march.market.core.specifications.ProductSpecifications;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductServiceTest extends SpringBootTestBase {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductRepository productRepository;

    @Test
    void findAll() {
        List<Product> productList = productRepository.findAll();
        Assertions.assertNotEquals(productList.isEmpty(),true);
    }

    @Test
    void deleteById() {
        productRepository.deleteById(1L);
        Product savedProduct = productRepository.getById(1L);
        Assertions.assertNull(savedProduct);
    }

    @Test
    void createNewProduct() {
        Product product = new Product();

        product.setCategory(categoryService.findByTitle("Еда").orElseThrow(
                () -> new ResourceNotFoundException("Категория с названием: " + "Еда" + " не найдена")));
        product.setPrice(BigDecimal.valueOf(88.22));
        product.setTitle("Milk128");

        productRepository.save(product);
        Product savedProduct = productRepository.getById(product.getId());
        Assertions.assertNotNull(savedProduct);
        Assertions.assertNotNull(savedProduct.getId());
        Assertions.assertEquals("Milk128", savedProduct.getTitle());
    }

    @Test
    void findById() {
        Product savedProduct = productRepository.getById(1L);
        Assertions.assertNotNull(savedProduct);
    }

    @Test
    void createSpecByFilters() {
        Specification<Product> spec = Specification.where(null); //спецификация - неизменяемый объект как стринг
        Integer minPrice = 50;
        spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(minPrice));
        Assertions.assertNull(spec);

    }
}