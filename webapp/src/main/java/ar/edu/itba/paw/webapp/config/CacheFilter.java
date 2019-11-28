package ar.edu.itba.paw.webapp.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CacheFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().matches(".*/static/.*") || request.getRequestURI().matches(".*/image") || request.getRequestURI().matches(".*/cover")) {
           response.setHeader("Cache-Control", "max-age=31536000, public");
        } else {
            response.setHeader("Cache-Control", "no-cache");
        }
        filterChain.doFilter(request,response);
    }
}
