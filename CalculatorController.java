package Calculator;

public class CalculatorController {
    // CalculatorAppを継承→っていうより、ActionListenerで公式の見張り番っぽい
    // 入口制限

    // ★これがいるのか↓微妙…あとでちゃんと確認すること★
    // →クラス図におるので、使う。そのままでOK
    // たぶん、クラスは作ってあるけど、中身がないから黄色線
    private CalculatorModel model;
    private CalculatorFrame view;


    public CalculatorController(CalculatorModel model, CalculatorFrame view){
        this.model = model;
        this.view = view;
        view.bindController(this);
    }

    public void onDigit(char ch){
        this.model.appendDigit(ch);
        this.view.setDisplayText(this.model.getDisplayText());
    }

    public void onDot(){
        this.model.appendDot();
        this.view.setDisplay(this.model.getDisplayText());
    }
    
    public void onOperator(Operator op){
        this.model.inputOperator(op);
        this.view.setDisplay(this.model.getDisplayText());
    }

    public void onEquals(){
        this.model.equaolOp();
        this.view.setDisplay(this.model.getDisplayText());
    }

    public void onClear(){
        this.model.clearAll();
        this.view.setDisplay(this.model.getDisplayText());
    }
}
    