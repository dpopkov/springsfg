package learn.sprb2g.recipe.converters;

import learn.sprb2g.recipe.commands.CategoryCommand;
import learn.sprb2g.recipe.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }
        CategoryCommand cmd = new CategoryCommand();
        cmd.setId(source.getId());
        cmd.setDescription(source.getDescription());
        return cmd;
    }
}
