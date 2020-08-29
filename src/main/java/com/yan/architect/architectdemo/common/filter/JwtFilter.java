package com.yan.architect.architectdemo.common.filter;

import com.yan.architect.architectdemo.common.exception.BusinessException;
import com.yan.architect.architectdemo.common.utils.ApplicationContextProvider;
import com.yan.architect.architectdemo.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * @author Administrator
 *
 */
public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {

        // Change the req and res to HttpServletRequest and HttpServletResponse
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        // Get authorization from Http request
        final String authHeader = request.getHeader("authorization");

        // If the Http request is OPTIONS then just return the status code 200
        // which is HttpServletResponse.SC_OK in this code
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);

            chain.doFilter(req, res);
        }
        // Except OPTIONS, other request should be checked by JWT
        else {

            // Check the authorization, check if the token is started by "Bearer "
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new ServletException("Missing or invalid Authorization header");
            }

            // Then get the JWT token from authorization
            final String token = authHeader.substring(7);

            try {
                // Use JWT parser to check if the signature is valid with the Key "secretkey"
                final Claims claims = Jwts.parser().setSigningKey(JwtUtil.SECRETKEY).parseClaimsJws(token).getBody();
                
                String account = (String)claims.get("account");
                String password = (String)claims.get("password");
                /**
                验证Token信息
                ApiService apiService = (ApiService) ApplicationContextProvider.getBean(ApiService.class);
                User user = apiService.queryUserByAccount(account);
                if(user == null){
                    throw new BusinessException(502,"2001");//Token校验失败,账号不存在
                }
                
                if(!password.equals(user.getPassword())){
                    throw new BusinessException(502,"2002");//Token校验失败,密码错误
                }
                 *
                 */

                // Add the claim to request header
                request.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                e.printStackTrace();
                if (e.getMessage().contains("JWT expired")) {
                    throw new BusinessException(502,"2003");//Token校验失败,登录信息失效
                }
                if (e.getMessage().contains("not match locally computed signature")) {
                    throw new BusinessException(502,"2004");//Token校验失败,签名不匹配
                }
                throw e;
            }

            chain.doFilter(req, res);
        }
    }
}
