package com.example.meetpro.service.category;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.meetpro.dto.category.CategoryCreateRequest;
import com.example.meetpro.dto.category.CategoryDto;
import com.example.meetpro.entity.category.Category;
import com.example.meetpro.exception.CategoryNotFoundException;
import com.example.meetpro.repository.category.CategoryRepository;


import java.util.List;

@Service
public class CategoryService {

    private final static String DEFAULT_CATEGORY = "Default";

    private final CategoryRepository categoryRepository;

    public CategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> findAllCategory() {
        List<Category> categories = categoryRepository.findAllOrderByParentIdAscNullsFirstCategoryIdAsc();
        return CategoryDto.toDtoList(categories);
    }

    @Transactional
    public void createAtFirst() {
        Category category = new Category(DEFAULT_CATEGORY, null);
        categoryRepository.save(category);
    }

    @Transactional
    public void createCategory(final CategoryCreateRequest req) {
        Category parent = categoryRepository.findById(req.getParentId())
                .orElseThrow(CategoryNotFoundException::new);

        categoryRepository.save(new Category(req.getName(), parent));
    }

    @Transactional
    public void deleteCategory(final int id) {
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        categoryRepository.delete(category);
    }
}
