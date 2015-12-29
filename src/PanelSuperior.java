import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Estructura de Datos
 * Arturo Iturriaga García
 * Daniela Becerra González
 * Proyecto de Buscaminas
 * 5/03/15
 */

public class PanelSuperior extends JPanel implements Runnable, ActionListener{
	private VentanaJuego vj;
	protected ImageIcon caritaFeliz, caritaCool, caritaTriste, caritaPreocupada;
	protected JButton caritas;
	protected JLabel tiempo, marcador, ts, restantes;
	private int segundos;
	private Thread h;
	
	public PanelSuperior(VentanaJuego vj){
		super();
		this.vj = vj;
		
		h = new Thread(this);
		h.start();
		
		caritaPreocupada = new ImageIcon("worried.jpg"); 
		caritaCool = new ImageIcon("ganaste3.jpg");      // ganamos
		caritaTriste = new ImageIcon("lost.gif"); 
		caritaFeliz = new ImageIcon("feliz.jpg"); //feliz.jpg
		
		this.tiempo = new JLabel("Tiempo         ");
		this.ts = new JLabel("0       ");
		this.marcador = new JLabel("       Bombas ");
		this.restantes = new JLabel(this.vj.panelJuego.minas+" ");
		this.caritas = new JButton(caritaFeliz);
		
		this.caritas.addActionListener(this);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth=1; 
		c.gridy = 0;
		c.gridx = 0; 
		
		this.add(tiempo,c);
		c.gridx = 1; 
		this.add(ts,c);
		c.gridx = 2; 
		this.add(caritas,c);
		c.gridx = 3; 
		this.add(marcador,c);
		c.gridx = 4;
		this.add(restantes,c);
		
	}

	
	
	@Override
	public void run() {
		int parasegundos = 0;
		segundos = 0;
		while(this.vj.panelJuego.jugando){ //(!this.vj.panelJuego.perdio 
			try {
				Thread.sleep(5);	
				if(parasegundos==200){
					segundos ++;
					this.ts.setText(segundos + "          ");
					parasegundos=0;
				}
				parasegundos++;
			} catch (InterruptedException e) {
				
			}	
		}	
	}
	
	
	public void restart(int nivel, int juego, File archivo){
		this.vj.setVisible(false);
		this.vj= new VentanaJuego(nivel, juego, archivo);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==caritas){
			restart(this.vj.n, this.vj.j, this.vj.file);
		}
	}


	
}
