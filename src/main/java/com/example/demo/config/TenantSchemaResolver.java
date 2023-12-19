package com.example.demo.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    private static final String DEFAULT_TENANT_ID = "public";

    @Override
    public String resolveCurrentTenantIdentifier() {
        return Objects.requireNonNullElse(TenantContext.getCurrentTenant(), DEFAULT_TENANT_ID);
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
