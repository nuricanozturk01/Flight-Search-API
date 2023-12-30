package nuricanozturk.dev.service.search.flight.util;

import java.text.Normalizer;

public final class StringNormalization
{
    private StringNormalization()
    {
    }

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
