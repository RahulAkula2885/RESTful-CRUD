package com.example.demo.crud.controller;

import com.example.demo.crud.entity.Category;
import com.example.demo.crud.exception.GlobalException;
import com.example.demo.crud.model.BaseResponse;
import com.example.demo.crud.service.ICrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class CrudController extends GlobalException {

    private final ICrudService iCrudService;

    @PostMapping("/create")
    public BaseResponse create(@RequestBody Category req) {
        return iCrudService.create(req);
    }

    @PostMapping("/update")
    public BaseResponse update(@RequestBody Category req) {
        return iCrudService.update(req);
    }

    @DeleteMapping("/delete/{id}")
    public BaseResponse deleteById(@PathVariable("id") Long id) {
        return iCrudService.deleteById(id);
    }

    @GetMapping("/details")
    public List<Category> getAllDetails() {
        return iCrudService.getAllDetails();
    }

    @GetMapping("/{id}")
    public Category getDetailsById(@PathVariable("id") Long id) {
        return iCrudService.getDetailsById(id);
    }
}
