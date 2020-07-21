package jp.ac.kagawalab.mynah.core.oauth2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class IllegalEmailDomainException extends BadCredentialsException {
    private static final long serialVersionUID = 7182805755140521674L;
    private final String providedDomain;
    private final String[] requiredDomain;

    public IllegalEmailDomainException(String message, String providedDomain, String[] requiredDomain) {
        super(getMessage(message, providedDomain, requiredDomain));
        this.providedDomain = providedDomain;
        this.requiredDomain = requiredDomain;
    }

    public IllegalEmailDomainException(String providedDomain, String[] requiredDomain) {
        this("", providedDomain, requiredDomain);
    }

    private static String getMessage(String message, String providedDomain, String[] requiredDomain) {
        return "The domain of email specified, '" + providedDomain + "' , is not available. Available domains is [" + String
                .join(", ", requiredDomain) + "] " + ((!message.isBlank()) ? ": " + message : "");
    }

    public IllegalEmailDomainException(String message, Throwable cause, String providedDomain, String[] requiredDomain) {
        super(getMessage(message, providedDomain, requiredDomain), cause);
        this.providedDomain = providedDomain;
        this.requiredDomain = requiredDomain;
    }
}
