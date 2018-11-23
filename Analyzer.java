public class Analyzer {

    public static final String[] KEYWORDS = {"BEGIN ", "END."};

    public int indexBegin(String program){
        program = program.toUpperCase();
        int beginPos = program.indexOf(KEYWORDS[0]);
        return beginPos;
    }

    public int indexEnd(String program){
        program = program.toUpperCase();
        int endPos = program.lastIndexOf(KEYWORDS[1]);
        return endPos;
    }

    public boolean analyzeProgram(String program){
        String narrativePart = program.substring(0,indexBegin(program));
        String operatorPart = program.substring(indexBegin(program)+5,indexEnd(program));
        return analyze(narrativePart) && analyze(operatorPart);
    }

    // Проверка, чем является строка
    public boolean analyze(String part) {
        part = part.trim();
        String[] commands = part.split(";");
        for (String command : commands) {
            if (!analyzeCommand(command))
                return false;
        }
        return true;
    }

    public boolean analyzeNarrativ(String narrativ){

    }

    // Проверка присваивания
    private boolean analyzeCommand(String command) {
        int assignPos = command.indexOf(":=");
        if (assignPos >= 0)
            return analyzeAssign(command, assignPos);
        else
            System.out.println("не присваивание");

        return true;
    }

    // Является ли написанное слева от присваивания переменной, а справа выражением
    private boolean analyzeAssign(String command, int assignPos) {

        String left = command.substring(0, assignPos);
        String right = command.substring(assignPos + 2);

        return analyzeVariable(left) && analyzeExpressions(right);
    }

    int searchClose(String exp, int indexOpen) {
        int c = 1;
        for (int i = indexOpen + 1; i < exp.length(); i++) {
            if (exp.charAt(i) == '(')
                c++;
            else if (exp.charAt(i) == ')')
                c--;
            if (c == 0) {
                c = i;
                break;
            }
        }
        return c;
    }

    boolean analyseParant(String str, int indexOpen) {
        int c = 1;
        for (int i = indexOpen + 1; i < str.length(); i++) {
            if (str.charAt(i) == '(')
                c++;
            else if (str.charAt(i) == ')')
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

    String stringsInParant(String str,int i) {
        String spic;
        if (analyseParant(str, i)) {
            spic = str.substring(i + 1, searchClose(str, i));
            return spic;
        }
    return str;
}

    String stringsOutParant(String str) {
        String spic = str;
        int k = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                if (i != 0) {
                    for (int j = i-1; j > 0; j--)
                        if (str.charAt(j) != ' ') {
                            k = j;
                            break;
                        }
                    spic = str.substring(0, k) + str.substring(searchClose(str, i) + 1, str.length());
                    spic = stringsOutParant(spic);
                    break;
                }
                else{
                    for (int j = searchClose(str, i)+1; j < str.length(); j++)
                        if (str.charAt(j) != ' ') {
                            k = j;
                            break;
                        }
                    spic = str.substring(k +1, str.length());
                    spic = stringsOutParant(spic);
                    break;
                }
            }
        }
        return spic;
    }

    // Явдяется ли выражением
    boolean analyzeExpressions(String exp){
        exp = exp.trim();
        if (exp.charAt(0)=='-')
            exp = "0"+exp;
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == '(')
               if (!analyzeExpressions(stringsInParant(exp,i)))
                   return false;
        }
        String dopStr = stringsOutParant(exp);
        String[] monomials = dopStr.split("[+*/-]");
        for (String monomial : monomials) {
            if ((!analyzeNumber(monomial)) && (!analyzeVariable(monomial)))
                return false;
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


    // Является ли числом
    boolean isMatchRegexNumber(String exp) {
        return exp.matches("[-]?[0123456789]+");
    }

    // Является ли переменной
    boolean analyzeVariable(String var) {
        var = var.trim();
        if (!analyzeNotKeyword(var))
            return false;

        return isMatchRegexVariable(var);
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
