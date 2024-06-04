package org.project.util.constants;

import static java.lang.String.format;

public class Patterns {
    public static final String SPLITERATOR_PATTERN = "\\|";
    public static final String DATE_PATTERN = "^\\s*(3[01]|[12][\\d]|0?[1-9])\\.(1[012]|0?[1-9])\\.(20[23-99]{2})\\s*$";
    public static final String TIME_PATTERN = "^(0[\\d]|1[\\d]|2[0-3]):[0-5][\\d]$";
    public static final String GENERAL_MESSAGE_PATTERN = "^[\\p{L}\\s\\d\\p{P}\\p{S}]{0,200}";
    public static String getGeneralMessagePatternWithParameterizedStartLimit(int value) {
        return format("^[\\p{L}\\s\\d\\p{P}\\p{S}]{%d,200}", value);
    }
}
