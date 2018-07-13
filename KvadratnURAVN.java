 import java.util.Scanner;

    class Uravn {
    private double a;
    private double b;
    private double c;
    private double x1;
    private double x2;
    private double d;

    public static void main(String[] args) {
        int i = 0;
        Uravn uravn = new Uravn();
        System.out.println("Программа для решения квадратный уравнений вида 'ax²+bx+c' ");

        System.out.println("Введи Коэффицент уравнения a");
        do {
            Scanner in = new Scanner(System.in);
            if (in.hasNextDouble()) {
                uravn.a = in.nextDouble();
                i = 1;
            }
            else
                System.out.println("Введено не число. Повторите попытку");
        } while (i == 0);

        i = 0;

        System.out.println("Введи Коэффицент уравнения b");
        do {
            Scanner in = new Scanner(System.in);
            if (in.hasNextDouble()) {
                uravn.b = in.nextDouble();
                i = 1;
            }
            else
                System.out.println("Введено не число. Повторите попытку");
        } while (i == 0);

        i = 0;

        System.out.println("Введи Коэффицент уравнения c");
        do {
            Scanner in = new Scanner(System.in);
            if (in.hasNextDouble()) {
                uravn.c = in.nextDouble();
                i = 1;
            }
            else
                System.out.println("Введено не число. Повторите попытку");
        } while (i == 0);
        System.out.println("Уравнение имеет вид - ("+uravn.a+"x²)+("+uravn.b+"x)+("+uravn.c+") = 0");
         uravn.Discr();
         System.out.println("Дискриминант уравнения = " + uravn.d);
         if (uravn.d > 0 ) {
             System.out.println("Дискриминант больше 0, а следовательно уравнение имеет 2 корня");
             uravn.DiscrPositiv();
             System.out.println("Первый корень уравнения = "+ uravn.x1);
             System.out.println("Второй корень уравнения = "+ uravn.x2);
         }
         else if (uravn.d == 0){
             System.out.println("Дискриминант равен 0, а следовательно уравнение имеет 1 корень");
             uravn.DiscrNull();
             System.out.println("Корень уравнения = "+ uravn.x1);
         }
         else {
             System.out.println("Дискриминант меньше 0, а следовательно уравнение не имеет корней");
         }
      }

        void Discr(){
            d = b*b - 4*a*c;
    }
        void DiscrPositiv(){
            x1 = (-b + Math.sqrt(d))/2*a;
            x2 = (-b - Math.sqrt(d))/2*a;
        }
        void DiscrNull(){
            x1 = -b/2*a;
        }
}
