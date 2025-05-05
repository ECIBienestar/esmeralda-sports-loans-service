package cvds.esmeralda.service.loans.exception;

public class LoanException extends RuntimeException {

    public LoanException() {
        super();
    }

    public LoanException(String message) {
        super(message);
    }

    public LoanException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoanException(Throwable cause) {
        super(cause);
    }
}
