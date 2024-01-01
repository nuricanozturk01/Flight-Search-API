package nuricanozturk.dev.service.search.flight.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import java.nio.file.Paths;

@Configuration
public class DataSourceConfig {

    @Value("${test.h2.db_name}")
    private String dbName;

    @Bean
    public DataSource dataSource() {
        String dbPath = Paths.get(System.getProperty("user.dir"), dbName).toString();
        String dataSourceUrl = "jdbc:h2:file:" + dbPath;
        System.out.println("dataSourceUrl: " + dataSourceUrl);
        return DataSourceBuilder
                .create()
                .url(dataSourceUrl)
                .driverClassName("org.h2.Driver")
                .build();
    }
}
