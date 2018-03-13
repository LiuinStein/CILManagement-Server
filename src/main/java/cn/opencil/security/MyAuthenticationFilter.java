package cn.opencil.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private Long userId;
    private String password;

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        if ("application/json".equals(request.getHeader("Content-Type"))) {
            return password;
        }
        return super.obtainPassword(request);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        if ("application/json".equals(request.getHeader("Content-Type"))) {
            return userId.toString();
        }
        return super.obtainUsername(request);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if ("application/json".equals(request.getHeader("Content-Type"))) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                JSONObject jsonObject = JSONObject.parseObject(stringBuilder.toString());
                this.userId = jsonObject.getLong("username");
                this.password = jsonObject.getString("password");
            } catch (Exception e) {
                throw new UsernameNotFoundException("username error");
            }
        }
        return super.attemptAuthentication(request, response);
    }
}
