package ticketMachineGui;

import com.sun.deploy.panel.JavaPanel;
import ticketMachine.Car;
import ticketMachine.TicketMachine;
import ticketMachine.TransactionBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * gui for the ticketmachine
 */

/**
 * Created by Lies on 4-11-2016.
 */
public class TicketmachineFrame extends JFrame {
    private TicketMachine ticketMachine;
    private TransactionBuilder transactionBuilder;

    private static final int FRAME_HOOGTE = 300;
    private static final int FRAME_BREEDTE = 500;


    /**
     * constructs a new user interface
     */
    public TicketmachineFrame() {
        super();
        ticketMachine = new TicketMachine();
        initialize();


    }

    /**
     * helper method to build the gui
     */
    private void initialize() {
        setTitle("Parkingticket Machine");
        this.setSize(FRAME_BREEDTE, FRAME_HOOGTE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(0, (dim.height - FRAME_HOOGTE * 2));
        JTabbedPane tabbedPane = new JTabbedPane();
        Container pane = getContentPane();
        pane.add(tabbedPane);
        JPanel ownerPane = new OwnerPane(ticketMachine);
        JPanel servantPane = new ServantPane(ticketMachine);

        tabbedPane.add("Car owner", ownerPane);
        tabbedPane.add("Civil servant", servantPane);
        ticketMachine.addCar(new Car(LocalDateTime.now(), LocalDateTime.now().plusMinutes(12), "ABC345", 12));
        ticketMachine.addCar(new Car(LocalDateTime.now(), LocalDateTime.now().plusMinutes(13), "ABC678", 12));
        ticketMachine.addCar(new Car(LocalDateTime.now(), LocalDateTime.now().plusMinutes(14), "DEF123", 12));
        ticketMachine.addCar(new Car(LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), "DIY123", 12));
        ticketMachine.addCar(new Car(LocalDateTime.now(), LocalDateTime.now().plusMinutes(16), "Matthias", 12));
        ticketMachine.addCar(new Car(LocalDateTime.now(), LocalDateTime.now().plusMinutes(17), "ABC321", 12));
        ticketMachine.addCar(new Car(LocalDateTime.now(), LocalDateTime.now().plusMinutes(18), "OPL123", 12));

    }

    /**
     * main programm makes an instantion of the class and makes the interface visible on the screen
     */
    public static void main(String[] args) {
        TicketmachineFrame frame = new TicketmachineFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

