import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private String firstName;
    // имя
    private String lastName;
    // фамилия
    private String uuid;
    // id номер пользователя
    private byte pinHash[];
    // алгоритм хэширования пароля MD5
    private ArrayList<Account> accounts;
    // список учётных записей пользователя


    /*Создание нового пользователя
    firstName - имя пользователя
    lastName - фамилия пользователя
    pin - пин-код
    theBank - клиентом какого банка является пользователь
     */
    public User(String firstName, String lastName, String pin, Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;
        // установка имени пользователя

        // шифровка пин-кода
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        // получение нового id кода
        this.uuid = theBank.getNewUserUUID();
        // создание пустого списка аккаунтов
        this.accounts = new ArrayList<Account>();
        // вывод лога
        System.out.println("Новый пользователь " + firstName + " " + lastName + " с ID " + this.uuid + " создан.");
    }

    // добавление аккаунта пользователю
    //anAcct - добавляемый аккаунт
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    // возврат id пользователя
    public String getUUID() {
        return this.uuid;
    }

    //соотнесение полученого пин-кода с правильным
    //pin - введённый код
    //возвращает логическое утверждение: правильно или нет
    public boolean validatePin(String pin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    // получение имени пользователя
    public String getFirstName() {
        return this.firstName;
    }

    public void printAccountsSummary() {
        System.out.println("Ваш список счетов");
        for (int i = 0; i < this.accounts.size(); i++) {
            System.out.printf("%d) %s\n", i + 1, this.accounts.get(i).getSummaryLine());
        }
    }

    // получение количества аккаунтов пользователя
    public int numAccounts() {
        return this.accounts.size();
    }


    // вывод истории транзакций для определённого аккаунта
    public void printAccountTransactionHistory(int akk) {
        this.accounts.get(akk).printTransactionHistory();
    }


    //получение баланса конкретного аккаунта
    public double getBalance(int fromAkk) {
        return this.accounts.get(fromAkk).getBalance();
    }


    // получение id определённого аккаунта
    public String getAccountUUID(int fromAkk) {
        return this.accounts.get(fromAkk).getUUID();
    }

    // добавление транзакции к какому-либо аккаунту
    //Akk - аккаунт транзакции
    //amount - сумма транзакции
    //memory - пометки о транзакции
    public void addAccountTransaction(int Akk, double amount, String memory) {
        this.accounts.get(Akk).addTransaction(amount, memory);
    }
}
