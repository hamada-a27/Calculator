package Calculator;

public class CalculatorApp {
    // Mainのところ！★後で消す
    public static void main(String[] args) {
        // 今一旦呼び出してるだけ　フレームを
        CalculatorModel model = new CalculatorModel();
        CalculatorFrame view = new CalculatorFrame();
        

        CalculatorController controller = new CalculatorController(model, view);
        view.bindController(controller);

    


        
    }
}
