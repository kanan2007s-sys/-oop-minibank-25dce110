
public class InvalidAmountException extends BankException {

    public InvalidAmountException(long amount) {
        super("Invalid transaction amount: " + amount);
    }
}
