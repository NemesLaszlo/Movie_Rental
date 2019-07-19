package view;


import moldel.SystemManager;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class GUI {
    
    SystemManager sm;
    GUI thisGUI=this;
    private final JFrame mainWindow;
    private final JScrollPane scrollFilmsFrame;
    
    private final JPanel searchPanel;
    private final JPanel dataPanel;
    private final JPanel lendPanel;
    private final JPanel filmsPanel;
    private final JPanel buttonsPanel;
    
    
    private JTextField cimTextField;
    private JTextField rendTextField;
    private JTextField yearTextField;
    private JTextField lengthTextField;
    private JTextField mcTextField;
    private JComboBox<String> mediaSelect;
    private JCheckBox originalCheck;
    private JCheckBox lendCheck;
    private CardLayout cl;
    private JButton lendViewButton;
    
    GUI() {
        sm = new SystemManager();
        mainWindow = new JFrame("Film nyilvántartás");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLayout(new BorderLayout());
        
        searchPanel = new JPanel();
        JButton searchButton = new JButton("Keresés");
        searchButton.addActionListener(new SearchButtonActionListener());
        searchPanel.add(searchButton);
        
        cimTextField = new JTextField("Cím");
        cimTextField.setPreferredSize(new Dimension(100,24));
        searchPanel.add(cimTextField);
        
        rendTextField = new JTextField("Rendező");
        rendTextField.setPreferredSize(new Dimension(100,24));
        searchPanel.add(rendTextField);
        
        yearTextField = new JTextField("Év");
        yearTextField.setPreferredSize(new Dimension(40,24));
        searchPanel.add(yearTextField);
        
        mcTextField = new JTextField("Főszereplő");
        mcTextField.setPreferredSize(new Dimension(100,24));
        searchPanel.add(mcTextField);
        
        lengthTextField = new JTextField("Hossz");
        lengthTextField.setPreferredSize(new Dimension(30,24));
        searchPanel.add(lengthTextField);
        
        String[] medias = new String[]{"Mindkettő","DVD", "VHS"};
        mediaSelect = new JComboBox<>(medias);
        searchPanel.add(mediaSelect);
        
        originalCheck = new JCheckBox("Eredeti");
        originalCheck.setSelected(true);
        searchPanel.add(originalCheck);
        
        lendCheck = new JCheckBox("Kölcsönadva");
        searchPanel.add(lendCheck);
        mainWindow.getContentPane().add(searchPanel, BorderLayout.NORTH);
        
        dataPanel = new JPanel();
        cl = new CardLayout();
        dataPanel.setLayout(cl);
        
        
        lendPanel = new JPanel();
        lendPanel.add(new JScrollPane(new JTable(sm.getLtb())));
        
        
        filmsPanel = new JPanel();
        filmsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        reloadFilmPanel();
        
        scrollFilmsFrame = new JScrollPane(filmsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        dataPanel.add(scrollFilmsFrame, "filmek");
        dataPanel.add(lendPanel,"kolcsonok");
        mainWindow.getContentPane().add(dataPanel);
        buttonsPanel = new JPanel();
        JButton newFilmButton = new JButton("Új Film");
        newFilmButton.addActionListener(new NewFilmButtonActionListener());
        
        buttonsPanel.add(newFilmButton, BorderLayout.WEST);
        lendViewButton = new JButton("Kölcsönzések");
        lendViewButton.addActionListener(new LendButtonActionListener());
        buttonsPanel.add(lendViewButton);
        mainWindow.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        
        mainWindow.pack();
        mainWindow.setVisible(true);
    }
    
    public void reloadFilmPanel(){
        filmsPanel.removeAll();
        filmsPanel.revalidate();
        filmsPanel.repaint();
        sm.getFilms().forEach((film) -> {
            FilmPanel filmPanel = new FilmPanel(film, thisGUI);
            filmsPanel.add(filmPanel.getFilmPanel());
        });    
    }
    
    class LendButtonActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            cl.next(dataPanel);
            if("Kölcsönzések".equals(lendViewButton.getText())){
              lendViewButton.setText("Filmek");  
            }else{
                lendViewButton.setText("Kölcsönzések");
            }
            
        }
    
    }
    
    
    class NewFilmButtonActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            NewFilmWindow newFilmWindow = new NewFilmWindow(thisGUI);
        }
        
    }
    
    class SearchButtonActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            SystemManager.searchFilms(cimTextField.getText(),rendTextField.getText(),
                                        yearTextField.getText(),mcTextField.getText(),
                                        lengthTextField.getText(),mediaSelect.getSelectedItem().toString(), 
                                        originalCheck.isSelected(), lendCheck.isSelected());
            reloadFilmPanel();
        }
        
    }
    
    
}
