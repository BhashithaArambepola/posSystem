package com.example.possystem.api;

import com.example.possystem.dto.CustomerDTO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "CustomerServlet", value = {"/CustomerServlets","/CustomerServlets/"})
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Working");

    }

    @Resource(name = "java:comp/env/jdbc/pool4posSystem")
    private volatile DataSource pool;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("working..");
        if (request.getContentType() == null || !request.getContentType().startsWith("application/json")) {
            response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Less Content");
            return;
        }
        if (request.getPathInfo() != null && !request.getPathInfo().equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }


//        -----------------------------
        try {

            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customer = jsonb.fromJson(request.getReader(), CustomerDTO.class);
            System.out.println("customer:" + customer);


            if (customer.getId() == null || !customer.getId().matches("\\d{9}[Vv]")) {
//            System.out.println("invalid ID");

                throw new RemoteException("Invalid ID");

            } else if (customer.getName() == null || !customer.getName().matches("[A-Za-z .]+")) {

                throw new ValidationException("Invalid name");
            }

            try (Connection connection = pool.getConnection()) {
                System.out.println("????");
                PreparedStatement stm = connection.prepareStatement("INSERT INTO customer (cus_name,id ) VALUES(?,?) ");
                stm.setString(1, customer.getName());
                stm.setString(2, customer.getId());

                if (stm.executeUpdate() != 1) {

                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "try again later");
                    return;
                }
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                jsonb.toJson(customer, response.getWriter());

            } catch (SQLException e) {
                System.out.println("bad");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "try again");

                return;
            }

        } catch (JsonbException | ValidationException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, (e instanceof JsonbException)? "Invalid Json" : ((ValidationException) e).getMessage());
        } catch (Throwable t) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            t.printStackTrace();
        }
    }
}
