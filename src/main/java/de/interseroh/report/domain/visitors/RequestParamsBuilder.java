package de.interseroh.report.domain.visitors;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.core.convert.ConversionService;

import de.interseroh.report.domain.Parameter;
import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.domain.ScalarParameter;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class RequestParamsBuilder implements ParameterVisitor {

	private List<String> params = new ArrayList<>();

	private ConversionService conversionService;

    public RequestParamsBuilder(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

    /**
     * @param parameters
     * @return encoded request parameters
     */
    public String asRequestParams(Collection<Parameter> parameters) {
        parse(parameters);
        return conjoin();
    }

    private void parse(Collection<Parameter> parameters) {
        for (Parameter parameter : parameters) {
            parameter.accept(this);
        }
    }

    private String conjoin() {
        StringBuilder builder = new StringBuilder();
        for (String param : params) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(param);
        }
        return (builder.length() > 0) ? "?" + builder.toString() : "";
    }

	@Override
	public <T> void visit(ScalarParameter<T> parameter) {
		String paramName = parameter.getName();

		if (parameter.isMultiValue()) {
			for (T paramValue : (T[]) parameter.getValue()) {
				addKeyValueParam(paramName, paramValue);
			}
		} else {
			addKeyValueParam(paramName, parameter.getValue());
		}
	}

	private <T> void addKeyValueParam(String paramName, T paramValue) {
        if (paramValue != null) {
            String stringValue = conversionService.convert(paramValue,
                    String.class);
            params.add(paramName + "=" + urlEncode(stringValue));
        }
	}

	@Override
	public void visit(ParameterGroup group) {
		for (ScalarParameter parameter : group.getParameters()) {
			visit(parameter);
		}
	}

	protected String urlEncode(String text) {
		try {
			return URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(
					"Report Parameter Encoding did not work.", e);
		}
	}
}
