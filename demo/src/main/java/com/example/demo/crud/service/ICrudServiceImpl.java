package com.example.demo.crud.service;

import com.example.demo.crud.entity.Category;
import com.example.demo.crud.entity.Product;
import com.example.demo.crud.exception.InternalServerException;
import com.example.demo.crud.model.BaseResponse;
import com.example.demo.crud.repository.CategoryRepository;
import com.example.demo.crud.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ICrudServiceImpl implements ICrudService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public BaseResponse create(Category req) {

        if (!StringUtils.hasText(req.getName())) {
            throw new InternalServerException("Name must not be null or empty");
        }
        if (req.getProducts() == null || req.getProducts().isEmpty()) {
            throw new InternalServerException("Products must not be null or empty");
        }
        req.getProducts().forEach(a -> {
            if (!StringUtils.hasText(a.getName())) {
                throw new InternalServerException("Product name must not be null or empty");
            }
            if (!StringUtils.hasText(a.getPrice())) {
                throw new InternalServerException("Price must not be null or empty");
            }

        });
        List<Product> productList = new ArrayList<>();
        req.getProducts().forEach(a -> {
            Product product = new Product();
            product.setName(a.getName());
            product.setDescription(a.getDescription());
            product.setPrice(a.getPrice());
            product.setCategory(req);
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());

            productList.add(product);
        });

        req.setProducts(productList);
        Category category = categoryRepository.save(req);

        return BaseResponse.builder()
                .status(true)
                .statusMessage("SUCCESS")
                .systemTime(Instant.now())
                .build();
    }

    @Override
    @Transactional
    public BaseResponse update(Category req) {

        if (req.getId() == null) {
            throw new InternalServerException("Id must not be null or empty");
        }

        Optional<Category> checkCategory = categoryRepository.findById(req.getId());
        if (!checkCategory.isPresent()) {
            throw new InternalServerException("Category not found with id " + req.getId());
        }
        Category category = checkCategory.get();

        if (StringUtils.hasText(req.getName())) {
            category.setName(req.getName());
        }

        if (StringUtils.hasText(req.getDescription())) {
            category.setDescription(req.getDescription());
        }

        List<Product> products = new ArrayList<>();

        //1st approach if we want to delete some of the fields and update
        if (req.getProducts() != null && !req.getProducts().isEmpty()) {

            //1st delete previous records
            List<Product> allProductList = productRepository.findByCategoryId(req);
            if (!allProductList.isEmpty()) {
                allProductList.forEach(a -> {
                    productRepository.deleteById(a.getId());
                });
            }

            req.getProducts().forEach(a -> {
                Product product = new Product();
                product.setName(a.getName());
                product.setDescription(a.getDescription());
                product.setPrice(a.getPrice());
                product.setCategory(req);
                product.setCreatedAt(LocalDateTime.now());
                product.setUpdatedAt(LocalDateTime.now());

                products.add(product);
            });
        }
//*****************************************************************************************************************************************
        // 2nd approach without deleting previous records
//*****************************************************************************************************************************************
       /* if (req.getProducts() != null && !req.getProducts().isEmpty()) {
            req.getProducts().forEach(a -> {

                Product product = new Product();
                if (a.getId() != null) {
                    Optional<Product> checkProduct = productRepository.findById(a.getId());
                    if (!checkProduct.isPresent()) {
                        throw new InternalServerException("Product doesn't exists  with id " + a.getId());
                    }
                    product = checkProduct.get();
                } else {
                    product.setCreatedAt(LocalDateTime.now());
                }
                if (StringUtils.hasText(a.getName())) {
                    product.setName(a.getName());
                }
                if (StringUtils.hasText(a.getDescription())) {
                    product.setDescription(a.getDescription());
                }
                if (StringUtils.hasText(a.getPrice())) {
                    product.setPrice(a.getPrice());
                }

                product.setUpdatedAt(LocalDateTime.now());

                products.add(product);
            });
        }*/

        category.setProducts(products);
        categoryRepository.save(category);
        return BaseResponse.builder()
                .status(true)
                .statusMessage("SUCCESS")
                .systemTime(Instant.now())
                .build();
    }

    @Override
    public BaseResponse deleteById(Long id) {

        if (id == null || id == 0) {
            throw new InternalServerException("Id must not be nll or empty");
        }
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.deleteById(id);
        } else {
            throw new InternalServerException("Category doesn't exists with id:- " + id);
        }

        return BaseResponse.builder()
                .status(true)
                .statusMessage("SUCCESS")
                .systemTime(Instant.now())
                .build();
    }

    @Override
    public List<Category> getAllDetails() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public Category getDetailsById(Long id) {
        if (id == null) {
            throw new InternalServerException("Id must not be null or empty");
        }
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new InternalServerException("Category with id:- " + id + " doesn't exist");
        }
    }
}
