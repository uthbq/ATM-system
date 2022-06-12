import java.util.ArrayList;

public class Account {
    private String name;
    // название аккаунта
    private String uuid;
    // id номер аккаунта
    private User holder;
    // пользователь владеющий аккаунтом
    private ArrayList<Transaction> transactions;
    // список транзакций


    /* создание новго аккаунта
    name - название аккаунта
    holder - владелец аккаунта
    theBank - банк в котором заведён аккаунт
     */
    public Account(String name, User holder, Bank theBank) {
        // установка названия аккаунта и его владельца
        this.name = name;
        this.holder = holder;
        // получения нового id
        this.uuid = theBank.getAccountUUID();
        // совершённые транзакции
        this.transactions = new ArrayList<Transaction>();

    }

    // возврат id аккаунта
    public String getUUID() {
        return this.uuid;
    }

    // получение сводки об аккаунте
    public String getSummaryLine() {

        // получение баланса аккаунта

        double balance = this.getBalance();

        // форматирование строки в зависимости от баланса
        if (balance >= 0) {
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);

        }
    }


    // подсчёт баланса при помощи сложения всех транзакций
    public double getBalance() {
        double balance = 0;

        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    public void printTransactionHistory() {
        System.out.printf("История транзакций для %s\n", this.uuid);
        for (int trans = this.transactions.size() - 1; trans >= 0; trans--) {
            System.out.printf(this.transactions.get(trans).getSummaryLine() + "\n");
        }
        System.out.println();
    }


    // добавление новой транзакции
    //amount - сумма транзакции
    //memory - пометки о транзакции
    public void addTransaction(double amount, String memory) {

        //создание новой транзакции и добавление её в список
        Transaction newTransaction = new Transaction(amount, memory, this);
        this.transactions.add(newTransaction);
    }
}
