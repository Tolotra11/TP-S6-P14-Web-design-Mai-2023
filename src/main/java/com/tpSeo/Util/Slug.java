package com.tpSeo.Util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class Slug {
    private static Pattern NOLATTIN =Pattern.compile("[^\\w_-]");
    private static Pattern SEPARATORS = Pattern.compile("[\\s\\p{Punct}&&[^-]]");
    public static String makeSlug(String titre){
        String noseparators = SEPARATORS.matcher(titre).replaceAll("-");
        String normalized = Normalizer.normalize(noseparators, Normalizer.Form.NFD);
        String slug  = NOLATTIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH).replaceAll("-{2,}", "-").replaceAll("^-|-$", "");
    }
}
