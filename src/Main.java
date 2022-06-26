import java.util.Scanner;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Main {

    public static void main(String args[]) {
        for (;;) {
            Scanner in = new Scanner(System.in);
            System.out.println("Введите математическое выражение:");
            String str = in.nextLine();
            System.out.println(calc(str));
        }
    }

    public static String calc(String input){
        String output = "";
        boolean arabik;
        char operator;
        int indexOperator;

        //Убрали возможные лишние пробелы вначале и конце
        String expression = input.trim();

        //Знак первого операнда
        try {
            checkNeg(expression);
        }catch (Exception e){
            return ("throws Exception //т.к. принимаютя на вход числа от 1 до 10 включительно, не более");
        }

        //Уточнили первый операнд арабский или нет
        try {
            arabik = arabikExpr(expression);
        } catch (Exception e) {
            return ("throws Exception //т.к. строка не является математической операцией");
        }

        //Проверили является ли строка математической операцией
        try {
            checkExpression(expression);
        } catch (Exception e) {
            return ("throws Exception //т.к. строка не является математической операцией");
        }

        //Проверяем количество операторов
        if (numOperators(input) == 0)
            return ("throws Exception //т.к. строка не является математической операцией");
        else if (numOperators(input) > 1)
            return ("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");

        //Выясняем какой у нас оператор
        operator = getOperator(expression);

        //Получаем операнды
        String operands [] = split(expression, operator);

        //Операнды одной системы счисления?
        if (chekNS(operands) != TRUE){
            return ("throws Exception //т.к. используются одновременно разные системы счисления");
        }

        if (arabik != TRUE) {
            try {
                operands[0] = romanToArabik(operands[0]);
            } catch (Exception e)
            {
                return ("throws Exception //т.к. принимаютя на вход числа от 1 до 10 включительно, не более");
            }
            try {
                operands[1] = romanToArabik(operands[1]);
            } catch (Exception e)
            {
                return ("throws Exception //т.к. принимаютя на вход числа от 1 до 10 включительно, не более");
            }
        }

        //Проверка попадения в ОДЗ
        if (checkArabikValid(operands) != TRUE)
            {
                return ("throws Exception //т.к. принимаютя на вход числа от 1 до 10 включительно, не более");
            }


        int resultInt;
        resultInt = mathOperArabik(operands, operator);
        output = Integer.toString(resultInt);

        if (arabik == FALSE)
        {
            if (resultInt < 0)
                return ("throws Exception //т.к. в римской системе нет отрицательных чисел");
            else if (resultInt < 1 )
                return ("throws Exception //т.к. в римской системе нет числа 0");
            output = arabikToRoman(resultInt);
        }

        return output;
    }

    static void checkNeg(String expression) throws Exception {
        if ((expression.charAt(0)) == '-')
            throw new Exception("Допустимы только положительные числа");
    }

    static String arabikToRoman(int resultInt) {
        //1 5 10 50 100
        //I V X  L  C
        String str = Integer.toString(resultInt);
        String ans = "";
        int shift = 0;
        char ch;

        if (resultInt == 100)
            return "C";

        if (resultInt > 9)
            shift = 1;

        if (shift == 1) {
            ch = str.charAt(0);
            switch (ch) {
                case '1':
                    ans += "X";
                    break;
                case '2':
                    ans += "XX";
                    break;
                case '3':
                    ans += "XXX";
                    break;
                case '4':
                    ans += "XL";
                    break;
                case '5':
                    ans += "L";
                    break;
                case '6':
                    ans += "LX";
                    break;
                case '7':
                    ans += "LXX";
                    break;
                case '8':
                    ans += "LIII";
                    break;
                case '9':
                    ans += "XC";
                    break;
            }
        }

        ch = str.charAt(0 + shift);
        switch (ch){
            case '0':
                break;
            case '1':
                ans += "I";
                break;
            case '2':
                ans += "II";
                break;
            case '3':
                ans += "III";
                break;
            case '4':
                ans += "IV";
                break;
            case '5':
                ans += "V";
                break;
            case '6':
                ans += "VI";
                break;
            case '7':
                ans += "VII";
                break;
            case '8':
                ans += "VIII";
                break;
            case '9':
                ans += "IX";
                break;
        }

        return ans;
    }

    static String romanToArabik(String romanNum) throws Exception {
        switch (romanNum){
            case "I":
                return "1";
            case "II":
                return "2";
            case "III":
                return "3";
            case "IV":
                return "4";
            case "V":
                return "5";
            case "VI":
                return "6";
            case "VII":
                return "7";
            case "VIII":
                return "8";
            case "IX":
                return "9";
            case "X":
                return "10";
        }
        throw new Exception("Римское число слишком большое");
    }

    static boolean checkArabikValid(String[] operands) {
        if (0 < Integer.parseInt (operands[0]) && 10 >= Integer.parseInt (operands[0])){
            if (0 < Integer.parseInt (operands[1]) && 10 >= Integer.parseInt (operands[1]))
                return TRUE;
        }
        return false;
    }

    static boolean chekNS(String[] operands) {
        String arabikTersms = "1234567890";
        String romeTerms = "IVXLCDM";
        if ((arabikTersms.indexOf(operands[0].charAt(0)) != -1) && ((arabikTersms.indexOf(operands[1].charAt(0)) != -1)))
            return TRUE;
        else if ((romeTerms.indexOf(operands[0].charAt(0)) != -1) && ((romeTerms.indexOf(operands[1].charAt(0)) != -1)))
            return TRUE;
        else
            return FALSE;
    }

    static int mathOperArabik(String[] operands, char operator) {
        int a = Integer.parseInt (operands[0]);
        int b = Integer.parseInt (operands[1]);

        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;

            case '/':
                return a / b;
        }
        return 0;
    }

    static String[] split(String expression, char operator) {
        String operands [] = new String [] {"",""};
        int numOperand = 0;
        for (int i = 0; i < expression.length(); i++){
            if (expression.charAt(i) == ' ')
                continue;
            if (expression.charAt(i) != operator){
                operands[numOperand] += expression.charAt(i);
            }
            else{
                numOperand++;
            }
        }
        return operands;
    }

    static char getOperator(String expression) {
        String operators ="+-/*";
        for (int i = 0; i<expression.length(); i++)
            for (int j = 0; j < 4; j++)
                if (expression.charAt(i) == operators.charAt(j))
                    return operators.charAt(j);
        return '\0';
    }

    //Проверяем является ли строка математическим выражением (или это просто набор символов)
    static boolean checkExpression(String expression) throws Exception {
        expression.trim();

        boolean nextOnlyNum = TRUE, nextNumArabik = FALSE, nextNumRoman = FALSE;;
        String operators = "+-/*";
        String numbers = "1234567890IVXLCDM";
        String arabikNumbers = "1234567890";
        String romanNumbers = "IVXLCDM";
        String allTerms = "1234567890IVXLCDM+*-/";
        String romanNumber = "";
        int numOperators = 0;

        if (numbers.indexOf(expression.charAt(0)) == -1)
            throw new Exception("Перый символ не число");

        if (numbers.indexOf(expression.charAt(expression.length() - 1)) == -1)
            throw new Exception("Последний символ не число");

        for (int i = 0; i < expression.length(); i++){
            if (expression.charAt(i) == ' ') {
                if (romanNumber != ""){
                    if (romanNumberIsValid(romanNumber) == FALSE) { //...то проверяем ей запись на корректность
                            throw new Exception("Римсчкая запись некорректна");
                    }
                    romanNumber = "";
                }
                continue;
            }
            if (operators.indexOf(expression.charAt(i)) != -1){
                if (nextOnlyNum == TRUE)
                {
                    throw new Exception("Не математическое выражение: два и более операторов подряд");
                }

                if (romanNumber != ""){
                    if (romanNumberIsValid(romanNumber) == FALSE) { //...то проверяем ей запись на корректность
                        throw new Exception("Римсчкая запись некорректна");
                    }
                    romanNumber = "";
                }

                nextOnlyNum = TRUE;
                nextNumArabik = FALSE;
                nextNumRoman = FALSE;
                numOperators++;
            }

            //Если встретилась римская цифра
            else if (romanNumbers.indexOf(expression.charAt(i)) != -1 ){
                if (nextNumArabik == TRUE) {
                    throw new Exception("Числа из разных цифр");
                }
                if (nextOnlyNum == FALSE && expression.charAt(i-1) == ' '){
                    throw new Exception("Числа чрез пробел");
                }
                romanNumber += expression.charAt(i); //Добавляем римскую цифру в запись римского числа
                nextOnlyNum = FALSE;
                nextNumRoman = TRUE;
            }

            else if (arabikNumbers.indexOf(expression.charAt(i)) != -1 ){ //Если встретили арабское число..
                if (nextNumRoman == TRUE) {   //..а должно было быть Римское:
                    throw new Exception("Числа из разных цифр");
                }
                //Если число уже встречали, а потом встретился пробел, а сейчас снова число, то число - не число.
                if (nextOnlyNum == FALSE && expression.charAt(i-1) == ' '){
                    throw new Exception("Числа с пробелами пробел");
                }
                nextOnlyNum = FALSE;
                nextNumArabik = TRUE;
            }
        }
        return TRUE;
    }

    //Проверяем корректность записи римского числа (I - MMMCMXCIX)
    static boolean romanNumberIsValid(String romanNumber) throws Exception{
        //M D C L X V I    CMDIX  IX    I II III IV V VI VII VIII IX X XI XII XIII XIV XV XVI XVII XVIII XIX XX
        //6 5 4 3 2 1 0           02    0 00 000 01 1 10 100 1000 02 2 20 200 2000 201 21 210 2100 21000 202 22
        //1 5 10 50 100 500 1000  4   9   40  90  400 900
        //I V X  L  C   D   M     IV  IX  XL  XC  CD  CM
        String romanSeq = "IVXLCDM";
        int repeat = 0;
        boolean neg = FALSE; //признак уменьшения
        int size = romanNumber.length();
        if (size == 1)
            return TRUE; //Последовательность из одного допустимого символа точно верная и дальнейшая обработка не требуется

        int [] array = new int [size];
        for (int i = 0; i < size; i++)
            array[i] = romanSeq.indexOf(romanNumber.charAt(i));

        char chArr [] = romanNumber.toCharArray();

        for (int i = 1; i < size; i++){
            if (array[i] > array[i - 1]) {
                if (array[i] - array[i - 1] > 2)
                    throw new Exception("Неверная запись римского числа: вычитание разных порядков IM");
                if ((array[i] - array[i - 1] == 2) && ((romanSeq.indexOf(romanNumber.charAt(i))) % 2 != 0))
                    throw new Exception("Неверная запись римского числа: запрещенное вычитание, например VL");
                if ((array[i] - array[i - 1] == 1) && ((romanSeq.indexOf(romanNumber.charAt(i))) % 2 == 0))
                    throw new Exception("Неверная запись римского числа: запрещенное вычитание, например LC");
                if (repeat > 0)
                    throw new Exception("Неверная запись римского числа: двойное вычитание, например: IIX");
                repeat = 0;
            }
            else if (array[i] < array[i-1]){
                repeat = 0;
                if (i < 2)
                    continue;
                else if (array[i] == array[i-2])
                    throw new Exception("Неверная запись римского числа: одной цифрой и вычитаем и прибавляем, например XCX");
            }
            else {
                repeat++;
                if (repeat == 3)
                    throw new Exception("Неверная запись римского числа: слишком много повторов, например IIII");
            }
        }
        return TRUE;
    }

    //Проверяем арабские или римские цифры используем. Если арабские - TRUE, римские - FALSE
    static boolean arabikExpr(String expression) throws Exception{
        String arabikTersms = "1234567890";
        String romeTerms = "IVXLCDM";
        if (arabikTersms.indexOf(expression.charAt(0)) != -1)
            return TRUE;
        else if (romeTerms.indexOf(expression.charAt(0)) != -1)
            return FALSE;
        else
            throw new Exception("throws Exception //т.к. строка не является математической операцией");
    }

    static int numOperators(String string){
        char operators [] = {'+','-','/','*'};
        int numOperators = 0;

        for (int i = 0; i<string.length(); i++)
            for (int j = 0; j < 4; j++)
                if (string.charAt(i) == operators[j])
                    numOperators++;
        return numOperators;
    }
}