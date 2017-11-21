import javax.swing.JFrame;

public class MainWindow extends JFrame {
    public MainWindow() {
        super("Погода"); //Заголовок окна
        setBounds(100, 100, 200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
