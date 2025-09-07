package net.splodgebox.eliteapi.engine.parsing;

import java.util.HashMap;
import java.util.Map;

public final class EffectSpecParser {
    public static EffectSpec parse(String line) {
        String condition = extract(line, "<condition>", "</condition>");
        if (condition != null) line = line.replaceAll("(?s)<condition>.*?</condition>", "").trim();

        String targetToken = "@Self";
        int at = line.lastIndexOf('@');
        if (at >= 0) {
            targetToken = line.substring(at).trim();
            line = line.substring(0, at).trim();
        }

        String[] head = line.split("\\s+", 2);
        String name = head[0].trim().toUpperCase();

        Map<String, String> params = new HashMap<>();
        if (head.length > 1) for (String token : head[1].split(",")) {
            token = token.trim();
            if (token.isEmpty()) continue;
            String[] kv = token.split("=", 2);
            if (kv.length == 2) params.put(kv[0].toLowerCase(), kv[1]);
        }
        return new EffectSpec(name, params, targetToken, condition);
    }

    private static String extract(String s, String a, String b) {
        int i = s.indexOf(a), j = s.indexOf(b);
        return (i >= 0 && j > i) ? s.substring(i + a.length(), j).trim() : null;
    }
}