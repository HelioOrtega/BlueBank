/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOMAIN.Interfaces;

import DOMAIN.DTO.Account;
import java.sql.Connection;

/**
 *
 * @author helio
 */
public interface IAccount {
    public Boolean registerAccount(Account acc, Connection conn);
}
