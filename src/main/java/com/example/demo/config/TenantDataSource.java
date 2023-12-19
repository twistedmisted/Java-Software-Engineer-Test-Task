package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class TenantDataSource {

    private Map<String, DataSource> dataSources = new HashMap<>();

    public DataSource getDataSource(String tenantId) {
        if (dataSources.get(tenantId) != null) {
            return dataSources.get(tenantId);
        }
        DataSource dataSource = createDataSource(tenantId);
        if (dataSource != null) {
            dataSources.put(tenantId, dataSource);
        }
        return dataSource;
    }

    private DataSource createDataSource(String tenantId) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            DbConfiguration db = restTemplate.getForObject("http://localhost:8080/api/v1/db-configurations/" + tenantId, DbConfiguration.class);
            return DataSourceBuilder.create()
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .username(db.getUsername())
                    .password(db.getPassword())
                    .url(db.getUrl() + db.getName()).build();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(NOT_FOUND, "Can't find tenantId = [" + tenantId + "]");
        }
    }

    @Data
    private static class DbConfiguration {
        private String tenantId;
        private String url;
        private String name;
        private String username;
        private String password;
    }
}
