package banking;



import java.sql.*;
import java.util.*;



public class Main {

    static Map<String,String> userNPass = new HashMap<>();

    static boolean closeConnection = false;

    public static String getTemperCard() {
        return temperCard;
    }

    public static void setTemperCard(String temperCard) {
        Main.temperCard = temperCard;
    }



    public static String temperCard;


    public static void main(String[] args) {

        createTable();
        menu();
        closeConnection = true;


    }

    public static Connection connect(){
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:C:/Users/Вадим/Desktop/Simple Banking System/Simple Banking System/task/card.s3db");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
        if (closeConnection){
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return con;
    }
    private static void menu(){
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        switch (input){
            case (1):{
                createAnAccount();
                break;
            }
            case (2):{
                logIntoAccount();
                break;
            }
            case (0):{
                System.out.println("Bye!");
                closeConnection = true;
                break;
            }


        }
    }

    private static void createAnAccount(){

        List<Integer> array = new ArrayList<>();
        int sum = 0;
        boolean b = true;
        StringBuilder temper = null;
        int counter = 0;
        String temper2 = null;

        Random random = new Random();

        int numberGenerated = random.nextInt(1000000000);
        int pinGenerated = random.nextInt(10000);
        long cardNumber = 4000000000000000L + numberGenerated;

        String pinConcat = "0000" + pinGenerated;
        String pin = pinConcat.substring(pinConcat.length() - 4);

        String str = Long.toString(cardNumber);
        String[]numb=str.split("");

        int[] numArr = new int[numb.length];
        for (int i = 0; i < numb.length; i++) {
            numArr[i] = Integer.parseInt(numb[i]);
        }
        for (int j = 0; j < 16; j++) {
            if(j % 2 ==0){
                    int temp;
                    temp = numArr[j] * 2;
                    if(temp > 9){
                        temp = temp - 9;
                    }
                    array.add(temp);
            }
            else {
                array.add(numArr[j]);
            }
        }

           while (b) {
                sum = 0;
                for (int i = 0; i < array.size(); i++) {
                    sum = sum + array.get(i);
                }
                if(sum %10 == 0){
                  temper = new StringBuilder(str);
                  temper.deleteCharAt(temper.length()-1);
                  temper.append(array.get(array.size()-1));
                  temper2 = temper.toString();



                    userNPass.put(temper2,pin);
                    b = false;
                }
                else {
                    array.remove(array.size()-1);
                    array.add(array.size(),counter);
                    counter++;
                }
            }



        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(temper2);
        System.out.println("Your card PIN:");
        System.out.println(pin);
        System.out.println();

        insert(temper2,pin,0);

        menu();
    }
    private static void logIntoAccount(){
        readData();

        Scanner scCardNumber = new Scanner(System.in);

        System.out.println("Enter your card number:");
        Scanner scCardPin = new Scanner(System.in);
        String cardNumber = scCardNumber.nextLine();

        System.out.println("Enter your PIN:");
        String pin = scCardPin.nextLine();

        setTemperCard(cardNumber);

        if(userNPass.containsKey(cardNumber) && userNPass.containsValue(pin)){
            System.out.println("You have successfully logged in!");


            loginMenu();


            }
        else {
            System.out.println("Wrong card number or PIN!");
            menu();

        }
    }
    private static void loginMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
        int choose = scanner.nextInt();
        switch (choose){
            case (1):{
                getBalance();
                loginMenu();
                break;
            }
            case (2):{
                addIncome();
                loginMenu();
                break;
            }
            case (3):{
                doTransfer();
                loginMenu();
                break;
            }
            case (4):{
                closeAcc();
                menu();
                break;
            }
            case (5):{
                System.out.println("You have successfully logged out!");
                menu();
                break;
            }
            case (0):{
                System.out.println("Bye!");
                closeConnection = true;
                break;
            }
        }

    }
    private static void insert(String number,String pin,int balance){
        Connection con = connect();
        PreparedStatement ps = null;
        try{
            String sql = "INSERT INTO card (number,pin,balance) VALUES (?,?,?)";
            ps = con.prepareStatement(sql);

            ps.setString(1,number);
            ps.setString(2,pin);
            ps.setInt(3,balance);
            ps.execute();

        }
        catch (SQLException e){
            System.out.println(e.toString());
        }

    }
    private static void getBalance(){
        Connection connection = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select balance from card where number = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,getTemperCard());
            ps.execute();
            rs = ps.executeQuery();
            String card = rs.getString("balance");
            System.out.println("Balance: " + card);



        }catch (SQLException e){
            System.out.println(e.toString());
        }finally {
            try {
                rs.close();
                ps.close();
                connection.close();

            } catch (SQLException e){
                System.out.println(e.toString());
            }

        }


    }
    private static void addIncome(){
        System.out.println("Enter income:");
        Scanner sc = new Scanner(System.in);
        String income = sc.nextLine();
        Connection connection = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "UPDATE card SET balance = balance + ? WHERE number=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,income);
            ps.setString(2,getTemperCard());
            ps.execute();


            System.out.println("Income was added!");



        }catch (SQLException e){
            System.out.println(e.toString());
        }finally {
            try {

                ps.close();
                connection.close();

            } catch (SQLException e){
                System.out.println(e.toString());
            }

        }


    }
    private static void doTransfer(){
        boolean luna = false;
        boolean cardNumberCheck = false;
        boolean sameCard = true;
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        Scanner sc = new Scanner(System.in);
        Long card = sc.nextLong();
        List<Integer> array = new ArrayList<>();
        int sum = 0;


        String str = Long.toString(card);
        String[]numb=str.split("");

        int[] numArr = new int[numb.length];
        for (int i = 0; i < numb.length; i++) {
            numArr[i] = Integer.parseInt(numb[i]);
        }
        for (int j = 0; j < 16; j++) {
            if(j % 2 ==0){
                int temp;
                temp = numArr[j] * 2;
                if(temp > 9){
                    temp = temp - 9;
                }
                array.add(temp);
            }
            else {
                array.add(numArr[j]);
            }
        }
        for (int i = 0; i < array.size(); i++) {
                sum = sum + array.get(i);
            }
        if(sum %10 == 0){
               luna = true;

            }
        else {
                System.out.println("Probably you made a mistake in the card number. Please try again!");

            }

        if (userNPass.containsKey(card.toString())){
            cardNumberCheck = true;
        }
        else {
            System.out.println("Such a card does not exist.");

        }
        if (!card.toString().equals(getTemperCard())){
            sameCard = false;
        }
        else {
            System.out.println("You can't transfer money to the same account!");

        }

        if (luna && cardNumberCheck && !sameCard) {
            Connection connection = connect();
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                String sql = "Select balance from card where number = ?";
                ps = connection.prepareStatement(sql);
                ps.setString(1,getTemperCard());
                ps.execute();
                rs = ps.executeQuery();
                String card1 = rs.getString("balance");
                int balance = Integer.parseInt(card1);
                System.out.println("Enter how much money you want to transfer:");
                Scanner scanner = new Scanner(System.in);
                int transfer = scanner.nextInt();

                if(balance>= transfer){
                    try {
                        String sql1 = "UPDATE card SET balance= balance + ? WHERE number=?";
                        ps = connection.prepareStatement(sql1);
                        ps.setInt(1,transfer);
                        ps.setLong(2,card);
                        ps.execute();

                        String sql2 = "UPDATE card SET balance= balance - ? WHERE number=?";
                        ps = connection.prepareStatement(sql2);
                        ps.setInt(1,transfer);
                        ps.setString(2,getTemperCard());
                        ps.execute();

                        System.out.println("Success!");


                    }catch (SQLException e){
                        System.out.println(e.toString());
                    }finally {
                        try {

                            ps.close();
                            connection.close();

                        } catch (SQLException e){
                            System.out.println(e.toString());
                        }

                    }
                }
                else {
                    System.out.println("Not enough money!");

                }




            }catch (SQLException e){
                System.out.println(e.toString());
            }finally {
                try {
                    rs.close();
                    ps.close();
                    connection.close();

                } catch (SQLException e){
                    System.out.println(e.toString());
                }

            }

        }

    }
    private static void closeAcc(){
        Connection connection = connect();
        PreparedStatement ps = null;

        try {
            String sql = "Delete FROM card  WHERE number = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,getTemperCard());

            ps.execute();


            System.out.println("The account has been closed!");



        }catch (SQLException e){
            System.out.println(e.toString());
        }finally {
            try {

                ps.close();
                connection.close();

            } catch (SQLException e){
                System.out.println(e.toString());
            }

        }
    }
    private static void createTable(){
        Connection connection = connect();
        PreparedStatement ps = null;

        try {
            String sql = "CREATE TABLE IF NOT EXISTS card (" +
                    "id INTEGER constraint card_pk primary key autoincrement," +
                    "number TEXT," +
                    "pin TEXT," +
                    "balance int) ";
            ps = connection.prepareStatement(sql);
            ps.execute();

        }catch (SQLException e){
            System.out.println(e.toString());
        }finally {
            try {

                ps.close();
                connection.close();

            } catch (SQLException e){
                System.out.println(e.toString());
            }

        }
    }


    private static void readData(){
        Connection connection = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select number, pin from card";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                String card = rs.getString("number");
                String pin = rs.getString("pin");

                userNPass.put(card,pin);
            }

        }catch (SQLException e){
            System.out.println(e.toString());
        }finally {
            try {
                rs.close();
                ps.close();
                connection.close();

            } catch (SQLException e){
                System.out.println(e.toString());
            }

        }
    }
}


