/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Address;
import entity.Book;
import entity.Customer;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jsoncreator.BookJsonBuilder;
import jsoncreator.CustomerJsonBuilder;
import jsoncreator.UserJsonBuilder;
import session.AddressFacade;
import session.BookFacade;
import session.CustomerFacade;
import session.UserFacade;
import util.EncryptPass;

/**
 *
 * @author user
 */
@WebServlet(name = "LoginController",  urlPatterns = {
    "/loginJson",
    "/logoutJson",
    "/getListNewBooks",
    "/createCustomerJson",
    
    
})
public class LoginController extends HttpServlet {
    @EJB private BookFacade bookFacade;
    @EJB private UserFacade userFacade;
    @EJB private AddressFacade addressFacade;
    @EJB private CustomerFacade customerFacade;
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
        request.setCharacterEncoding("UTF-8");
        JsonArrayBuilder jab = Json.createArrayBuilder();
        JsonObjectBuilder job = Json.createObjectBuilder();
        HttpSession session = request.getSession(false);
        String json = "";
        String path = request.getServletPath();
        switch (path) {
            case "/loginJson":
                JsonReader jsonReader = Json.createReader(request.getReader());
                JsonObject jsonObject = jsonReader.readObject();
                String login = jsonObject.getString("login");
                String password = jsonObject.getString("password");
                
                if("".equals(login) || login == null
                       || "".equals(password) || password == null ){
                    job.add("authStatus", "false");
                    try(Writer writer =new StringWriter()) {
                        Json.createWriter(writer).write(job.build());
                        json = writer.toString(); 
                      }
                    break;
                }   
                User user = userFacade.findByLogin(login);
                if(user == null){
                    job.add("authStatus", "false");
                    try(Writer writer =new StringWriter()) {
                        Json.createWriter(writer).write(job.build());
                        json = writer.toString(); 
                      }
                    break;
                }
                EncryptPass ep = new EncryptPass();
                String criptPass = ep.getEncryptPass(password, user.getSalts());
                if(!criptPass.equals(user.getPassword())){
                    job.add("authStatus", "false");
                    try(Writer writer =new StringWriter()) {
                        Json.createWriter(writer).write(job.build());
                        json = writer.toString(); 
                      }
                    break;
                }
                session = request.getSession(true);
                session.setAttribute("user", user);
                UserJsonBuilder ujb = new UserJsonBuilder();
                job.add("authStatus", "true")
                        .add("user", ujb.createJsonObject(user))
                        .add("token", session.getId());
                
                try(Writer writer =new StringWriter()) {
                  Json.createWriter(writer).write(job.build());
                  json = writer.toString(); 
                }
                break;
            case "/logoutJson":
                json = "";
                if(session != null){
                    session.invalidate();
                }
                job.add("authStatus", "false")
                        .add("user", "null")
                        .add("token","null");
                try(Writer writer =new StringWriter()) {
                    Json.createWriter(writer).write(job.build());
                    json = writer.toString(); 
                    
                  }
                break;
             case "/getListNewBooks":
                List<Book> listNewBooks = bookFacade.findNewBooks();
                BookJsonBuilder bookJsonBuilder = new BookJsonBuilder();
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                for(Book book : listNewBooks){
                    arrayBuilder.add(bookJsonBuilder.createJsonObject(book));
                }
                JsonObjectBuilder jsonBooksBuilder = Json.createObjectBuilder();
                jsonBooksBuilder.add("books", arrayBuilder);
                try(Writer writer =new StringWriter()) {
                  Json.createWriter(writer).write(jsonBooksBuilder.build());
                  json = writer.toString(); 
                }
                break; 
             case "/createCustomerJson":
                jsonReader = Json.createReader(request.getReader());
                jsonObject = jsonReader.readObject();
                String firstname = jsonObject.getString("firstname");
                String lastname = jsonObject.getString("lastname");
                String cantry = jsonObject.getString("cantry");
                login = jsonObject.getString("login");
                password = jsonObject.getString("password");
                Address address = new Address(cantry);
                addressFacade.create(address);
                List<Address> listAddreses = new ArrayList<>();
                listAddreses.add(address);
                Customer customer = new Customer(
                        firstname, 
                        lastname,  
                        listAddreses 
                        );
                customerFacade.create(customer);
                //Здесь шифруем пароль!
                ep = new EncryptPass();
                String salts = ep.getSalts();
                String cryptPassword = ep.getEncryptPass(password, salts);
                user = new User(login, cryptPassword, salts, true, customer);
                userFacade.create(user);
                ujb = new UserJsonBuilder();
                job.add("actionStatus", "true")
                        .add("user", ujb.createJsonObject(user));
                try(Writer writer =new StringWriter()) {
                  Json.createWriter(writer).write(job.build());
                  json = writer.toString(); 
                }
                break;
        }
        if(json != ""){
            try (PrintWriter out = response.getWriter()) {
              out.println(json);        
            }
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
