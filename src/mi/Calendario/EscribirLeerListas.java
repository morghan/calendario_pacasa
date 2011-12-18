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
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class EscribirLeerListas {
    private ArrayList <Maquina> listA, listB, listC, listGeneral;
    private String nombreArchivo;
    private ArrayList <ArrayList> resultante; 
    private ArrayList <Maquina> rel;
    private int cont, a, b, c;
    
    public EscribirLeerListas(){
        listA = new ArrayList();
        listB = new ArrayList();
        listC = new ArrayList();
        listGeneral = new ArrayList();
        nombreArchivo = "ListaMaquinas.txt";
        leerLista();
        resultante = null;
        rel = null;
        cont = 0;
        a = 0; b= 0; c = 0;
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
                    aux = listGeneral.get(i);
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
                aux = listGeneral.get(index); 
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
                aux = listGeneral.get(index); 
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
                Maquina temp = listGeneral.get(index);
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
    
    private boolean llenarABC(){
        boolean empty = true;
        if(!listGeneral.isEmpty()){
            empty = false;
            listA.clear();
            listB.clear();
            listC.clear();
            for(int i = 0; i < listGeneral.size(); i++){
                Maquina aux = listGeneral.get(i);
                if(aux.getPrioridad().equals("A")){
                    listA.add(aux);
                }
                if(aux.getPrioridad().equals("B")){
                    listB.add(aux);
                }
                if(aux.getPrioridad().equals("C")){
                    listC.add(aux);
                }
            }
            //System.out.println(listA.size() + " " + listB.size() + " " + listC.size());
        }
        return empty;
    } //Listo!    
    
    private void caso1Resultante(){ 
        /*Crea la mayor cantidad de relaciones del tipo 2B y 1C ó 1B y 2C ó en su defecto 
        * (1B y 1C ó 2B ó 1B {las últimas 2 relaciones son causadas puesto que B >> C.
        * Por ahora no atañe si B << C, es decir termina el ciclo y no se forman relaciones} )*/
        while(!listB.isEmpty() || !listC.isEmpty()){
            //Relación del tipo 2B y 1C
            if(b >= 2 && c >= 1){
                rel = new ArrayList(3);
                rel.add(listB.get(0));
                rel.add(listB.get(1));
                rel.add(listC.get(0));
                resultante.add(rel);
                cont++;
                listB.remove(0);
                listB.remove(0);
                listC.remove(0);
                b = listB.size();
                c = listC.size();
            }
            //Relación del tipo 1B y 2C
            else if(b >= 1 && c >= 2){
                rel = new ArrayList(3);
                rel.add(listB.get(0));
                rel.add(listC.get(0));
                rel.add(listC.get(1));   
                resultante.add(rel);
                cont++;
                listB.remove(0);
                listC.remove(0);
                listC.remove(0);   
                b = listB.size();
                c = listC.size();
            }
            //Defecto 1B y 1C
            else if(b >= 1 && c >= 1){
                rel = new ArrayList(2);
                rel.add(listB.get(0));
                rel.add(listC.get(0));
                resultante.add(rel);
                cont++;
                listB.remove(0);
                listC.remove(0);
                b = listB.size();
                c = listC.size();
            }
            else if (b >= 1 && c < 1){
                //Defecto 2B {B >> C}
                while(b >= 2){
                    rel = new ArrayList(2);
                    rel.add(listB.get(0));
                    rel.add(listB.get(1));
                    resultante.add(rel);
                    cont++;
                    listB.remove(0);
                    listB.remove(0);
                    b = listB.size();
                    c = listC.size();
                }
                //Defecto 1B {B >> C}
                if(b == 1){
                    rel = new ArrayList();
                    rel.add(listB.get(0));
                    resultante.add(rel);
                    cont++;
                    listB.remove(0);
                    b = listB.size();
                    c = listC.size();
                }
            }
            if(listB.isEmpty() || listC.isEmpty()){
                break;
            }
        }//fin while
                
                /*En este punto se está seguro de que listB está vacía; 
                 * Ahora, es menester determinar qué hacer con el resto de C,
                 es decir que en este punto se toma en cuenta el caso B << C y se forman relaciones con 
                 los elementos restantes de listC*/
                
                /*Ahora, meramente se formarán relaciones del tipo 2C ó 1C, tomando en cuenta si existe un 
                 remanente en listC luego de ejecutarse el código anterior*/
        while(!listC.isEmpty()){
            if(c >= 2){
                rel = new ArrayList(2);
                rel.add(listC.get(0));
                rel.add(listC.get(1));
                resultante.add(rel);
                cont++;
                listC.remove(0);
                listC.remove(0);
                b = listB.size();
                c = listC.size(); 
            }
            if(c == 1){
                rel = new ArrayList();
                rel.add(listC.get(0));
                resultante.add(rel);
                cont++;
                listC.remove(0);
                b = listB.size();
                c = listC.size();
            }
            if(listC.isEmpty()){
                break;
            }
        }
        //Con esto se formaron todas las relaciones posibles cuando listA quedó vacía primero
    } //Listo!
    
    private void caso2Resultante(){ 
        /*Crea la mayor cantidad de relaciones del tipo 2A y 1C ó 1A y 2C ó en su defecto 
        * (1A y 1C ó 2A ó 1A {las últimas 2 relaciones son causadas puesto que A >> C.
        * Por ahora no atañe si A << C, es decir termina el ciclo y no se forman relaciones} )*/
        while(!listA.isEmpty() || !listC.isEmpty()){
            //Relación del tipo 2A y 1C
            if(a >= 2 && c >= 1){
                rel = new ArrayList(3);
                rel.add(listA.get(0));
                rel.add(listA.get(1));
                rel.add(listC.get(0));
                resultante.add(rel);
                cont++;
                listA.remove(0);
                listA.remove(0);
                listC.remove(0);
                a = listA.size();
                c = listC.size();
            }
            //Relación del tipo 1A y 2C
            else if(a >= 1 && c >= 2){
                rel = new ArrayList(3);
                rel.add(listA.get(0));
                rel.add(listC.get(0));
                rel.add(listC.get(1));   
                resultante.add(rel);
                cont++;
                listA.remove(0);
                listC.remove(0);
                listC.remove(0);   
                a = listA.size();
                c = listC.size();
            }
            //Defecto 1A y 1C
            else if(a >= 1 && c >= 1){
                rel = new ArrayList(2);
                rel.add(listA.get(0));
                rel.add(listC.get(0));
                resultante.add(rel);
                cont++;
                listA.remove(0);
                listC.remove(0);
                a = listA.size();
                c = listC.size();
            }
            else if (a >= 1 && c < 1){
                //Defecto 2A {A >> C}
                while(a >= 2){
                    rel = new ArrayList(2);
                    rel.add(listA.get(0));
                    rel.add(listA.get(1));
                    resultante.add(rel);
                    cont++;
                    listA.remove(0);
                    listA.remove(0);
                    a = listA.size();
                    c = listC.size();
                }
                //Defecto 1A {A >> C}
                if(a == 1){
                    rel = new ArrayList();
                    rel.add(listA.get(0));
                    resultante.add(rel);
                    cont++;
                    listA.remove(0);
                    a = listA.size();
                    c = listC.size();
                }
            }
            if(listA.isEmpty() || listC.isEmpty()){
                break;
            }
        }//fin while
                
                /*En este punto se está seguro de que listA está vacía; 
                 * Ahora, es menester determinar qué hacer con el resto de C,
                 es decir que en este punto se toma en cuenta el caso A << C y se forman relaciones con 
                 los elementos restantes de listC*/
                
                /*Ahora, meramente se formarán relaciones del tipo 2C ó 1C, tomando en cuenta si existe un 
                 remanente en listC luego de ejecutarse el código anterior*/
        while(!listC.isEmpty()){
            if(c >= 2){
                rel = new ArrayList(2);
                rel.add(listC.get(0));
                rel.add(listC.get(1));
                resultante.add(rel);
                cont++;
                listC.remove(0);
                listC.remove(0);
                a = listA.size();
                c = listC.size(); 
            }
            if(c == 1){
                rel = new ArrayList();
                rel.add(listC.get(0));
                resultante.add(rel);
                cont++;
                listC.remove(0);
                a = listA.size();
                c = listC.size();
            }
            if(listC.isEmpty()){
                break;
            }
        }
        //Con esto se formaron todas las relaciones posibles cuando listB quedó vacía primero
    } //Listo!
    
    private void caso3Resultante(){ 
        /*Crea la mayor cantidad de relaciones del tipo 2A y 1B ó 1A y 2B ó en su defecto 
        * (1A y 1B ó 2A ó 1A {las últimas 2 relaciones son causadas puesto que A >> B.
        * Por ahora no atañe si A << B, es decir termina el ciclo y no se forman relaciones} )*/
        while(!listA.isEmpty() || !listB.isEmpty()){
            //Relación del tipo 2A y 1B
            if(a >= 2 && b >= 1){
                rel = new ArrayList(3);
                rel.add(listA.get(0));
                rel.add(listA.get(1));
                rel.add(listB.get(0));
                resultante.add(rel);
                cont++;
                listA.remove(0);
                listA.remove(0);
                listB.remove(0);
                a = listA.size();
                b = listB.size();
            }
            //Relación del tipo 1A y 2B
            else if(a >= 1 && b >= 2){
                rel = new ArrayList(3);
                rel.add(listA.get(0));
                rel.add(listB.get(0));
                rel.add(listB.get(1));   
                resultante.add(rel);
                cont++;
                listA.remove(0);
                listB.remove(0);
                listB.remove(0);   
                a = listA.size();
                b = listB.size();
            }
            //Defecto 1A y 1B
            else if(a >= 1 && b >= 1){
                rel = new ArrayList(2);
                rel.add(listA.get(0));
                rel.add(listB.get(0));
                resultante.add(rel);
                cont++;
                listA.remove(0);
                listB.remove(0);
                a = listA.size();
                b = listB.size();
            }
            else if (a >= 1 && b < 1){
                //Defecto 2A {A >> B}
                while(a >= 2){
                    rel = new ArrayList(2);
                    rel.add(listA.get(0));
                    rel.add(listA.get(1));
                    resultante.add(rel);
                    cont++;
                    listA.remove(0);
                    listA.remove(0);
                    a = listA.size();
                    b = listB.size();
                }
                //Defecto 1A {A >> B}
                if(a == 1){
                    rel = new ArrayList();
                    rel.add(listA.get(0));
                    resultante.add(rel);
                    cont++;
                    listA.remove(0);
                    a = listA.size();
                    b = listB.size();
                }
            }
            if(listA.isEmpty() || listB.isEmpty()){
                break;
            }
        }//fin while
                
                /*En este punto se está seguro de que listA está vacía; 
                 * Ahora, es menester determinar qué hacer con el resto de B,
                 es decir que en este punto se toma en cuenta el caso A << B y se forman relaciones con 
                 los elementos restantes de listB*/
                
                /*Ahora, meramente se formarán relaciones del tipo 2B ó 1B, tomando en cuenta si existe un 
                 remanente en listB luego de ejecutarse el código anterior*/
        while(!listB.isEmpty()){
            if(b >= 2){
                rel = new ArrayList(2);
                rel.add(listB.get(0));
                rel.add(listB.get(1));
                resultante.add(rel);
                cont++;
                listB.remove(0);
                listB.remove(0);
                a = listA.size();
                b = listB.size(); 
            }
            if(b == 1){
                rel = new ArrayList();
                rel.add(listB.get(0));
                resultante.add(rel);
                cont++;
                listB.remove(0);
                a = listA.size();
                b = listB.size();
            }
            if(listB.isEmpty()){
                break;
            }
        }
        //Con esto se formaron todas las relaciones posibles cuando listC quedó vacía primero
    } //Listo!
    
    public ArrayList resultante(){
        resultante = null;
        //Paso 1: Llenar las listas según prioridad.
        if(!llenarABC()){  
            /*Paso 2: rel={(x,y,z) ┤|(x∈A∩y∈B∩z∈C)∪⌈(x∩y∈B)∩z∈C⌉∪[x∈B∩(y∩z∈C)]}
            resultante= {〖re〗_1  ,〖re〗_2  ,…,〖re〗_24 }*/
            resultante = new ArrayList(24);
            cont = 0;
            //ArrayList <Maquina> rel;
            
            //Primero hacer la mayor cantidad de relaciones de manera que tome un elemento de cada lista de prioridades 
            while(!listA.isEmpty() || !listB.isEmpty() || !listC.isEmpty()){
                rel = new ArrayList(3);
                rel.add(listA.get(0));
                rel.add(listB.get(0));
                rel.add(listC.get(0));
                resultante.add(rel);
                cont++;
                listA.remove(0);
                listB.remove(0);
                listC.remove(0);
                if(listA.isEmpty() || listB.isEmpty() || listC.isEmpty()){ //No debería hacer esta validación, pero por alguna razón sale error sin ella :S
                    break;
                }
            }
            //En este punto existe al menos 1 lista que está vacía; De allí se parte para averiguar qué lista está vacía y poder
            //realizar los otros tipos de relaciones
            a = listA.size();
            b = listB.size();
            c = listC.size();
            
            //ASUMIENDO QUE listA ESTÁ VACÍA
            if(a == 0 && b > 0 && c > 0){
                caso1Resultante();
                System.out.println("Caso 1");
            }
            //Ahora, se repite el proceso solo que asumiendo que listB se vacío primero
            else if(a > 0 && b == 0 && c > 0){
                caso2Resultante();
                System.out.println("Caso 2");
            }
            //Por ultimo se hace el caso en que listC se vacio primero
            else if(a > 0 && b > 0 && c == 0){
                caso3Resultante();
                System.out.println("Caso 3");
            }
            
            else{
                //En este caso se combinaron todas las listas de forma A,B,C
                if(a == 0 && b == 0 && c == 0){
                    System.out.println("Todas las combinaciones son de tipo A,B,C");
                }
                else{
                    System.out.println("Se produjo un error inesperado en los casos de las combinaciones");
                }
                
            }
                
            System.out.println(listA.size() + " " + listB.size() + " " + listC.size());
       
        }//Fin del if (!llenarABC())
        else{
            System.out.println("No hay ninguna máquina!!!");
        }
        //Paso 3: Enjoy!
        return resultante;
    } //Listo!
    
}
