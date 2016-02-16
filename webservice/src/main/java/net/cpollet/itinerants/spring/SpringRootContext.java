package net.cpollet.itinerants.spring;

import net.cpollet.itinerants.core.api.PersonService;
import net.cpollet.itinerants.mocks.PersonServiceMock;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Christophe Pollet
 */
@Configuration
public class SpringRootContext {
    @Bean
    public Mapper mapper() {
//        List<String> mappingFiles = new ArrayList();
//        mappingFiles.add("dozerJdk8Converters.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
//        dozerBeanMapper.setMappingFiles(mappingFiles);

        return dozerBeanMapper;
    }

    @Bean
    public PersonService personService() {
        return new PersonServiceMock();
    }
}
