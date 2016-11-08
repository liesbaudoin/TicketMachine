package ticketMachineGui;

import ticketMachine.TicketMachine;
import ticketMachine.TransactionBuilder;
import ticketMachine.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Lies on 7-11-2016.
 */
public class OwnerPane extends JPanel implements Observer {
    private JTextField endTimeField;
    private JTextField licenceplateField;
    private JTextField startTimeField;
    private JTextField feeField;
    JLabel validatedLabel;
    private TicketMachine ticketMachine;
    private TransactionBuilder transactionBuilder;


    public OwnerPane(TicketMachine ticketMachine) {
        this.ticketMachine = ticketMachine;
        this.ticketMachine.addObserver(this);
        this.setLayout(null);
        feeField = new JTextField();
        feeField.setBounds(250, 5, 205, 30);
        feeField.setText("parking Fee =" + ticketMachine.getFee() + " cents/minute");
        this.add(feeField);

        JLabel startTimeLabel = new JLabel("startTime");
        startTimeLabel.setBounds(10, 80, 290, 30);
        this.add(startTimeLabel);
        startTimeField = new JTextField();
        startTimeField.setBounds(10, 110, 200, 30);
        startTimeField.setEditable(false);
        this.add(startTimeField);

        JLabel licenceplateLabel = new JLabel("enter licenceplatenumber");
        licenceplateLabel.setBounds(10, 20, 290, 30);
        this.add(licenceplateLabel);
        licenceplateField = new JTextField();
        licenceplateField.setBounds(10, 50, 200, 30);
        licenceplateField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transactionBuilder = ticketMachine.startTransaction();
                startTimeField.setText(formatTime(transactionBuilder.getStartTime()));
            }
        });
        this.add(licenceplateField);

        JLabel endTimeLabel = new JLabel("endTime");
        endTimeLabel.setBounds(10, 140, 80, 20);
        this.add(endTimeLabel);
        endTimeField = new JTextField();
        endTimeField.setBounds(10, 170, 200, 30);
        endTimeField.setEditable(false);
        this.add(endTimeField);

        Button plusTenCentsButton = new Button(10, "+ 10 cents");
        plusTenCentsButton.setBounds(250, 50, 100, 30);
        plusTenCentsButton.setText(plusTenCentsButton.getTitle());
        plusTenCentsButton.addActionListener(new Listener());
        this.add(plusTenCentsButton);

        Button minTenCentsButton = new Button(-10, "- 10 cents");
        minTenCentsButton.setBounds(355, 50, 100, 30);
        minTenCentsButton.setText(minTenCentsButton.getTitle());
        minTenCentsButton.addActionListener(new Listener());
        this.add(minTenCentsButton);

        Button plusEuroButton = new Button(100, "+ 1 euro");
        plusEuroButton.setBounds(250, 110, 100, 30);
        plusEuroButton.setText(plusEuroButton.getTitle());
        plusEuroButton.addActionListener(new Listener());
        this.add(plusEuroButton);

        Button minEuroButton = new Button(-100, "- 1 euro");
        minEuroButton.setBounds(355, 110, 100, 30);
        minEuroButton.setText(minEuroButton.getTitle());
        minEuroButton.addActionListener(new Listener());
        this.add(minEuroButton);

        JButton stopButton = new JButton();
        stopButton.setBounds(250, 170, 100, 30);
        stopButton.setText("Stop");
        stopButton.setBackground(Color.red);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        this.add(stopButton);

        JButton validationButton = new JButton();
        validationButton.setBounds(355, 170, 100, 30);
        validationButton.setText("Okay");
        validationButton.setBackground(Color.green);
        validationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (transactionBuilder == null) {
                    return;
                } else {
                    transactionBuilder.setLicencePlate(licenceplateField.getText());
                    System.out.println(transactionBuilder.finishTransaction().getStartTime());
                    ticketMachine.addCar(transactionBuilder.finishTransaction());
                    validatedLabel.setText("Transaction Okay");

                }
                clearFieldsdelayed();
            }
        });
        this.add(validationButton);

        validatedLabel = new JLabel();
        validatedLabel.setBounds(10, 205, 200, 30);
        this.add(validatedLabel);


    }


    /**
     * formats date in a string
     */

    private String formatTime(LocalDateTime time) {
        //return time.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM- yyyy   HH:mm");
        return time.format(formatter);
    }

    /**
     * clears the textFields
     */
    private void clearFields() {
        startTimeField.setText("");
        licenceplateField.setText("");
        endTimeField.setText("");
        validatedLabel.setText("");
    }

    /**
     * clears the textfields after 30000msecs
     */
    private void clearFieldsdelayed() {
        int delay = 3000; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        };
        Timer timer = new Timer(delay, taskPerformer);
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        feeField.setText("parking Fee =" + ticketMachine.getFee() + " cents/minute");
    }

    /**
     * listener class that listens to the + and - buttons
     */
    public class Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (transactionBuilder == null) {
                return;
            } else {
                transactionBuilder.adjustMoney(((Button) e.getSource()).getAmount());
                String endTime = formatTime(transactionBuilder.calculateEndTime());
                endTimeField.setText(endTime);
            }
        }
    }
}


