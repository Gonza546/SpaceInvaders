package Vista;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import Controlador.Partida;
import ValueObjects.DisparoVO;
import ValueObjects.EnemigoVO;
import ValueObjects.JugadorVO;
import ValueObjects.MuroVO;

public class Ventana extends JFrame {

	private static final long serialVersionUID = -3976866435966505711L;
	private JLabel jugadores[] = new JLabel[5],nave, title, muros[] = new JLabel[4], enemigoGif, enemigos[] = new JLabel[15], vida[], puntaje, puntajeMax, disparoa,fondo, disEnemigo1;
	private JInternalFrame inicio, juego, topJgs;
	private Container c;
	private String nombre;
	private Timer te, td, tde,tde1;
	private JButton play,exit,jgs,atras, cadete, guerrero, master;
	private int i, vEnemigos,v=0, puntajeVida=500;
	private DisparoVO dis;
	private String directorio;
	Dimension s = new Dimension();
	Toolkit tk = Toolkit.getDefaultToolkit();

	public Ventana() throws IOException{
		this.s = tk.getScreenSize();
		directorio=obtenerImagen();
		configurar();
		this.setTitle("SPACE INVADERS");
		this.setVisible(true);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setResizable(false);
		
	}
	
	
	private String obtenerImagen() throws IOException {
	       File fichero = new File("icon.jpg");
	       String rutaArchivos= "";
	       String rutaDelPrograma=fichero.getCanonicalPath();
	       char c='\\';
	       int i=0;
	       int posFinal=0;
	       i=rutaDelPrograma.length()-1;
	       while(posFinal==0){
	    	   if(rutaDelPrograma.charAt(i)==c) 
	    	   {
	    		   posFinal=i;
	    		   
	    	   }

			   i--;
	       }
	       for(i=0;i<posFinal;i++) {
	    	   rutaArchivos+=rutaDelPrograma.charAt(i);
	       }
	       
	       return rutaArchivos+"\\src\\images";
	}

	private void setTimers() {

		tde=null;
		tde1 = new Timer(600, new ManejoDisparoEnemigo());
	}

	private void configurar() {

		c = this.getContentPane();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setInicio();
		setJuego();
	}
	
	private void setJuego() {

		if (juego != null)
			juego = null;
		juego = new JInternalFrame();
		juego.setLayout(null);
		juego.setVisible(false);
		juego.setBounds(0, 0, s.width, s.height);
		juego.setBackground(Color.BLACK);
		BasicInternalFrameUI bi = (BasicInternalFrameUI) juego.getUI();
		bi.setNorthPane(null);
		setTimers();
		setEnemigos();
		setMuros();
		setVida();
		setPuntaje();
		setNave();
		setFondo();

		c.add(juego);
	}

	private void setTopJgs(){
		topJgs = new JInternalFrame();
		topJgs.setLayout(null);
		topJgs.setVisible(false);
		topJgs.setBounds(0, 0, s.width, s.height);
		topJgs.setBackground(Color.BLACK);
		BasicInternalFrameUI bi = (BasicInternalFrameUI) topJgs.getUI();
		bi.setNorthPane(null);
		
		title = new JLabel();
		title.setIcon(new ImageIcon(directorio+"\\logo2.png"));
		title.setBounds(401, -40, 700, 318);

		enemigoGif = new JLabel();
		enemigoGif.setIcon(new ImageIcon(directorio+"\\enemigogif.gif"));
		enemigoGif.setBounds(621, 262, 140, 100);
		
		JugadorVO[] top = new JugadorVO[5]; 
		
		top = Partida.getInstancia().topJgs();
		int pos=400;
		if(top!=null)
			for(i=0;i<5;i++){
				jugadores[i] = new JLabel(top[i].getNombre() + "........." + top[i].getPuntaje());
				jugadores[i].setFont(new Font("Cosmic Alien", Font.BOLD, 25));
				jugadores[i].setForeground(Color.GREEN);
				jugadores[i].setBounds(441, pos, 500, 50);
				topJgs.add(jugadores[i]);
				pos+=45;
			}
		
		atras = new JButton("Atras");
		atras.setBackground(null);
		atras.setBorder(null);
		atras.setFont(new Font("Cosmic Alien", Font.BOLD, 30));
		atras.setForeground(Color.GREEN);
		atras.setBounds(591, pos, 200, 50);
		
		ActionListener atrasButton = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				topJgs.setVisible(false);
				inicio.setVisible(true);
			}
			
		};
		
		atras.addActionListener(atrasButton);
		
		topJgs.add(atras);
		topJgs.add(title);
		topJgs.add(enemigoGif);
		
		c.add(topJgs);
		
	}
	
	private void setNave() {

		if(nave==null){
			nave = new JLabel();
			juego.add(nave);
			juego.addKeyListener(new ManejoTeclas());
		}
		nave.setIcon(new ImageIcon(directorio+"\\nave.png"));
		nave.setBounds(150, s.height - 150, 64, 64);
		juego.setFocusable(true);
		juego.requestFocusInWindow();

	}

	private void setPuntaje() {
		puntaje = new JLabel("SCORE:" + Partida.getInstancia().getPuntaje());
		puntaje.setFont(new Font("Cosmic Alien", Font.BOLD, 25));
		puntaje.setForeground(Color.GREEN);
		puntaje.setBounds(s.width - 600, 15, 350, 50);
		puntajeMax = new JLabel("HIGHSCORE: "+Partida.getInstancia().getPuntajeMax());
		puntajeMax.setFont(new Font("Cosmic Alien", Font.BOLD,25));
		puntajeMax.setForeground(Color.GREEN);
		puntajeMax.setBounds(s.width-s.width+50, 15, 350, 50);
		juego.add(puntajeMax);
		juego.add(puntaje);

	}

	private void setFondo(){
		if(fondo==null){
			fondo = new JLabel();
		}
		fondo.setIcon(new ImageIcon(directorio+"\\space.png"));
		fondo.setBounds(0, 0, s.width, s.height);
		juego.add(fondo);
	}
	private void setInicio() {

		inicio = new JInternalFrame();
		inicio.setLayout(null);
		inicio.setBounds(0, 0, s.width, s.height);
		inicio.setBackground(Color.BLACK);
		inicio.setVisible(true);
		BasicInternalFrameUI bi = (BasicInternalFrameUI) inicio.getUI();
		bi.setNorthPane(null);

		title = new JLabel();
		title.setIcon(new ImageIcon(directorio+"\\logo2.png"));
		title.setBounds(401, -40, 700, 318);

		enemigoGif = new JLabel();
		enemigoGif.setIcon(new ImageIcon(directorio+"\\enemigogif.gif"));
		enemigoGif.setBounds(621, 262, 140, 100);

		play = new JButton("Jugar");
		play.setBackground(null);
		play.setBorder(null);
		play.setFont(new Font("Cosmic Alien", Font.BOLD, 30));
		play.setForeground(Color.GREEN);
		play.setBounds(591, 400, 200, 50);

		ActionListener playButton = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				play.setVisible(false);
				jgs.setVisible(false);
				cadete.setVisible(true);
				guerrero.setVisible(true);
				master.setVisible(true);

			}

		};

		play.addActionListener(playButton);

		jgs = new JButton("Top Jugadores");
		jgs.setBackground(null);
		jgs.setBorder(null);
		jgs.setFont(new Font("Cosmic Alien", Font.BOLD, 30));
		jgs.setForeground(Color.GREEN);
		jgs.setBounds(441, 500, 500, 50);
		
		ActionListener jgsButton = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				inicio.setVisible(false);
				setTopJgs();
				topJgs.setVisible(true);
			}
			
		};
		
		jgs.addActionListener(jgsButton);

		exit = new JButton("Salir");
		exit.setBackground(null);
		exit.setBorder(null);
		exit.setFont(new Font("Cosmic Alien", Font.BOLD, 30));
		exit.setForeground(Color.GREEN);
		exit.setBounds(591, 600, 200, 50);
		
		ActionListener exitButton = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
			
		};
		
		exit.addActionListener(exitButton);

		cadete = new JButton("Cadete");
		cadete.setBackground(null);
		cadete.setBorder(null);
		cadete.setFont(new Font("Cosmic Alien", Font.BOLD, 30));
		cadete.setForeground(Color.GREEN);
		cadete.setBounds(591, 400, 200, 50);
		cadete.setVisible(false);

		ActionListener cadeteButton = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				vEnemigos = 100;
				te = new Timer(vEnemigos, new ManejoEnemigos());
				inicio.setVisible(false);
				juego.setVisible(true);
				tde1.restart();
				te.restart();
			}

		};

		cadete.addActionListener(cadeteButton);

		guerrero = new JButton("Guerrero");
		guerrero.setBackground(null);
		guerrero.setBorder(null);
		guerrero.setFont(new Font("Cosmic Alien", Font.BOLD, 30));
		guerrero.setForeground(Color.GREEN);
		guerrero.setBounds(541, 465, 300, 50);
		guerrero.setVisible(false);

		ActionListener guerreroButton = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				vEnemigos = 75;
				te = new Timer(vEnemigos, new ManejoEnemigos());
				inicio.setVisible(false);
				juego.setVisible(true);
				tde1.restart();
				te.restart();
			}

		};

		guerrero.addActionListener(guerreroButton);

		master = new JButton("Master");
		master.setBackground(null);
		master.setBorder(null);
		master.setFont(new Font("Cosmic Alien", Font.BOLD, 30));
		master.setForeground(Color.GREEN);
		master.setBounds(541, 530, 300, 50);
		master.setVisible(false);

		ActionListener masterButton = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				vEnemigos = 35;
				te = new Timer(vEnemigos, new ManejoEnemigos());
				inicio.setVisible(false);
				juego.setVisible(true);
				tde1.restart();
				te.restart();
			}

		};

		master.addActionListener(masterButton);

		inicio.add(cadete);
		inicio.add(guerrero);
		inicio.add(master);
		inicio.add(play);
		inicio.add(exit);
		inicio.add(jgs);
		inicio.add(enemigoGif);
		inicio.add(title);

		c.add(inicio);
	}

	private void setEnemigos() {

		EnemigoVO[] e = Partida.getInstancia().getEnemigos();

		for (i = 0; i < 15; i++) {

			if (enemigos[i] == null)
				enemigos[i] = new JLabel();
			enemigos[i].setVisible(true);
			if (i < 5)
				enemigos[i].setIcon(new ImageIcon(directorio+"\\enemigo1.png"));
			else if (i < 10)
				enemigos[i].setIcon(new ImageIcon(directorio+"\\enemigo2.png"));
			else
				enemigos[i].setIcon(new ImageIcon(directorio+"\\enemigo3.png"));
			enemigos[i].setBounds(e[i].getX(), e[i].getY(), 64, 64);
			juego.add(enemigos[i]);
		}
	}

	private void setMuros() {

		int sep=125;
		for(i=0;i<4;i++){
			if(muros[i]==null){
				muros[i] = new JLabel();
				muros[i].setVisible(true);
				muros[i].setIcon(new ImageIcon(directorio+"\\muro3.png"));
				muros[i].setBounds((s.width-s.width)+sep, s.height-250, 128, 64);
				sep+=325;
			}
			else{
				muros[i].setIcon(new ImageIcon(directorio+"\\muro3.png"));
				muros[i].setVisible(true);
				sep+=325;
			}
			juego.add(muros[i]);
		}
	}
	
	private void setVida() {

		this.vida = new JLabel[3];

		for (i = 0; i < 3; i++) {

			if (i == 0) {
				vida[i] = new JLabel();
				vida[i].setIcon(new ImageIcon(directorio+"\\fullHeart.png"));
				vida[i].setBounds(s.width - 100, 10, 64, 64);

			} else if (i == 1) {
				vida[i] = new JLabel();
				vida[i].setIcon(new ImageIcon(directorio+"\\fullHeart.png"));
				vida[i].setBounds(s.width - 170, 10, 64, 64);

			} else {
				vida[i] = new JLabel();
				vida[i].setIcon(new ImageIcon(directorio+"\\fullHeart.png"));
				vida[i].setBounds(s.width - 240, 10, 64, 64);

			}
			juego.add(vida[i]);
		}
	}

	private void setImagenMuro(MuroVO m){
		
		for(i=0;i<4;i++){
			if(m.getX()==muros[i].getX()&&m.getY()==muros[i].getY()){
				if(m.getEnergia()<=90&&m.getEnergia()>80)
					muros[i].setIcon(new ImageIcon(directorio+"\\muro4.1.png"));
				else if(m.getEnergia()<=80&&m.getEnergia()>70)
					muros[i].setIcon(new ImageIcon(directorio+"\\muro5.1.png"));
				else if(m.getEnergia()<=70&&m.getEnergia()>60)
					muros[i].setIcon(new ImageIcon(directorio+"\\muro6.png"));
				else if(m.getEnergia()<=60&&m.getEnergia()>50)
					muros[i].setIcon(new ImageIcon(directorio+"\\muro7.png"));
				else if(m.getEnergia()<=50&&m.getEnergia()>40)
					muros[i].setIcon(new ImageIcon(directorio+"\\muro8.png"));
				else if(m.getEnergia()<=40&&m.getEnergia()>30)
					muros[i].setIcon(new ImageIcon(directorio+"\\muro9.png"));
				else if(m.getEnergia()<=30&&m.getEnergia()>20)
					muros[i].setIcon(new ImageIcon(directorio+"\\muro10.png"));
				else if(m.getEnergia()<=20&&m.getEnergia()>10)
					muros[i].setIcon(new ImageIcon(directorio+"\\muro11.png"));
				else if(m.getEnergia()<=10)
					muros[i].setIcon(new ImageIcon(directorio+"\\muro12.png"));		
			}
		}
		
	}
	
	class ManejoEnemigos implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			EnemigoVO[] ens = new EnemigoVO[15];
			ens = Partida.getInstancia().moverEnemigos();
			int y = 0;
			for (i = 0; i < 15; i++) {
				if (ens[i].getY() > y) {
					y = ens[i].getY();
				}
			}
			if (y>=s.height-250) {
				for (i = 0; i < 3; i++) {
					if (vida[i].getIcon() != new ImageIcon(directorio+"\\fullHeart.png"))
					{
						vida[i].setIcon(new ImageIcon(directorio+"\\emptyHeart.png"));
					}
				}
				disEnemigo1.setVisible(false);
				disEnemigo1=null;
				nave.setIcon(new ImageIcon(directorio+"\\explosion.gif"));
				te.stop();
				tde1.stop();
				tde.stop();
				do{
					nombre = JOptionPane.showInputDialog(null, "Ingrese su nombre", "GAME OVER", JOptionPane.QUESTION_MESSAGE);
					}while(nombre == null||nombre.equals(""));
				if (nombre != null) {
					Partida.getInstancia().guardarJugador(nombre);
					Partida.getInstancia().clearEnemigos();
					Partida.getInstancia().clearNave();
					Partida.getInstancia().crearEnemigos();
					Partida.getInstancia().crearNave();
					Partida.getInstancia().setPuntaje(0);
					juego.setVisible(false);
					nave=null;
					juego = null;
					disparoa = null;
					disEnemigo1=null;
					puntajeVida=500;
					setJuego();
					cadete.setVisible(false);
					guerrero.setVisible(false);
					master.setVisible(false);
					play.setVisible(true);
					exit.setVisible(true);
					jgs.setVisible(true);
					inicio.setVisible(true);
					te.stop();
					tde1.stop();
				}
			}
			for (i = 0; i < 15; i++) {
				enemigos[i].setBounds(ens[i].getX(), ens[i].getY(), 64, 64);
			}
		}

	}
	
	class ManejoDisparoEnemigo implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
					
					if(tde==null){
						dis = Partida.getInstancia().disparoEnemigo();
						disEnemigo1 = new JLabel();
						disEnemigo1.setIcon(new ImageIcon(directorio+"\\bulletEnemigo.png"));
						disEnemigo1.setBounds(dis.getX(), dis.getY(), 16, 16);
						juego.add(disEnemigo1);
						setFondo();
						tde = new Timer(75, new ManejoMovimientoDisparoEnemigo(dis, i));
						tde.start();
					}
					else if(!tde.isRunning()){
						dis = Partida.getInstancia().disparoEnemigo();
						disEnemigo1 = new JLabel();
						disEnemigo1.setIcon(new ImageIcon(directorio+"\\bulletEnemigo.png"));
						disEnemigo1.setBounds(dis.getX(), dis.getY(), 16, 16);
						juego.add(disEnemigo1);
						setFondo();
						tde = new Timer(75, new ManejoMovimientoDisparoEnemigo(dis, i));
						tde.start();
					}
				}
			}		
	class ManejoMovimientoDisparoEnemigo implements ActionListener {
		
		DisparoVO d;
		MuroVO m;
		int i;
		public ManejoMovimientoDisparoEnemigo(DisparoVO disparo, int i){
			this.d=disparo;
			this.i=i;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(d.getY()<s.height+20&&disEnemigo1!=null){
				m=Partida.getInstancia().tocoMuro(d);
				if(m!=null)
					if(m.getEnergia()>0){
						setImagenMuro(m);
						disEnemigo1.setVisible(false);
						tde.stop();
						Partida.getInstancia().borrarDisparoEnemigo(d);
					}
					else{
						for(i=0;i<4;i++){
							if(m.getX()==muros[i].getX()&&m.getY()==muros[i].getY()){
								muros[i].setVisible(false);
								muros[i]=null;
								disEnemigo1.setVisible(false);
								tde.stop();
								Partida.getInstancia().borrarDisparoEnemigo(d);
								
							}
						}
					}
				else{
					if(Partida.getInstancia().tocoNave(d)){
						if(vida[v].getIcon() != new ImageIcon(directorio+"\\fullHeart.png")) {
							vida[v].setIcon(new ImageIcon(directorio+"\\emptyHeart.png"));
							v++;
						}
						Partida.getInstancia().actualizarVida(false);
						Partida.getInstancia().borrarDisparoEnemigo(d);
						disEnemigo1.setVisible(false);
						disEnemigo1=null;
						nave.setIcon(new ImageIcon(directorio+"\\gifexplosion.gif"));
						te.stop();
						tde1.stop();
						tde.stop();
						d=null;
						if(Partida.getInstancia().ultimaVida()){
							do{
							nombre = JOptionPane.showInputDialog(null, "Ingrese su nombre", "GAME OVER", JOptionPane.QUESTION_MESSAGE);
							}while(nombre == null||nombre.equals(""));
							if (nombre != null) {
								
								Partida.getInstancia().guardarJugador(nombre);
								Partida.getInstancia().clearEnemigos();
								Partida.getInstancia().clearNave();
								Partida.getInstancia().crearEnemigos();
								Partida.getInstancia().crearNave();
								Partida.getInstancia().setPuntaje(0);
								Partida.getInstancia().clearMuros();
								Partida.getInstancia().crearMuros();
								juego.setVisible(false);
								v=0;
								nave = null;
								juego = null;
								if(disEnemigo1!=null){
									disEnemigo1.setVisible(false);
									disEnemigo1 = null;
								}
								if(disparoa!=null){
									disparoa.setVisible(false);
									disparoa =null;
								}
								puntajeVida=500;
								setJuego();
								cadete.setVisible(false);
								guerrero.setVisible(false);
								master.setVisible(false);
								play.setVisible(true);
								exit.setVisible(true);
								jgs.setVisible(true);
								inicio.setVisible(true);
							}
						}
						else{
							for(int  q=0;q<=1000000000;q++){
								if(q==1000000000){
									Partida.getInstancia().restartNave();
									setNave();
									te.start();
									tde1.restart();
								}
							}
						}
					}
					else{
						if(disEnemigo1!=null){
						d.setY(d.getY()+20);
						disEnemigo1.setBounds(d.getX(), d.getY(), 16, 16);
						}
					}
				}
			}
			else{
				Partida.getInstancia().borrarDisparoEnemigo(d);
				if(disEnemigo1!=null){
				disEnemigo1.setVisible(false);
				disEnemigo1=null;
				}
				if(tde.isRunning()){
					tde.stop();
					d=null;
				}
			}
		}
		
	}

	class ManejoTeclas implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() == 39 || e.getKeyCode() == 37) {
				int pos = Partida.getInstancia().moverNave(e.getKeyCode());
				nave.setBounds(pos, s.height - 150, 64, 64);
			}
			if (e.getKeyCode() == 32) {
				if (!Partida.getInstancia().existeDisparoNave()) {
					DisparoVO dis = new DisparoVO();
					dis = Partida.getInstancia().disparoNave();
					if (disparoa == null) {
						disparoa = new JLabel();
						disparoa.setIcon(new ImageIcon(directorio+"\\bullet.png"));
						juego.add(disparoa);
						setFondo();
					}
					disparoa.setBounds(dis.getX(), dis.getY(), 16, 16);
					td = new Timer(50, new ManejoDisparoNave(dis.getX()));
					disparoa.setVisible(true);
					td.start();
				}
			}
		}
	}

	class ManejoDisparoNave implements ActionListener {

		int x;
		MuroVO m;

		public ManejoDisparoNave(int x) {
			this.x = x;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int y = Partida.getInstancia().disparoMovimientoNave();
			EnemigoVO e;
			if (y > 0) {
				m=Partida.getInstancia().tocoMuro(new DisparoVO(this.x,y));
				if(m!=null)
					if(m.getEnergia()>0){
						setImagenMuro(m);
						disparoa.setVisible(false);
						td.stop();
						Partida.getInstancia().borrarDisparoNave();
					}
					else{
						for(i=0;i<4;i++){
							if(m.getX()==muros[i].getX()&&m.getY()==muros[i].getY()){
								muros[i].setVisible(false);
								disparoa.setVisible(false);
								td.stop();
								Partida.getInstancia().borrarDisparoNave();
							}
						}
					}
				else{
					e = Partida.getInstancia().tocoEnemigo();
					if (e != null) {
						Partida.getInstancia().setPuntaje(Partida.getInstancia().getPuntaje() + 100);
						if(Partida.getInstancia().getPuntaje()>=puntajeVida){
							if(!Partida.getInstancia().vidaLlena()){
								v--;
								if (vida[v].getIcon()!= new ImageIcon(directorio+"\\emptyHeart.png"))
								{
									vida[v].setIcon(new ImageIcon(directorio+"\\fullHeart.png"));
									System.out.println("cambie icono");
									puntajeVida+=500;
								}
								Partida.getInstancia().actualizarVida(true);
							}
						}
						puntaje.setText("SCORE:" + Partida.getInstancia().getPuntaje());
						for (i = 0; i < 15; i++) {
							if (enemigos[i].getX() == e.getX() && enemigos[i].getY() == e.getY())
								enemigos[i].setVisible(false);
						}
						Partida.getInstancia().borrarDisparoNave();
						disparoa.setVisible(false);
						td.stop();
						if (Partida.getInstancia().ultimoEnemigo()) {
							disEnemigo1.setVisible(false);
							te.stop();
							tde1.stop();
							int res = JOptionPane.showConfirmDialog(null, "�Desea Continuar?", "Completaste el nivel",
									JOptionPane.YES_NO_OPTION);
							if (res == JOptionPane.YES_OPTION) {
								te.stop();
								Partida.getInstancia().clearEnemigos();
								Partida.getInstancia().crearEnemigos();
								Partida.getInstancia().clearMuros();
								Partida.getInstancia().crearMuros();
								Partida.getInstancia().setPuntaje(Partida.getInstancia().getPuntaje() + 200);
								puntaje.setText("SCORE:" + Partida.getInstancia().getPuntaje());
								if (vEnemigos >= 35)
									vEnemigos -= 5;
								disEnemigo1=null;
								setTimers();
								setEnemigos();
								setMuros();
								setNave();
								setMuros();
								Partida.getInstancia().restartNave();
								setFondo();
								te.restart();
								tde1.restart();
							} else if (res == JOptionPane.NO_OPTION) {
								do{
									nombre = JOptionPane.showInputDialog(null, "Ingrese su nombre", "GAME OVER", JOptionPane.QUESTION_MESSAGE);
									}while(nombre == null||nombre.equals(""));
								if (nombre != null)
									Partida.getInstancia().guardarJugador(nombre);
								Partida.getInstancia().clearEnemigos();
								Partida.getInstancia().clearNave();
								Partida.getInstancia().crearEnemigos();
								Partida.getInstancia().crearNave();
								Partida.getInstancia().setPuntaje(0);
								Partida.getInstancia().clearMuros();
								Partida.getInstancia().crearMuros();
								disparoa = null;
								nave=null;
								juego.setVisible(false);
								juego = null;
								disEnemigo1=null;;
								puntajeVida=500;
								setJuego();
								cadete.setVisible(false);
								guerrero.setVisible(false);
								master.setVisible(false);
								play.setVisible(true);
								exit.setVisible(true);
								jgs.setVisible(true);
								inicio.setVisible(true);
							}
	
						}
					} else {
						y = Partida.getInstancia().disparoMovimientoNave();
						disparoa.setBounds(this.x, y, 16, 16);
					}
				}
			} else {
				Partida.getInstancia().borrarDisparoNave();
				disparoa.setVisible(false);
				td.stop();
			}
		}
	}
}
