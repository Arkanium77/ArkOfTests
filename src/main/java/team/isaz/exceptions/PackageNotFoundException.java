package team.isaz.exceptions;

public class PackageNotFoundException extends Exception {
    public PackageNotFoundException(String message){
        super(message);
    }

    public PackageNotFoundException() {
        super();
    }
}
