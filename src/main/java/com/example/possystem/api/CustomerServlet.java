package com.example.possystem.api;

import com.example.possystem.dto.CustomerDTO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "CustomerServlet", value = {"/CustomerServlets","/CustomerServlets/"})
public class CustomerServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool4posSystem")
    private volatile DataSource pool;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("working..");
        if(request.getContentType()== null || !request.getContentType().startsWith("application/json") ){
            response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,"Less Content");
            return;
        }
        if(request.getPathInfo() !=null && !request.getPathInfo().equals("/")){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customer = jsonb.fromJson(request.getReader(), CustomerDTO.class);

    }
}
