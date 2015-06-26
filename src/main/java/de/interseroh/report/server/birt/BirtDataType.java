package de.interseroh.report.server.birt;

public enum BirtDataType {

    SCALAR_PARAMETER(0),
    FILTER_PARAMETER(1),
    LIST_PARAMETER(2),
    TABLE_PARAMETER(3),
    PARAMETER_GROUP(4),
    CASCADING_PARAMETER_GROUP(5);

    private int parameterType;

    BirtDataType(int parameterType) {
        this.parameterType = parameterType;
    }

    public int getType() {
        return parameterType;
    }

    public static BirtDataType valueOf(int parameterType) {
        for (BirtDataType type : BirtDataType.values()) {
            if (type.parameterType == parameterType)
                return type;

        }
        return null; // FIXME - Throw UnkknownParameterType Exception
    }

}
