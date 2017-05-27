package net.cpollet.itinerants.da.neo4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by cpollet on 27.05.17.
 */
@Configuration
@SpringBootApplication
@EnableTransactionManagement
@EnableNeo4jRepositories(basePackages = "net.cpollet.itinerants.da.neo4j")
@EntityScan(basePackages = "net.cpollet.itinerants.da.neo4j")
@ComponentScan(basePackages = "net.cpollet.itinerants.da.neo4j.repositories")
public class Context {
    @Autowired
    private Neo4jProperties neo4jProperties;

    @PostConstruct
    public void configureNeo4j() throws IOException {
        Properties properties = new Properties();
        properties.load(Context.class.getClassLoader().getResourceAsStream("application.properties"));

        if (neo4jProperties.getUri().contains("${integration.neo4j.port}")) {
            neo4jProperties.setUri(properties.getProperty("default.data.neo4j.uri"));
        }
    }
}
