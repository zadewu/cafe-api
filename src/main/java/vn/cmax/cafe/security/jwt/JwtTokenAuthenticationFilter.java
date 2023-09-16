package vn.cmax.cafe.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.JDBCException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.cmax.cafe.api.models.ApiError;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
  private static final String REFRESH_URL = "/auth/refresh";
  private final JwtTokenManager jwtTokenManager;

  public JwtTokenAuthenticationFilter(JwtTokenManager jwtTokenManager) {
    this.jwtTokenManager = jwtTokenManager;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = jwtTokenManager.resolveToken(request);
    if (token == null) {
      filterChain.doFilter(request, response);
      return;
    }
    String path = request.getServletPath();
    JwtAuthentication jwtAuthentication;
    TokenType tokenType = TokenType.ACCESS_TOKEN;
    if (path != null && path.startsWith(REFRESH_URL)) {
      tokenType = TokenType.REFRESH_TOKEN;
    }
    try {
      jwtAuthentication = jwtTokenManager.authenticate(token, tokenType, request);
    } catch (DataAccessResourceFailureException | JDBCException e) {
      String errorMessage =
          "*** JwtTokenAuthenticationFilter: DB connection exception happens when authenticate jwt"
              + " token";
      logger.warn(errorMessage, e);
      write500ErrorIntoResponse(response, errorMessage);
      return;
    } catch (NoSuchAlgorithmException e1) {
      String errorMessage = "*** Exception happens when hashing fingerprint token";
      logger.warn(errorMessage, e1);
      write500ErrorIntoResponse(response, errorMessage);
      return;
    }

    if (jwtAuthentication.isSuccess()) {
      Authentication auth = jwtAuthentication.getAuthentication();
      SecurityContextHolder.getContext().setAuthentication(auth);
      filterChain.doFilter(request, response);
    } else {
      write401ErrorIntoResponse(response, jwtAuthentication);
    }
  }

  private void write500ErrorIntoResponse(HttpServletResponse response, String errorMessage)
      throws IOException {
    ApiError dbException = new ApiError();
    dbException.code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(errorMessage);
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(new ObjectMapper().writeValueAsString(dbException));
  }

  private void write401ErrorIntoResponse(
      HttpServletResponse response, JwtAuthentication jwtAuthentication) throws IOException {
    logger.warn("*** JwtTokenAuthenticationFilter: " + jwtAuthentication.getMessage());
    ApiError errorResponse = new ApiError();
    errorResponse.code(HttpStatus.FORBIDDEN.value()).message(jwtAuthentication.getMessage());
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
  }
}
