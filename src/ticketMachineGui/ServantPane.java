package ticketMachineGui;

import ticketMachine.Car;
import ticketMachine.CarNotInListException;
import ticketMachine.TicketMachine;
import ticketMachine.TransactionBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Lies on 8-11-2016.
 */
public class ServantPane extends JPanel implements Observer {

    JTextField endTimeField;
    JTextField findCarField;
    JTextArea listArea;
    private TicketMachine ticketMachine;
    private TransactionBuilder transactionBuilder;
    private JTextField feeField;

    public ServantPane(TicketMachine ticketMachine) {
        this.ticketMachine = ticketMachine;
        this.ticketMachine.addObserver(this);
        this.setLayout(null);

        feeField = new JTextField();
        feeField.setBounds(250, 10, 205, 30);
        feeField.setText("parking Fee =" + ticketMachine.getFee() + " cents/minute");
        this.add(feeField);

        JLabel setFeeLabel = new JLabel("set Fee :");
        setFeeLabel.setBounds(15, 10, 50, 30);
        this.add(setFeeLabel);

        JTextField setFeeField = new JTextField();
        setFeeField.setBounds(70, 10, 100, 30);
        setFeeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ticketMachine.setFee(Integer.parseInt(setFeeField.getText()));
                } catch (NumberFormatException exception) {
                    showException(exception);
                }
            }
        });
        this.add(setFeeField);

        JLabel findCarLabel = new JLabel();
        findCarLabel.setBounds(20, 35, 200, 30);
        findCarLabel.setText("find Car");
        this.add(findCarLabel);

        findCarField = new JTextField();
        findCarField.setBounds(20, 65, 200, 30);
        findCarField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LocalDateTime endTime = ticketMachine.giveEndTime(findCarField.getText());
                    endTimeField.setText(formatTime(endTime));
                } catch (CarNotInListException exception) {
                    showException(exception);
                    clearTextFields();
                }
            }
        });
        this.add(findCarField);

        JLabel endTimeLabel = new JLabel();
        endTimeLabel.setBounds(250, 35, 200, 30);
        endTimeLabel.setText("end time");
        this.add(endTimeLabel);

        endTimeField = new JTextField();
        endTimeField.setBounds(250, 65, 200, 30);
        endTimeField.setEditable(false);
        this.add(endTimeField);

        listArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(listArea);
        scrollPane.setBounds(20, 155, 420, 80);
        this.add(scrollPane);

        JButton listButton = new JButton();
        listButton.setBounds(20, 120, 420, 30);
        listButton.setText("give List of cars paid for");
        listButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String text = "";
                ArrayList<Car> carsPaidFor = ticketMachine.giveCarsPaidFor();
                for (Car c : carsPaidFor) {
                    text = text + (c.getLicencePlate() + " until " + formatTime(c.getEndTime()) + "\n");
                }
                listArea.setText(text);
            }
        });
        this.add(listButton);


    }

    private void showException(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    private String formatTime(LocalDateTime time) {
        //return time.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM- yyyy   HH:mm");
        return time.format(formatter);
    }

    private void clearTextFields() {
        findCarField.setText("");
        endTimeField.setText("");
    }

    @Override
    public void update(Observable o, Object arg) {
        feeField.setText("parking Fee =" + ticketMachine.getFee() + " cents/minute");
    }
}
