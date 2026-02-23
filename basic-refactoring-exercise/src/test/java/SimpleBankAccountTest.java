import example.model.AccountHolder;
import example.model.BankAccount;
import example.model.SimpleBankAccount;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The test suite for testing the SimpleBankAccount implementation
 */
class SimpleBankAccountTest {

    private AccountHolder accountHolder;
    private BankAccount bankAccount;
    private final double BASE_AMOUNT = 100;
    private final double DEPOSIT_AMOUNT = 70;
    private final int WRONG_ID_ACCOUNT_HOLDER = 2;
    private final double FEE = 1;

    @BeforeEach
    void beforeEach(){
        accountHolder = new AccountHolder("Mario", "Rossi", 1);
        bankAccount = new SimpleBankAccount(accountHolder, 0);
    }

    @Test
    void testInitialBalance() {
        assertEquals(0, bankAccount.getBalance());
    }

    @Test
    void testDeposit() {
        bankAccount.deposit(accountHolder.id(), BASE_AMOUNT);
        assertEquals(BASE_AMOUNT, bankAccount.getBalance());
    }

    @Test
    void testNegativeDeposit(){
        double negativeAmount = -BASE_AMOUNT;
        assertThrows(IllegalArgumentException.class,() ->bankAccount.deposit(accountHolder.id(), negativeAmount));
    }

    @Test
    void testWrongDeposit() {
        bankAccount.deposit(accountHolder.id(), BASE_AMOUNT);
        bankAccount.deposit(WRONG_ID_ACCOUNT_HOLDER, DEPOSIT_AMOUNT);
        assertEquals(BASE_AMOUNT, bankAccount.getBalance());
    }

    private void basicDepositAndWithdrawOperation(int depositAccountId, int withdrawAccountId, double depositAmount, double withdrawAmount){
        bankAccount.deposit(depositAccountId, depositAmount);
        bankAccount.withdraw(withdrawAccountId, withdrawAmount);
    }

    @Test
    void testWithdraw() {
        basicDepositAndWithdrawOperation(accountHolder.id(),accountHolder.id(),BASE_AMOUNT,DEPOSIT_AMOUNT);
        assertEquals(BASE_AMOUNT - (DEPOSIT_AMOUNT + FEE), bankAccount.getBalance());
    }

    @Test
    void testNegativeWithdraw() {
        double negativeAmount = -DEPOSIT_AMOUNT;
        assertThrows(IllegalArgumentException.class,()->basicDepositAndWithdrawOperation(accountHolder.id(),accountHolder.id(),BASE_AMOUNT,negativeAmount));
    }

    @Test
    void testWrongWithdraw() {
        basicDepositAndWithdrawOperation(accountHolder.id(),WRONG_ID_ACCOUNT_HOLDER,BASE_AMOUNT,DEPOSIT_AMOUNT);
        assertEquals(BASE_AMOUNT, bankAccount.getBalance());
    }
}
