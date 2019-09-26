package exceptions;

public class NoPreviousTrainerFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NoPreviousTrainerFoundException(String errorMessage) {
        super(errorMessage);
    }

}
