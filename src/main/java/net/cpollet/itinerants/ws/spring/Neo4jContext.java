package net.cpollet.itinerants.ws.spring;

import net.cpollet.itinerants.ws.da.neo4j.data.Neo4jEntityPackage;
import net.cpollet.itinerants.ws.da.neo4j.repositories.Neo4jRepositoriesPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by cpollet on 11.02.17.
 */
@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories(basePackageClasses = {Neo4jRepositoriesPackage.class})
@EntityScan(basePackageClasses = {Neo4jEntityPackage.class})
public class Neo4jContext {
    // nothing
}
