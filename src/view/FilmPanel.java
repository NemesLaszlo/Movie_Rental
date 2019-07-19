package view;

import moldel.Film;
import view.FilmDataWindow;
import view.GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FilmPanel {
    private JPanel filmPanel;
    private Film film;
    
    GUI mainGUI;
    public FilmPanel(Film film, GUI mainGUI) {
        this.mainGUI=mainGUI;
        this.film=film;
        filmPanel = new JPanel();
            filmPanel.setLayout(new BorderLayout());
            filmPanel.addMouseListener(new PanelMouseListener());
            filmPanel.add(new JLabel(film.getCover()), BorderLayout.NORTH);
            filmPanel.add(new JLabel(film.getTitle()), BorderLayout.CENTER);
            filmPanel.add(new JLabel(film.getDirector()), BorderLayout.SOUTH);
    }

    public JPanel getFilmPanel() {
        return filmPanel;
    }
    
    class PanelMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            FilmDataWindow fdm = new FilmDataWindow(film, mainGUI);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            e.getComponent().setBackground(Color.red);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            e.getComponent().setBackground(Color.LIGHT_GRAY);
        }
        
    }
}
