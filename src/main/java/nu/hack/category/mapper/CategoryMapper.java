package nu.hack.category.mapper;

import nu.hack.category.dto.CategoryCreateRequest;
import nu.hack.category.entity.CategoryEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryEntity toEntity(CategoryCreateRequest request);
}
