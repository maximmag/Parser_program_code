import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


class Arrays {
    ArrayList<Integer> arr = new ArrayList<Integer>();
    ArrayList<Character> arrchar = new ArrayList<Character>();
}


public class ReadString {

    Arrays StrokaInArray(String str) {
        // Метод заполнящий массивы чисел и символов
        Arrays arrays = new Arrays();
        int i = 0;
        while (i < str.length()) {
            if (Character.isDigit(str.charAt(i))) {
                String spic = "";
                while (Character.isDigit(str.charAt(i))) {
                    spic += str.charAt(i);
                    i++;
                    if (i == str.length()) {
                        break;
                    }
                }
                arrays.arr.add(Integer.parseInt(spic));
            } else if (str.charAt(i) == '(') {
                String spic = "";
                int j = 1;
                for (int k = i + 1; k < str.length(); k++) {
                    if (str.charAt(k) == '(')
                        j++;
                    else if (str.charAt(k) == ')') {
                        j--;
                        if (j == 0) {
                            i = k + 1;
                            break;
                        }
                    }
                    spic += str.charAt(k);
                }
                arrays.arr.add(evaluateExpression(spic));
            } else if (str.charAt(i) != ' ') {
                arrays.arrchar.add(str.charAt(i));
                i++;
            }
        }
        return arrays;
    }

    void Mul(Arrays arrays, int z) {
        // Методы умножающий 2 числа массива и удаляющий использованые числа и симвыолы
        arrays.arr.set(z, arrays.arr.get(z) * arrays.arr.get(z + 1));
        arrays.arrchar.remove(z);
        arrays.arr.remove(z + 1);
        z = 0;
    }

    void Div(Arrays arrays, int z) {
        // Методы делящий 2 числа массива и удаляющий использованые числа и симвыолы
        arrays.arr.set(z, arrays.arr.get(z) / arrays.arr.get(z + 1));
        arrays.arrchar.remove(z);
        arrays.arr.remove(z + 1);
        z = 0;
    }

    void Sum(Arrays arrays, int z) {
        // Методы Складывающий 2 числа массива и удаляющий использованые числа и симвыолы
        arrays.arr.set(z, arrays.arr.get(z) + arrays.arr.get(z + 1));
        arrays.arrchar.remove(z);
        arrays.arr.remove(z + 1);
        z = 0;
    }

    void Sub(Arrays arrays, int z) {
        // Методы вычитающий 2 числа массива и удаляющий использованые числа и симвыолы
        arrays.arr.set(z, arrays.arr.get(z) - arrays.arr.get(z + 1));
        arrays.arrchar.remove(z);
        arrays.arr.remove(z + 1);
        z = 0;
    }

    void rech(Arrays arrays) {

        for (int z = 0; z < arrays.arrchar.size(); z++)
            if (arrays.arrchar.get(z) == '*')
                Mul(arrays, z);
            else if (arrays.arrchar.get(z) == '/')
                Div(arrays, z);

        for (int z = 0; z < arrays.arrchar.size(); z++)
            if (arrays.arrchar.get(z) == '+')
                Sum(arrays, z);
            else if (arrays.arrchar.get(z) == '-')
                Sub(arrays, z);

    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String inputStr = in.nextLine();

        int res = new ReadString().evaluateExpression(inputStr);

        System.out.println(res);

    }

    public int evaluateExpression(String inputStr) {


        Arrays arrays = StrokaInArray(inputStr);

        while (arrays.arr.size() > 1) {
            rech(arrays);
        }

        return arrays.arr.get(0);
    }
}