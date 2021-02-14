package learn.sprb2g.mvcrest.api.v1.mapper;

import learn.sprb2g.mvcrest.api.v1.model.CategoryDTO;
import learn.sprb2g.mvcrest.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDTO(Category category);
}
