import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


class Arrays {
    ArrayList<Integer> arr = new ArrayList<Integer>();
    ArrayList<Character> arrchar = new ArrayList<Character>();
}


public class ReadString {

    static void DeleteSpace(StringBuffer str) {
        // Метод удаляющий пробелы
        int i = 0;
        while (i < str.length()) {
            if (str.charAt(i) == ' ') {
                str.deleteCharAt(i);
                i--;
            } else
                i++;
        }
    }

    static Arrays StrokaInArray(StringBuffer str) {
        // Метод заполнящий массивы чисел и символов
        Arrays arrays = new Arrays();
        String Spic = "";
        int i = 0;
        while (i < str.length()) {
            if (Character.isDigit(str.charAt(i))) {
                while (Character.isDigit(str.charAt(i))) {
                    Spic += str.charAt(i);
                    i++;
                    if (i == str.length()) {
                        break;
                    }
                }
                arrays.arr.add(Integer.parseInt(Spic));
                Spic = "";
            } else {
                arrays.arrchar.add(str.charAt(i));
                i++;
            }
        }
        return arrays;
    }

    static void Mul(Arrays arrays, int z, int k, int i){
        // Методы умножающий 2 числа массива и удаляющий использованые числа и симвыолы
        arrays.arr.set(z, arrays.arr.get(z) * arrays.arr.get(i));
        arrays.arrchar.remove(k);
        arrays. arr.remove(i);
    }
    static void Div(Arrays arrays, int z, int k, int i){
        // Методы делящий 2 числа массива и удаляющий использованые числа и симвыолы
        arrays.arr.set(z, arrays.arr.get(z) / arrays.arr.get(i));
        arrays.arrchar.remove(k);
        arrays. arr.remove(i);
    }
    static void Sum(Arrays arrays, int z, int k, int i){
        // Методы Складывающий 2 числа массива и удаляющий использованые числа и симвыолы
        arrays.arr.set(z, arrays.arr.get(z) + arrays.arr.get(i));
        arrays.arrchar.remove(k);
        arrays. arr.remove(i);
    }
    static void Sub(Arrays arrays, int z, int k, int i){
        // Методы вычитающий 2 числа массива и удаляющий использованые числа и симвыолы
        arrays.arr.set(z, arrays.arr.get(z) - arrays.arr.get(i));
        arrays.arrchar.remove(k);
        arrays. arr.remove(i);
    }


    public static void main(String[] args) {
        Arrays arrays = new Arrays();

        StringBuffer stroka = new StringBuffer();
        Scanner in = new Scanner(System.in);
        stroka.append(in.nextLine());

        DeleteSpace(stroka);
        System.out.println(stroka);
        arrays = StrokaInArray(stroka);


        int countOpen = Collections.frequency(arrays.arrchar, '(');
        int countClose = Collections.frequency(arrays.arrchar, ')');

        while (arrays.arr.size()>1) {
            if ((countOpen > 0) && (countOpen == countClose))  {
                for(int j = 0; j<countOpen;j++) {
                    for (int z = arrays.arrchar.indexOf('('); z < arrays.arrchar.indexOf(')'); z++) {
                        if (arrays.arrchar.get(z) == '*') {
                            Mul(arrays,z-1, z, z);

                            z = arrays.arrchar.indexOf('(');
                        } else {
                            if (arrays.arrchar.get(z) == '/') {
                                Div(arrays,z-1, z, z);
                                z =arrays. arrchar.indexOf('(');
                            }
                        }
                    }
                    for (int z = arrays.arrchar.indexOf('('); z < arrays.arrchar.indexOf(')'); z++) {
                        if (arrays.arrchar.get(z) == '+') {
                            Sum(arrays,z-1, z, z);
                            z = arrays.arrchar.indexOf('(');
                        } else {
                            if (arrays.arrchar.get(z) == '-') {
                                Sub(arrays,z-1, z, z);
                                z = arrays.arrchar.indexOf('(');
                            }
                        }
                    }
                    arrays.arrchar.remove(arrays.arrchar.indexOf('('));
                    arrays. arrchar.remove(arrays.arrchar.indexOf(')'));
                }
            }
            countOpen = 0;

                for (int z = 0; z < arrays.arrchar.size(); z++) {
                    if (arrays.arrchar.get(z) == '*') {
                        Mul(arrays,z, z, z+1);
                        z = 0;
                    } else {
                        if (arrays.arrchar.get(z) == '/') {
                            Div(arrays,z, z,z+1);
                            z = 0;
                        }
                    }
                }
                for (int z = 0; z < arrays.arrchar.size(); z++) {
                    if (arrays.arrchar.get(z) == '+') {
                        Sum(arrays,z, z, z+1);
                        z = 0;
                    } else {
                        if (arrays.arrchar.get(z) == '-') {
                            Sub(arrays,z, z, z+1);
                            z = 0;
                        }
                    }
                }
        }
        for (int j = 0; j<arrays.arr.size(); j++)
            System.out.println(arrays.arr.get(j));

    }
}