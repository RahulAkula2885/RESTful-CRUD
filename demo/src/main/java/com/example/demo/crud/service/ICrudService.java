package com.example.demo.crud.service;


import com.example.demo.crud.entity.Category;
import com.example.demo.crud.model.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICrudService {
    BaseResponse create(Category req);

    BaseResponse update(Category req);

    BaseResponse deleteById(Long id);

    List<Category> getAllDetails();

    Category getDetailsById(Long id);
}
