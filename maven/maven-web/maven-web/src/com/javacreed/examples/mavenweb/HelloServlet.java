package com.javacreed.examples.mavenweb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javacreed.examples.mpfw.HelloService;

@WebServlet(name = "hello", urlPatterns = { "/hello" })
@SuppressWarnings("serial")
public class HelloServlet extends HttpServlet {

  private HelloService helloService = new HelloService();

  @Override
  protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
      IOException {
    sayHello(response);
  }

  @Override
  protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
      IOException {
    sayHello(response);
  }

  private void sayHello(final HttpServletResponse response) throws IOException {
    final ServletOutputStream out = response.getOutputStream();
    out.println("<html>");
    out.println("  <head>");
    out.println("    <title>Maven Web Example</title>");
    out.println("  </head>");
    out.println("  <body>");
    out.println("    Maven Web Example - " + helloService.greet());
    out.println("  </body>");
    out.println("</html>");
  }

}
