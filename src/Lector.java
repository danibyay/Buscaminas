import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/*
 * Estructura de Datos
 * Arturo Iturriaga García
 * Daniela Becerra González
 * Proyecto de Buscaminas
 * 5/03/15
 */

public class Lector {
	protected File dir;
	protected int size;
	protected int[][] campoLeido;

	public Lector(File dir, int size){
		this.dir =dir;
		this.size = size;
		campoLeido=new int[size][size]; 
	}

	public int[][] leer( ){
		try{
			BufferedReader bf = new BufferedReader(new FileReader(this.dir)); //archivo
			String linea;
			int j=0;
			while((linea=bf.readLine())!=null){		
				for(int i=0; i<linea.length();i++){
					String s=Character.toString(linea.charAt(i));
					if(s.equals("-"))
						campoLeido[j][i]=0;
					else if(s.equals("1"))
						campoLeido[j][i]=1;
					else if(s.equals("2"))
						campoLeido[j][i]=2;
					else if(s.equals("3"))
						campoLeido[j][i]=3;
					else if(s.equals("4"))
						campoLeido[j][i]=4;
					else if(s.equals("5"))
						campoLeido[j][i]=5;
					else if(s.equals("6"))
						campoLeido[j][i]=6;
					else if(s.equals("7"))
						campoLeido[j][i]=7;
					else if(s.equals("8"))
						campoLeido[j][i]=8;
					else if(s.equals("X"))
						campoLeido[j][i]=9;
				}
				j++;			
			}
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return campoLeido;
	}
}
	

