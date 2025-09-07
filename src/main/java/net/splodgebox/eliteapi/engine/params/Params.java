package net.splodgebox.eliteapi.engine.params;

import org.bukkit.Material;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Params {
    private final Map<String, String> parameters;

    public Params(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public int getInt(String key, int def) {
        try {
            return Integer.parseInt(parameters.getOrDefault(key, String.valueOf(def)));
        } catch (Exception e) {
            return def;
        }
    }

    public long getLong(String key, long def) {
        try {
            return Long.parseLong(parameters.getOrDefault(key, String.valueOf(def)));
        } catch (Exception e) {
            return def;
        }
    }

    public double getDouble(String key, double def) {
        try {
            return Double.parseDouble(parameters.getOrDefault(key, String.valueOf(def)));
        } catch (Exception e) {
            return def;
        }
    }

    public boolean getBool(String key, boolean def) {
        return Boolean.parseBoolean(parameters.getOrDefault(key, String.valueOf(def)));
    }

    public String getString(String key, String def) {
        return parameters.getOrDefault(key, def);
    }

    public Set<Material> getMaterials(String key) {
        Set<Material> out = new HashSet<>();
        for (String t : parameters.getOrDefault(key, "").split(",")) {
            if (t.isBlank()) continue;
            Material m = Material.matchMaterial(t.trim());
            if (m != null) out.add(m);
        }
        return out;
    }
}
