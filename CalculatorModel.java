package Calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorModel {
    // Controllerを継承

    // Modelで計算処理　受け取った数字を計算させる

    private BigDecimal leftOperand;
    private StringBuilder currentInput = new StringBuilder();
    private Operator pendingOp;
    private InputState state = InputState.READY;
    private static int maxDigits = 8;


    /**
     * 
     * @param ch 追加対象の数字（文字）★省略後も変えようね！
     * @return 追加成功true ,最大桁数超過等で拒否時false
     */

    public boolean appendDigit(char ch){

        if(currentInput.length() >= maxDigits){
            return false;
        }

        currentInput.append(ch);
        return true;
    }

    public InputState getState(){
        return state;
    }

    public void setState(InputState state){
        this.state = state;
    }

    public boolean appendDot(){
        // "."小数点１度のみ使用/数値未入力の小数点無視
        
        if(this.state == InputState.READY){
            return false;
        }

        if(currentInput.indexOf(".") != -1){
            return false;
        }
        if(currentInput.length() == 0){
            currentInput.append("0.");
        } else {
            currentInput.append(".");
        }
        return true;
    }

    public void inputOperator(Operator op){

        // －だったら負号開始+それ以外は無視
        if(this.state == InputState.READY){

            if (op == Operator.SUB){
                currentInput.append("-");
                this.state = InputState.INPUT_NUMBER;
                return;
            }
            return;
        }

    
        
        // 演算子連続押下上書き判定
        // 空白or"-"の時にはopへ入れて戻る
        if(currentInput.isEmpty() || currentInput.toString().equals("-")){
            currentInput.setLength(0);
            pendingOp = op;
            return;
        }
        
        BigDecimal currentNumber = new BigDecimal(currentInput.toString());

        if(leftOperand == null){
            leftOperand = currentNumber;
        } else {
            leftOperand = calculate(leftOperand , currentNumber , pendingOp);
        }

        pendingOp = op;
        currentInput.setLength(0);


    }

    // =の不計算条件/演算子未指定→表示維持/演算子直後=→計算しない
    public void equalsOp(){

        // 初期状態何もしない
        if(this.state == InputState.READY){
            return ;
        }

        // 数字入力無し/空白/"-"→いずれかの場合何も計算しない
        if(currentInput.length() == 0 || pendingOp == null || currentInput.toString().equals("-")){
            return;
        }

        BigDecimal currentNumber = new BigDecimal(currentInput.toString());

        leftOperand = calculate(leftOperand, currentNumber, pendingOp);

        // 計算終了後演算子クリア
        pendingOp = null;
        // 次の入力の為にクリア
        currentInput.setLength(0);
        
    }

    public void clearAll(){
        leftOperand = null;
        pendingOp = null;
        currentInput.setLength(0);

        this.state = InputState.READY;

    }

    // 画面表示
    public String getDisplayValue(){

        if(this.state == InputState.INPUT_NUMBER && currentInput.length() > 0){
            return currentInput.toString();
        }

        if(this.state ==  InputState.READY || currentInput.length() == 0){

            if(leftOperand == null){
                return "0";
            }

            // 末尾0は無意味なのでトリム/
            // ８桁超過した場合、切り捨てで行くこと★ここあとでやること
            BigDecimal trimmed = leftOperand.stripTrailingZeros();

            String PlainStr = trimmed.toPlainString().replace("-", "").replace(".", "");

            if(PlainStr.length() > maxDigits){
                return trimmed.toEngineeringString();
            }
            // 末尾0削除後値trimmedを返す
            return trimmed.toPlainString();
        }
        return currentInput.toString();
    }

    public BigDecimal calculate(BigDecimal left ,BigDecimal right , Operator op){

        if (op == null){
            return right;
        }

        int integerDigits = left.precision() - left.scale();

        if( integerDigits < 0) {
            integerDigits = 0;
        }

        // 0ならEROORへ遷移
        if(op == Operator.DIV && right.compareTo(BigDecimal.ZERO)==0){
            throw new ArithmeticException("Zero divistion error");
        }

        int scale = Math.max(0,maxDigits - integerDigits);

        switch (op) {

            case ADD:
                return left.add(right);
            
            case SUB:
                return left.subtract(right);

            case MUL:
                return left.multiply(right);

            case DIV:
                return left.divide(right , scale , RoundingMode.HALF_UP);

            default:
                throw new IllegalArgumentException("Unknown operator");
        }
    }

    


}
