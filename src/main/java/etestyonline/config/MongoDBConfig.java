package etestyonline.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;

@Configuration
@EnableMongoRepositories(basePackages = "etestyonline.repository")
@ComponentScan(basePackages = "etestyonline.service")
@PropertySource("classpath:config.properties")
public class MongoDBConfig extends AbstractMongoConfiguration {

    @Value("${mongo.database}")
    private String database;

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.port}")
    private String port;

    @Value("${mongo.userName}")
    private String userName;

    @Value("${mongo.password}")
    private String password;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    public Mongo mongo() throws Exception {
        ServerAddress serverAddress = new ServerAddress(host, Integer.valueOf(port));
        MongoCredential mongoCredential = MongoCredential.createCredential(userName,database, password.toCharArray());
        List<MongoCredential> creds = new ArrayList<>();
        creds.add(mongoCredential);
        return new MongoClient(serverAddress, creds);
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        List<String> mappings = new ArrayList<>();
        mappings.add("etestyonline.model");
        return mappings;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);

        return propertySourcesPlaceholderConfigurer;
    }
}
