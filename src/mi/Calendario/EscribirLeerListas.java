/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mi.Calendario;

/**
 *
 * @author r.marin
 */

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class EscribirLeerListas {
    private ArrayList listA, listB, listC, listGeneral;
    private String nombreArchivo;
    
    public EscribirLeerListas(){
        listA = new ArrayList();
        listB = new ArrayList();
        listC = new ArrayList();
        listGeneral = new ArrayList();
        nombreArchivo = "ListadoMaquinas.txt";
        leerLista();
    }
    
    protected boolean escribirLista(){
        boolean chk = false;
        Maquina aux;
        if(listGeneral.isEmpty()){
            File f = new File(nombreArchivo);
            if(f.exists()){
                f.delete();
            }
            JOptionPane.showMessageDialog(null, "No hay máquinas existentes", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
        else{ 
            try{
                FileWriter arch = new FileWriter(nombreArchivo, false); //True = append
                PrintWriter data = new PrintWriter(arch);
                for(int i = 0; i < listGeneral.size(); i++){
                    aux = (Maquina) listGeneral.get(i);
                    data.println(aux.getCodigo());
                    data.println(aux.getNombre());
                    data.println(aux.getPrioridad());
                }
                data.close();
                chk = true;
            }catch(IOException e){
                System.out.println("IOException:" + e);
            }
            copiarArchivo();
        }
        return chk;
    } //Listo!
    
    private void copiarArchivo(){
        try{
            File f1 = new File(nombreArchivo);
            File f2 = new File("bk/copiaMaquinas.txt");
            if(f1.exists()){
                InputStream in = new FileInputStream(f1);
                OutputStream out = new FileOutputStream(f2);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0){
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                System.out.println("Archivo Copiado.");
            }
            else{
                System.out.println("No hay archivo fuente por copiar.");
            }                      
        }
        
        catch(IOException e){
            System.out.println("IOException:" + e);  
        }
    } //Listo!
    
    private void leerLista(){
        try{
            File temp = new File(nombreArchivo);
            // estado = 1 indica que no hay arhivo existente para leer,listGeneral sigue vacía
            if(!temp.exists()){
                //No hay nada que leer
                JOptionPane.showMessageDialog(null, "No hay máquinas, favor agregar", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                FileReader arch = new FileReader(nombreArchivo);
                BufferedReader data = new BufferedReader(arch);
                String firstLine = null;
                //Si el archivo existe pero esta vacío, estado = 2 para indicar que listGeeneral sigue vacía
                if((firstLine = data.readLine()) == null){
                    JOptionPane.showMessageDialog(null, "No hay máquinas, favor agregar", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    data.close();
                }
                else{
                    //Ya que el archivo existe y no está vacío, se sacan las máquinas del archivo
                    String cod, nom, pr;
                    Maquina aux;
                    do{
                        cod = firstLine;
                        nom = data.readLine();
                        pr = data.readLine();
                        aux = new Maquina (cod, nom, pr);
                        listGeneral.add(aux);
                    }while((firstLine = data.readLine()) != null);
                    
                    data.close(); 
                }  
            }
            
        }catch(IOException e){
            System.out.println("IOException:" + e);
        }
    } //Listo!
      
    public boolean agregarMaquina(Maquina N){
        return listGeneral.add(N);
    } //Listo!
    
    public boolean revisarDuplicado(Maquina N){
        boolean dup = false;
        // Si listGeneral está vacía, no puede estar duplicada
        if(listGeneral.isEmpty()){
            dup = false;
        }
        else{
            // Se comprueba si N ya está en listGeneral  
            Maquina aux;
            for(int index = 0; index < listGeneral.size(); index++){
                aux = (Maquina) listGeneral.get(index); 
                if(N.getCodigo().equals(aux.getCodigo())){
                    dup = true; 
                    break;
                }
            }
        }      
        return dup;
    } //Listo!
    
    public boolean revisarDuplicado(String codigo){
        boolean dup = false;
        // Si listGeneral está vacía, no puede estar duplicada
        if(listGeneral.isEmpty()){
            dup = false;
        }
        else{
            // Se comprueba si el código ya está en listGeneral  
            Maquina aux;
            for(int index = 0; index < listGeneral.size(); index++){
                aux = (Maquina) listGeneral.get(index); 
                if(codigo.equals(aux.getCodigo())){
                        dup = true; 
                        break;
                }
            }
        }      
        return dup;
    } //Función sobre cargada para revisar duplicado enviando como parámetro solo el código
    
    public Maquina buscarMaquina(String codigo){
        Maquina aux = null;
        if(listGeneral.isEmpty()){
            aux = null;
        }
        else{
            for(int index = 0; index < listGeneral.size(); index++){
                Maquina temp = (Maquina) listGeneral.get(index);
                if(temp.getCodigo().equals(codigo)){
                    aux = temp;
                    break;
                }
            }
        }
        return aux;
    } //Listo!
    
    public boolean eliminarMaquina(Maquina N){
        return listGeneral.remove(N);
    } //Listo!
    
    public boolean editarMaquina(Maquina N, String [] v){
        return N.setTodo(v[0], v[1], v[2]);
    } //Listo!
    
    public ArrayList getLista(){
        return listGeneral;
    }
}
