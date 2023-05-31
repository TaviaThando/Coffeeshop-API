package za.co.fnb.coffeshop.exceptions;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long id) {
        super("Could not find customer " + id);
    }

    public CustomerNotFoundException(String name) {
        super("Could not find customer " + name);
    }

}
