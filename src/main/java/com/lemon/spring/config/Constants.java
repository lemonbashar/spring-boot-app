package com.lemon.spring.config;

public final class Constants {
    public static final String GLOBAL_MESSAGE = "MESSAGE";

    public static final String PROFILE_DEVELOPMENT = "dev";
    public static final String PROFILE_PRODUCTION = "prod";
    public static final String PROFILE_FAST = "fast";
    public static final String PROFILE_CLOUD = "cloud";

    /*Application Session-Policy*/
    public static final String PROFILE_STATELESS = "stateless";
    public static final String PROFILE_STATEFUL = "stateful";
    public static final String PROFILE_STATELESS_STATEFUL = "both";
    public static final String PROFILE_BOTH = PROFILE_STATELESS_STATEFUL;

    /*Database*/
    public static final String PROFILE_MYSQL = "mysql";
    public static final String PROFILE_POSTGRE = "postgre";


    /*Remote Config*/
    public static final String PROFILE_REMOTE = "remote";
    public static final String PROFILE_RMI = "rmi";
    public static final String PROFILE_HTTP_INVOKER = "http";
    public static final String PROFILE_HESSIAN = "hessian";

    /*Messanging and Web-Socket*/
    public static final String PROFILE_JMS = "jms";
    public static final String PROFILE_MESSANGING = "ms";
}
