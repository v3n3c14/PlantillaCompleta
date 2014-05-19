package mx.ipn.escom.ema.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.ipn.escom.ema.services.UsersService;
import mx.ipn.escom.ema.services.impl.UserServiceimpl;
import mx.ipn.escom.ema.to.UsersTO;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class VisualizationServlet extends HttpServlet{
	
	@Override	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();// Obtiene la sesion de google
	    System.out.println("context");
	   
	}
	@Override 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	

}
