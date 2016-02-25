package net.cpollet.itinerants.rest;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Christophe Pollet
 */
public enum Version implements Comparator<Version> {
    V_1("v1", 1);

    public final String version;
    private final int ordering;

    Version(String version, int ordering) {
        this.version = version;
        this.ordering = ordering;
    }

    @Override
    public String toString() {
        return version;
    }

    @Override
    public int compare(Version version1, Version version2) {
        return version1.ordering - version2.ordering;
    }

    public static Version[] orderedValues() {
        Version[] values = values();
        Arrays.sort(values);
        return values;
    }
}
