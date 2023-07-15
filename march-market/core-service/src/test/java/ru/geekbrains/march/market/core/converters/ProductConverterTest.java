package ru.geekbrains.march.market.core.converters;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.core.SpringBootTestBase;
import ru.geekbrains.march.market.core.entities.Product;
import ru.geekbrains.march.market.core.services.CategoryService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@RequiredArgsConstructor
class ProductConverterTest extends SpringBootTestBase {
    private final CategoryService categoryService;

    @Test
    void entityToDto() {
        Product product = new Product();
        product.setCategory(categoryService.findByTitle("Еда").get());
        product.setTitle("Хлеб");
        product.setPrice(BigDecimal.valueOf(48.50));
        product.setId(999L);

        Assertions.assertNotNull(product);
        Assertions.assertEquals("Еда",product.getCategory().toString());
        Assertions.assertEquals("Хлеб",product.getTitle());
        Assertions.assertEquals(999L,product.getId());
        Assertions.assertEquals(48.50, product.getPrice());

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        productDto.setCategoryTitle(product.getCategory().getTitle());

        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(productDto.getId(),product.getId());
        Assertions.assertEquals(productDto.getTitle(),product.getTitle());
        Assertions.assertEquals(productDto.getCategoryTitle(),product.getCategory().toString());
        Assertions.assertEquals(productDto.getPrice(),product.getPrice());
    }
}