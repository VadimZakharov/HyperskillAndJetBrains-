package bot;

import java.util.Scanner;

public class SimpleBot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        if (s.endsWith("burg")){
            System.out.println("true");
        }
        else System.out.println("false");

    }
}