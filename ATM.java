import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Bank theBank = new Bank("МорозБанк");

        //добавление пользователя
        User user = theBank.addUser("Georgii", "Moroz", "1411");

        Account account = new Account("Депозит", user, theBank);
        user.addAccount(account);
        theBank.addAccount(account);

        User user1;
        while (true) {
            // оставаться в меню до успешного входа
            user1 = ATM.mainMenuPrompt(theBank, scanner);

            //оставаться в меню пока пользователь не выйдет
            ATM.printUserMenu(user1, scanner);

        }
    }

    public static void printUserMenu(User user1, Scanner scanner) {
        //вывод списка аккаунтов пользователя
        user1.printAccountsSummary();

        int choice;

        do {
            System.out.println("Здравствуйте, " + user1.getFirstName());
            System.out.println("Что бы Вы хотели сделать?");
            System.out.println();
            System.out.println("    1) Вывести историю транзакций");
            System.out.println("    2) Вывод средств");
            System.out.println("    3) Внесение средств");
            System.out.println("    4) Перевод средств");
            System.out.println("    5) Выйти");
            System.out.println();
            System.out.println("Введите номер операции: ");
            choice = scanner.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Неправильный выбор. Доступны команды с номерами 1-5.");
            }
        } while (choice < 1 || choice > 5);

        switch (choice) {
            case 1:
                ATM.showTransactionHistory(user1, scanner);
                break;
            case 2:
                ATM.withdrawMoney(user1, scanner);
                break;
            case 3:
                ATM.enterDeposit(user1, scanner);
                break;
            case 4:
                ATM.transferMoney(user1, scanner);
                break;
            case 5:
                scanner.nextLine();
                break;
        }

        if (choice != 5) {
            ATM.printUserMenu(user1, scanner);
        }
    }

    // внесение депозита
    public static void enterDeposit(User user1, Scanner scanner) {
        int toAkk;
        double transfAmount;
        double akkBalance;
        String memory;


        // получение информации о том с какого аккаунта переводят средства
        do {
            System.out.printf("Введите номер счёта (1-%d), на который" +
                    " будут внесены средства:", user1.numAccounts());
            toAkk = scanner.nextInt() - 1;
            if (toAkk < 0 || toAkk >= user1.numAccounts()) {
                System.out.println("Такой счёт не существует. Попробуйте снова.");
            }


        } while (toAkk < 0 || toAkk >= user1.numAccounts());

        akkBalance = user1.getBalance(toAkk);


        // получение информации о сумме перевода
        do {
            System.out.println("Введите сумму пополнения:");
            transfAmount = scanner.nextDouble();
            if (transfAmount < 0) {
                System.out.println("Сумма должна быть больше 0.");
            }
        } while (transfAmount < 0);
        scanner.nextLine();

        //получение пометки к переводу
        System.out.println("Введите пометку к переводу");
        memory = scanner.nextLine();

        //внесение средств
        user1.addAccountTransaction(toAkk, transfAmount, memory);

    }


    //вывод средств
    public static void withdrawMoney(User user1, Scanner scanner) {

        int fromAkk;
        double transfAmount;
        double akkBalance;
        String memory;


        // получение информации о том с какого аккаунта переводят средства
        do {
            System.out.printf("Введите номер счёта (1-%d), с которого будут" +
                    " списаны средства:", user1.numAccounts());
            fromAkk = scanner.nextInt() - 1;
            if (fromAkk < 0 || fromAkk >= user1.numAccounts()) {
                System.out.println("Такой счёт не существует. Попробуйте снова.");
            }


        } while (fromAkk < 0 || fromAkk >= user1.numAccounts());

        akkBalance = user1.getBalance(fromAkk);


        // получение информации о сумме перевода
        do {
            System.out.printf("Введите сумму (максимум $%.02f):", akkBalance);
            transfAmount = scanner.nextDouble();
            if (transfAmount < 0) {
                System.out.println("Сумма должна быть больше 0.");
            } else if (transfAmount > akkBalance) {
                System.out.println("Сумма не может быть больше баланса.");
            }
        } while (transfAmount < 0 || transfAmount > akkBalance);
        scanner.nextLine();

        //получение пометки к переводу
        System.out.println("Введите пометку к переводу");
        memory = scanner.nextLine();

        //вывод средств
        user1.addAccountTransaction(fromAkk, -1 * transfAmount, memory);

    }


    // функция перевода средств
    // user1 - пользователь совершающий перевод
    public static void transferMoney(User user1, Scanner scanner) {

        int fromAkk;
        int toAkk;
        double transfAmount;
        double akkBalance;


        // получение информации о том с какого аккаунта переводят средства
        do {
            System.out.printf("Введите номер счёта (1-%d), с которого будут" +
                    " списаны средства для перевода:", user1.numAccounts());
            fromAkk = scanner.nextInt() - 1;
            if (fromAkk < 0 || fromAkk >= user1.numAccounts()) {
                System.out.println("Такой счёт не существует. Попробуйте снова.");
            }


        } while (fromAkk < 0 || fromAkk >= user1.numAccounts());

        akkBalance = user1.getBalance(fromAkk);

        // получение информации о том на какой аккаунт переводят средства
        do {
            System.out.printf("Введите номер счёт (1-%d), на который будут" +
                    " переведены средства:", user1.numAccounts());
            toAkk = scanner.nextInt() - 1;
            if (toAkk < 0 || toAkk >= user1.numAccounts()) {
                System.out.println("Такой счёт не существует. Попробуйте снова.");
            }


        } while (toAkk < 0 || toAkk >= user1.numAccounts());

        // получение информации о сумме перевода
        do {
            System.out.printf("Введите сумму перевода (максимум $%.02f):", akkBalance);
            transfAmount = scanner.nextDouble();
            if (transfAmount < 0) {
                System.out.println("Сумма перевода должна быть больше 0.");
            } else if (transfAmount > akkBalance) {
                System.out.println("Сумма перевода не может быть больше баланса.");
            }
        } while (transfAmount < 0 || transfAmount > akkBalance);

        // сам процесс перевода
        user1.addAccountTransaction(fromAkk, -1 * transfAmount, String.format("Перевод на счёт %s",
                user1.getAccountUUID(toAkk)));
        user1.addAccountTransaction(toAkk, transfAmount, String.format("Перевод с счёта %s",
                user1.getAccountUUID(fromAkk)));

    }

    // распечатка истории транзакций аккаунта
    //user1 - пользователь
    public static void showTransactionHistory(User user1, Scanner scanner) {
        int Akk;

        // получение информации о том про историю какого аккаунта
        // хочет узнать пользователь
        do {

            System.out.printf("Введите номер счёта (1-%d), историю, которого вы " +
                    "хотите увидеть:", user1.numAccounts());
            Akk = scanner.nextInt() - 1;

            if (Akk < 0 || Akk >= user1.numAccounts()) {
                System.out.println("Такой счёта не существует. Попробуйте снова.");
            }
        } while (Akk < 0 || Akk >= user1.numAccounts());

        // вывод истории транзакций
        user1.printAccountTransactionHistory(Akk);
    }

    /*
    Вывод меню банкомата
    theBank - банк чей аккаунт используется
     */
    public static User mainMenuPrompt(Bank theBank, Scanner scanner) {
        String userID;
        String pin;
        User authUser;

        // запрос к пользователю комбинации id и пин-кода пока не будет верно
        do {
            System.out.println("\n\n " + theBank.getName() + "\n\n");
            System.out.println("Введите id пользователя:");
            userID = scanner.nextLine();
            System.out.println("Введите пин-код:");
            pin = scanner.nextLine();
            // ищем объект пользователя соответствующий введённым данным
            authUser = theBank.UserLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Неверный id или пин-код. Попробуйте ещё раз.");
            }
        } while (authUser == null);

        return authUser;
    }
}
