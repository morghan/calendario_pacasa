/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mi.Calendario;

/**
 *
 * @author r.marin
 */
public class Maquina {
    private String codigo, nombre, prioridad;
    
    public Maquina(){
        codigo = null;
        nombre = null;
        prioridad = null;       
    }
    public Maquina(String c, String n, String p){
        codigo = c;
        nombre = n;
        prioridad = p;
    }
    public void setNombre(String n){
        nombre = n;
    }
    public void setPrioridad(String p){
        prioridad = p;
    }
    public void setCodigo(String c){
        codigo = c;
    }
    public void setTodo(String codigo, String nombre, String prioridad){
        this.codigo = codigo;
        this.nombre = nombre;
        this.prioridad = prioridad;     
    }
    public String getNombre(){
        return nombre;
    }
    public String getPrioridad(){
        return prioridad;
    }
    public String getCodigo(){
        return codigo;
    }
}