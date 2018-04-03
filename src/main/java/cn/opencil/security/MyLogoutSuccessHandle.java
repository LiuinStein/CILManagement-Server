package cn.opencil.security;

import cn.opencil.vo.RestfulResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class MyLogoutSuccessHandle implements LogoutSuccessHandler {

    /**
     * Which http status will be returned when the logout was successful
     */
    private HttpStatus httpStatusToReturn = HttpStatus.OK;
    /**
     * Whether or not to return the message body
     * If the http status doesn't permit to return any messages (such as 204 NO_CONTENT),
     * this setting will become ineffective.
     *
     * For example
     * You set the httpStatusToReturn is 204 NO_CONTENT,
     * even if you set the messageReturning is true, it will also return nothing
     */
    private boolean messageReturning = true;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (messageReturning) {
            RestfulResult result = new RestfulResult(0, "logout succeeded", new HashMap<>());
            SecurityRestfulResponsePrinter printer = new SecurityRestfulResponsePrinter();
            printer.print(request, response, result);
        }
        response.setStatus(httpStatusToReturn.value());
    }

    public void setMessageReturning(boolean messageReturning) {
        this.messageReturning = messageReturning;
    }

    public void setHttpStatusToReturn(HttpStatus httpStatusToReturn) {
        this.httpStatusToReturn = httpStatusToReturn;
    }
}
