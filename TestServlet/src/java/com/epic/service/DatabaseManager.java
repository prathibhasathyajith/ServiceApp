/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

public class DatabaseManager {

    static Connection con;
    static String url;

    public static Connection getConnection() throws FileNotFoundException, IOException {

        try {
            String url = "jdbc:mysql://192.168.1.66:3306/LVMT_DEV_2?zeroDateTimeBehavior";

            Class.forName("com.mysql.jdbc.Driver");

            try {
                con = DriverManager.getConnection(url, "duser", "password");

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

        return con;
    }

}
