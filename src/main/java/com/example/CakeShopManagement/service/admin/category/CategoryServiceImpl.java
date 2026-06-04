package com.example.CakeShopManagement.service.admin.category;

import com.example.CakeShopManagement.dto.CategoryDto;
import com.example.CakeShopManagement.entity.CategoryEntity;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.mappers.CategoryMapper;
import com.example.CakeShopManagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryEntity createCategory(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName(categoryDto.getCategoryName());
        categoryEntity.setDescription(categoryDto.getDescription());

        return categoryRepository.save(categoryEntity);
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryDto> getCategories(){
        try {
            List<CategoryEntity> categoryEntities = categoryRepository.findAll();
            List<CategoryDto> categoryDtoList = categoryMapper.toCategoryDtoList(categoryEntities);
            return categoryDtoList;
        }
        catch (AppException e) {
            throw new AppException("Request failed with error: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        try {
            Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryId);

            if (!optionalCategoryEntity.isPresent()) {
                throw new AppException("Category does not Exists", HttpStatus.BAD_REQUEST);
            }

            CategoryEntity newCategoryEntity = categoryMapper.toCategoryEntity(categoryDto);
            newCategoryEntity.setCategoryId(categoryId);

            CategoryEntity categoryEntity = categoryRepository.save(newCategoryEntity);
            CategoryDto responseCategoryDto = categoryMapper.toCategoryDto(categoryEntity);
            return responseCategoryDto;
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteCategory(Long categoryId) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isPresent()) {
            CategoryEntity categoryEntity = optionalCategory.get();

            categoryRepository.deleteById(categoryId);
            return true;
        }
        return false;
    }
}
