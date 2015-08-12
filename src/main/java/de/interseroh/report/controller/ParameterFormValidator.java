package de.interseroh.report.controller;

import de.interseroh.report.domain.ParameterFormUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import de.interseroh.report.domain.ParameterForm;
import de.interseroh.report.domain.ScalarParameter;
import de.interseroh.report.domain.visitors.AbstractParameterVisitor;
import de.interseroh.report.domain.visitors.MutablePropertyValueBuilder;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@Component
public class ParameterFormValidator implements Validator {

	@Autowired
	private ConversionService conversionService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(ParameterForm.class);
	}

	@Override
	public void validate(Object target, final Errors errors) {
		final ParameterForm parameterForm = (ParameterForm) target;
		convertAndBindRequestParameters(errors, parameterForm);

		parameterForm.accept(new AbstractParameterVisitor() {
			@Override
			public <T> void visit(ScalarParameter<T> parameter) {
                if (!parameter.isValid()) {
                    // TODO idueppe - provide a valid
                    String propertyPath = ParameterFormUtils.nameToPath(parameter.getName());
                    errors.rejectValue(propertyPath,"javax.validation.constraints.NotEmpty.message", "Es muss ein Wert angegeben werden.");
                }
			}
		});
	}

	private void convertAndBindRequestParameters(Errors errors,
			ParameterForm parameterForm) {
		MutablePropertyValues mpvs = new MutablePropertyValueBuilder(
				conversionService, errors).build(parameterForm);
		new DataBinder(parameterForm).bind(mpvs);
	}
}
