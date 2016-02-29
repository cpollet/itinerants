package net.cpollet.itinerants.spring;

import net.cpollet.itinerants.core.api.PersonService;
import net.cpollet.itinerants.mocks.PersonServiceMock;
import net.cpollet.itinerants.services.InMemorySessionService;
import net.cpollet.itinerants.services.SessionService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christophe Pollet
 */
@Configuration
public class SpringRootContext {
    @Bean
    public Mapper mapper() {
        List<String> mappingFiles = new ArrayList<>();
        mappingFiles.add("dozer/personConverter.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(mappingFiles);

        return dozerBeanMapper;
    }

    @Bean
    public PersonService personService() {
        return new PersonServiceMock();
    }

    @Bean
    public SessionService sessionService() {
        return new InMemorySessionService(personService());
    }
}
