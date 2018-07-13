import java.util.Scanner;

public class Value {
    public static void main(String[] args){

        int value1 = 0;
        int value2 = 0;
        int i = 0;
        int rezult;
        String ch;

        do {
            Scanner in = new Scanner(System.in);
        System.out.println("Введи первое целое число");
            if (in.hasNextInt()) {
                value1 = in.nextInt();
                i = 1;
            }
            else
                System.out.println("Введено не целое число");
        } while (i == 0);

        i = 0;

        do {
            Scanner in = new Scanner(System.in);
            System.out.println("'+' - сложить.  '-' - вычесть. '*' - умножить. '/' - разделить");
            ch = in.next();
            i = 1;
        } while (i == 0);
        i = 0;
        do {
            Scanner in = new Scanner(System.in);
            System.out.println("Введи второе целое число");
            if (in.hasNextInt()) {
                value2 = in.nextInt();
                i = 1;
            }
            else
                System.out.println("Введено не целое число");
        } while (i == 0);

 //       Scanner in = new Scanner(System.in);
 //       System.out.println("'+' - сложить.  '-' - вычесть. '*' - умножить. '/' - разделить");
 //       String ch = in.next();

        if (ch.equals("+")) {
            rezult = value1 + value2;
            System.out.println("Результат сложения = " + rezult);
        }
        else if (ch.equals("-")) {
            rezult = value1 - value2;
            System.out.println("Результат вычитания = " + rezult);
        }
        else if (ch.equals("*")) {
            rezult = value1 * value2;
            System.out.println("Результат умножения = " + rezult);
        }
        else if (ch.equals("/")) {
            if (value2 != 0) {
                rezult = value1 / value2;
                System.out.println("Результат деления = " + rezult);
            }
            else
                System.out.println("На 0 делить нельзя");
        }
        else
            System.out.println("Невозможно выполнить несуществующую операцию");
        }

    }

