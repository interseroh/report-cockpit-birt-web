package de.interseroh.report.domain;

import de.interseroh.report.domain.visitors.ParameterVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class SelectionParameter<T> extends AbstractScalarParameter<SelectionParameter, T> {

    private List<SelectionOption> options;

    public SelectionParameter(Class<T> valueType) {
        super(valueType);
    }

    public List<SelectionOption> getOptions() {
        if (options == null) {
            options = new ArrayList<>(10);
        }
        return options;
    }

    public void setOptions(List<SelectionOption> options) {
        this.options = options;
    }

    public SelectionParameter withOptions(final List<SelectionOption> options) {
        this.options = options;
        return this;
    }

    @Override
    public String toString() {
        return super.toString() + "\n\t {" + "options=" + options + "} ";
    }

    @Override
    public void accept(ParameterVisitor visitor) {
        visitor.visit(this);
    }

    public static <T> SelectionParameter<T> newInstance(Class<T> valueType) {
        return new SelectionParameter(valueType);
    }

}
