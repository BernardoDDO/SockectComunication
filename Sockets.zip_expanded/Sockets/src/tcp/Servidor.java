package tcp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JTextArea;

public class Servidor extends Thread {
    
    private final int PORTA = 5000;
    
    private ObjectInputStream msg_in;
    private ObjectOutputStream msg_out;
    
    private final JTextArea txt;
    
    public Servidor(JTextArea txt){
        this.txt = txt;
    }
    
    private void show(String msg){
        this.txt.append(msg + "\n");
    }
    
    public void enviar(String msg){
        try {
            msg_out.writeObject(msg);
            msg_out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void run(){
        try {
            ServerSocket srv = new ServerSocket(PORTA);
            show("Servidor iniciado: " + PORTA);
            while(true){
                show("Aguardando conexões... ");
                Socket s = srv.accept();
                show("Conexao de: " + 
                    s.getInetAddress().getHostAddress());
                
                msg_out = new ObjectOutputStream(s.getOutputStream());
                msg_in = new ObjectInputStream(s.getInputStream());
                
                String msg = null;
                do{
                    msg = (String)msg_in.readObject();
                    show(msg);
                }while (!msg.toUpperCase().equals("FIM"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    
}
