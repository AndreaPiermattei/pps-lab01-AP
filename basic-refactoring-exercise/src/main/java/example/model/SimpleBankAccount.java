package example.model;

/**
 * This class represent a particular instance of a BankAccount.
 * In particular, a Simple Bank Account allows always the deposit
 * while the withdrawal is allowed only if the balance greater or equal the withdrawal amount
 */
public class SimpleBankAccount implements BankAccount {

    private double balance;
    private final AccountHolder holder;

    public SimpleBankAccount(final AccountHolder holder, final double balance) {
        this.holder = holder;
        this.balance = balance;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    private boolean checkUser(final int id) {return this.holder.id() == id;}

    @Override
    public void deposit(final int userID, final double amount) throws IllegalArgumentException{
        if(amount<=0){
            throw new IllegalArgumentException("amount is <= 0");
        }
        if (checkUser(userID)) {
            this.balance += amount;
        }
    }

    private double calculateFinalAmount(final double amount,final double fee){return amount+fee;}

    private boolean isWithdrawAllowed(final double amount,final double fee){return this.balance >= calculateFinalAmount(amount,fee);}

    @Override
    public void withdraw(final int userID, final double amount) throws IllegalArgumentException{
        final double feeToApply = 1;
        if(amount<=0){
            throw new IllegalArgumentException("amount is <= 0");
        }
        if (checkUser(userID) && isWithdrawAllowed(amount,feeToApply)) {
            this.balance -= calculateFinalAmount(amount,feeToApply);
        }
    }
}
