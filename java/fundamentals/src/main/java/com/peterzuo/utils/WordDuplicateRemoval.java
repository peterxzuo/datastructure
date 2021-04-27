package com.peterzuo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordDuplicateRemoval {
    private WordDuplicateRemoval(){
        throw new IllegalStateException("Utility class");
    }

    private static String dupRegex;
    private static Pattern duplicatePattern;

    /* 
     * https://regex101.com/ for resources:
     * lookahead: (?=)
     * lookbehind: (?<=) 
     * backreference: \1
     * \b vs \s: \b includes start/end of line.
     */

    static {
        dupRegex = "((?<=(\\b))\\w+(?=\\b))(\\s\\1)+(?=\\b)";
        duplicatePattern = Pattern.compile(dupRegex, Pattern.CASE_INSENSITIVE);
    }

    public static String removeDuplicates(String text){
        Matcher matcher = duplicatePattern.matcher(text);
        while (matcher.find()) {
            String matched = matcher.group(1);
            text = text.replaceAll(matcher.group(0), matched);
        }
        return text;
    }
}
