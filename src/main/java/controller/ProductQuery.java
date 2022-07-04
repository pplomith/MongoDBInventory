package controller;

import model.ProductDAO;
import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ProductQuery")
public class ProductQuery extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int flag = Integer.parseInt(request.getParameter("flag"));
        ProductDAO tDato = new ProductDAO();
        if (flag == 0) {
            String productName = request.getParameter("productName");
            String selectedValue = request.getParameter("selectedValue");
            String minPrice = request.getParameter("minPrice");
            String maxPrice = request.getParameter("maxPrice");
            String minWeight = request.getParameter("minWeight");
            String maxWeight = request.getParameter("maxWeight");

            if (productName.equals("")) {
                productName = null;
            }
            if (selectedValue.equals("null")) {
                selectedValue = null;
            }
            if (Float.parseFloat(minPrice) == 0 && Float.parseFloat(maxPrice) == 0) {
                minPrice = null;
                maxPrice = null;
            }
            if (Float.parseFloat(minWeight) == 0 && Float.parseFloat(maxWeight) == 0) {
                minWeight = null;
                maxWeight = null;
            }

            JSONObject jsonObject = tDato.getResultQuery(productName, selectedValue, minPrice, maxPrice, minWeight, maxWeight);
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
        } else if (flag == 1){

            String productName = request.getParameter("productName");
            String category = request.getParameter("category");
            String price = request.getParameter("price");
            String weight = request.getParameter("weight");
            String aboutProd = request.getParameter("aboutProd");
            String techDet = request.getParameter("techDet");
            String productUrl = request.getParameter("productUrl");
            String imageUrl = request.getParameter("imageUrl");

            double priceF =  (Math.round(Double.parseDouble(price) *100.0)/100.0);
            double weightF = (Math.round(Double.parseDouble(weight) *100.0)/100.0);

            JSONObject jsonObject = tDato.insertData(productName, category, priceF, weightF,
                    aboutProd, techDet, productUrl, imageUrl);
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

        } else if (flag == 2) {
            String idProduct = request.getParameter("idProduct");

            JSONObject jsonObject = tDato.getProductByID(idProduct);
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

        } else if (flag == 3) {
            String idProduct = request.getParameter("idProduct");

            JSONObject jsonObject = tDato.deleteProduct(idProduct);
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

        } else if (flag == 4) {
            String productID = request.getParameter("productID");
            String productName = request.getParameter("productName");
            String category = request.getParameter("category");
            String price = request.getParameter("price");
            String weight = request.getParameter("weight");
            String aboutProd = request.getParameter("aboutProd");
            String techDet = request.getParameter("techDet");
            String productUrl = request.getParameter("productUrl");
            String imageUrl = request.getParameter("imageUrl");

            double priceF =  (Math.round(Double.parseDouble(price) *100.0)/100.0);
            double weightF = (Math.round(Double.parseDouble(weight) *100.0)/100.0);

            JSONObject jsonObject = tDato.editData(productID, productName, category, priceF, weightF,
                    aboutProd, techDet, productUrl, imageUrl);
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
        } else if (flag == 5) {
            String aggType = request.getParameter("aggregateType");
            JSONObject jsonObject = new JSONObject();
            if (aggType.equals("weight")) {
                jsonObject = tDato.avgWeightAggregate();
            } else if (aggType.equals("price")){
                jsonObject = tDato.avgPriceAggregate();
            } else {
                request.setCharacterEncoding("utf8");
                response.setContentType("text/plain");
                response.getWriter().write("Type Error");
            }
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
        } else {
            request.setCharacterEncoding("utf8");
            response.setContentType("text/plain");
            response.getWriter().write("Server Error");
        }
    }
}
