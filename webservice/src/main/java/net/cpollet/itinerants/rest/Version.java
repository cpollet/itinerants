package net.cpollet.itinerants.rest;

/**
 * @author Christophe Pollet
 */
public enum Version {
    V_1("v1");

    public final String version;

    Version(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return version;
    }
}
