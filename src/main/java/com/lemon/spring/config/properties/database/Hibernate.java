package com.lemon.spring.config.properties.database;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Hibernate {
    public String hbm2DDLAuto="update";
    public String dialect;
    public boolean formatSQL=false;
    public boolean comments=false;
    public boolean showSql=false;

    public String getHbm2DDLAuto() {
        return hbm2DDLAuto;
    }

    public void setHbm2DDLAuto(String hbm2DDLAuto) {
        this.hbm2DDLAuto = hbm2DDLAuto;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public boolean isFormatSQL() {
        return formatSQL;
    }

    public void setFormatSQL(boolean formatSQL) {
        this.formatSQL = formatSQL;
    }

    public boolean isComments() {
        return comments;
    }

    public void setComments(boolean comments) {
        this.comments = comments;
    }

    public boolean isShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }
}
