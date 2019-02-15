package eu.rafalsiuta.simplecalc;
/**
 * @author Rafal Siuta
 */

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Logic extends Activity {

    public static String newText;
    public static String oldText;
    public static String operator = " ";

    public static double oldValue = 0;
    public static double newValue = 0;
    public static double total = 0;

    public static Flag flag = Flag.RESULT;

    public static DecimalFormat nf;

    public static void setTxt(TextView display, double value, DecimalFormat nf){
        display.setText(String.valueOf(nf.format(value)).replaceAll(",","."));
    }

    public static double parser(TextView display){

        return  Double.parseDouble(display.getText().toString().replaceAll(",","."));
    }

    /**
     * @param <<code>checkFlag</code>
     * check operand status and return parsed String to double
     */
    public static void checkFlag(TextView display){
        if(flag == Flag.RESULT || flag == Flag.FIRST_OPERAND){
            total=oldValue;
            oldValue = parser(display);
            flag = Flag.FIRST_OPERAND;
        }else{
            total = newValue;
            newValue = parser(display);
            flag = Flag.SECOND_OPERAND;
        }
    }
    /**
     * @param <<code>flag</code>> check digit status and store operand as a oldText
     */
    public static void flag(TextView display){
        if(flag == Flag.FIRST_OPERAND || flag == Flag.SECOND_OPERAND){
            oldText = display.getText().toString();
        }else {
            oldText = "";
        }
    }
    /**
     * @param v <code>onNumbers</code>take button text and  append digits in main display
     *         in case 0 jus replace for new text
     */
    public static void onNumbers(View v, TextView display){
        newText = ((Button) v).getText().toString();
        try{
            flag(display);
            if ("0".equals(oldText)) {

                display.setText(newText);

            } else {

                display.setText(oldText + newText);
            }

            checkFlag(display);
        }catch(Exception ex){
        }
    }
    /**
     * @param v <code>onDecimal</code> append dot in digits in case of 0
     *          just press dot button only to append it to 0
     */
    public static void onDecimal(View v, TextView display){
        newText = ((Button)v ).getText().toString();
        oldText =  display.getText().toString();

        try{
            if(flag == Flag.RESULT || flag == Flag.FIRST_OPERAND || flag == Flag.SECOND_OPERAND){


                if(display.getText().toString().contains(".") || display.getText().toString().contains(",")){
                    oldText =  display.getText().toString().replaceAll(",",".");
                }else {
                    display.setText((oldText + newText ).replaceAll(",","."));
                }

            }
            if(flag == Flag.OPERATOR ){
                if(display.getText().toString().equals("0")){
                    display.setText((oldText + newText ).replaceAll(",","."));
                }else {
                    display.setText(("0" + newText ).replaceAll(",","."));

                }
            }
            checkFlag(display);
        }catch(Exception e){
            display.setText("0");

        }
    }

    /**
     * @param v <code>onOperator</code> check operand status get operator from button text
     * and set operator flag
     */
    public static void onOperator(View v, TextView display, DecimalFormat nf){

        if(flag == Flag.RESULT){
            operator = ((Button)v).getText().toString();
        }
        if(flag == Flag.SECOND_OPERAND){

            switch(operator){
                case "+": oldValue += newValue; break;
                case "-": oldValue -= newValue; break;
                case "*": oldValue *= newValue; break;
                case "/": oldValue /= newValue; break;
            }
            total = oldValue;
            newValue = 0;
            setTxt(display,oldValue,nf);
        }
        operator = ((Button)v).getText().toString();
        flag = Flag.OPERATOR;
    }

    /**
     * @param display <code>onEqual</code>
     */
    public static void onEqual(TextView display, DecimalFormat nf){
        try {
            switch (operator) {
                case "+":
                    oldValue += newValue;
                    break;
                case "-":
                    oldValue -= newValue;
                    break;
                case "*":
                    oldValue *= newValue;
                    break;
                case "/":
                    oldValue /= newValue;
                    break;
                case "=": oldValue = total;
                    break;
            }
            total = oldValue;
            newValue = 0;
            setTxt(display,oldValue,nf);

            flag = Flag.RESULT;
        }catch (Exception ex){

            display.setText("0");
        }
    }

    /**
     * @param display <code>onClear</code> reset all counting status
     */
    public static void onClear(TextView display){
        display.setText(newText="0");

        newValue = 0;
        oldValue = 0;
        total = 0;
        flag = Flag.RESULT;

    }

    /**
     * @param display <code>onDelete</code> removing on digit when lest digit left convert it to 0
     */
    public static void onDelete(TextView display){

        try{
            int start = 0;
            int end = display.getText().toString().length() - 1;

            if(display.getText().toString().length()>0){
                newText = display.getText().toString().substring(start, end);
                display.setText(newText);
            }
            if(display.getText().toString().length()==0){
                display.setText("0");
            }

            flag = Flag.RESULT;

        }catch(Exception ex){

            display.setText("0");
        }

    }

    /**
     * @param display
     * @param nf
     * mark up digit as negative
     */
    public static void onPlusminus(TextView display,DecimalFormat nf){

        total = parser(display);

        if(total==0){

            onClear(display);
        }else {
            total = total * (-1);
            setTxt(display,total,nf);

        }

        checkFlag(display);
    }

    public static void onPercent(View v, TextView display,DecimalFormat nf){
        if (flag == Flag.RESULT) {
            operator = ((Button) v).getText().toString();
        }
        if (flag == Flag.FIRST_OPERAND){
            oldValue = (oldValue / 100);
        }
        if (flag == Flag.SECOND_OPERAND){
            oldValue *= newValue / 100;
        }
        total = oldValue;
        newValue = 0;
        setTxt(display,oldValue,nf);

        flag = Flag.RESULT;
    }

    public static void onSqrt(TextView display, DecimalFormat nf){//nie musi byc arg View
        try{

            total = parser(display);

            total = Math.sqrt(total);
            setTxt(display,total,nf);

        }catch(Exception ex){
            display.setText("0");
        }

        checkFlag(display);
    }

    public static void onPow(TextView display,DecimalFormat nf){
        try{
            total = parser(display);
            total = Math.pow(total, 2);
            setTxt(display,total,nf);
            flag = Flag.RESULT;
            checkFlag(display);
        }catch(Exception ex){

            display.setText("0");
        }
    }

}
