import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


class Arrays {
    ArrayList<Double> arr = new ArrayList<Double>();
    ArrayList<Character> arrchar = new ArrayList<Character>();
}



public class Analyzer {


    public static final String WRITE = "WRITE";
    public static final String READ = "READ";


    public static void main(String[] args) throws Exception {
        String str = "";
        File file = new File("temp.txt");
        FileReader fr = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fr);

        while (bufferedReader.ready()) {
            str = str + bufferedReader.readLine() + " ";
        }

        Analyzer analyzer = new Analyzer();
        boolean res = analyzer.analyzeProgram(str);
        if (res){
            System.out.println("Программа написана верно");
        }
    }


    public static final String[] KEYWORDS = {"BEGIN ", " END", "VAR ", "REAL", "OF ", "ARRAY ", "FOR ", " TO ", " DO ", "INTEGER", WRITE, READ};
    public ArrayList<String> varOfArray = new ArrayList<String>();
    public ArrayList<String> varOfDouble = new ArrayList<String>();
    public ArrayList<String> varOfInteger = new ArrayList<String>();
    public ArrayList<Double> numOfDouble = new ArrayList<Double>();
    public ArrayList<Integer> numOfInteger = new ArrayList<Integer>();


    // Метод фасующий именя по массивам чисел и символов
    Arrays StrokaInArray(String str) {
        Arrays arrays = new Arrays();

        if (str.charAt(0) == '-')
            str = "0" + str;

        int i = 0;
        while (i < str.length()) {
            if (Character.isDigit(str.charAt(i)) || (str.charAt(i) == '.')) {
                String spic = "";
                while (Character.isDigit(str.charAt(i)) || (str.charAt(i) == '.')) {
                    spic += str.charAt(i);
                    i++;
                    if (i == str.length()) {
                        break;
                    }
                }
                arrays.arr.add(Double.parseDouble(spic));
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
            } else if (!Character.toString(str.charAt(i)).matches("[+*/-]")) {
                String spic = "";
                while (!Character.toString(str.charAt(i)).matches("[+*/-]")) {
                    spic += str.charAt(i);
                    i++;
                    if (i == str.length()) {
                        break;
                    }
                }
                int varPos;
                if (Collections.frequency(varOfDouble, spic) == 1) {
                    varPos = varOfDouble.indexOf(spic.trim());
                    arrays.arr.add(numOfDouble.get(varPos));
                } else if (Collections.frequency(varOfInteger, spic) == 1) {
                    varPos = varOfInteger.indexOf(spic.trim());
                    arrays.arr.add(Double.parseDouble(Integer.toString(numOfInteger.get(varPos))));
                }
            } else {
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
    }

    void Div(Arrays arrays, int z) {
        // Методы делящий 2 числа массива и удаляющий использованые числа и симвыолы
        if (arrays.arr.get(z + 1) == 0.0) {
            System.out.println("На 0 делить нельзя");
            System.exit(0);
        }
        arrays.arr.set(z, arrays.arr.get(z) / arrays.arr.get(z + 1));
        arrays.arrchar.remove(z);
        arrays.arr.remove(z + 1);
    }

    void Sum(Arrays arrays, int z) {
        // Методы Складывающий 2 числа массива и удаляющий использованые числа и симвыолы
        arrays.arr.set(z, arrays.arr.get(z) + arrays.arr.get(z + 1));
        arrays.arrchar.remove(z);
        arrays.arr.remove(z + 1);
    }

    void Sub(Arrays arrays, int z) {
        // Методы вычитающий 2 числа массива и удаляющий использованые числа и симвыолы
        arrays.arr.set(z, arrays.arr.get(z) - arrays.arr.get(z + 1));
        arrays.arrchar.remove(z);
        arrays.arr.remove(z + 1);
    }

    // Метод рассчёта
    void rech(Arrays arrays) {

        for (int z = 0; z < arrays.arrchar.size(); z++)
            if (arrays.arrchar.get(z) == '*') {
                Mul(arrays, z);
                z = 0;
            } else if (arrays.arrchar.get(z) == '/') {
                Div(arrays, z);
                z = 0;
            }


        for (int z = 0; z < arrays.arrchar.size(); z++)
            if (arrays.arrchar.get(z) == '+') {
                Sum(arrays, z);
                z = 0;
            } else if (arrays.arrchar.get(z) == '-') {
                Sub(arrays, z);
                z = 0;
            }


    }

    // Метод - На вход подаётся выражение, на выход итог выражения
    public double evaluateExpression(String inputStr) {


        Arrays arrays = StrokaInArray(inputStr);

        while (arrays.arr.size() > 1) {
            rech(arrays);
        }

        return arrays.arr.get(0);
    }
    // Метод, заполняющий объявлённые переменные нулями
    public void inputNum() {
        for (int i = 0; i < varOfInteger.size(); i++)
            numOfInteger.add(0);
        for (int i = 0; i < varOfDouble.size(); i++)
            numOfDouble.add(0.0);
    }

    // метод для поиска первого begin
    public int indexBegin(String program) {
        program = program.toUpperCase();
        int beginPos = program.indexOf(KEYWORDS[0]);
        return beginPos;
    }

    // Метод для поиска последнего End
    public int indexEnd(String program) {
        program = program.toUpperCase();
        int endPos = program.lastIndexOf(KEYWORDS[1] + ".");
        return endPos;
    }

    // Основной метод. разделяющий входную строку на 2 части - объявления переменных и вызова операций
    public boolean analyzeProgram(String program) {
        if (indexBegin(program) >= 0) {
            if (indexEnd(program) >= 0) {
                String narrativePart = program.substring(0, indexBegin(program));
                String operatorPart = program.substring(indexBegin(program) + 5, indexEnd(program));
                return analyzeNarrativPart(narrativePart) && analyzeOperation(operatorPart);
            } else {
                System.out.println("Не найдена точка выхода из программы - 'end.'");
                return false;
            }
        } else {
            System.out.println("Не найдена точка входа в программу - 'begin'");
            return false;
        }
    }

    // Метод Проверяющий часть объявления переменных
    public boolean analyzeNarrativPart(String part) {
        part = part.trim();
        if (part.equals(""))
            return true;
        if (part.charAt(part.length() - 1) != ';') {
            System.out.println(part + " - Ожидалось ';' в конце объявления переменных");
            return false;
        }
        String[] narrativs = part.split(";");
        for (String narrativ : narrativs) {
            if (!analyzeNarrativ(narrativ))
                return false;
        }
        inputNum();
        return true;
    }

    // Метод проверяющий разделяющий часть операций на команды
    public boolean analyzeOperation(String input) {
        input = input.toUpperCase() + ";";
        int k, c;
        for (int i = 0; i < input.length(); i++) {
            k = i;
            String spic = "";
            if (input.charAt(i) != ' ') {
                do {
                    if (i + 1 == input.length()) {
                        spic += input.charAt(i);
                        break;
                    }
                    spic += input.charAt(i);
                    i++;
                } while (input.charAt(i) != ' ');
                if (spic.equals(KEYWORDS[6].trim())) {
                    spic = "";
                    if (input.indexOf(KEYWORDS[8], i) >= 0) {
                        i = input.indexOf(KEYWORDS[8], i) + 4;
                        c = i;
                        while (input.charAt(i) == ' ') {
                            i++;
                        }
                        do {
                            if (i + 1 == input.length()) {
                                spic += input.charAt(i);
                                break;
                            }
                            spic += input.charAt(i);
                            i++;
                        } while (input.charAt(i) != ' ');
                        if (spic.trim().equals(KEYWORDS[0].trim())) {
                            c = searchCloseEnd(input, i);
                            if (c >= 0) {
                                spic = input.substring(k, input.indexOf(";", c));
                                i = c;
                            } else {
                                System.out.println("Не найдена точка выхода из цикла");
                                return false;
                            }
                        } else {
                            i = input.indexOf(";", c);
                            spic = input.substring(k, i);
                        }
                    } else
                        return false;
                    if (!analyzeFor(spic))
                        return false;
                } else if (spic.equals(KEYWORDS[8].trim())) {
                    int beginPos = input.indexOf(KEYWORDS[0].trim(), i);
                    if (beginPos >= 0) {
                        c = searchCloseEnd(input, beginPos + 5);
                        if (c >= 0) {
                            spic = input.substring(k, input.indexOf(";", c));
                            i = c;

                        } else {
                            System.out.println("Не найдена точка выхода из цикла do");
                            return false;
                        }
                    } else {
                        System.out.println("Не найдена точка входа для цикла do");
                        return false;
                    }
                    if (!analyzeDo(spic))
                        return false;
                } else {
                    i = input.indexOf(";", k);
                    spic = input.substring(k, i);
                    if (!analyzeCommand(spic))
                        return false;
                }
            }
        }
        return true;
    }


    //Проверка на наличие var
    public boolean analyzeNarrativ(String narrative) {
        narrative = narrative.toUpperCase().trim();
        int varPos = narrative.indexOf(KEYWORDS[2]);
        if (varPos == 0) {
            String spic = narrative.substring(4);
            return analyzeVar(spic);
        } else {
            System.out.println(narrative + " - Ожидалось 'var'");
            return false;
        }
    }

    // Метод проверяет правильность написания объявления переменных
    public boolean analyzeVar(String narrative) {
        int colonPos = narrative.indexOf(":");
        if (colonPos >= 0) {
            String left = narrative.substring(0, colonPos).trim();
            String right = narrative.substring(colonPos + 1).trim();
            if (isReal(right))
                return analyzeVarConsis(left, varOfDouble);
            else if (isInteger(right))
                return analyzeVarConsis(left, varOfInteger);
            else if (IsArray(right))
                return analyzeVarConsis(left, varOfArray);
            else {
                System.out.println(right + " - Объявлен неизвестный тип переменных");
                return false;
            }
        }
        System.out.println(narrative + " - ожидалось ':'");
        return false;
    }

    // Метод проверяет является ли строка объявлением массива
    public boolean IsArray(String right) {
        int ofPos = right.toUpperCase().indexOf(KEYWORDS[4]);
        if (ofPos >= 0) {
            String leftOf = right.substring(0, ofPos);
            String rightOf = right.substring(ofPos + 2).trim();
            return (isInteger(rightOf) || isReal(rightOf)) && leftOfIsArray(leftOf);
        }
        return false;
    }

    // Метод проверяет является ли объявление массива с гранницами или без них
    public boolean leftOfIsArray(String leftOf) {
        return leftOfIsArrayAlone(leftOf) || leftOfIsArrayWithBorder(leftOf);
    }

    // Метод проверяет  наличие of без гранниц
    public boolean leftOfIsArrayAlone(String leftOf) {
        if (leftOf.charAt(leftOf.length() - 1) == ' ') {
            leftOf = leftOf.trim();
            leftOf += " ";
            return leftOf.equals(KEYWORDS[5]);
        }
        return false;

    }

    // Метод возвращает переменную стояющую перед := в цикле
    public String varInForAssign(String str) {
        int assignPos = str.indexOf(":=");
        if (assignPos >= 0)
            return str.substring(0, assignPos).trim();
        return str;
    }

    // Метод проверяет явличие of  с гранницами
    boolean leftOfIsArrayWithBorder(String leftOf) {
        leftOf = leftOf.trim();
        if (leftOf.indexOf(KEYWORDS[5].trim()) == 0) {
            for (int i = 5; i < leftOf.length(); i++) {
                if (leftOf.charAt(i) != ' ') {
                    if ((leftOf.charAt(i) == '[') && (analyseParant(leftOf, i, '[', ']'))) {
                        String spic = stringsInParantKw(leftOf, i);
                        return borderOfArray(spic);
                    } else
                        return false;
                }
            }
        }
        return false;
    }

    // Метод проверяет правильность написания гранниц массива
    boolean borderOfArray(String spic) {
        int pointPos = spic.indexOf("..");
        if (pointPos >= 0) {
            String left = spic.substring(0, pointPos);
            String right = spic.substring(pointPos + 2);
            return ((analyzeVariable(left) || analyzeNumber(left)) && (analyzeVariable(right) || analyzeNumber(right)));
        }
        return false;
    }

    // Метод проверяет, является ли строка Real
    public boolean isReal(String right) {
        return right.equals(KEYWORDS[3]);
    }

    // Метод проверяет, является ли строка Integer
    public boolean isInteger(String right) {
        return right.equals(KEYWORDS[9]);
    }

    // Проверка присваивания
    private boolean analyzeCommand(String command) {
        command = command.toUpperCase().trim();
        int assignPos = command.indexOf(":=");
        if (command.equals(""))
            return true;
        else if (analyzeWrite(command))
            return true;

        else if (analyzeRead(command))
            return true;

        else if (assignPos >= 0)
            return analyzeAssign(command, assignPos);
        else {
            System.out.println(command + " - Неизвестная комманда");
            return false;
        }

    }
    // Метод проверяет правильность оформления команды Read
    public boolean analyzeRead(String command) {
        int pos = command.indexOf(READ);
        if (pos == 0) {
            String spic = command.substring(4);
            return analyzeInRead(spic);
        }
        return false;
    }

    // Метод проверяет првильность вызова переменных в комманды Read
    public boolean analyzeInRead(String read) {
        Scanner in = new Scanner(System.in);
        String spic;
        read = read.trim();
        if (read.length() >= 3)
            if ((read.charAt(0) == '(') && (read.charAt(read.length() - 1) == ')')) {
                read = read.substring(1, read.length() - 1).trim();
                if (Collections.frequency(varOfDouble, read) == 1) {
                    int k = varOfDouble.indexOf(read);
                    spic = in.next();
                    if (spic.matches("^[0-9]+[.][0-9]+$")) {
                        numOfDouble.set(k, Double.parseDouble(spic));
                    } else {
                        System.out.println(spic + " - Ожидалось вещественное число");
                        return false;
                    }
                    return true;
                } else {
                    System.out.println(read + " - Неизвестное имя");
                }
            }
        return false;
    }


    // Метод проверяет правильность оформления команды Write
    public boolean analyzeWrite(String command) {
        int pos = command.indexOf(WRITE);
        if (pos == 0) {
            String spic = command.substring(5);
            return analizeInWrite(spic);
        }
        return false;
    }

    // Метод проверяет првильность вызова переменных в комманды Read
    public boolean analizeInWrite(String write) {
        write = write.trim();
        if (write.length() >= 3) {
            if ((write.charAt(0) == '(') && (write.charAt(write.length() - 1) == ')')) {
                write = write.substring(1, write.length() - 1).trim();
                return analyzeWriteConsis(write);
            }
        } else
            System.out.println(write + " - ожидалось выражение");
        return false;
    }

    // Метод, проверяющий правильность оформления цикла Do
    public boolean analyzeDo(String str) {
        str = str.trim();
        int beginDo;
        int endDo;
        int beginPos = str.indexOf(KEYWORDS[0]);
        int endPos = searchCloseEnd(str, beginPos + 5);
        String left = str.substring(3, beginPos);
        String right = str.substring(beginPos + 5, endPos - 2);
        int pos = left.indexOf(",");
        if (pos >= 0) {
            String str1 = left.substring(0, pos).trim();
            String str2 = left.substring(pos + 1).trim();
            if (analyzeForAssign(str1)) {
                String var = varInForAssign(str1);
                beginDo = numOfInteger.get(varOfInteger.indexOf(var));
                if (analyzeNumberInteger(str2)) {
                    endDo = Integer.parseInt(str2);
                    if (beginDo < endDo) {
                        while (beginDo <= endDo) {
                            if (!analyzeOperation(right))
                                return false;
                            beginDo++;
                        }
                        return true;
                    } else {
                        System.out.println("Цикл должен выполнятся хотя-бы 1 раз");
                        return false;
                    }
                } else {
                    System.out.println(str2 + " - Ожидалось порядковое число");
                    return false;
                }
            }
        } else {
            System.out.println(left + " - Ожидалось ','");
            return false;
        }
        return false;
    }


    // Метод, проверяющий правильность оформления цикла For
    public boolean analyzeFor(String command) {
        command = command.trim();
        int beginFor;
        int endFor;
        int toPos = command.indexOf(KEYWORDS[7]);
        int doPos = command.indexOf(KEYWORDS[8]);
        if ((toPos >= 0) && (doPos > toPos + 4)) {
            String leftTo = command.substring(4, toPos).trim();
            String rightTo = command.substring(toPos + 4, doPos).trim();
            String rightDo = command.substring(doPos + 4).trim();
            if (analyzeForAssign(leftTo)) {
                String var = varInForAssign(leftTo);
                beginFor = numOfInteger.get(varOfInteger.indexOf(var));
                if (analyzeNumberInteger(rightTo)) {
                    endFor = Integer.parseInt(rightTo);
                    if (beginFor < endFor) {
                        while (beginFor <= endFor) {
                            if (!analyzeRightFor(rightDo))
                                return false;
                            beginFor++;
                            numOfInteger.set(varOfInteger.indexOf(var), beginFor);
                        }
                        return true;
                    }else{
                        System.out.println("Цикл должен выполнятся хотя-бы 1 раз");
                        return false;
                    }
                } else if (Collections.frequency(varOfInteger, rightTo) == 1) {
                    endFor = numOfInteger.get(varOfInteger.indexOf(rightTo));
                    if (beginFor < endFor) {
                        while (beginFor <= endFor) {
                            if (!analyzeRightFor(rightDo))
                                return false;
                            beginFor++;
                        }
                        return true;
                    }else{
                        System.out.println("Цикл должен выполнятся хотя-бы 1 раз");
                        return false;
                    }
                } else {
                    System.out.println(rightTo+" - Должен быть целым числом или порядковой переменной ");
                    return false;
                }
            }else return false;
        }else
        {
            System.out.println(command+ " - Отсутствует 'to' или 'do'");
            return false;
        }
    }

    // Метод, проверяющий правильность оформления выполнительной части цикла
    public boolean analyzeRightFor(String right){
        right = right.trim();
        int begPos = right.indexOf(KEYWORDS[0]);
        int endPos = searchCloseEnd(right,begPos +5);
        if (begPos >= 0){
            if (endPos >= 0) {
                String podProgramm = right.substring(begPos + 5, endPos - 2);
                return analyzeOperation(podProgramm);
            }else {
                System.out.println(right+ " - Не найдена точка выхода из цикла");
                return false;
            }
        }else return analyzeCommand(right);
    }

    // Метод проверяющий наличие := в цикле
    public boolean analyzeForAssign(String leftTo){
        int assignPos = leftTo.indexOf(":=");
        if (assignPos >= 0)
            return analyzeAssignFor(leftTo, assignPos);
        else {
            System.out.println(leftTo+ " - Ожидалось ':=' ");
            return false;
        }
    }

    // Метод проверяющий и выполняющий присваивание в цикле For
    private boolean analyzeAssignFor(String command, int assignPos) {

        String left = command.substring(0, assignPos).trim();
        String right = command.substring(assignPos + 2).trim();

        if (Collections.frequency(varOfInteger,left) == 1) {
            int varPos;
            varPos = varOfInteger.indexOf(left.trim());
            if (analyzeNumberInteger(right)) {
                numOfInteger.set(varPos, Integer.parseInt(right));
                return true;
            }
            else if (Collections.frequency(varOfInteger,right) == 1) {
                numOfInteger.set(varPos,varOfInteger.indexOf(right));
                return true;
            }else {
                System.out.println(right+ " - Должен быть целым числом или порядковой переменной");
                return false;
            }
        }else {
            System.out.println(left+" - Переменная должна быть порядкового типа");
            return false;
        }
    }


    // Является ли написанное слева от присваивания - переменной, а справа - выражением
    private boolean analyzeAssign(String command, int assignPos) {

        String left = command.substring(0, assignPos).trim();
        String right = command.substring(assignPos + 2);

        if (analyzeVariableInArray(left) && analyzeExpressions(right)) {
            int varPos;
            varPos = varOfDouble.indexOf(left.trim());
            numOfDouble.set(varPos,evaluateExpression(right.replaceAll("\\s","")));
            return true;
        }
       return false;
    }

    // Метод удаляющий лишние скобки в выражении
    String deleteParant(String str){
        str = str.trim();
        while ((str.charAt(0) == '(') && (str.charAt(str.length()-1) == ')')) {
            str = str.substring(1, str.length() - 1).trim();
            if (str.equals(""))
                return "";
        }
        return str;
    }

    // Метод ищет индекс закрывающего End для заданного Begin
    int searchCloseEnd(String str,int indexOpen){
        int c = 1;
        for (int i = indexOpen; i < str.length(); i++) {
            String spic = "";
            if (str.charAt(i)!=' ') {
                do {
                    if (i+1 == str.length()) {
                        spic += str.charAt(i);
                        break;
                    }
                    spic += str.charAt(i);
                    i++;
                } while ((str.charAt(i) != ' ') && (str.charAt(i) != ';'));
                if (spic.equals(KEYWORDS[0].trim()))
                    c++;
                else if ((spic.equals(KEYWORDS[1].trim())))
                    c--;
                if (c == 0) {
                    c = i;
                    break;
                }
            }
            if (i+1 == str.length())
                return -1;
        }
        return c;
    }

    // Метод ищет индекс закрывающей скобки для заданной
    int searchClose(String exp, int indexOpen,char a, char b) {
        int c = 1;
        for (int i = indexOpen + 1; i < exp.length(); i++) {
            if (exp.charAt(i) == a)
                c++;
            else if (exp.charAt(i) == b)
                c--;
            if (c == 0) {
                c = i;
                break;
            }
        }
        return c;
    }

    // Проверяет, существует ли закрывающая скобка для заданной
    boolean analyseParant(String str, int indexOpen,char a, char b) {
        int c = 1;
        for (int i = indexOpen + 1; i < str.length(); i++) {
            if (str.charAt(i) == a)
                c++;
            else if (str.charAt(i) == b)
                c--;
            if (c == 0) {
                break;
            }
        }
        if (c == 0)
            return true;
        else
            return false;
    }

    // Возвращает подстроку В круглых скобках
    String stringsInParant(String str,int i) {
        String spic;
            spic = str.substring(i, searchClose(str, i, '(', ')'));
            return spic;
    }
    // Возвращает подстроку В квадратных скобках
    String stringsInParantKw(String str,int i) {
        String spic;
        spic = str.substring(i + 1, searchClose(str, i, '[', ']'));
        return spic;
    }

    // Метод проверяет првильность перечисления выражений в комманде Read
    boolean analyzeWriteConsis(String consis){
        if (Character.toString(consis.charAt(consis.length()-1)).matches(",")) {
            System.out.println(consis+" Встречено - " +consis.charAt(consis.length()-1)+ " - А ожидалось выражение");
            return false;
        }
        for (int i = 0; i < consis.length(); i++) {
            String spic = "";
            do {
                if (i+1 == consis.length()) {
                    spic += consis.charAt(i);
                    break;
                }
                spic += consis.charAt(i);
                i++;
            } while (!Character.toString(consis.charAt(i)).matches(","));
            spic = spic.trim();
            if (!analyzeExpressions(spic)){
                return false;
            }
            else
                System.out.println(evaluateExpression(spic));

        }
        return true;
    }

    // Метод проверяет, явдяется ли строка выражением
    boolean analyzeExpressions(String exp){
        exp = deleteParant(exp).trim();
        if (exp.equals("")) {
            System.out.println("Выражение не может быть пустым");
            return false;
        }
        if (exp.charAt(0)=='-')
            exp = "0"+exp;
        if (!analyzeEpxConsis(exp))
            return false;
        return true;
    }


    // Проверка Правильности оформления выражения
    boolean analyzeEpxConsis(String consis){
        if (Character.toString(consis.charAt(consis.length()-1)).matches("[+*/-]")) {
            System.out.println("Встречено '"+consis.charAt(consis.length()-1)+"' а ожидалась переменная");
            return false;
        }
        for (int i = 0; i < consis.length(); i++) {
            String spic = "";
            do {
                if (consis.charAt(i) == '(')
                    if (analyseParant(consis,i,'(',')')) {
                        spic += stringsInParant(consis,i);
                        i = searchClose(consis,i,'(',')');
                    }else {
                        System.out.println(consis+ " - не хватает закрывающей скобки");
                        return false;
                    }
                if (i+1 == consis.length()) {
                    spic += consis.charAt(i);
                    break;
                }
                spic += consis.charAt(i);
                i++;
            } while (!Character.toString(consis.charAt(i)).matches("[+*/-]"));
            spic = spic.trim();
            if (!analyzeNumber(spic) && !analyzeExpInParant(spic) && !analyzeVariableInArray(spic) )
                return false;
        }
        return true;
    }

    // Проверка является ли строка выражением
    boolean analyzeExpInParant(String str){
        str = str.trim();
        if ((str.charAt(0) == '(') && (str.charAt(str.length()-1) == ')'))
            return analyzeExpressions(str);
        return false;
    }

    // Проверка правильности оформления перечисления переменных
    boolean analyzeVarConsis(String consis,ArrayList var){
        if (Character.toString(consis.charAt(consis.length()-1)).matches(",")) {
            System.out.println(consis+ " - Перечесление переменных оформлено неверно");
            return false;
        }
        for (int i = 0; i < consis.length(); i++) {
            String spic = "";
            do {
                if (i+1 == consis.length()) {
                    spic += consis.charAt(i);
                    break;
                }
                spic += consis.charAt(i);
                i++;
            } while (!Character.toString(consis.charAt(i)).matches(","));
            spic = spic.trim();
            if ((!analyzeVariable(spic)) || analyzeNoPovtor(spic)) {
                return false;
            }
            else
                var.add(spic);

        }
        return true;
    }

    // Является ли строка числом
    boolean analyzeNumber(String exp) {
        exp = exp.trim();
        if (!analyzeNotKeyword(exp))
            return false;

        return isMatchRegexNumber(exp);
    }

    // Является ли строка целым числом
    boolean analyzeNumberInteger(String exp) {
        exp = exp.trim();
        if (!analyzeNotKeyword(exp))
            return false;

        return exp.matches("[0-9]+");
    }
    // Является ли строка числом
    boolean isMatchRegexNumber(String exp) {
        return exp.matches("^[0-9]+[.][0-9]+$") || exp.matches("[0-9]+");
    }

    // Является ли строка переменной
    boolean analyzeVariable(String var) {
        var = var.trim();
        if (!analyzeNotKeyword(var)) {
            System.out.println(var+" - Имя переменной не может совпадать с названием команды");
            return false;
        }

        if (isMatchRegexVariable(var))
            return true;
        else {
            System.out.println(var+" - Неизвестное имя");
            return false;
        }
    }


    // Метод проверяет, существует ли данная строка в массивах
    boolean analyzeVariableInArray(String var){
        if (Collections.frequency(varOfDouble,var) == 1 || Collections.frequency(varOfInteger,var) == 1 || Collections.frequency(varOfArray,var) == 1)
            return true;
        else {
            System.out.println(var+ " - Неизвестное имя");
            return false;
        }
    }

    // Метод проверяет, существует ли строка в массивах
    boolean analyzeNoPovtor(String var){
        if (Collections.frequency(varOfDouble,var) > 0 || Collections.frequency(varOfInteger,var) > 0 || Collections.frequency(varOfArray,var) > 0) {
            System.out.println( var+ " - Нельзя использовать уже существующие имена переменных");
            return true;
        }
        else
            return false;
    }


    // Написано ли как переменная
    boolean isMatchRegexVariable(String left) {
        return left.matches("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_][abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_]*");
    }

    // Является ли слово - ключевым словом
    private boolean analyzeNotKeyword(String left) {
        for (String keyword : KEYWORDS) {
            if (left.equalsIgnoreCase(keyword.trim()))
                return false;
        }
        return true;
    }
}
