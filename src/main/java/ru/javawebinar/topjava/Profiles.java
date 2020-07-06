package ru.javawebinar.topjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Profiles {
    private static Logger log = LoggerFactory.getLogger("");
    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    public static final String REPOSITORY_IMPLEMENTATION = DATAJPA;

    public static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb";

    //  Get DB profile depending of Implementation in classpath
    public static String getActiveImplProfile() {
        if (new File("/Users/taras/Downloads/topjava/src/main/java/ru/javawebinar/topjava/repository/datajpa").exists()) {
            return DATAJPA;
        }
        if (new File("/Users/taras/Downloads/topjava/src/main/java/ru/javawebinar/topjava/repository/jpa").exists()) {
            return JPA;
        }
        if (new File("/Users/taras/Downloads/topjava/src/main/java/ru/javawebinar/topjava/repository/jdbc").exists()) {
            return JDBC;
        }
        //return "Could not find Repository directory";
        return JDBC;
    }

    //  Get DB profile depending of DB driver in classpath
    public static String getActiveDbProfile() {
        try {
            Class.forName("org.postgresql.Driver");
            return POSTGRES_DB;
        } catch (ClassNotFoundException ex) {
            try {
                Class.forName("org.hsqldb.jdbcDriver");
                return HSQL_DB;
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Could not find DB driver");
            }
        }
    }

    public static String[] getProfiles() {
          return new String[]{getActiveImplProfile(),getActiveDbProfile()};
    }
}
