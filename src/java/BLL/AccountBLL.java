/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import DOMAIN.DTO.Account;
import DOMAIN.Interfaces.IAccount;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author helio
 */
public class AccountBLL implements IAccount {

    public Boolean registerAccount(Account acc, Connection conn) {
        Boolean isExistent = false;
        PreparedStatement stmt = null;
        try {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT cpf, agencia, numero FROM conta WHERE cpf = ? OR agencia = ? OR numero = ?;");
            checkStmt.setString(1, acc.getCpf());
            checkStmt.setInt(2, acc.getAgency());
            checkStmt.setInt(3, acc.getNumber());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                isExistent = true;
            } else {
                stmt = conn.prepareStatement("INSERT INTO conta VALUES (NULL, ?, ?, ?);");
                stmt.setString(1, acc.getCpf());
                stmt.setInt(2, acc.getAgency());
                stmt.setInt(3, acc.getNumber());
            }
            checkStmt.close();
            int rows = stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isExistent;
    }

}
