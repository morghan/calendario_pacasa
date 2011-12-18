/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GestionMaquinas.java
 *
 * Created on 11-23-2011, 08:04:41 AM
 */
package mi.Calendario;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
/**
 *
 * @author r.marin
 */
public class GestionMaquinas extends javax.swing.JFrame{
    private EscribirLeerListas docs;
    private ArrayList <ArrayList> combinacion;
    
    //Inicio de componentes de calendario
    private JLabel lblMonth, lblYear;
    private JButton btnPrev, btnNext;   
    private JComboBox cmbYear;
    private JTable tblCalendar;
    private DefaultTableModel mtblCalendar; //Table model
    private JScrollPane stblCalendar;
    private int realDay, realMonth, realYear, currentMonth, currentYear;
    //Fin
     
    //Inicio de funciones y clases para calendario
    private class CustomRenderer extends DefaultTableCellRenderer{
        private int c, f;
        public CustomRenderer(int fil, int col){
            if(col >= 0 && col <= 6){
                c = col;
            }
            if(fil >= 0 && fil <= 7){
                f = fil;
            }
        }       
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if((column == c && c != 0) && (row == f && f != 0)){
                setBackground(new Color(220, 220, 255));
            }
            else if(column == 0 || column == 6 ){
                setBackground(new Color(255, 220, 220));
            }
            else{
                setBackground(new Color(255, 255, 255));
            }
        setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        setForeground(Color.black);
        return this;
        }
    }
    
    private void refreshCalendar(int mes, int año){
        String [] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre",
        "Noviembre","Diciembre"};
        
        int numeroDias, inicioMes; //Se necesita saber cuantos días tiene el mes y el día de la semana en que comienza
        
        btnPrev.setEnabled(true); //Habilitar botones de navegación
        btnNext.setEnabled(true);
        
        //Validar los límites inferior y superior de navegación de meses y años (En este ejemplo -10 años, +50 años
        //Los meses van de 0 = Enero a 11 = Diciembre
        if(mes == 0 && año <= realYear-10){ //Límite inferior
            btnPrev.setEnabled(false);
        }
        if(mes == 11 && año >= realYear+50){ //Límite superior
            btnNext.setEnabled(false);
        }
        
        lblMonth.setText(meses[mes]); //Actualizar el mes deseado
        lblMonth.setBounds(200-lblMonth.getPreferredSize().width/2, 25, 100, 25); //Realinear el label
        cmbYear.setSelectedItem(Integer.toString(año)); //Actualizar año deseado
        
        //Conseguir los parámetros de días para dibujar 
        GregorianCalendar cal = new GregorianCalendar(año, mes, 1); //Con fecha de año, mes y primer día del mes
        numeroDias = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); //Máximo de días con respecto al primer día del mes
        inicioMes = cal.get(GregorianCalendar.DAY_OF_WEEK); //Los días van de 1 = Domingo a 7 = Sábado; también con respecto al primer día del mes
        //System.out.println(numeroDias + " " + inicioMes);
        
        //Limpiar tabla 6X7
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
		mtblCalendar.setValueAt(null, i, j);
            }
        }
        //Dibujar calendario
        
        //Primero conseguir los datos resultantes
        combinacion = docs.resultante();
        int index = 0;
        /*System.out.println(combinacion.size());
        for(int i =0 ; i < combinacion.size(); i++){
            ArrayList <Maquina> temp = combinacion.get(i);
            for(int j = 0; j < temp.size(); j++){
                Maquina obj = temp.get(j);
                System.out.println(i + " | "+ obj.getCodigo() + " " + obj.getNombre()+ " " + obj.getPrioridad());
            }
        }*/
        String [] data = {"Hola","lkhj","dfgh"};
        int a1 = 0, a2 = 0;
        int col =inicioMes-1, fila=0, init=1;
        while(fila < 7 && init <= numeroDias){
            while(col <= 6 && init <= numeroDias){
                if(col == 0 || index >= combinacion.size()){
                    mtblCalendar.setValueAt("<html>"+ String.valueOf(init) +" <br /> </html>", fila, col);
                }
                else{
                    if(index < combinacion.size()){
                        ArrayList <Maquina> m = combinacion.get(index);
                        if(m.size() == 3){
                            mtblCalendar.setValueAt("<html>"+
                                    "<p>"+String.valueOf(init)+"</p>"+
                                    "<p>"+m.get(0).getNombre()+"</p>"+
                                    "<p>"+m.get(1).getNombre()+"</p>"+
                                    "<p>"+m.get(2).getNombre()+"</p>"+
                                    "</html>", fila, col);
                        }
                        if(m.size() == 2){
                            mtblCalendar.setValueAt("<html>"+
                                    "<p>"+String.valueOf(init)+"</p>"+
                                    "<p>"+m.get(0).getNombre()+"</p>"+
                                    "<p>"+m.get(1).getNombre()+"</p>"+
                                    "</html>", fila, col);

                        }
                        if(m.size() == 1){
                            mtblCalendar.setValueAt("<html>"+
                                    "<p>"+String.valueOf(init)+"</p>"+
                                    "<p>"+m.get(0).getNombre()+"</p>"+
                                    "</html>", fila, col);

                        }
                        index++;
                    }
                }
                if(init == realDay && currentMonth == realMonth && currentYear == realYear){
                    a1 = fila;
                    a2 = col;
                }
                col++;
                if(col <= 6){
                    init++;
                }
            }
            col=0;
            fila++;
            init++;
        }
        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new CustomRenderer(a1, a2));//Aplica el metodo de la clase de arriba
    }
    
    private class btnPrev_Action implements ActionListener{
        @Override
        public void actionPerformed (ActionEvent e){
            if(currentMonth == 0){ //Atrasar un año
                currentMonth = 11;
                currentYear--;
            }
            else{ //Un mes atras
                currentMonth--;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    
    private class btnNext_Action implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(currentMonth == 11){ //Adelantar un año
                currentMonth = 0;
                currentYear++;
            }
            else{ //Un mes después
                currentMonth++;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    
    private class cmbYear_Action implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(cmbYear.getSelectedItem() != null){
                currentYear = Integer.parseInt(String.valueOf(cmbYear.getSelectedItem()));
                refreshCalendar(currentMonth, currentYear);
            }
        }
    }
    
    private void posicionarCalendario(){
        Calendario.setBorder(BorderFactory.createTitledBorder("Calendario")); //Set border
        Calendario.add(lblMonth);
        Calendario.add(lblYear);
        Calendario.add(cmbYear);
        Calendario.add(btnPrev);
        Calendario.add(btnNext);
        Calendario.add(stblCalendar);
        
        //setBounds(x, y, width, height);
        //Calendario.setBounds(0, 0, 200, 300);
        lblMonth.setBounds(280 -(lblMonth.getPreferredSize().width)/2, 25, 100, 25);
        btnPrev.setBounds(10, 25, 50, 25);
        btnNext.setBounds(360, 25, 50, 25);
        lblYear.setBounds(360+btnNext.getWidth()+150, 25, 80, 25);
        cmbYear.setBounds(360+btnNext.getWidth()+150+lblYear.getWidth(), 25, 80, 25);
        stblCalendar.setBounds(10, 50, (Tabs.getWidth()-20), (Tabs.getHeight()-100));
    }

    private void getFechaActual(){
        //Crear Calendario Gregoriano y conseguir mes y año
        GregorianCalendar cal = new GregorianCalendar();
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
        realMonth = cal.get(GregorianCalendar.MONTH);
        realYear = cal.get(GregorianCalendar.YEAR);
        currentMonth = realMonth;
        currentYear = realYear;
        //System.out.println (realDay + " " + realMonth + " " + realYear);
    }
    
    private void prepararCalendario (){
        //Paso 1: Agregar títulos de los días
        String [] dias = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
        for (int i = 0; i < 7; i++){
            mtblCalendar.addColumn(dias[i]);
        }
        //Paso 2: Poner estilo a tabla
        //Poner color blanco en el fondo
        tblCalendar.getParent().setBackground(new Color(255, 255, 255)); //supuestamente es porque hay partes que no tienen celdas y esta area es el componente padre
        //Para que los headers queden estáticos
        tblCalendar.getTableHeader().setResizingAllowed(false);
        tblCalendar.getTableHeader().setReorderingAllowed(false);
        //Single cell selection
        tblCalendar.setColumnSelectionAllowed(true);
        tblCalendar.setRowSelectionAllowed(true);
        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // 6 semanas X 7 dias 
        tblCalendar.setRowHeight(90);
        mtblCalendar.setRowCount(6);
        mtblCalendar.setColumnCount(7);
        
        //Paso 3: Llenar el combo box de años basado en el año actual
        //Importante hacerlo despues de preparar la tabla para que no haya conflicto a la hora de cambiar de año, mes, etc.
        for(int i = realYear-10; i < realYear+50; i++){
            cmbYear.addItem(Integer.toString(i));
        }
        
        //Paso 4: Actualizar calendario con fecha actual
        refreshCalendar (realMonth, realYear);
        
        //Paso 5: Agregar Acciones a los botones y combo box
        //Agregar action listener a los botones
        //Register action listeners
        btnPrev.addActionListener(new btnPrev_Action());
        btnNext.addActionListener(new btnNext_Action());
        cmbYear.addActionListener(new cmbYear_Action());
    }
    //Fin
    
    private void fullScreen(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	this.setSize(dim.width, dim.height);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Calendario Mantenimiento Nivel 1");
        this.setLayout(null);
        //Centra las Tabs en la pantalla
        int panelX = (this.getWidth() - Tabs.getWidth() - getInsets().left - getInsets().right) / 2;
        int panelY = ((this.getHeight() - Tabs.getHeight() - getInsets().top - getInsets().bottom) / 2);
        int tituloX = (this.getWidth()/2 - titulo.getWidth()/2);
        Tabs.setLocation(panelX, panelY);
        titulo.setLocation(tituloX, 0);
    }

    /** Creates new form GestionMaquinas */
    public GestionMaquinas() {
        initComponents();
        combinacion = new ArrayList(24); 
        docs = new EscribirLeerListas();
        lblMonth = new JLabel ("Enero");
        lblYear = new JLabel ("Año:");
        cmbYear = new JComboBox();
        btnPrev = new JButton ("<<");
        btnNext = new JButton (">>");
        mtblCalendar = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex){return false;}}; //Para evitar edición de celdas
        tblCalendar = new JTable(mtblCalendar);
        stblCalendar = new JScrollPane(tblCalendar); 
        posicionarCalendario();
        getFechaActual();
        prepararCalendario();
        fullScreen();
    }
     
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titulo = new javax.swing.JLabel();
        Tabs = new javax.swing.JTabbedPane();
        AgrMaquina = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        agregar = new javax.swing.JButton();
        borrar = new javax.swing.JButton();
        cod = new javax.swing.JTextField();
        nom = new javax.swing.JTextField();
        prd = new javax.swing.JComboBox();
        salir = new javax.swing.JButton();
        logo = new javax.swing.JLabel();
        BusqMaquina = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        codBuscar = new javax.swing.JTextField();
        nomResult = new javax.swing.JTextField();
        prdResult = new javax.swing.JTextField();
        salirBusq = new javax.swing.JButton();
        buscar = new javax.swing.JButton();
        eliminar = new javax.swing.JButton();
        editar = new javax.swing.JButton();
        logo1 = new javax.swing.JLabel();
        Calendario = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        titulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        titulo.setText("Gestion Máquinas Matenimiento Nivel 1");

        Tabs.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        AgrMaquina.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel2.setText("Código de máquina");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel3.setText("Nombre de máquina");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel4.setText("Prioridad");

        agregar.setFont(new java.awt.Font("Tahoma", 1, 11));
        agregar.setText("Agregar");
        agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarActionPerformed(evt);
            }
        });

        borrar.setFont(new java.awt.Font("Tahoma", 1, 11));
        borrar.setText("Borrar");
        borrar.setMaximumSize(new java.awt.Dimension(79, 23));
        borrar.setMinimumSize(new java.awt.Dimension(79, 23));
        borrar.setPreferredSize(new java.awt.Dimension(79, 23));
        borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarActionPerformed(evt);
            }
        });

        prd.setFont(new java.awt.Font("Tahoma", 1, 11));
        prd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B", "C" }));
        prd.setMinimumSize(new java.awt.Dimension(23, 18));
        prd.setPreferredSize(new java.awt.Dimension(28, 20));

        salir.setFont(new java.awt.Font("Tahoma", 1, 11));
        salir.setText("Salir y Actualizar");
        salir.setMaximumSize(new java.awt.Dimension(79, 23));
        salir.setMinimumSize(new java.awt.Dimension(79, 23));
        salir.setPreferredSize(new java.awt.Dimension(79, 23));
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        String path = "bk/logo.jpg";
        logo.setIcon(new ImageIcon(path));

        javax.swing.GroupLayout AgrMaquinaLayout = new javax.swing.GroupLayout(AgrMaquina);
        AgrMaquina.setLayout(AgrMaquinaLayout);
        AgrMaquinaLayout.setHorizontalGroup(
            AgrMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgrMaquinaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AgrMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AgrMaquinaLayout.createSequentialGroup()
                        .addGroup(AgrMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(35, 35, 35)
                        .addGroup(AgrMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cod)
                            .addComponent(nom)
                            .addComponent(prd, 0, 134, Short.MAX_VALUE)))
                    .addGroup(AgrMaquinaLayout.createSequentialGroup()
                        .addComponent(agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(salir, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        AgrMaquinaLayout.setVerticalGroup(
            AgrMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgrMaquinaLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(AgrMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(AgrMaquinaLayout.createSequentialGroup()
                        .addGroup(AgrMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(AgrMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(nom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(AgrMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(prd, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(AgrMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(agregar)
                            .addComponent(borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(salir, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(267, 267, 267))
        );

        Tabs.addTab("Agregar Máquina", AgrMaquina);

        BusqMaquina.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel6.setText("Código a buscar");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel7.setText("Nombre de máquina");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel8.setText("Prioridad de máquina");

        nomResult.setEditable(false);

        prdResult.setEditable(false);

        salirBusq.setFont(new java.awt.Font("Tahoma", 1, 11));
        salirBusq.setText("Salir");
        salirBusq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirBusqActionPerformed(evt);
            }
        });

        buscar.setFont(new java.awt.Font("Tahoma", 1, 11));
        buscar.setText("Buscar");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        eliminar.setFont(new java.awt.Font("Tahoma", 1, 11));
        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        editar.setFont(new java.awt.Font("Tahoma", 1, 11));
        editar.setText("Editar");
        editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarActionPerformed(evt);
            }
        });

        logo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logo1.setIcon(new ImageIcon(path));

        javax.swing.GroupLayout BusqMaquinaLayout = new javax.swing.GroupLayout(BusqMaquina);
        BusqMaquina.setLayout(BusqMaquinaLayout);
        BusqMaquinaLayout.setHorizontalGroup(
            BusqMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BusqMaquinaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BusqMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BusqMaquinaLayout.createSequentialGroup()
                        .addGroup(BusqMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(25, 25, 25)
                        .addGroup(BusqMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(prdResult, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                            .addComponent(nomResult)
                            .addComponent(codBuscar))
                        .addGap(76, 76, 76))
                    .addGroup(BusqMaquinaLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                        .addGap(276, 276, 276))
                    .addGroup(BusqMaquinaLayout.createSequentialGroup()
                        .addComponent(buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(editar, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(salirBusq, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logo1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        BusqMaquinaLayout.setVerticalGroup(
            BusqMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BusqMaquinaLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(BusqMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logo1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(BusqMaquinaLayout.createSequentialGroup()
                        .addGroup(BusqMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(codBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(BusqMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(nomResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(BusqMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(prdResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(BusqMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(salirBusq))))
                .addContainerGap(103, Short.MAX_VALUE))
        );

        Tabs.addTab("Buscar Máquina", BusqMaquina);

        javax.swing.GroupLayout CalendarioLayout = new javax.swing.GroupLayout(Calendario);
        Calendario.setLayout(CalendarioLayout);
        CalendarioLayout.setHorizontalGroup(
            CalendarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 867, Short.MAX_VALUE)
        );
        CalendarioLayout.setVerticalGroup(
            CalendarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );

        Tabs.addTab("Calendario", Calendario);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(272, Short.MAX_VALUE)
                .addComponent(titulo)
                .addGap(352, 352, 352))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 877, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarActionPerformed
        String codTemp = cod.getText();
        String nomTemp = nom.getText();
        String prTemp =  (String) prd.getSelectedItem();
        Maquina elemento = new Maquina();
        
        if(nomTemp.equals("") || codTemp.equals("")){
            JOptionPane.showMessageDialog(null, "Error: Nombre o Código en blanco", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            elemento.setTodo(codTemp, nomTemp, prTemp);
            if(!docs.revisarDuplicado(elemento)){
                if (docs.agregarMaquina(elemento)){
                    JOptionPane.showMessageDialog(null, "Éxito: Máquina Agregada", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Error al agregar máquina", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else{
               JOptionPane.showMessageDialog(null, "Código Duplicado: La máquina ya existe", "Mensaje", JOptionPane.INFORMATION_MESSAGE); 
            }
        }
    }//GEN-LAST:event_agregarActionPerformed

    private void borrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarActionPerformed
        nom.setText("");
        cod.setText("");
    }//GEN-LAST:event_borrarActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        if(docs.escribirLista()){
            JOptionPane.showMessageDialog(null, "Escritura de máquinas completada!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "La escritura de máquinas falló!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
        System.exit(0);
    }//GEN-LAST:event_salirActionPerformed

    private void salirBusqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirBusqActionPerformed
        if(docs.escribirLista()){
            JOptionPane.showMessageDialog(null, "Escritura de máquinas completada!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "La escritura de máquinas falló!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
        System.exit(0);
    }//GEN-LAST:event_salirBusqActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(docs.escribirLista()){
            JOptionPane.showMessageDialog(null, "Escritura de máquinas completada!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "La escritura de máquinas falló!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_formWindowClosing

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        String codTemp = codBuscar.getText();
        if(codTemp.equals("")){
            JOptionPane.showMessageDialog(null, "Error: Código en blanco", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            Maquina buscada = docs.buscarMaquina(codTemp);
            if(buscada != null){
                JOptionPane.showMessageDialog(null, "¡Máquina encontrada!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                nomResult.setText(buscada.getNombre());
                prdResult.setText(buscada.getPrioridad());
            }
            else{
                JOptionPane.showMessageDialog(null, "Código incorrecto, tal máquina no existe.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                nomResult.setText("");
                prdResult.setText("");
            }
        }
    }//GEN-LAST:event_buscarActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        String codTemp = codBuscar.getText();
        if(codTemp.equals("")){
            JOptionPane.showMessageDialog(null, "Error: Código en blanco", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            Maquina buscada = docs.buscarMaquina(codTemp);
            if(buscada != null){
                nomResult.setText(buscada.getNombre());
                prdResult.setText(buscada.getPrioridad());
                int op = JOptionPane.showConfirmDialog(null, "Desea eliminar máquina con código: " + codTemp + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if(op != JOptionPane.CLOSED_OPTION){
                    if(op == JOptionPane.YES_OPTION){
                        if(docs.eliminarMaquina(buscada)){
                            JOptionPane.showMessageDialog(null, "Máquina eliminada con éxito", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Error: No se pudo eliminar máquina", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    else if (op == JOptionPane.NO_OPTION){
                        JOptionPane.showMessageDialog(null, "Operación Cancelada", "Mensaje", JOptionPane.INFORMATION_MESSAGE); 
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Código incorrecto, tal máquina no existe.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                nomResult.setText("");
                prdResult.setText("");
            }
        }
    }//GEN-LAST:event_eliminarActionPerformed

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarActionPerformed
        String codTemp = codBuscar.getText();
        if(codTemp.equals("")){
            JOptionPane.showMessageDialog(null, "Error: Código en blanco", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            Maquina buscada = docs.buscarMaquina(codTemp);
            if(buscada != null){
                nomResult.setText(buscada.getNombre());
                prdResult.setText(buscada.getPrioridad());
                int op = JOptionPane.showConfirmDialog(null, "Desea editar máquina con código: " + codTemp + " ?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if(op != JOptionPane.CLOSED_OPTION){
                    if(op == JOptionPane.YES_OPTION){
                        String codIn =null, nomIn =null, prIn = null;
                        String [] prs = {"","A", "B", "C"};
                        boolean c = false;
                        do{
                            c = false;
                            codIn = JOptionPane.showInputDialog(null, "Ingrese Código nuevo:", "Editando código", JOptionPane.OK_CANCEL_OPTION);
                            if(!codIn.equals("")){
                                if(!codIn.equals(buscada.getCodigo())){
                                    if(c = docs.revisarDuplicado(codIn)){
                                        JOptionPane.showMessageDialog(null, "Código Duplicado: La máquina ya existe", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }
                            }
                        }while(codIn.equals("") || c == true);
                        
                        do{
                            nomIn = JOptionPane.showInputDialog(null, "Ingrese nombre nuevo:", "Editando nombre", JOptionPane.OK_CANCEL_OPTION);
                        }while(nomIn.equals(""));
                        
                        do{
                            prIn = (String) JOptionPane.showInputDialog(null, "Ingrese prioridad nueva:", "Editando prioridad", JOptionPane.OK_CANCEL_OPTION, null, prs, prs[0]);
                        }while(prIn.equals(""));
                        
                            String [] params = {codIn, nomIn, prIn}; // En este punto todos los parámetros estan validados (no están en blanco y código no duplicado)
                            if(docs.editarMaquina(buscada, params)){
                                JOptionPane.showMessageDialog(null, "Éxito: Máquina Editada", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Error al editar máquina", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                            }
                    }
                    else if (op == JOptionPane.NO_OPTION){
                        JOptionPane.showMessageDialog(null, "Operación Cancelada", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Código incorrecto, tal máquina no existe.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                nomResult.setText("");
                prdResult.setText("");
            }
        }
    }//GEN-LAST:event_editarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestionMaquinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionMaquinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionMaquinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionMaquinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new GestionMaquinas().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AgrMaquina;
    private javax.swing.JPanel BusqMaquina;
    private javax.swing.JPanel Calendario;
    private javax.swing.JTabbedPane Tabs;
    private javax.swing.JButton agregar;
    private javax.swing.JButton borrar;
    private javax.swing.JButton buscar;
    private javax.swing.JTextField cod;
    private javax.swing.JTextField codBuscar;
    private javax.swing.JButton editar;
    private javax.swing.JButton eliminar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel logo1;
    private javax.swing.JTextField nom;
    private javax.swing.JTextField nomResult;
    private javax.swing.JComboBox prd;
    private javax.swing.JTextField prdResult;
    private javax.swing.JButton salir;
    private javax.swing.JButton salirBusq;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
