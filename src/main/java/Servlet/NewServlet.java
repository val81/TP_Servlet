package Servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Servlet.DAOx;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import simplejdbc.CustomerEntity;
import simplejdbc.DAO;
import simplejdbc.DAOException;
import simplejdbc.DataSourceFactory;

/**
 *
 * @author pedago
 */
public class NewServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Servlet0</title>");            
            out.println("</head>");
            out.println("<body>");
            //out.println("<h1>Servlet Servlet0 at " + request.getContextPath() + "</h1>");
            
           
            DataSource myDataSource = DataSourceFactory.getDataSource(); 
            DAO myDAO = new DAO(myDataSource); 
            DAOx myDAOx = new DAOx(myDataSource);
                
           
            List<String> States = myDAOx.State();  // Informations récupérées

            out.println("<form method=\"get\" >");   // On récupère la valeur choisie avec la méthode GET
           
            out.println("<select name=\"etats\" >");
            for(int i=0;i<States.size();i++) {
                out.println("<option>" + States.get(i) + "</option>");
            }
            out.println("</select>");
            
            out.print("  <input type=\"submit\" value=\"Valider\"  >");
            out.println("</form>");
            
            String etatChoisi = request.getParameter("etats");  // Récupération de la valeur choisie
            
            /* Affichage de tous les clients en fonction de l'état */
            List<CustomerEntity> result = myDAO.customersInState(etatChoisi);  // Informations récupérées
            
            /* Création des colonnes du tableau */
            out.println("<table border=1 width=50% height=40%>");
            out.println("<tr>");
                out.println("<th>ID</th>");
                out.println("<th>Name</th>");
                out.println("<th>Address</th>");
            out.println("</tr>");
            
            /* Table HTML : Affichage des résultats */
            for (int i=0 ;i<result.size();i++) {
                out.println("<tr>");
               
                out.println("<td>" + result.get(i).getCustomerId() + "</td>");
                out.println("<td>" + result.get(i).getName() + "</td>");
                out.println("<td>" + result.get(i).getAddressLine1() + "</td>");
                
                out.println("</tr>");
            }
            
           out.println("</table>");
            
            out.println("</body>");
            out.println("</html>");

        } catch (DAOException ex) {
            Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);       
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
