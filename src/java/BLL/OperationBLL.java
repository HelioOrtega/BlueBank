/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import DAO.OperationServlet;
import DOMAIN.DTO.Account;
import DOMAIN.Interfaces.IOperation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OperationBLL implements IOperation {
    
    private final String balanceSql = "SELECT "
                    + "(SELECT SUM(VALOR) FROM transacao tr "
                    + "INNER JOIN conta co on tr.idorigem = co.id "
                    + "WHERE tipo_operacao = 1 and co.agencia = ? and co.numero = ?) "
                    + "- "
                    + "(SELECT SUM(VALOR) FROM transacao tr "
                    + "INNER JOIN conta co on tr.idorigem = co.id "
                    + "WHERE tipo_operacao = 0 and co.agencia = ? and co.numero = ?) as saldoConta; ";
    
    @Override
    public Boolean deposit(Account acc, Double value, Connection conn) {
        Boolean isExistent = false;
        PreparedStatement stmt = null;
        PreparedStatement idStmt = null;
        try {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT agencia, numero FROM conta WHERE agencia = ? OR numero = ?;");
            checkStmt.setInt(1, acc.getAgency());
            checkStmt.setInt(2, acc.getNumber());
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                isExistent = true;
            } else {
                stmt = conn.prepareCall("{CALL realizarDeposito(?, ?, ?, ?)}");
                idStmt = conn.prepareStatement("SELECT id FROM conta WHERE agencia = ? AND numero = ?;");
                idStmt.setInt(1, acc.getAgency());
                idStmt.setInt(2, acc.getNumber());
                ResultSet idRs = idStmt.executeQuery();
                if (idRs.next()) {
                    stmt.setInt(1, idRs.getInt("id"));
                    stmt.setDouble(2, value);
                    stmt.setInt(3, 1);
                    stmt.setInt(4, 1);
                }

            }
            checkStmt.close();
            idStmt.close();
            stmt.executeQuery();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isExistent;
    }

    @Override
    public Boolean transfer(Account firstAcc, Account secondAcc, double value, Connection conn) {
        Boolean isExistent = false;
        PreparedStatement stmt = null;
        try {
            PreparedStatement firstCheckStmt = conn.prepareStatement("SELECT agencia, numero FROM conta WHERE agencia = ? OR numero = ?;");
            PreparedStatement secondCheckStmt = conn.prepareStatement("SELECT agencia, numero FROM conta WHERE agencia = ? OR numero = ?;");
            firstCheckStmt.setInt(1, firstAcc.getAgency());
            firstCheckStmt.setInt(2, firstAcc.getNumber());
            secondCheckStmt.setInt(1, secondAcc.getAgency());
            secondCheckStmt.setInt(2, secondAcc.getNumber());
            ResultSet firstResultSet = firstCheckStmt.executeQuery();
            ResultSet secondResultSet = secondCheckStmt.executeQuery();
            PreparedStatement balance = conn.prepareStatement(balanceSql);
            balance.setInt(1, firstAcc.getAgency());
            balance.setInt(2, firstAcc.getNumber());
            balance.setInt(3, firstAcc.getAgency());
            balance.setInt(4, firstAcc.getNumber());
            ResultSet balanceResultSet = balance.executeQuery();
            Double saldoConta = null;
            if (balanceResultSet.next()) {
                saldoConta = balanceResultSet.getDouble("saldoConta");
                if (!firstResultSet.next() && !secondResultSet.next() && saldoConta < value) {
                    isExistent = true;
                } else {
                    stmt = conn.prepareCall("{CALL realizarTransferencia(?, ?, ?, ?, ?, ?)}");
                    stmt.setInt(1, firstAcc.getAgency());
                    stmt.setDouble(2, firstAcc.getNumber());
                    stmt.setInt(3, secondAcc.getAgency());
                    stmt.setInt(4, secondAcc.getNumber());
                    stmt.setDouble(5, value);
                    stmt.setInt(6, OperationServlet.TRANSFER_OPERATION);
                }
                firstCheckStmt.close();
                secondCheckStmt.close();
                stmt.executeQuery();
                stmt.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isExistent;
    }

    @Override
    public Double verifyBalance(Account acc, Connection conn) {
        Boolean isExistent = false;
        PreparedStatement balance = null;
        Double saldoConta = null;
        try {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT agencia, numero FROM conta WHERE agencia = ? OR numero = ?;");
            checkStmt.setInt(1, acc.getAgency());
            checkStmt.setInt(2, acc.getNumber());
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                isExistent = true;
            } else {
                balance = conn.prepareStatement(balanceSql);
                balance.setInt(1, acc.getAgency());
                balance.setInt(2, acc.getNumber());
                balance.setInt(3, acc.getAgency());
                balance.setInt(4, acc.getNumber());
                ResultSet balanceResultSet = balance.executeQuery();
                if (balanceResultSet.next()) {
                    saldoConta = balanceResultSet.getDouble("saldoConta");
                }
            }
            checkStmt.close();
            balance.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saldoConta;
    }

}
