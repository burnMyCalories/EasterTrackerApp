/*
 * Copyright (c) 2020.  EasterTracker App
 * Group: Burn My Calories
 * Author: Binbin Tang , Jia Zhu , Quan Zhou , Weilun Chen , Xinnan Shen , and Zongdong Liu
 * Project 2 for COMP90018, 2020 S2
 * Time: 2020/10/22 23:24.
 * Usage: The Utility Code for database connection
 */

package com.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            try {
                Properties prop = new Properties();
                InputStream inputStream = DBUtils.class.getClassLoader().getResourceAsStream("/db.properties");
                prop.load(inputStream);
                String driver = prop.getProperty("driver");
                String url = prop.getProperty("url");
                String user = prop.getProperty("user");
                String password = prop.getProperty("password");
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                return connection;
            }

        }

    }
}
