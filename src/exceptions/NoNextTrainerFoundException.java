package exceptions;

public class NoNextTrainerFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NoNextTrainerFoundException(String errorMessage) {
        super(errorMessage);
    }

}
