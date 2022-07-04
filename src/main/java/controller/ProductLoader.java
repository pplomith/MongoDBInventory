package controller;

import model.ProductDAO;
import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ProductLoader")
public class ProductLoader extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductDAO tDato = new ProductDAO();
        JSONObject jsonObject = tDato.getDataFromDB();
        if (jsonObject != null) {
            request.setCharacterEncoding("utf8");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(jsonObject.toString());
        } else {
            request.setCharacterEncoding("utf8");
            response.setContentType("text/plain");
            response.getWriter().write("Server Error");
        }
    }


}
