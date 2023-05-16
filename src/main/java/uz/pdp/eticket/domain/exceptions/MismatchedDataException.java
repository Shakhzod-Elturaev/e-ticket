package uz.pdp.eticket.domain.exceptions;

public class MismatchedDataException extends RuntimeException{
    public MismatchedDataException(String message){
        super(message);
    }
}
