package de.interseroh.report.domain.visitors;

import java.util.List;

import de.interseroh.report.domain.ParameterFormUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.Errors;

import de.interseroh.report.domain.ParameterForm;
import de.interseroh.report.domain.ScalarParameter;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class MutablePropertyValueBuilder {

	private final ConversionService conversion;

	private final Errors errors;

	public MutablePropertyValueBuilder(ConversionService conversionService,
			Errors errors) {
		this.conversion = conversionService;
		this.errors = errors;
	}

	public MutablePropertyValues build(final ParameterForm form) {
		final MutablePropertyValues mpv = new MutablePropertyValues();

		form.accept(new AbstractParameterVisitor() {
			@Override
			public <T> void visit(ScalarParameter<T> parameter) {
				String name = parameter.getName();
                String propertyPath = ParameterFormUtils.nameToPath(name);
				Class<T> valueType = parameter.getValueType();

				if (form.getRequestParameters().containsKey(name)) {
					addValues(name, propertyPath, valueType);
				} else
					if (form.getRequestParameters().containsKey('_' + name)) {
					addValues('_' + name, propertyPath, valueType);
				}
			}

			private <T> void addValues(String name, String propertyPath, Class<T> valueType) {

				List<String> values = form.getRequestParameters().get(name);

				for (String requestValue : values) {
					convertAndAddPropertyValue(propertyPath, valueType, requestValue);
				}
			}

			private <T> void convertAndAddPropertyValue(String propertyPath,
					Class<T> valueType, String requestValue) {
				if (conversion.canConvert(String.class, valueType)) {
					try {
						T value = conversion.convert(requestValue, valueType);
						mpv.add(propertyPath, value);
					} catch (ConversionException ce) {
						// TODO idueppe - here we need a more user friendly
						// error code with parameters
						errors.rejectValue(propertyPath,
								"conversion.error.unkown_format",
								ce.getMessage());
					}
				} else {
					errors.rejectValue(propertyPath, "conversion.error.unknown_type",
							"No converter for " + valueType.getName());
				}
			}
		});

		return mpv;
	}

}
