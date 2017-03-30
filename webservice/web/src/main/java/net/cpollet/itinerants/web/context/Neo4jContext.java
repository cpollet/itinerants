package net.cpollet.itinerants.web.context;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by cpollet on 11.02.17.
 */
@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories(basePackages = "net.cpollet.itinerants.da.neo4j")
@EntityScan(basePackages = "net.cpollet.itinerants.da.neo4j")
public class Neo4jContext {
    // nothing
}
