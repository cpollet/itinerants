package net.cpollet.itinerants.dozer.v1;

import net.cpollet.itinerants.rest.data.v1.SessionData;
import net.cpollet.itinerants.services.data.Session;
import org.dozer.DozerConverter;

/**
 * @author Christophe Pollet
 */
public class SessionConverter extends DozerConverter<SessionData, Session> {
    public SessionConverter() {
        super(SessionData.class, Session.class);
    }

    @Override
    public Session convertTo(SessionData source, Session destination) {
        return new Session(source.rights);
    }

    @Override
    public SessionData convertFrom(Session source, SessionData destination) {
        SessionData sessionData = new SessionData();
        sessionData.rights = source.getRights();
        return sessionData;
    }
}
