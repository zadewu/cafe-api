package vn.cmax.cafe.security;

import java.util.List;

public final class UrlIgnorePattern {
    public static final List<String> IGNORE_URL_PATTERNS = List.of(
            "/promotion**",
            "/promotion/**",
            "/movie**",
            "/movie/**",
            "/category**",
            "/category/**",
            "/information**"
    );

    public static String[] toArray() {
        String[] arr = new String[IGNORE_URL_PATTERNS.size()];
        arr = IGNORE_URL_PATTERNS.toArray(arr);
        return arr;
    }
}
