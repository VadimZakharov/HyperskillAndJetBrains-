package machine;

import java.util.Scanner;

public class CoffeeMachine {

    static int water = 400;
    static int milk = 540;
    static int beans = 120;
    static int money = 550;
    static int disposableCups = 9;


    public static int getWater() {
        return water;
    }

    public static void setWater(int water) {
        CoffeeMachine.water = water;
    }

    public static int getMilk() {
        return milk;
    }

    public static void setMilk(int milk) {
        CoffeeMachine.milk = milk;
    }

    public static int getBeans() {
        return beans;
    }

    public static void setBeans(int beans) {
        CoffeeMachine.beans = beans;
    }

    public static int getMoney() {
        return money;
    }

    public static void setMoney(int money) {
        CoffeeMachine.money = money;
    }

    public static int getDisposableCups() {
        return disposableCups;
    }

    public static void setDisposableCups(int disposableCups) {
        CoffeeMachine.disposableCups = disposableCups;
    }



    public static void main(String[] args) {

         menu();
    }

    public static void about(){
        System.out.println("The coffee machine has:");
        System.out.println(getWater() + " ml of water");
        System.out.println(getMilk() + " ml of milk");
        System.out.println(getBeans() + " g of coffee beans");
        System.out.println(getDisposableCups() +" disposable cups");
        System.out.println("$" + getMoney() +" of money");
        System.out.println();
        menu();
    }

    public static void actionBuy(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String choose = scanner.nextLine();
        switch (choose) {
            case "1":

                if (getWater() >= 250 && getBeans() >= 120 && getDisposableCups() >= 1) {
                    setWater(water - 250);
                    setBeans(beans - 16);
                    setDisposableCups(disposableCups - 1);
                    setMoney(money + 4);
                    System.out.println("I have enough resources, making you a coffee!");
                    menu();

                } else if (getWater() < 250) {
                    System.out.println("Sorry, not enough water!");
                    menu();

                }

                else if (getBeans() < 16){
                    System.out.println("Sorry, not enough coffee beans!");
                    menu();

                }
                else if (getDisposableCups() < 1){
                    System.out.println("Sorry, not enough disposable cups");
                    menu();

                }



                break;
            case "2":
                if (getWater() >= 350 && getMilk() >= 75 && getBeans() >= 20 && getDisposableCups() >= 1) {
                    setWater(water - 350);
                    setMilk(milk - 75);
                    setBeans(beans - 20);
                    setDisposableCups(disposableCups - 1);
                    setMoney(money + 7);
                    System.out.println("I have enough resources, making you a coffee!");
                    menu();
                } else if (getWater() < 350) {
                    System.out.println("Sorry, not enough water!");
                    menu();

                }
               else if (getMilk() < 75){
                     System.out.println("Sorry, not enough milk!");
                 menu();
                }
                else if (getBeans() < 20){
                    System.out.println("Sorry, not enough coffee beans!");
                    menu();

                }
                else if (getDisposableCups() < 1){
                    System.out.println("Sorry, not enough disposable cups");
                    menu();

                }
                break;
            case "3":
                if (getWater() >= 200 && getMilk() >= 100 && getBeans() >= 12 && getDisposableCups() >= 1) {
                    setWater(water - 200);
                    setMilk(milk - 100);
                    setBeans(beans - 12);
                    setDisposableCups(disposableCups - 1);
                    setMoney(money + 6);
                    System.out.println("I have enough resources, making you a coffee!");
                    menu();

                } else if (getWater() < 200) {
                    System.out.println("Sorry, not enough water!");
                    menu();

                }
                else if (getMilk() < 100){
                    System.out.println("Sorry, not enough milk!");
                    menu();
                }
                else if (getBeans() <12){
                    System.out.println("Sorry, not enough coffee beans!");
                    menu();

                }
                else if (getDisposableCups() < 1){
                    System.out.println("Sorry, not enough disposable cups");
                    menu();

                }


                break;
            case "back":
                menu();
                break;
        }
        }

    public static void actionFill(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water you want to add:");
        int waterFill = scanner.nextInt();
        setWater(water + waterFill);
        System.out.println("Write how many ml of milk you want to add: ");
        int milkFill = scanner.nextInt();
        setMilk(milk + milkFill);
        System.out.println("Write how many grams of coffee beans you want to add:");
        int beansFill = scanner.nextInt();
        setBeans(beans + beansFill);
        System.out.println("Write how many disposable cups of coffee you want to add: ");
        int discupsFill = scanner.nextInt();
        setDisposableCups(disposableCups + discupsFill);

        menu();



    }

    public static void actionTake(){
        System.out.println("I gave you $" + getMoney());
        System.out.println();
        setMoney(0);
        menu();

    }

    public static void menu(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Write action (buy, fill, take, remaining, exit): ");
        String choose = scanner.nextLine();



        switch (choose) {
            case "buy":
                actionBuy();
                break;
            case "fill":
                actionFill();
                break;
            case "take":
                actionTake();
                break;
            case "remaining":
                about();
                break;
            case "exit":
                System.exit(0);
                break;
        }

    }

}
