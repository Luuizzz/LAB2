/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lab2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author juluc
 */



public class FrameCryptoBreaker extends javax.swing.JFrame {
    
    int pistas = 0;
    int palabrasAdivinadas = 0;
    int tiempo=0;    
    int p=0;
    int puntaje = 0;
    boolean dif = false;
    boolean comprobante = true;
    javax.swing.Timer timerPregunta;
    Random r = new Random();
    int tamaño = 0;
    int []v;
    String []palabra;
    String[] listaCifrada;
    public FrameCryptoBreaker() {
        
        initComponents();
        this.setLocationRelativeTo(null);
        aplicarEstilos();
        
        timerPregunta = new Timer(1000, new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {

            if (tiempo <= 0) {
                timerPregunta.stop(); 
                JOptionPane.showMessageDialog(null, "Perdiste, se te acabo el tiempo.\n" + "Te faltaron " + (tamaño - palabrasAdivinadas) + " palabras.\n");
                TxtIntento.setEnabled(false);
                BttnComprobar.setEnabled(false);
                guardarresultados();
            } else {
                tiempo--; 
                LabelTiempo.setText(String.valueOf(tiempo));
            }
        }
    });
    }
    private void aplicarEstilos() {
    java.awt.Color fondo = new java.awt.Color(10, 35, 70);
    java.awt.Color colorItems = new java.awt.Color(18, 40, 65);
    java.awt.Color colorTexto = new java.awt.Color(220, 230, 240);
    java.awt.Color azulBtn = new java.awt.Color(40, 120, 220);
    java.awt.Color verdeBtn = new java.awt.Color(24, 160, 100);
    java.awt.Color naranjaBtn = new java.awt.Color(245, 130, 35);
    java.awt.Color rojoBtn = new java.awt.Color(200, 40, 40);
    getContentPane().setBackground(fondo);
    java.awt.Font labelFont = new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14);
    java.awt.Font indicadorFont = new java.awt.Font("SansSerif", java.awt.Font.BOLD, 16);
    

    if (jLabel1 != null) { 
        jLabel1.setFont(labelFont);
        jLabel1.setForeground(colorTexto);
    }
    if (jLabel2 != null) { 
        jLabel2.setFont(labelFont);
        jLabel2.setForeground(colorTexto);
    }
    if (jLabel3 != null) { 
        jLabel3.setFont(labelFont);
        jLabel3.setForeground(colorTexto);
    }
    if (jLabel4 != null) { 
    jLabel4.setFont(labelFont);
    jLabel4.setForeground(colorTexto);
    }

 
    if (LabelDif != null) {
        LabelDif.setFont(indicadorFont);
        LabelDif.setForeground(java.awt.Color.WHITE);
    }
    if (LabelTiempo != null) {
        LabelTiempo.setFont(indicadorFont);
        LabelTiempo.setForeground(java.awt.Color.WHITE);
    }
    if (LabelPuntaje != null) {
        LabelPuntaje.setFont(indicadorFont);
        LabelPuntaje.setForeground(java.awt.Color.WHITE);
    }

    java.awt.Font botonFont = new java.awt.Font("SansSerif", java.awt.Font.BOLD, 13);

    if (BttnIniciar != null) {
        BttnIniciar.setBackground(azulBtn);
        BttnIniciar.setForeground(java.awt.Color.WHITE);
        BttnIniciar.setOpaque(true);
        BttnIniciar.setBorder(javax.swing.BorderFactory.createLineBorder(azulBtn.darker()));
        BttnIniciar.setFont(botonFont);
    }
    if (BttnComprobar != null) {
        BttnComprobar.setBackground(verdeBtn);
        BttnComprobar.setForeground(java.awt.Color.WHITE);
        BttnComprobar.setOpaque(true);
        BttnComprobar.setBorder(javax.swing.BorderFactory.createLineBorder(verdeBtn.darker()));
        BttnComprobar.setFont(botonFont);
    }
    
    if (BttnReiniciar != null) {
        BttnReiniciar.setBackground(naranjaBtn);
        BttnReiniciar.setForeground(java.awt.Color.WHITE);
        BttnReiniciar.setOpaque(true);
        BttnReiniciar.setBorder(javax.swing.BorderFactory.createLineBorder(naranjaBtn.darker()));
        BttnReiniciar.setFont(botonFont);
    }
     if (BttnSalir != null) {
        BttnSalir.setBackground(rojoBtn);
        BttnSalir.setForeground(java.awt.Color.WHITE);
        BttnSalir.setOpaque(true);
        BttnSalir.setBorder(javax.swing.BorderFactory.createLineBorder(rojoBtn.darker()));
        BttnSalir.setFont(botonFont);
    }
    if (BttnPista != null) { 
        BttnPista.setBackground(azulBtn);
        BttnPista.setForeground(java.awt.Color.WHITE);
        BttnPista.setOpaque(true);
        BttnPista.setBorder(javax.swing.BorderFactory.createLineBorder(azulBtn.darker()));
        BttnPista.setFont(botonFont);
    }
    
    if (LstPal != null) {
        LstPal.setBackground(colorItems);
        LstPal.setForeground(colorTexto);
        LstPal.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 14));
        LstPal.setSelectionBackground(azulBtn); 
        LstPal.setSelectionForeground(java.awt.Color.WHITE);
    }
    if (jScrollPane1 != null) { 
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(azulBtn.darker()));
    }

    if (TxtIntento != null) {
        TxtIntento.setBackground(colorItems);
        TxtIntento.setForeground(java.awt.Color.WHITE); 
        TxtIntento.setCaretColor(java.awt.Color.WHITE); 
        TxtIntento.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
        TxtIntento.setBorder(javax.swing.BorderFactory.createLineBorder(azulBtn.darker()));
        TxtIntento.setHorizontalAlignment(javax.swing.JTextField.CENTER); 
    }
}
    public String[] encriptar(String[] listaPalabras, int clave) {
        listaCifrada = new String[listaPalabras.length];
    for (int i = 0; i < listaPalabras.length; i++) {   
        String pal = listaPalabras[i]; 
        char[] m = pal.toCharArray();        
        char[] cifrado = new char[m.length];       
        for (int j = 0; j < m.length; j++) {           
            char c = m[j];
            if (Character.isLetter(c)) {
                char CC = (char) (c + clave);                
                if (CC > 'Z') {
                    CC = (char) (CC - 26);
                }
                cifrado[j] = CC;
                
            } else {
                cifrado[j] = c; 
            }
        }
        String palCifrada = new String(cifrado);
        listaCifrada[i] = palCifrada;
    }

    return listaCifrada;
}

        public String ObtenerEvento(int n){
        switch (n) {
            case 1: return "AMENAZA";
            case 2: return "ANALIZAR";
            case 3: return "ATAQUE";
            case 4: return "BLOQUEAR";
            case 5: return "BOTNET";
            case 6: return "CIFRADO";
            case 7: return "CODIGO";
            case 8: return "CORREO";
            case 9: return "VECTOR";
            case 10: return "CLAVE";
            case 11: return "DATOS";
            case 12: return "DEFENSA";
            case 13: return "ENCRIPTAR";
            case 14: return "ENGANO";
            case 15: return "EVENTO";
            case 16: return "FIREWALL";
            case 17: return "FORENSE";
            case 18: return "HASH";
            case 19: return "INTRUSO";
            case 20: return "ALGORITMO";
            case 21: return "MALWARE";
            case 22: return "MATRIZ";
            case 23: return "PHISHING";
            case 24: return "OPTIMIZACION";
            case 25: return "PUERTO";
            case 26: return "RED";
            case 27: return "RIESGO";
            case 28: return "TROYANO";
            case 29: return "USUARIO";
            case 30: return "VIRUS";
            default:
                throw new AssertionError(); 
        }
    }
        
    public void CrearLista(){
        v = new int[tamaño];
        palabra = new String[tamaño];
        
        for (int i = 0; i < tamaño; i++) {
            int num = r.nextInt(30) + 1;
            boolean repetido = false;
            
            for (int j = 0; j < i; j++) {
                if(v[j] == num){
                    repetido = true;
                    break;
                }
            }
            
            if(repetido){
                i--;
            }else{
                v[i] = num;
                palabra[i] = (ObtenerEvento(num));
            }
            
        }
        
        LstPal.setListData(encriptar(palabra,p));
    }
    
    public void mostrarPista() {
 
    switch (pistas) {
        case 0:
            JOptionPane.showMessageDialog(null, "Pista 1/3\n" +"Pista: Todas las letras se están rodando " + p + " posiciones a la derecha.");
            break;
        
        case 1: 
        case 2:
            int numAleatorio1 = r.nextInt(26); 
            char o = (char) ('A' + numAleatorio1);
            
            char c = (char) (o + p);
            if (c > 'Z') {
                c = (char) (c - 26);
            }
            
            JOptionPane.showMessageDialog(null, "Pista "+ (pistas+1) +"/3\n" +"Ejemplo de letra: La '" + o + "' se convierte en '" + c + ".");
            break;
            
    }
    }
    public void guardarresultados(){
    int decision = javax.swing.JOptionPane.showConfirmDialog(this, 
            "¿Deseas guardar tus resultados en un archivo de texto?", 
            "Guardar Resultados", 
            javax.swing.JOptionPane.YES_NO_OPTION);
            
    if (decision != javax.swing.JOptionPane.YES_OPTION) {
        return; 
    }
    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
    chooser.setDialogTitle("Guardar resultados");
    chooser.setSelectedFile(new java.io.File("Resultados_CryptoBreaker.txt"));    
    int sel = chooser.showSaveDialog(this);
    if (sel != javax.swing.JFileChooser.APPROVE_OPTION) {
        return;
    }
    java.io.File f = chooser.getSelectedFile();
    try {
        java.io.BufferedWriter w = new java.io.BufferedWriter(new java.io.FileWriter(f));
        
        w.write("CryptoBreaker - Resultados\n");
        w.write("-----------------------------\n");
        w.write("Dificultad: " + LabelDif.getText() + "\n");
        w.write("Puntaje Final: " + puntaje + "\n");
        w.write("Palabras descifradas: " + palabrasAdivinadas + "\n");
        w.write("Total de palabras: " + tamaño + "\n");
        
        double porcentaje = (tamaño == 0) ? 0 : (100.0 * palabrasAdivinadas / tamaño);
        w.write(String.format("Porcentaje de acierto: %.2f%%\n", porcentaje));
        w.flush();
        w.close();
        javax.swing.JOptionPane.showMessageDialog(this, "Resultados guardados en: " + f.getAbsolutePath());
    } catch (java.io.IOException ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error guardando archivo: " + ex.getMessage());
    }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        LstPal = new javax.swing.JList<>();
        BttnIniciar = new javax.swing.JButton();
        BttnSalir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        BttnReiniciar = new javax.swing.JButton();
        TxtIntento = new javax.swing.JTextField();
        BttnComprobar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        LabelDif = new javax.swing.JLabel();
        LabelTiempo = new javax.swing.JLabel();
        LabelPuntaje = new javax.swing.JLabel();
        BttnPista = new javax.swing.JButton();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("CryptoBreaker");

        LstPal.setAutoscrolls(false);
        jScrollPane1.setViewportView(LstPal);

        BttnIniciar.setText("Iniciar");
        BttnIniciar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BttnIniciarMouseClicked(evt);
            }
        });

        BttnSalir.setText("Salir");
        BttnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BttnSalirMouseClicked(evt);
            }
        });

        jLabel2.setText("Tiempo:");

        jLabel3.setText("Puntaje:");

        BttnReiniciar.setText("Reiniciar");
        BttnReiniciar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BttnReiniciarMouseClicked(evt);
            }
        });

        TxtIntento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtIntentoActionPerformed(evt);
            }
        });

        BttnComprobar.setText("Comprobar");
        BttnComprobar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BttnComprobarMouseClicked(evt);
            }
        });

        jLabel4.setText("Dificultad:");

        LabelDif.setText("            ");

        LabelTiempo.setText("            ");

        LabelPuntaje.setText("            ");

        BttnPista.setText("Pista");
        BttnPista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BttnPistaMouseClicked(evt);
            }
        });
        BttnPista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BttnPistaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(3, 3, 3)
                                .addComponent(LabelDif)
                                .addGap(53, 53, 53)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelTiempo)
                                .addGap(60, 60, 60)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelPuntaje))
                            .addComponent(jLabel1))
                        .addContainerGap(152, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BttnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BttnReiniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(BttnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1)
                            .addComponent(TxtIntento))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BttnComprobar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BttnPista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(60, 60, 60))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(LabelDif)
                    .addComponent(LabelTiempo)
                    .addComponent(LabelPuntaje))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BttnPista)
                        .addGap(33, 33, 33)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BttnComprobar)
                    .addComponent(TxtIntento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BttnReiniciar)
                    .addComponent(BttnSalir)
                    .addComponent(BttnIniciar))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtIntentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtIntentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtIntentoActionPerformed

    private void BttnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BttnSalirMouseClicked
        this.setVisible(false);
        FramePrincipal frame = new FramePrincipal();
        frame.setVisible(true);
        
    }//GEN-LAST:event_BttnSalirMouseClicked

    private void BttnIniciarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BttnIniciarMouseClicked
        if(dif!= true){
            comprobante = false;
            String mensaje = "Elige una dificultad:\n1. Fácil\n2. Medio\n3. Difícil";
            String opcion = JOptionPane.showInputDialog(null, mensaje, "Dificultad", JOptionPane.QUESTION_MESSAGE);

            if (opcion != null) {
                if (opcion.equals("1")) {
                    JOptionPane.showMessageDialog(null, "Seleccionaste: Fácil");    
                    dif = true;
                    LabelDif.setText("Fácil");
                    tamaño = 10;
                    p=1;
                    tiempo=90;
                    CrearLista();
                    LabelTiempo.setText(String.valueOf(tiempo)); 
                    timerPregunta.start(); 
                    palabrasAdivinadas = 0;
                    pistas = 0;


                                       
                } else if (opcion.equals("2")) {
                    JOptionPane.showMessageDialog(null, "Seleccionaste: Medio");
                    dif = true;
                    LabelDif.setText("Media");
                    tamaño = 20;
                    p=2;
                    tiempo=60;
                    CrearLista();
                    LabelTiempo.setText(String.valueOf(tiempo)); 
                    timerPregunta.start(); 
                    palabrasAdivinadas = 0;
                    pistas = 0;
 
                    
                } else if (opcion.equals("3")) {
                    JOptionPane.showMessageDialog(null, "Seleccionaste: Difícil");
                    dif = true;
                    LabelDif.setText("Difícil");
                    tamaño = 30;                   
                    p=r.nextInt(25) + 1;
                    tiempo=30;
                    CrearLista();
                    LabelTiempo.setText(String.valueOf(tiempo)); 
                    timerPregunta.start(); 
                    palabrasAdivinadas = 0;
                    pistas = 0;

                    
                    

                } else {
                    JOptionPane.showMessageDialog(null, "Opción no válida, intenta de nuevo.");

                }
            } else {
                JOptionPane.showMessageDialog(null, "No seleccionaste ninguna dificultad.");

                
            }
            
            
            
        }else{
            JOptionPane.showMessageDialog(null, "Ya has seleccionado una dificultad. Dale a Reiniciar para seleccionar otra.");

        }
        
        


        
        
    }//GEN-LAST:event_BttnIniciarMouseClicked

    private void BttnReiniciarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BttnReiniciarMouseClicked
        if(comprobante == false){
            int confirmar2 = JOptionPane.showConfirmDialog(null, "De verdad deseas reiniciar?", "Confirmar reinicio", JOptionPane.YES_NO_OPTION);
            if(confirmar2 == JOptionPane.YES_OPTION){
                puntaje = 0;
                dif = false;
                v = null;
                palabra = null;
                tamaño = 0;
                LstPal.setListData(new String[0]);
                timerPregunta.stop();
                LabelTiempo.setText("");
                LabelPuntaje.setText("");
                LabelDif.setText("");
                TxtIntento.setText("");
                comprobante = true;
                palabrasAdivinadas = 0;
                pistas = 0;

                
            }
            
        }else{
            JOptionPane.showMessageDialog(null, "No hay un juego en proceso.");
        }
    }//GEN-LAST:event_BttnReiniciarMouseClicked

    private void BttnComprobarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BttnComprobarMouseClicked
       if (!timerPregunta.isRunning()) {
        JOptionPane.showMessageDialog(null, "Debes iniciar el juego.");
        return;
    }
    timerPregunta.stop();
    int fila = LstPal.getSelectedIndex();
    if (fila == -1) { 
        JOptionPane.showMessageDialog(null, "Debes seleccionar una palabra de la lista.");
        timerPregunta.start(); 
        return; 
    }
    
    String intento = TxtIntento.getText(); 
    
    if (palabra[fila] == null) {
        JOptionPane.showMessageDialog(null, "Ya has adivinado esa palabra.");
        timerPregunta.start(); 
        return; 
    }   
    if (intento.equalsIgnoreCase(palabra[fila])) {
        puntaje += tiempo; 
        LabelPuntaje.setText(String.valueOf(puntaje));
        palabra[fila] = null; 
        listaCifrada[fila] = ""; 
        LstPal.setListData(listaCifrada);
        palabrasAdivinadas++;
        TxtIntento.setText(""); 
        
        if (palabrasAdivinadas == tamaño) {
            timerPregunta.stop();
        JOptionPane.showMessageDialog(null, "¡FELICITACIONES!\n" + "Has descifrado todas las palabras.\n" + "Puntaje Final: " + puntaje);
        Main.puntajeCryptoBreaker+=puntaje;
        TxtIntento.setEnabled(false);
        BttnComprobar.setEnabled(false);
        guardarresultados();

        } else {
            
            JOptionPane.showMessageDialog(null, "¡Correcto!");
            if (LabelDif.getText().equals("Fácil")) tiempo = 90;
            else if (LabelDif.getText().equals("Media")) tiempo = 60;
            else if (LabelDif.getText().equals("Difícil")) tiempo = 30;
            LabelTiempo.setText(String.valueOf(tiempo));
            timerPregunta.start();
        }
        
    } else {
        
        puntaje -= 10;
        LabelPuntaje.setText(String.valueOf(puntaje));
        JOptionPane.showMessageDialog(null, "Incorrecto. Intenta de nuevo.");
        timerPregunta.start();
    }
    
    


    }//GEN-LAST:event_BttnComprobarMouseClicked

    private void BttnPistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BttnPistaActionPerformed
        if (!timerPregunta.isRunning()) {
            JOptionPane.showMessageDialog(null, "Debes iniciar el juego.");
            return;
            
        }
        timerPregunta.stop();
            if (pistas >= 3) {
        JOptionPane.showMessageDialog(null, "¡Ya has usado tus 3 pistas!");
        timerPregunta.start();
        return;
    }
            mostrarPista();
            pistas++;
            timerPregunta.start();  
    }//GEN-LAST:event_BttnPistaActionPerformed

    private void BttnPistaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BttnPistaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BttnPistaMouseClicked

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
            java.util.logging.Logger.getLogger(FrameCryptoBreaker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameCryptoBreaker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameCryptoBreaker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameCryptoBreaker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameCryptoBreaker().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BttnComprobar;
    private javax.swing.JButton BttnIniciar;
    private javax.swing.JButton BttnPista;
    private javax.swing.JButton BttnReiniciar;
    private javax.swing.JButton BttnSalir;
    private javax.swing.JLabel LabelDif;
    private javax.swing.JLabel LabelPuntaje;
    private javax.swing.JLabel LabelTiempo;
    private javax.swing.JList<String> LstPal;
    private javax.swing.JTextField TxtIntento;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
