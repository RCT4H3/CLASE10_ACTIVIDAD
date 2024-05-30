package com.example.clase10crud.servlets;

import com.example.clase10crud.beans.Job;
import com.example.clase10crud.daos.JobDao;
import com.example.clase10crud.beans.Employee;
import com.example.clase10crud.daos.EmployeeDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

//  http://localhost:8080/EmployeeServlet
@WebServlet(name = "EmployeeServlet", value = "/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");


        JobDao jobDao = new JobDao();

        String action = request.getParameter("action") == null ? "crear" : request.getParameter("action");

        switch (action){
            case "crear"://voy a crear un nuevo empleado :D
                String birth_date = request.getParameter("birth_date");
                String first_name = request.getParameter("first_name");
                String last_name = request.getParameter("first_name");
                String gender = request.getParameter("gender");
                String gender = request.getParameter("gender");

                boolean isAllValid = true;

                if(jobTitle.length() > 35){
                    isAllValid = false;
                }

                if(jobId.length() > 10){
                    isAllValid = false;
                }

                if(isAllValid){

                    Job job = jobDao.buscarPorId(jobId);

                    if(job == null){
                        jobDao.crear(jobId,jobTitle,Integer.parseInt(minSalary),Integer.parseInt(maxSalary));
                        response.sendRedirect(request.getContextPath() + "/JobServlet");
                    }else{
                        request.getRequestDispatcher("job/form_new.jsp").forward(request,response);
                    }
                }else{
                    request.getRequestDispatcher("job/form_new.jsp").forward(request,response);
                }
                break;
            case "e": //voy a actualizar
                String jobId2 = request.getParameter("jobId");
                String jobTitle2 = request.getParameter("jobTitle");
                String minSalary2 = request.getParameter("minSalary");
                String maxSalary2 = request.getParameter("maxSalary");

                boolean isAllValid2 = true;

                if(jobTitle2.length() > 35){
                    isAllValid2 = false;
                }

                if(jobId2.length() > 10){
                    isAllValid2 = false;
                }
                if(isAllValid2){
                    Job job = new Job();
                    job.setJobId(jobId2);
                    job.setJobTitle(jobTitle2);
                    job.setMinSalary(Integer.parseInt(minSalary2));
                    job.setMaxSalary(Integer.parseInt(maxSalary2));

                    jobDao.actualizar(job);
                    response.sendRedirect(request.getContextPath() + "/JobServlet");
                }else{
                    request.setAttribute("job",jobDao.buscarPorId(jobId2));
                    request.getRequestDispatcher("job/form_edit.jsp").forward(request,response);
                }
                break;
            case "s":
                String textBuscar = request.getParameter("textoBuscar");
                ArrayList<Job> lista = jobDao.buscarIdOrTitle(textBuscar);

                request.setAttribute("lista",lista);
                request.setAttribute("busqueda",textBuscar);
                request.getRequestDispatcher("job/lista.jsp").forward(request,response);

                break;
        }
    }
}