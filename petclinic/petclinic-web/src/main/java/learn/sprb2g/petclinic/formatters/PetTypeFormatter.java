package learn.sprb2g.petclinic.formatters;

import learn.sprb2g.petclinic.model.PetType;
import learn.sprb2g.petclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Set;

/**
 * Instructs Spring MVC on how to parse and print elements of type 'PetType'.
 */
@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    /**
     * Parse a text String to produce a PetType.
     *
     * @param text   the text string
     * @param locale the current user locale
     * @return an instance of PetType
     * @throws IllegalArgumentException when a parse exception occurs
     */
    @Override
    @NonNull
    public PetType parse(@NonNull String text, @SuppressWarnings("NullableProblems") Locale locale) {
        Set<PetType> all = petTypeService.findAll();
        for (PetType type : all) {
            if (type.getName().equals(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("PetType not found for text: " + text);
    }

    /**
     * Print the object of type PetType for display.
     *
     * @param petType the instance to print
     * @param locale the current user locale
     * @return the printed text string
     */
    @Override
    @NonNull
    public String print(PetType petType, @SuppressWarnings("NullableProblems") Locale locale) {
        return petType.getName();
    }
}
