package nuricanozturk.dev.service.search.flight.config;

import callofproject.dev.service.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nuricanozturk.dev.service.search.flight.service.impl.AuthenticationService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthFilter is a custom filter for JWT (JSON Web Token) based authentication.
 * This filter intercepts HTTP requests to authenticate users based on JWT tokens in the Authorization header.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter
{
    private final AuthenticationService m_authenticationService;

    /**
     * Constructs a JwtAuthFilter with the necessary authentication service.
     *
     * @param authenticationService The authentication service to verify user details.
     */
    public JwtAuthFilter(AuthenticationService authenticationService)
    {
        m_authenticationService = authenticationService;
    }

    /**
     * Filters incoming HTTP requests for JWT authentication.
     * Extracts the JWT token from the Authorization header and authenticates the user if the token is valid.
     * If authentication is successful, it sets the security context with the user details.
     *
     * @param request     The HttpServletRequest object.
     * @param response    The HttpServletResponse object.
     * @param filterChain The FilterChain for passing the request to the next filter or resource.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException      if an I/O error occurs during request processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        try
        {
            var authenticationHeader = request.getHeader("Authorization");

            if (authenticationHeader != null && authenticationHeader.startsWith("Bearer "))
            {
                var token = authenticationHeader.substring(7);
                var username = JwtUtil.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
                {
                    var user = m_authenticationService.findCustomerByUsername(username);

                    if (JwtUtil.isTokenValid(token, username) && username.equals(user.get().getUsername()))
                    {
                        var authToken = new UsernamePasswordAuthenticationToken(user, null, user.get().getRoles());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException | BadCredentialsException exception)
        {
            response.setStatus(403);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + exception.getMessage() + "\"}");
        }
    }
}