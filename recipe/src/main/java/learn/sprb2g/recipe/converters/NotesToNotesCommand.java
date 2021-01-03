package learn.sprb2g.recipe.converters;

import learn.sprb2g.recipe.commands.NotesCommand;
import learn.sprb2g.recipe.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {
    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if (source == null) {
            return null;
        }
        final NotesCommand cmd = new NotesCommand();
        cmd.setId(source.getId());
        cmd.setRecipeNotes(source.getRecipeNotes());
        return cmd;
    }
}
