package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("cloud")
public class CloudConfiguration extends AbstractCloudConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudConfiguration.class);

    @Bean
    public DataSource dataSource() {
        final DataSource ds = connectionFactory().dataSource();
        LOGGER.info("Connecting to data source {}", ds);
        return ds;
    }

    @Bean
    public ApplicationInstanceInfo info() {
        final ApplicationInstanceInfo info = info();
        LOGGER.info("Cloud info: {}", info);
        return info;
    }
}
