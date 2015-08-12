package de.interseroh.report.domain;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterFormUtils {

    public static String nameToPath(String parameterName) {
        return "params["+parameterName+"].value";
    }
}
