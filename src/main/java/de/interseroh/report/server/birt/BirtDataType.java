package de.interseroh.report.server.birt;

public enum BirtDataType {

    TYPE_ANY(0),
    TYPE_STRING(1),
    TYPE_FLOAT(2),
    TYPE_DECIMAL(3),
    TYPE_DATE_TIME(4),
    TYPE_BOOLEAN(5),
    TYPE_INTEGER(6),
    TYPE_DATE(7),
    TYPE_TIME(8);

    private int dataType;

    BirtDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getType() {
        return dataType;
    }

    public static BirtDataType valueOf(int dataType) {
        for (BirtDataType type : BirtDataType.values()) {
            if (type.dataType == dataType)
                return type;

        }
        throw new UnknownDataTypeException(dataType);
    }

}
