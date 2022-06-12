import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    // название
    private ArrayList<User> users;
    // список пользователей
    private ArrayList<Account> accounts;
    // общий список аккаунтов

    /*
    создание объекта банка
    name - название банка
     */
    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    // генерация уникального id для пользователя
    // возвращает id
    public String getNewUserUUID() {
        String uuid;
        Random rng = new Random();
        int lenght = 6;
        boolean NonUnique;

        // создание нового id пока не будет уникального
        do {
            // генерация числа
            uuid = "";
            for (int i = 0; i < lenght; i++) {
                uuid += ((Integer) rng.nextInt(10)).toString();
            }

            // проверка на уникальность
            NonUnique = false;
            for (User you : this.users) {
                if (uuid.compareTo(you.getUUID()) == 0) {
                    NonUnique = true;
                    break;
                }
            }

        } while (NonUnique);

        return uuid;
    }

    // генерация уникального id для аккаунта
    // возвращает id
    public String getAccountUUID() {
        String uuid;
        Random rng = new Random();
        int lenght = 10;
        boolean NonUnique;

        // создание нового id пока не будет уникального
        do {
            // генерация числа
            uuid = "";
            for (int i = 0; i < lenght; i++) {
                uuid += ((Integer) rng.nextInt(10)).toString();
            }

            // проверка на уникальность
            NonUnique = false;
            for (Account account : this.accounts) {
                if (uuid.compareTo(account.getUUID()) == 0) {
                    NonUnique = true;
                    break;
                }
            }

        } while (NonUnique);

        return uuid;
    }


    // добавление аккаунта
    // anAcct - добавляемый аккаунт
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    /*создание нового пользователя в банке
     firstName - имя пользователя
     lastName - фамилия пользователя
     pin - пин-код пользователя
     */
    public User addUser(String firstName, String lastName, String pin) {

        //создание нового пользователя и добавление его в список
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        //создание сберегательного аккаунта и добавление его в списки пользователя и банка
        Account newAccount = new Account("Сбережения", newUser, this);
        // добавление в списки банка и пользователя
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    /* получение пользователя связанного с определёнными id и пин-кодом
    userID - id пользователя для входа
    pin - пин-код пользователя
     */
    public User UserLogin(String userID, String pin) {
        // поиск в списке пользователей
        for (User you : this.users) {

            // id
            if (you.getUUID().compareTo(userID) == 0 && you.validatePin(pin)) {
                return you;
            }
        }
        // если пользователь не найден или неверный пин-код
        return null;

    }

    //установка имени банка
    public String getName() {
        return this.name;
    }
}
