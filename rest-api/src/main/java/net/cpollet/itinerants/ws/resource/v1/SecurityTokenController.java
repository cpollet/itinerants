package net.cpollet.itinerants.ws.resource.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cpollet on 14.03.17.
 */
@RestController
@RequestMapping("/securityToken")
public class SecurityTokenController {
    @GetMapping
    public String get() {
        return "token";
    }
}
