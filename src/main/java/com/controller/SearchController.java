package com.controller;

import com.dao.ProductDao;
import com.entities.ProductEntity;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Search", value = "/search")
public class SearchController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String csrfField = request.getParameter("csrf");

        String csrfCookie = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("csrfToken")) {
                csrfCookie = cookie.getValue();
            }
        }
        //Đã fix từ dòng 25->39
        if (csrfCookie.equals(csrfField)) {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            String url;
            url = "/product.jsp";
            String name = request.getParameter("search");
            ProductDao productDao = new ProductDao();
            List<ProductEntity> product = productDao.searchName(name);
            request.setAttribute("product", product);
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }else {
            response.sendError(401);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
