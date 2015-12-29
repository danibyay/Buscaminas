import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/*
 * Estructura de Datos
 * Arturo Iturriaga García
 * Daniela Becerra González
 * Proyecto de Buscaminas
 * 5/03/15
 */

public class PanelJuego extends JPanel implements ActionListener, MouseListener{
	private JButton[][] cuadritos;
	private boolean[][] tieneBandera, visitado;
	private ImageIcon i1, i2, i3, i4, i5, i6 , i7, i8, exp, mina, cuadro, nada, bandera;
	public int size, lado, minas;
	private Map mapa;
	private int[][] campo;
	public boolean perdio, click, cd, yagane, jugando;
	protected VentanaJuego v;
	
	public PanelJuego(int modo, int juego, VentanaJuego ve){
		super();
		
		this.v=ve;
		jugando = true;
		
		if(modo ==1){
			size = 8;
			lado = 540;
			minas = 10;
			//imagenes
			this.i1 = new ImageIcon("1.png");
			this.i2 = new ImageIcon("2.png");
			this.i3 = new ImageIcon("3.png");
			this.i4 = new ImageIcon("4.png");
			this.i5 = new ImageIcon("5.png");
			this.i6 = new ImageIcon("6.png");
			this.i7 = new ImageIcon("7.png");
			this.i8 = new ImageIcon("8.png");
			this.exp = new ImageIcon("explosion.png");
			this.mina = new ImageIcon("mina.png");
			this.cuadro = new ImageIcon("cuadro.png");
			this.nada = new ImageIcon("nada.png");
			this.bandera = new ImageIcon("bandera.gif");
			}
		else{
			size = 16;
			lado = 700;
			minas = 40; 
			//imagenes
			this.i1 = new ImageIcon("rsz_111.png"); 
			this.i2 = new ImageIcon("rsz_12.png");
			this.i3 = new ImageIcon("rsz_13.png");
			this.i4 = new ImageIcon("rsz_14.png");
			this.i5 = new ImageIcon("rsz_15.png");
			this.i6 = new ImageIcon("rsz_116.png");
			this.i7 = new ImageIcon("rsz_117.png");
			this.i8 = new ImageIcon("rsz_118.png");
			this.exp = new ImageIcon("rsz_1explosion.png");
			this.mina = new ImageIcon("rsz_11mine.png");
			this.cuadro = new ImageIcon("rsz_11cuadro.png"); 
			this.nada = new ImageIcon("rsz_nada.png");
			this.bandera = new ImageIcon("bandera1.gif");
			}
		
		this.cuadritos = new JButton[size][size];
		this.tieneBandera = new boolean[size][size];
		this.visitado = new boolean [size][size];
		this.setPreferredSize(new Dimension(lado, lado));
		
		//cuadricula de botones
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth=1;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				c.gridx = j; c.gridy = i;
				cuadritos[i][j] = new JButton(cuadro);
				cuadritos[i][j].addActionListener(this);
				cuadritos[i][j].addMouseListener(this);
				this.add(cuadritos[i][j],c);
			}
		}
		
		//lo importante
		if(juego==0 || this.v.file ==null){//aleatorio
			this.mapa = new Map(this.size, this.minas);
			this.mapa.fillMap();
			this.campo = this.mapa.getMap();
		}
		else {
			Lector l = new Lector(this.v.file, size);
			this.campo= l.leer();
		}
		
		////////////////////////
		//campo en consola
		///////////////////////
		//esto se puede quitar, sirve para debuggear, hacer trampa o ver el campo minado que está detrás
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				System.out.print("[" + campo[i][j] + "] ");
			}
			System.out.println();
		}
		System.out.println("termina");
		
	
		
	}
	
	public void actionPerformed(ActionEvent e) {
		top: for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (e.getSource()==cuadritos[i][j]) 
						if(!tieneBandera[i][j]){
							destapar(i,j);
							checarGanaste();
							break top;
					}
				}
		}
	}
	
	

	private void destapar(int i, int j) {
		switch(campo[i][j]){
			case 1: cuadritos[i][j].setIcon(i1);
					visitado[i][j] = true;
					break;
			case 2: cuadritos[i][j].setIcon(i2); 
					visitado[i][j] = true;
					break;
			case 3: cuadritos[i][j].setIcon(i3); 
					visitado[i][j] = true;
					break;
			case 4: cuadritos[i][j].setIcon(i4); 
					visitado[i][j] = true;
					break;
			case 5: cuadritos[i][j].setIcon(i5); 
					visitado[i][j] = true;
					break;
			case 6: cuadritos[i][j].setIcon(i6); 
					visitado[i][j] = true;
					break;
			case 7: cuadritos[i][j].setIcon(i7); 
					visitado[i][j] = true;
					break;
			case 8: cuadritos[i][j].setIcon(i8); 
					visitado[i][j] = true;
					break;
			case 9: perdiste();
					cuadritos[i][j].setIcon(exp); break; //perdiste
			case 0: cuadritos[i][j].setIcon(nada);
					visitado[i][j]=true;
			
					if((i-1)>=0)     //arriba
						if((campo[i-1][j])>=0 && (campo[i-1][j])<9 )
							if(!visitado[i-1][j])
								destapar(i-1,j);
					
					if((i-1)>=0 && (j-1)>=0)     //arriba izquierda
						if((campo[i-1][j-1])>=0 && (campo[i-1][j-1])<9 )
							if(!visitado[i-1][j-1])
								destapar(i-1,j-1);

					if((i-1)>=0 && (j+1)<size)     //arriba derecha
						if((campo[i-1][j+1])>=0 && (campo[i-1][j+1])<9 )
							if(!visitado[i-1][j+1])
								destapar(i-1,j+1);
					
					
					if((i+1)<size)     //abajo
						if((campo[i+1][j])>=0 && (campo[i+1][j])<9 )
							if(!visitado[i+1][j])
								destapar(i+1,j);
					
					if((i+1)<size && (j-1)>=0)     //abajo izquierda
						if((campo[i+1][j-1])>=0 && (campo[i+1][j-1])<9 )
							if(!visitado[i+1][j-1])
								destapar(i+1,j-1);
					

					if((i+1)<size && (j+1)<size)     //abajo derecha
						if((campo[i+1][j+1])>=0 && (campo[i+1][j+1])<9 )
							if(!visitado[i+1][j+1])
								destapar(i+1,j+1);
					
					
					if((j-1)>=0)     //izquierda
						if((campo[i][j-1])>=0 && (campo[i][j-1])<9 )
							if(!visitado[i][j-1])
								destapar(i,j-1);

					if((j+1)<size)     //derecha
						if((campo[i][j+1])>=0 && (campo[i][j+1])<9 )
							if(!visitado[i][j+1])
								destapar(i,j+1);
					
					
					
					
					break; //recursividad


		}
	}
	
	public void perdiste(){
		perdio=true; jugando = false;
		System.out.println("perdimos");
		this.v.panelSuperior.caritas.setIcon(this.v.panelSuperior.caritaTriste);
	    for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cuadritos[i][j].removeActionListener(this);
				cuadritos[i][j].removeMouseListener(this);
				if (campo[i][j]==9) {
					cuadritos[i][j].setIcon(mina);
				}
			}
	    }
	}
	    
	public void checarGanaste(){
		int c = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(visitado[i][j])
					c++;
			}
		}
		
		if(c==size*size && this.minas==0){
			jugando = false;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					cuadritos[i][j].removeActionListener(this);
					cuadritos[i][j].removeMouseListener(this);
				}
			}
			
			this.v.panelSuperior.caritas.setIcon(this.v.panelSuperior.caritaCool);
			JOptionPane.showMessageDialog(this, "¡Felicidades! ¡Ganaste!", "El mejor buscaminas", JOptionPane.PLAIN_MESSAGE, new ImageIcon("ganaste.gif"));
			yagane = true;		
		}	
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		click = true;
		this.v.panelSuperior.caritas.setIcon(this.v.panelSuperior.caritaPreocupada);
	}

	public void mouseReleased(MouseEvent e) {		
		if(SwingUtilities.isRightMouseButton(e)){
			
				top: for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						if (e.getSource()==cuadritos[i][j]) {
							if(!tieneBandera[i][j]){
								cuadritos[i][j].setIcon(bandera);
								minas--;
								this.v.panelSuperior.restantes.setText("        "+minas+" ");
								tieneBandera[i][j]= true;
								visitado[i][j] = true;
							}
							else
							{
								cuadritos[i][j].setIcon(cuadro);
								minas++;
								this.v.panelSuperior.restantes.setText("        "+minas+" ");
								tieneBandera[i][j]= false;
								visitado[i][j] = false;
							}
							break top;
						}
					}
				}
			
		}
		click=false;
		if(!perdio)
			this.v.panelSuperior.caritas.setIcon(this.v.panelSuperior.caritaFeliz);
		if(!yagane) checarGanaste();


		
	}
	
	

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
}
