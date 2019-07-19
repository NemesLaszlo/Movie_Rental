package view;

import moldel.Film;
import moldel.SystemManager;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LendWindow {
    private final JFrame window;
    private final JPanel datasPanel;
    private final JPanel buttonsPanel;
    
    private final JTextField borrowerTextField;
    private final JFormattedTextField dateTextField;
    private final JButton lendSubmitButton;
    
    private JButton lb;
    private final Film film;
    public LendWindow(Film film, JButton lb) {
        this.lb=lb;
        this.film=film;
        window = new JFrame(film.getTitle()+" kölcsönadása.");
        
        datasPanel = new JPanel();
        buttonsPanel = new JPanel();
        datasPanel.setLayout(new GridLayout(0,2));
        datasPanel.add(new JLabel("Kölcsön vevő: "));
        borrowerTextField = new JTextField();
        datasPanel.add(borrowerTextField);
        datasPanel.add(new JLabel("Visszaadás dátuma: "));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        dateTextField = new JFormattedTextField(format);
        datasPanel.add(dateTextField);
        lendSubmitButton = new JButton("Kölcsönad");
        lendSubmitButton.addActionListener(new LendSubmitButtonActionListener());
        buttonsPanel.add(lendSubmitButton);
        
        
        window.getContentPane().add(datasPanel, BorderLayout.NORTH);
        window.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        window.pack();
        window.setVisible(true);
    }
    
    class LendSubmitButtonActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            SystemManager.addLend(borrowerTextField.getText(), Date.valueOf(dateTextField.getText()), film);
            lb.setText("Film visszavétele");
            window.dispose();
        }
    
    }
}
