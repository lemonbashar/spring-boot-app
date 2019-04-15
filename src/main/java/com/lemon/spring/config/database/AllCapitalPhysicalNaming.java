package com.lemon.spring.config.database;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class AllCapitalPhysicalNaming extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        name = super.toPhysicalCatalogName(name, context);
        return defaultNaming(name, context);
    }

    private Identifier defaultNaming(Identifier name, JdbcEnvironment context) {
        //name= super.toPhysicalTableName(name, context);
        if (name == null || name.getText() == null) return name;
        return new Identifier(name.getText().toUpperCase(), name.isQuoted());
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        name = super.toPhysicalSchemaName(name, context);
        return defaultNaming(name, context);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        name = super.toPhysicalTableName(name, context);
        return defaultNaming(name, context);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        name = super.toPhysicalSequenceName(name, context);
        return defaultNaming(name, context);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        name = super.toPhysicalColumnName(name, context);
        return defaultNaming(name, context);
    }
}
