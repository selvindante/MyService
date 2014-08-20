package service.model;

/**
 * User: gkislin
 * Date: 18.04.2014
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable e) {
        super(message, e);
    }
}
