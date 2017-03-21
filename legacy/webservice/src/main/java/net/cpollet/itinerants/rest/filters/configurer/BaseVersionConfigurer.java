package net.cpollet.itinerants.rest.filters.configurer;

import net.cpollet.itinerants.rest.Version;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Arrays;

/**
 * @author Christophe Pollet
 */
public abstract class BaseVersionConfigurer implements VersionConfigurer {
    private static final String VERSION_NOT_SUPPORTED = "Version %s not supported. Supported versions are: %s";

    protected Version decodeVersion(String version) {
        if (version == null) {
            return null;
        }

        return Arrays.stream(Version.values())
                .filter(v -> v.version.equals(version))
                .findFirst()
                .orElseThrow(() -> new WebApplicationException(String.format(VERSION_NOT_SUPPORTED, version, Arrays.toString(Version.orderedValues())),
                        Response.status(406).build()));
    }

    protected Version decodeVersionIfPossible(String version) {
        return Arrays.stream(Version.values())
                .filter(v -> v.version.equals(version))
                .findFirst()
                .orElse(null);
    }
}
