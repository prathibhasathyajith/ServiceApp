/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.epic.service.DatabaseManager;

/**
 *
 * @author Darshana Expression tags is undefined on line 13, column 6 in
 * Templates/Classes/Class.java. Sep 30, 2016 Expression year is undefined on
 * line 13, column 22 in Templates/Classes/Class.java.
 */
public class UserDao {

    public static String login(String pagecode) throws NoSuchAlgorithmException, Exception {

        Connection currentCon = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String desc = "";

        String searchQuery = "select u.description "
                + "from lvmt_wb_page u "
                + "where pagecode=? ";

        try {

            currentCon = DatabaseManager.getConnection();
            ps = currentCon.prepareStatement(searchQuery);
            ps.setString(1, pagecode);

            rs = ps.executeQuery();
            while (rs.next()) {
                desc = rs.getString("u.description");
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    throw e;
                }
                rs = null;
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    throw e;
                }
                ps = null;
            }

            if (currentCon != null) {
                try {
                    currentCon.close();
                } catch (Exception e) {
                    throw e;
                }

                currentCon = null;
            }
        }

        return desc;

    }

}
