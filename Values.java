import java.util.ArrayList;
import java.util.Scanner;

public class Values {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            String str = in.nextLine();
            String hole = "";
            ArrayList<Integer> arr = new ArrayList<Integer>();
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == ' ')
                    i++;
                if (i == str.length() - 1)
                    break;
                hole += str.charAt(i);
                int k = Integer.parseInt(hole);
                if (k > 4) {
                    System.out.println("0");
                    return;
                }
                hole = "";
                if (k == 0)
                    i++;
                else {
                    for (int j = 0; j < k; j++) {
                        i = i + 2;
                        hole += str.charAt(i);
                    }
                    arr.add(Integer.parseInt(hole));
                    if (arr.size() > 1000) {
                        System.out.println("0");
                        return;
                    }
                    hole = "";
                }
            }
            System.out.print(arr.size() + " ");
            for (int i = 0; i < arr.size(); i++)
                System.out.print(arr.get(i) + " ");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("0");
        }
    }
}
