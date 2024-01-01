package nuricanozturk.dev.service.search.flight.util;

import java.text.Normalizer;

/**
 * StringNormalization is a utility class providing a method to normalize and convert strings.
 * This class is specifically used for processing strings to ensure they conform to a standard format,
 * particularly useful in scenarios where string matching or consistency is important.
 * This class is not meant to be instantiated.
 */
public final class StringNormalization
{
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private StringNormalization()
    {
    }

    /**
     * Converts and normalizes a given string. The conversion includes changing Turkish characters to their English counterparts,
     * removing accents, converting to uppercase, and replacing spaces with underscores.
     *
     * @param str The string to be converted and normalized.
     * @return The normalized string.
     */
    public static String convert(String str)
    {
        var converted = str.toUpperCase()
                .replace('ğ', 'g')
                .replace('ü', 'u')
                .replace('ş', 's')
                .replace('ı', 'i')
                .replace('ö', 'o')
                .replace('ç', 'c')
                .replace('Ğ', 'G')
                .replace('Ü', 'U')
                .replace('Ş', 'S')
                .replace('İ', 'I')
                .replace('Ö', 'O')
                .replace('Ç', 'C');

        return Normalizer
                .normalize(converted, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .trim()
                .toUpperCase()
                .replaceAll("\\s+", "_");
    }

}
