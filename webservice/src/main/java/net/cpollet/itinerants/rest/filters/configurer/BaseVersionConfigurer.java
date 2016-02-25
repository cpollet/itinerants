package net.cpollet.itinerants.rest.filters.configurer;

import net.cpollet.itinerants.rest.Version;

import java.util.Arrays;

/**
 * @author Christophe Pollet
 */
public abstract class BaseVersionConfigurer implements VersionConfigurer {
    protected Version decodeVersion(String version) {
        return Arrays.stream(Version.values())
                .filter(v -> v.version.equals(version))
                .findFirst()
                .orElse(null);
    }
}
