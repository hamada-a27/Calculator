package Calculator;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

public class CalculatorFrame extends JFrame {
    // ディスプレイがこっちっぽい？Frameをつくる
    // 最初に作るのがよさそう
    // MainとControllerを継承→継承というより利用っぽい

    private JLabel displayLabel;
    private JPanel buttonPanel;

    public CalculatorFrame() {
        // 枠組み
        this.setTitle("Calculator");
        this.setSize(300, 400);
        this.setDefaultCloseOperation(3);
        this.setLayout(new BorderLayout());

        // ディスプレイ(JLabel)の作成(初期値0)
        this.displayLabel = new JLabel("0" , 4);

        // フォント右寄せ/
        this.displayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        this.displayLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));


        // ディスプレイを上段に配置
        this.add(this.displayLabel,"North");

        // ボタンパネル作成
        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(new GridLayout(5, 4));
        this.add(buttonPanel , "Center");
    }

/**
 * 計算制御する
 * @param calcController 
 */
    public void bindController(CalculatorController calcController) {

        // ボタンに数字を入れてパネルへ配置
        String[] buttons = {
                "7", "8", "9", "÷",
                "4", "5", "6", "×",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "c"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            this.buttonPanel.add(button);
            button.addActionListener((e) ->{
                switch (text) {
                    case "*":
                        calcController.onOperator(Operator.MUL);
                        return;
                    
                    case "+":
                        calcController.onOperator(Operator.ADD);
                        return;
                    
                    case "-":
                        calcController.onOperator(Operator.SUB);
                        return;

                    case ".":
                        calcController.onDot();;
                        return;

                    case "/":
                        calcController.onOperator(Operator.DIV);
                        return;
                    
                    case "=":
                        calcController.onEquals();

                    case "C":
                        calcController.onClear();
                        return;

                    }
                        
                    calcController.onDigit(text.charAt(0));
            });

        }

        // 目に見えるようにする（電源ON）最後に来ることが多い
    public void setDisplayText(String text){
        this.displayLabel.setTxet(text);
    }
    }

}
