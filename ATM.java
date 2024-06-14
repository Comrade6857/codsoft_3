import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class BankAccount {
    private  long balance;

    public BankAccount(long initialBalance) {
        this.balance = initialBalance;
    }

    public long getBalance() {
        return balance;
    }

    public void deposit(long amount) {
        balance += amount;
    }

    public boolean withdraw(long amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;  // Withdrawal successful
        }
        return false;  // Withdrawal failed due to insufficient balance
    }
}




public class ATM extends JFrame {
    private BankAccount bankAccount;

    private JLabel balanceLabel;
    private JTextField amountField;
    private JTextArea messageArea;

    public ATM(BankAccount account) {
        this.bankAccount = account;
        setTitle("ATM Machine");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        balanceLabel = new JLabel("Balance: $" + bankAccount.getBalance());
        topPanel.add(balanceLabel);

        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);
        middlePanel.add(amountLabel);
        middlePanel.add(amountField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton checkBalanceButton = new JButton("Check Balance");
        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(checkBalanceButton);

        messageArea = new JTextArea(8, 30);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.EAST);

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = amountField.getText();
                if (!amountStr.isEmpty()) {
                    try {
                       long amount =Long.parseLong(amountStr);
                                boolean success = bankAccount.withdraw(amount);
                        if (success) {
                            updateBalanceLabel();
                            showMessage("Withdrawal successful: $" + amount);
                        } else {
                            showMessage("Insufficient funds for withdrawal");
                        }
                    } catch (NumberFormatException ex) {
                        showMessage("Invalid amount");
                    }
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = amountField.getText();
                if (!amountStr.isEmpty()) {
                    try {
                       long amount =Long.parseLong(amountStr);
                        bankAccount.deposit(amount);
                        updateBalanceLabel();
                        showMessage("Deposit successful: $" + amount);
                    } catch (NumberFormatException ex) {
                        showMessage("Invalid amount");
                    }
                }
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMessage("Current Balance: $" + bankAccount.getBalance());
            }
        });
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Balance: $" + bankAccount.getBalance());
    }

    private void showMessage(String message) {
        messageArea.append(message + "\n");
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000); // Initial balance of $1000
        new ATM(account);
    }
}

