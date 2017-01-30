/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DOMAIN.DTO.Account;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author helio
 */
public class DataAccess {

    public Connection conn;
    private String driver;
    private String host;
    private String user;
    private String password;

    public DataAccess() throws SQLException, ClassNotFoundException {
        this.driver = "org.mariadb.jdbc.Driver";
        this.host = "jdbc:mariadb://localhost:3306/bluebank";
        this.user = "root";
        this.password = "";
        Class.forName(this.driver);
        this.conn = DriverManager.getConnection(this.host, this.user, this.password);
    }

    public ResultSet getStuff() throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "SELECT id, cpf, agencia, numero FROM conta";
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }

}
