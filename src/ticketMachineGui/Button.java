package ticketMachineGui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Lies on 5-11-2016.
 */
public class Button extends JButton {

    int amount;
    String title;

    public Button(int amount, String title){
        this.amount = amount;
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public String getTitle() {
        return title;
    }
}
