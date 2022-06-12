import java.util.Date;

public class Transaction {
    private double amount;
    // сумма транзакции
    private Date time;
    // время транзакции
    private String memory;
    // пометки о транзакции
    private Account inAccount;
    // принадлежность транзакции к какому-либо аккаунту

    //создание транзакции
    //amount - сумма транзакции
    //inAccount - аккаунт к которому относится транзакция
    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.time = new Date();
        this.memory = "";
    }

    //создание транзакции
    //amount - сумма транзакции
    //inAccount - аккаунт к которому относится транзакция
    //memory - пометки к транзакции
    public Transaction(double amount, String memory, Account inAccount) {
        // вызов первого конструктора
        this(amount, inAccount);
        // установка пометок
        this.memory = memory;
    }

    // получение суммы транзакции
    public double getAmount() {
        return this.amount;
    }


    // получение транзакции
    public String getSummaryLine() {

        if (this.amount >= 0) {
            return String.format("%s : $%.02f : %s", this.time.toString(),
                    this.amount, this.memory);
        } else {
            return String.format("%s : $(%.02f) : %s", this.time.toString(),
                    this.amount, this.memory);
        }
    }
}
