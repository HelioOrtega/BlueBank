/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BLL.AccountBLL;
import BLL.OperationBLL;
import DOMAIN.DTO.Account;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author helio
 */
@WebServlet(name = "OperationServlet", urlPatterns = {"/OperationServlet"})
public class OperationServlet extends HttpServlet {

    public final static int DEPOSIT_OPERATION = 1;
    public final static int WITHDRAWAL_OPERATION = 2;
    public final static int TRANSFER_OPERATION = 3;
    public final static int BALANCE_OPERATION = 4;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Integer operationType = Integer.parseInt(request.getParameter("operationType").replaceAll("\\D+", ""));
        if (operationType == DEPOSIT_OPERATION) {
            String depositAgency = request.getParameter("agencia-deposito").replaceAll("\\D+", "");
            String depositAccount = request.getParameter("conta-deposito").replaceAll("\\D+", "");
            Double depositValue = Double.parseDouble(request.getParameter("valor-deposito").replaceAll("\\D+", ""));
            Account acc = new Account(depositAgency, depositAccount);
            try {
                DataAccess dataAccess = new DataAccess();
                OperationBLL optBll = new OperationBLL();
                String result = optBll.deposit(acc, depositValue, dataAccess.conn).toString();
                out.write(result);
            } catch (SQLException ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (operationType == TRANSFER_OPERATION) {
            String agencyOrigin = request.getParameter("agencia-origem").replaceAll("\\D+", "");
            String accountOrigin = request.getParameter("conta-origem").replaceAll("\\D+", "");
            String agencyDestination = request.getParameter("agencia-destino").replaceAll("\\D+", "");
            String accountDestination = request.getParameter("conta-destino").replaceAll("\\D+", "");
            Double transferValue = Double.parseDouble(request.getParameter("valor-transf").replaceAll("\\D+", ""));
            Account firstAcc = new Account(agencyOrigin, accountOrigin);
            Account secondAcc = new Account(agencyDestination, accountDestination);
            try {
                DataAccess dataAccess = new DataAccess();
                OperationBLL optBll = new OperationBLL();
                String result = optBll.transfer(firstAcc, secondAcc, transferValue, dataAccess.conn).toString();
                out.write(result);
            } catch (SQLException ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (operationType == BALANCE_OPERATION) {
            String agencyBalance = request.getParameter("agencia-saldo").replaceAll("\\D+", "");
            String accountBalance = request.getParameter("conta-saldo").replaceAll("\\D+", "");
            Account acc = new Account(agencyBalance, accountBalance);
            try {
                DataAccess dataAccess = new DataAccess();
                OperationBLL optBll = new OperationBLL();
                String result = optBll.verifyBalance(acc, dataAccess.conn).toString();
                out.write(result);
            } catch (SQLException ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
