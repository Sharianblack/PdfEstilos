package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.LoginService;
import services.LoginServiceImplement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet({"/login", "/login.html"}) //este servlet responde a estas url
public class LoginServlet extends HttpServlet {
    final static String USERNAME = "adri";
    final static String PASSWORD = "12345";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginService auth = new LoginServiceImplement();
        Optional<String> usernameOptional = auth.getUsername(req);

        if (usernameOptional.isPresent()) {
            HttpSession session = req.getSession();
            //recuperar el contador de la sesion
            Integer contador = (Integer) session.getAttribute("contador");

            //si no existe, se inicializa en 1
            if (contador == null) {
                contador = 1;
            } else {
                //si ya existe, lo incrementamos
                contador++;
            }
            //guardamos el nuevo valor
            session.setAttribute("contador", contador);

            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Hola " + usernameOptional.get() + "</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Hola " + usernameOptional.get() + " has iniciado sesion correctamente</h1>");
                out.println("<p>Has iniciado sesión <span class='admin'>" + contador + "</span> veces.</p>");
                out.println("<p><a href='" + req.getContextPath() + "/index.html'Volver a login</a></p>");
                out.println("<p><a href='" + req.getContextPath() + "/Logout'Cerrar Sesion</a></p>");
                out.println("</body>");
                out.println("</html>");
            }
        }else {
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            resp.sendRedirect(req.getContextPath() + "/login.html");
        } else  {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos no esta autorizado para ingresar a esta pagina!");
        }
    }
}
