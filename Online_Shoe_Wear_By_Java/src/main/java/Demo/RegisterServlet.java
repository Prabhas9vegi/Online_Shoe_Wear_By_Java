package Demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		 try (PrintWriter out = response.getWriter()) {
	            // Load the database driver
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // Establish the database connection
	            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineshoe", "root", "812181551");
	                 PreparedStatement ps = con.prepareStatement("INSERT INTO userregister (name, email, password, conpassword) VALUES (?, ?, ?, ?)")) {

	    			PreparedStatement pslogin = con.prepareStatement("INSERT INTO userlogin (email, password) VALUES (?, ?)");

	            	
	                // Retrieve form data
	                String n = request.getParameter("name");
	                String e = request.getParameter("email");
	                String p = request.getParameter("password");
	                String cp = request.getParameter("conpassword");

	                // Set parameters for the prepared statement
	                ps.setString(1, n);
	                ps.setString(2, e);
	                ps.setString(3, p);
	                ps.setString(4, cp);
	                
	                pslogin.setString(1, e);
	                pslogin.setString(2, p);
	                
	    			int rs = pslogin.executeUpdate();


	                // Execute the query
	                int rowsAffected = ps.executeUpdate();

	                if (rowsAffected > 0) {
	                    // Redirect to welcome page on successful registration
	                	 out.println("<script type='text/javascript'>");
	                	    out.println("alert('Registration Successful!');");
	                	    out.println("window.location.href = 'login.jsp';");
	                	    out.println("</script>");
	                } else {
	                    // Show failure message
	                	 out.println("<script type='text/javascript'>");
	                	    out.println("alert('Registration Failed!');");
	                	    out.println("window.location.href = 'register.jsp';");
	                	    out.println("</script>");
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	                out.println("<h1>Error connecting to the database!</h1>");
	            }
	        } catch (ClassNotFoundException ex) {
	            ex.printStackTrace();
	        }

		
		
	}

}
