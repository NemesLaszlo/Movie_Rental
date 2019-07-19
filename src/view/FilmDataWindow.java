package view;

import moldel.Actor;
import moldel.Film;
import moldel.SystemManager;
import view.GUI;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;


public class FilmDataWindow {
    private JFrame window;
    private JPanel cards;
    private JPanel dataPanel;
    private JPanel editPanel;
    
    private JPanel coverPanel;
    private JPanel infoPanel;
    private JPanel buttonsPanel;
    
    private ImageIcon filmCover;
    private JLabel filmTitleLabel;
    private JLabel filmDirectorLabel;
    private JLabel filmLengthLabel;
    private JLabel filmYearLabel;
    private JLabel filmMediaLabel;
    private JLabel filmLendInfoLabel;
    private JLabel filmOriginalLabel;
    private JList<String> filmMainCharacters;
    
    private JTextField alterTitleTextField;
    private JTextField alterDirTextField;
    private JTextField alterYearTextField;
    private JTextField alterLengthTextField;
    private JComboBox<String> alterMediaComboBox;
    private JTextField alterMainCharactersTextField;
    private JCheckBox alterOriginalCheckbox;
    private JLabel fileLabel;
    
    private JPanel editButtonsPanel;
    
    private CardLayout cl;
    GUI mainGUI;
    
    private JButton lendButton;
    
    
    private Film  film;
    public FilmDataWindow(Film film, GUI mainGUI) {
        alterTitleTextField = new JTextField(film.getTitle());
        alterDirTextField = new JTextField(film.getDirector());
        alterYearTextField = new JTextField(film.getYear()+"");
        alterLengthTextField = new JTextField(film.getLenth()+"");
        fileLabel = new JLabel(film.getCover().getDescription());
        String[] medias = new String[]{"DVD","VHS"};
        alterMediaComboBox = new JComboBox<>(medias);
        
        String mcString="";
        for(Actor a : film.getMainCharacters()){
            mcString+=a.getName()+";";
        }
        mcString=mcString.substring(0, mcString.length() - 1);
        alterMainCharactersTextField = new JTextField(mcString);
        alterOriginalCheckbox = new JCheckBox();
        alterOriginalCheckbox.setSelected(film.isOriginal());
        cl = new CardLayout();
        cards = new JPanel(cl);
        dataPanel = new JPanel();
        dataPanel.setLayout(new BorderLayout());
        editPanel = new JPanel();
        editPanel.setLayout(new GridLayout(0,2));
        editPanel.add(new JLabel("Cím: "));
        editPanel.add(alterTitleTextField);
        editPanel.add(new JLabel("Rendező: "));
        editPanel.add(alterDirTextField);
        editPanel.add(new JLabel("Év: "));
        editPanel.add(alterYearTextField);
        editPanel.add(new JLabel("Hossz: "));
        editPanel.add(alterLengthTextField);
        editPanel.add(new JLabel("Adathordozó: "));
        editPanel.add(alterMediaComboBox);
        editPanel.add(new JLabel("Főszereplők: "));
        editPanel.add(alterMainCharactersTextField);
        editPanel.add(fileLabel);
        JButton fileSelectButton = new JButton("Tallózás");
        fileSelectButton.addActionListener(new CoverSelectActionListener());
        editPanel.add(fileSelectButton);
        editPanel.add(new JLabel("Eredeti: "));
        editPanel.add(alterOriginalCheckbox);
        
        editButtonsPanel=new JPanel();
        JButton editSubmitButton = new JButton("Jóváhagyás");
        editSubmitButton.addActionListener(new SubmitButtonActionListener());
        editButtonsPanel.add(editSubmitButton);
        editPanel.add(editButtonsPanel);
        
        this.window = new JFrame(film.getTitle()+" adatai");
        
        
        
        
        this.mainGUI=mainGUI;
        
        coverPanel = new JPanel();
        buttonsPanel = new JPanel();
        this.film=film;
        
        this.filmCover = film.getCover();        
        Image image = this.filmCover.getImage(); 
        Image newimg = image.getScaledInstance(183*2, 273*2,  java.awt.Image.SCALE_SMOOTH); 
        this.filmCover = new ImageIcon(newimg);
        
        coverPanel.add(new JLabel(this.filmCover));
        dataPanel.add(coverPanel, BorderLayout.WEST);
        
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0,1));        
        this.filmTitleLabel = new JLabel(film.getTitle());       
        this.filmDirectorLabel = new JLabel(film.getDirector());
        this.filmLengthLabel = new JLabel(film.getLenth()+" perc");
        this.filmYearLabel = new JLabel(film.getYear()+"");
        
        String[] characterNames = new String[film.getMainCharacters().size()];
        for(int i = 0; i<film.getMainCharacters().size();i++){
            characterNames[i]=film.getMainCharacters().get(i).getName();
        }
        this.filmMainCharacters = new JList(characterNames);
        this.filmMediaLabel = new JLabel(film.getMedia().toString());
    
        infoPanel.add(this.filmTitleLabel);
        infoPanel.add(this.filmDirectorLabel);
        infoPanel.add(this.filmYearLabel);
        infoPanel.add(this.filmLengthLabel);
        infoPanel.add(this.filmMediaLabel);
        infoPanel.add(this.filmMainCharacters);
        
        
        
        String originalLabelString;
        if(film.isOriginal()){
            originalLabelString = "A film eredeti.";
        }else{
            originalLabelString = "A film egy másolat";
        }
        filmOriginalLabel = new JLabel(originalLabelString);
        infoPanel.add(filmOriginalLabel);
        dataPanel.add(infoPanel,BorderLayout.EAST);
        
        
        JButton deleteButton = new JButton("Film törlése");
        deleteButton.addActionListener(new DeleteButtonActionListener());
        JButton editButton = new JButton("Film módosítása");
        editButton.addActionListener(new EditButtonActionListener());
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        
        lendButton = new JButton();
        if(!film.isBorrowed()){
            lendButton.setText("Film kölcsönadása");
        }else{
            lendButton.setText("Film visszavétele");
        }
        lendButton.addActionListener(new LendButtonActionListener());
        buttonsPanel.add(lendButton);
        dataPanel.add(buttonsPanel,BorderLayout.SOUTH);
        

        cards.add(dataPanel, "Adat panel");
        cards.add(editPanel, "Szerkesztés panel");
        window.getContentPane().add(cards);
        window.pack();
        window.setVisible(true);
    }

    
    class LendButtonActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!film.isBorrowed()){
            lendButton.setText("Film kölcsönadása");
            LendWindow lw = new LendWindow(film,lendButton);
            }else{
            lendButton.setText("Film visszavétele");
            SystemManager.getBackFilm(film);
            }
        }
    
    }
    
    
    class CoverSelectActionListener implements ActionListener{    
        @Override
        public void actionPerformed(ActionEvent ae) {
           JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());       
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
                        fileLabel.setText(selectedFile.getName());
            }
        }
        
    }
    
    class SubmitButtonActionListener implements ActionListener {
     @Override
        public void actionPerformed(ActionEvent ae) {
        
            SystemManager.alterFilm(film.getID(), alterTitleTextField.getText(), alterDirTextField.getText(), Integer.parseInt(alterYearTextField.getText()), 
                    alterMainCharactersTextField.getText(),
                    Integer.parseInt(alterLengthTextField.getText()), alterMediaComboBox.getSelectedItem().toString(), fileLabel.getText(), alterOriginalCheckbox.isSelected());
                    mainGUI.reloadFilmPanel();
                    window.dispose();
        }
    }
    
    class DeleteButtonActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
           SystemManager.deleteFilm(film);
           mainGUI.reloadFilmPanel();
           window.dispose();
        }
        
    }
    
    class EditButtonActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            cl.show(cards, "Szerkesztés panel");
        }
    
    }
    
}
