package com.gordonfromblumberg.games.core.common.utils;

public class StringUtils {
    static final String PLACE_HOLDER = "#";

    private StringUtils() {}

    public static String format(String template, Object... args) {
        if (args == null || args.length == 0) {
            if (template.contains(PLACE_HOLDER)) {
                throw new IllegalArgumentException("No arguments passed for template '" + template + "'");
            } else {
                return template;
            }
        }

        String[] parts = template.split(PLACE_HOLDER, -1);
        if (parts.length - 1 != args.length) {
            throw new IllegalArgumentException("Incorrect number of arguments: " + args.length
                    + " passed for template '" + template + "'");
        }

        StringBuilder result = new StringBuilder(parts[0]);
        for (int i = 0; i < args.length; i++) {
            result.append(args[i]).append(parts[i + 1]);
        }

        return result.toString();
    }

    public static String padLeft(int number, int pad) {
        StringBuilder result = new StringBuilder(String.valueOf(number));
        while (result.length() < pad)
            result.insert(0, "0");

        return result.toString();
    }
}
