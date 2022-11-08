package info.memphis.exception;

public class WrongOperationExeption extends RuntimeException {
    public WrongOperationExeption(String message) {
        super(message);
    }
}
