package com.lemon.spring.config;

public final class Constants {
    public static final String GLOBAL_MESSAGE = "MESSAGE";

    public static final String PROFILE_DEVELOPMENT="dev";
    public static final String PROFILE_PRODUCTION = "prod";
    public static final String PROFILE_FAST = "fast";
    public static final String PROFILE_CLOUD = "cloud";

    /*Application Session-Policy*/
    public static final String PROFILE_STATELESS = "stateless";
    public static final String PROFILE_STATEFUL = "stateful";
    public static final String PROFILE_STATELESS_STATEFUL = "both";
    public static final String PROFILE_BOTH = PROFILE_STATELESS_STATEFUL;


    /*Remote Config*/
    public static final String PROFILE_REMOTE = "remote";
    public static final String PROFILE_RMI = "rmi";
}
