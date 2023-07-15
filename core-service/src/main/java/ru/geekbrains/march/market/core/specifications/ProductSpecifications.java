package ru.geekbrains.march.market.core.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.march.market.core.entities.Product;

public class ProductSpecifications {
    public static Specification<Product> priceGreaterOrEqualsThan(Integer price){
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"),price);
    }
    public static Specification<Product> priceLessOrEqualsThan(Integer price){
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"),price);
    }

    public static Specification<Product> titleLike(String titlePart){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"),
                String.format("%%%s%%",titlePart));
    }
}
