package tcp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JTextArea;

public class Cliente extends Thread {
    
    private final int PORTA = 5000;
    private final String URL = "127.0.0.1";
    
    private ObjectInputStream msg_in;
    private ObjectOutputStream msg_out;
    
    private final JTextArea txt;
    
    public Cliente(JTextArea txt){
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
            Socket s =  new Socket(URL,PORTA);
            show("Conectado ao servidor na porta " + PORTA);
            
            msg_out = new ObjectOutputStream(s.getOutputStream());
            msg_in = new ObjectInputStream(s.getInputStream());
                
            String msg = null;
            do{
                msg = (String)msg_in.readObject();
                show(msg);
            }while (!msg.toUpperCase().equals("FIM"));
            
            msg_in.close();
            msg_out.close();
            s.close();
            show("Conexão finalizada!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    
}
