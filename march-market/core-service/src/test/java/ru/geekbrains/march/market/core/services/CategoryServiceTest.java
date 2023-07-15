package ru.geekbrains.march.market.core.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.geekbrains.march.market.core.SpringBootTestBase;
import ru.geekbrains.march.market.core.entities.Category;
import ru.geekbrains.march.market.core.repositories.CategoryRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RequiredArgsConstructor
class CategoryServiceTest extends SpringBootTestBase {

    private final CategoryRepository categoryRepository;
    @Test
    void findByTitle() {
        Category category = categoryRepository.findByTitle("Еда").get();

        Assertions.assertNotNull(category);
        Assertions.assertEquals("Еда",category.getTitle());

    }
}