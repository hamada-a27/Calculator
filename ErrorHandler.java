package Calculator;

public class ErrorHandler {
    // CalculatorModelを継承
    // エラーのところからな…
    // try catch　で０除算んのところをかけばいいっぽいね
    // むずいな…
    
    private CalculatorController controller;

    public ErrorHandler(CalculatorController controller){
        this.controller = controller;
    }

    public static void transitionToError(Exception e){
        System.out.println("エラーが発生しました" + e.getMessage());
        
    }

    public void handleInput(char inputChar){
        if (inputChar == 'C' || inputChar == 'C'){
            // onClearを呼べたら理想らしい
            // エラー解除メソッドを呼ぶらしい？↓
            controller.resetErrorState();
            controller.onClear();
            System.out.println("状態をREADYに戻して表示を0");
        } else {
            // それ以外は何もしない
        }
    }

    

    
}
