package ar.edu.itba.paw.webapp.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class IndexRedirector extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().matches(".*/api/.*") && !request.getRequestURI().matches("/webapp/?") && !request.getRequestURI().matches(".+\\.(js|css|png|jpg|jpeg|gif|ico|html)$")) {
        	response.sendRedirect("/webapp");
        	return;
        }
        filterChain.doFilter(request,response);
    }
}
