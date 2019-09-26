package exceptions;

public class NoTrainerFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NoTrainerFoundException(String errorMessage) {
        super(errorMessage);
    }

}
