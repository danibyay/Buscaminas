import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * Estructura de Datos
 * Arturo Iturriaga García
 * Daniela Becerra González
 * Proyecto de Buscaminas
 * 5/03/15
 */

public class VentanaJuego extends JFrame implements ActionListener{
	protected PanelSuperior panelSuperior;
	public PanelJuego panelJuego;
	protected JMenuBar menubar;
	protected int n,j;
	protected JFileChooser fc;
	protected File file;
	protected JMenuItem escoger, escoger2;
	protected int alto, ancho;
	
	public VentanaJuego(int nivel, int juego, File archivo){
		super("El mejor Buscaminas");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		if(nivel==1){
			alto = 620; ancho=590;
		}
		else{
			ancho = 700; alto = 750;
		}
		
		this.setSize(new Dimension(ancho,alto)); 
		
		n=nivel; j=juego; file=archivo;
		
		this.panelJuego = new PanelJuego(n,j, this);
		this.panelSuperior = new PanelSuperior(this);
		this.add(panelSuperior, BorderLayout.NORTH);
		this.add(panelJuego, BorderLayout.CENTER);
		
		this.fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("archivos de texto", "txt"));
		
		createMenuBar();
		
		this.setVisible(true);	
		
	}
	
	public void createMenuBar(){
		this.menubar= new JMenuBar();
		
		JMenu nivel = new JMenu("Opciones");
		
		JMenu facil = new JMenu("Fácil");
		JMenu inter = new JMenu("Intermedio");
		
		JMenuItem  aleatorio;
		escoger = new JMenuItem("Seleccionar archivo");
		aleatorio = new JMenuItem("Aleatorio");
		
		JMenuItem aleatorio2;
		escoger2 = new JMenuItem("Seleccionar archivo");
		aleatorio2 = new JMenuItem("Aleatorio");
		
		facil.setToolTipText("8x8");
		inter.setToolTipText("16x16");
	
		escoger.addActionListener(this);
		
		aleatorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	n=1; j=0;
            	panelSuperior.restart(n,j, null);
            }  });
		
		escoger2.addActionListener(this);
		
		aleatorio2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	n=2; j=0;
            	panelSuperior.restart(n,j, null);
            }  });
		
		nivel.add(facil); nivel.add(inter);
		facil.add(escoger); facil.add(aleatorio);
		inter.add(escoger2); inter.add(aleatorio2);
		
		menubar.add(nivel);		
		
		this.setJMenuBar(menubar);
	}
	
	
	
	
	public static void main(String[] args) {
		VentanaJuego v = new VentanaJuego(1,0, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==escoger){
			fc.showOpenDialog(this);
        	file = fc.getSelectedFile();
        	n=1; j=1;
        	panelSuperior.restart(n,j, file);
		}
		else if(e.getSource()==escoger2){
			fc.showOpenDialog(this);
			file = fc.getSelectedFile();
        	n=2; j=1;
        	panelSuperior.restart(n,j, file);
		}
	}
}


