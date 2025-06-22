/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.User;

/**
 *
 * @author PC
 */
public abstract class BaseRequiredAuthenticationController extends HttpServlet{
    private User getAuthenticated(HttpServletRequest req){
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        return user;
    }
    
    protected abstract void 
        doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException;
    
        protected abstract void 
        doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException;
        
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getAuthenticated(req);
        if (user == null){
            resp.sendError(403, "You have not yet authenticated!");
        }else{
            //
            doPost(req, resp, user);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getAuthenticated(req);
        if (user == null){
            resp.sendError(403, "You have not yet authenticated!");
        }else{
            //
            doPost(req, resp, user);
        }
    }
    
}
