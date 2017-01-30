/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOMAIN.Interfaces;

import DOMAIN.DTO.Account;
import java.sql.Connection;

public interface IOperation {
    public Boolean deposit(Account acc, Double value, Connection conn);
    public Boolean transfer(Account firstAcc, Account secondAcc, double value, Connection conn);
    public Double verifyBalance(Account acc, Connection conn);
}
