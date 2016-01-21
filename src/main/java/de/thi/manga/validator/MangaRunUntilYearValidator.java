package de.thi.manga.validator;

import de.thi.manga.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import java.util.Calendar;

@Named
@RequestScoped
public class MangaRunUntilYearValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value.toString().isEmpty()) {
            return; //ongoing
        }

        try {
            int untilYear = Integer.parseInt(value.toString());
            if (untilYear == 0) {
                return; //ongoing
            }

            UIInput runFromYear = (UIInput) uiComponent.getAttributes().get("runFromYear");
            int fromYear = Integer.parseInt(runFromYear.getValue().toString());

            validateValidYearCombination(untilYear, fromYear);
        } catch (NumberFormatException e) {
            throw new ValidatorException(new FacesMessage(
                    new Messages().getString("de.thi.manga.validator.MangaRunUntilYearValidator.invalidYear")
            ));
        }
    }

    private void validateValidYearCombination(int untilYear, int fromYear) {
        if (untilYear < fromYear) {
            throw new ValidatorException(new FacesMessage(
                    new Messages().getString("de.thi.manga.validator.MangaRunUntilYearValidator.untilYearBeforeFromYear")
            ));
        }
        if (untilYear > Calendar.getInstance().get(Calendar.YEAR)) {
            throw new ValidatorException(new FacesMessage(
                    new Messages().getString("de.thi.manga.validator.MangaRunUntilYearValidator.untilYearInFuture")
            ));
        }
    }
}
