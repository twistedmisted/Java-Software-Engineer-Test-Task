package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DataSourceBasedMultiTenantConnectionProviderImpl
        extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private static final String DEFAULT_TENANT_ID = "public";
    private final TenantDataSource tenantDataSource;

    @Override
    protected DataSource selectAnyDataSource() {
        return tenantDataSource.getDataSource(DEFAULT_TENANT_ID);
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        DataSource dataSource = tenantDataSource.getDataSource(tenantIdentifier);
        return dataSource != null ? dataSource : tenantDataSource.getDataSource(DEFAULT_TENANT_ID);
    }

}
