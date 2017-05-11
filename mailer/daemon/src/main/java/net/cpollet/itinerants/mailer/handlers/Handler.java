package net.cpollet.itinerants.mailer.handlers;

import net.cpollet.itinerants.mailer.Notification;

/**
 * Created by cpollet on 11.05.17.
 */
public interface Handler {
    void handle(Notification notification);
}
