package nu.hack.category.service;

import lombok.RequiredArgsConstructor;
import nu.hack.category.dto.CategoryCreateRequest;
import nu.hack.category.dto.CategoryResponse;
import nu.hack.category.entity.CategoryEntity;
import nu.hack.category.mapper.CategoryMapper;
import nu.hack.category.repository.CategoryRepository;
import nu.hack.common.dto.PageResponse;
import nu.hack.common.exception.CommonException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static nu.hack.category.entity.CategoryEntity_.NAME;
import static nu.hack.common.specification.CommonSpecification.attributeEquals;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void create(CategoryCreateRequest request) {
        var entity = CategoryMapper.INSTANCE.toEntity(request);
        Specification<CategoryEntity> where = attributeEquals(NAME, request.getName());
        if (categoryRepository.exists(where)) {
            throw new CommonException(400, "Category already exists");
        }
        categoryRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public PageResponse<CategoryResponse> findAll(Pageable pageable) {
        var categorys = categoryRepository.findAll(pageable);
        return PageResponse.fromPage(categorys.map(CategoryMapper.INSTANCE::toResponse));
    }

    @Transactional(readOnly = true)
    public CategoryResponse findById(Integer id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new CommonException(404, "Category with this id not found"));
        return CategoryMapper.INSTANCE.toResponse(category);
    }

    @Transactional
    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }
}
