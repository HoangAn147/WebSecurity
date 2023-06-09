package com.controller;

import com.dao.CategoryDao;
import com.dao.ProductDao;
import com.entities.CategoryEntity;
import com.entities.ProductEntity;
import com.fix.CSRF;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
@WebServlet(name = "index1", value = "/index1")
public class LoadController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url;
        //Đã fix từ dòng 23->32
        String token = null;
        try {
            token = CSRF.getToken();
            System.out.println("token: " + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("csrfToken", token);
        Cookie cookie = new Cookie("csrfToken", token);
        response.addCookie(cookie);

        url = "/index.jsp";
        ProductDao productDao = new ProductDao();
        List<ProductEntity> product = productDao.getListProduct();
        request.setAttribute("product", product);
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
