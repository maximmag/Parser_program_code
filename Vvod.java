import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class Vvod {

    public static void main(String[] args) {
        ArrayList <Integer> arr = new ArrayList<Integer>();
        ArrayList <Character> arrchar = new ArrayList<Character>();

        StringBuffer stroka = new StringBuffer();

        Scanner in = new Scanner(System.in);

        stroka.append(in.nextLine());

        int i = 0;
        while (i < stroka.length()){
                if (stroka.charAt(i) == ' ') {
                    stroka.deleteCharAt(i);
                    i--;
                }
                else
                    i++;
            }

        System.out.println(stroka);

        String Spic = "";
         i = 0;
        while (i < stroka.length()){
            if(Character.isDigit(stroka.charAt(i))){
                while(Character.isDigit(stroka.charAt(i))) {
                    Spic += stroka.charAt(i);
                    i++;
                    if (i == stroka.length()){
                        break;
                    }
                }
                arr.add(Integer.parseInt(Spic));
                Spic = "";
            } else {
                arrchar.add(stroka.charAt(i));
                i++;
            }
        }




        int count = Collections.frequency(arrchar, '(');

        while (arr.size()>1) {
            if (count > 0) {
                for(int j = 0; j<count;j++) {
                    for (int z = arrchar.indexOf('('); z < arrchar.indexOf(')'); z++) {
                        if (arrchar.get(z) == '*') {
                            arr.set(z-1, arr.get(z - 1) * arr.get(z));
                            arrchar.remove(z);
                            arr.remove(z);
                            z = arrchar.indexOf('(');
                        } else {
                            if (arrchar.get(z) == '/') {
                                arr.set(z-1, arr.get(z - 1) / arr.get(z));
                                arrchar.remove(z);
                                arr.remove(z);
                                z = arrchar.indexOf('(');
                            }
                        }
                    }
                    for (int z = arrchar.indexOf('('); z < arrchar.indexOf(')'); z++) {
                        if (arrchar.get(z) == '+') {
                            arr.set(z-1, arr.get(z - 1) + arr.get(z));
                            arrchar.remove(z);
                            arr.remove(z);
                            z = arrchar.indexOf('(');
                        } else {
                            if (arrchar.get(z) == '-') {
                                arr.set(z-1, arr.get(z - 1) - arr.get(z));
                                arrchar.remove(z);
                                arr.remove(z);
                                z = arrchar.indexOf('(');
                            }
                        }
                    }
                    arrchar.remove(arrchar.indexOf('('));
                    arrchar.remove(arrchar.indexOf(')'));
                }
            }
            count = 0;

                for (int z = 0; z < arrchar.size(); z++) {
                    if (arrchar.get(z) == '*') {
                        arr.set(z, arr.get(z) * arr.get(z+1));
                        arrchar.remove(z);
                        arr.remove(z + 1 );
                        z = 0;
                    } else {
                        if (arrchar.get(z) == '/') {
                            arr.set(z, arr.get(z) / arr.get(z+1));
                            arrchar.remove(z);
                            arr.remove(z + 1);
                            z = 0;
                        }
                    }
                }
                for (int z = 0; z < arrchar.size(); z++) {
                    if (arrchar.get(z) == '+') {
                        arr.set(z, arr.get(z) + arr.get(z+1));
                        arrchar.remove(z);
                        arr.remove(z + 1);
                        z = 0;
                    } else {
                        if (arrchar.get(z) == '-') {
                            arr.set(z, arr.get(z) - arr.get(z+1));
                            arrchar.remove(z);
                            arr.remove(z + 1);
                            z = 0;
                        }
                    }
                }


        }


        for (int j = 0; j<arr.size(); j++)
            System.out.println(arr.get(j));

        for (int j = 0; j<arrchar.size(); j++)
            System.out.println(arrchar.get(j));

    }
}