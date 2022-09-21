package in.dudeapp.userservice.config;

import in.dudeapp.common.dto.DatabaseProperties;
import in.dudeapp.common.service.AWSSecretsMangerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * Created by mohan on 27/07/22
 */
@Configuration
@Profile(value = "dev")
public class DataSourceConfig {

    @Value("${aws.secretsmanager.db.secret}")
    public String secretName;

    private final AWSSecretsMangerService awsSecretsMangerService;

    public DataSourceConfig(AWSSecretsMangerService awsSecretsMangerService) {
        this.awsSecretsMangerService = awsSecretsMangerService;
    }

    @Bean
    public DataSource dataSource() {
        DatabaseProperties databaseProperties=this.awsSecretsMangerService.getDataBaseProperties(secretName);
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .password(databaseProperties.getPassword())
                .username(databaseProperties.getUsername())
                .url(buildUrl(databaseProperties))
                .build();
    }

    private String buildUrl(DatabaseProperties databaseProperties){
        return "jdbc:" + databaseProperties.getEngine() +
                "://" +
                databaseProperties.getHost() +
                ":" +
                databaseProperties.getPort() +
                "/" +
                databaseProperties.getDbname() +
                "?createDatabaseIfNotExist=true";
    }
}
