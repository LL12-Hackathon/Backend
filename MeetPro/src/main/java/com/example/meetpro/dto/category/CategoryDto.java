package com.example.meetpro.dto.category;

import java.util.ArrayList;
import java.util.List;

import com.example.meetpro.entity.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {

    private int id;
    private String name;
    private List<CategoryDto> children;

    public static List<CategoryDto> toDtoList(List<Category> categories) {
        CategoryHelper helper = CategoryHelper.newInstance(
                categories,
                c -> new CategoryDto(c.getId(), c.getName(), new ArrayList<>()),
                Category::getParent,
                Category::getId,
                CategoryDto::getChildren);
        return helper.convert();
    }
}
