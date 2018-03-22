package cn.opencil.security;

import cn.opencil.vo.RestfulResult;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class MyFailureHandle implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        RestfulResult result = new RestfulResult(1, e.getMessage(), new HashMap<>());
        SecurityRestfulResponsePrinter responseHandle = new SecurityRestfulResponsePrinter();
        responseHandle.print(request, response, result);
        // run to here when log in failed
        if (e instanceof InternalAuthenticationServiceException) {
            // an InternalAuthenticationServiceException will be threw when username is not existent
            // why did I do this?
            // if we throw an UsernameNotFoundException at UserDetailsService,
            // the upper interface DaoAuthenticationProvider will hide it by default
            // related code at DaoAuthenticationProvider:
            // --- omitted --
            //    catch (UsernameNotFoundException notFound) {
            //        logger.debug("User '" + username + "' not found");
            //     if (hideUserNotFoundExceptions) {
            //         throw new BadCredentialsException(messages.getMessage(
            //                 "AbstractUserDetailsAuthenticationProvider.badCredentials",
            //                 "Bad credentials"));
            // --- omitted --
            // the default value of hideUserNotFoundExceptions is true
            // so both the result of non-exist username and password error are
            // BadCredentialsException with the same message "Bad credentials".
            // This not in line with our requirements, we need to distinguish between username not found and password error
            // I Google this problem and found the following solutions:
            //
            // First: change the Spring Security framework code directly
            //        disadvantage: uncountable, not enumerating one by one
            // Second: customize an MyUsernameNotFoundException to replace UsernameNotFoundException
            //        refer to (in Chinese): https://www.jianshu.com/p/b0cc88574f5d
            //        disadvantage: -
            // Third: throw BadCredentialsException directly at UserDetailsService
            //        refer to (in Chinese): https://www.jianshu.com/p/b0cc88574f5d
            //        disadvantage: we should check the message field of exception to judge which error was happened
            // Fourth: manually inject an authenticationProvider bean
            //        refer to (in Chinese): http://blog.csdn.net/jaune161/article/details/18359321
            //        disadvantage: fussy configurations
            // Fifth: return null if username not found at loadUserByUsername method of UserDetailsService
            //        refer to: this one is not from Google, form me :)
            //        disadvantage: when the loadUserByUsername return null, an InternalAuthenticationServiceException will be threw
            //                      then, an error level log will be output into the log file,
            //                      with the count of failed login increased, a substantial useless log will be outputted
            //                      it will bring a lot of troubles on the future log audit

            // Here, I use the fifth solution temporarily and hope the further improvements
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
