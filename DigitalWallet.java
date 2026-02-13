import java.util.Scanner;

public class DigitalWallet {

    String ownerName;
    double balance;
    String currency;
    int transactionCount;

    public void initializeWallet() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Owner Name: ");
        ownerName = scanner.nextLine();

        do {
            System.out.print("Enter Currency (TL/USD): ");
            currency = scanner.nextLine();

            if (currency.equalsIgnoreCase("TL") || currency.equalsIgnoreCase("USD")) {
                break;
            } else {
                System.out.println("Invalid currency. Please enter TL or USD.");
            }
        } while (!currency.equalsIgnoreCase("TL") && !currency.equalsIgnoreCase("USD"));

        balance = 0.0;
        transactionCount = 0;

    }

    public void deposit(double amount, String moneyType) {
        if (moneyType.equalsIgnoreCase(currency)) {
            balance += amount;
            transactionCount++;
        } else {
            if (!moneyType.equalsIgnoreCase(currency) && moneyType.equalsIgnoreCase("TL")) {
                balance += amount / 30.0;
                transactionCount++;
            } else if (!moneyType.equalsIgnoreCase(currency) && moneyType.equalsIgnoreCase("USD")) {
                balance += amount * 30.0;
                transactionCount++;
            }
        }

        System.out.println("Deposited " + amount + " " + moneyType + ". New balance: " + balance + " " + currency);
    }

    public void sendMoney(DigitalWallet receiver, double amount) {
        if (this.balance < amount) {
            System.out.println("Insufficient funds");
            return;
        }

        this.balance -= amount;
        this.transactionCount++;

        if (this.currency.equalsIgnoreCase(receiver.currency)) {
            receiver.balance += amount;
        } else {
            if (this.currency.equalsIgnoreCase("TL") && receiver.currency.equalsIgnoreCase("USD")) {
                receiver.balance += amount / 30.0;
            } else if (this.currency.equalsIgnoreCase("USD") && receiver.currency.equalsIgnoreCase("TL")) {
                receiver.balance += amount * 30.0;
            }
        }
        receiver.transactionCount++;

        System.out.println("Transfer successful. " + amount + " " + this.currency + " sent to " + receiver.ownerName);
    }

    public void printInfo() {
        System.out.println("Wallet Owner: " + ownerName + " | Currency: " + currency + " | Balance: " + balance
                + " | Transactions: " + transactionCount);
    }

    public static void main(String[] args) {
        DigitalWallet w1 = new DigitalWallet();
        DigitalWallet w2 = new DigitalWallet();

        w1.ownerName = "Cem";
        w1.currency = "TL";
        w1.balance = 5000.0;
        w1.transactionCount = 0;

        w2.initializeWallet();

        w1.deposit(100, "USD");
        w1.sendMoney(w2, 3000.0);

        w1.printInfo();
        w2.printInfo();
    }
}