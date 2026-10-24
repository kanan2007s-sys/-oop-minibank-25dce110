
@FunctionalInterface
public interface WithdrawRule {
    boolean canWithdraw(long balance, long amount);
}
