package lab2;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


/**
 *
 * @author juluc
 */
    public class FrameSec extends javax.swing.JFrame {
private void actualizarClasificacionesDesdeTabla() {
    if (modeloEventos == null) return;

    for (int i = 0; i < modeloEventos.getRowCount(); i++) {
        Object valor = modeloEventos.getValueAt(i, 3);
        String categoria = (valor == null) ? "" : valor.toString();
        registrarClasificacionJugador(i, categoria);
    }
}

private void llenarTablaConEventos() {
    modeloEventos.setRowCount(0);

    if (eventos == null) return;

    for (int i = 0; i < eventos.length; i++) {

        // 🔥 Limpia etiquetas tipo [INFO], [CRITICO], etc.
        String eventoVisible = eventos[i].replaceAll("\\[.*?\\]\\s*", "");

        modeloEventos.addRow(new Object[]{
                i + 1,
                eventoVisible,           // 👈 ahora sin etiquetas
                clasificacionSistema[i],
                ""
        });
    }
}



     public FrameSec() {
        initComponents();
        this.setLocationRelativeTo(null);
        personalizarComponentes();
            this.getContentPane().setBackground(new Color(0x0A0F1D));
            configurarTabla();
    
    estilizarBoton(btnload);
    estilizarBoton(btnSave);
    estilizarBotonRojo(Resumen);
    estilizarBotonRojo(btnrestart);
    estilizarBotonRojo(btnsalir);
    estilizarTabla(tabla);

    panel.setBorder(BorderFactory.createLineBorder(new Color(0x00EAFF), 2, true));
    tabla.setBorder(BorderFactory.createLineBorder(new Color(0x0080FF), 2, true));
    }
    
    
    private DefaultTableModel modeloEventos;

    
    Color azulFondo = new Color(0x0A0F1D);
    Color cyanNeon = new Color(0x00EAFF);
    Color rojoNeon = new Color(0xFF3040);
    Color azulClaro = new Color(0x0080FF);
    Color textoBlanco = new Color(0xE5E5E5);

    private void personalizarComponentes() {
    this.getContentPane().setBackground(new Color(0x0A0F1D));
    this.setBackground(new Color(0x0A0F1D)); // si tienes un panel base
}
    private void estilizarBoton(JButton btn) {
    btn.setBackground(new Color(0x0A0F1D));
    btn.setForeground(new Color(0x00EAFF));
    btn.setBorder(new javax.swing.border.LineBorder(new Color(0x00EAFF), 2, true));
    btn.setFocusPainted(false);
    btn.setContentAreaFilled(false);
}
    private void estilizarBotonRojo(JButton btn) {
    btn.setBackground(new Color(0x0A0F1D));
    btn.setForeground(new Color(0xFF3040));
    btn.setBorder(new javax.swing.border.LineBorder(new Color(0xFF3040), 2, true));
    btn.setFocusPainted(false);
    btn.setContentAreaFilled(false);
}
    private void estilizarTabla(JTable tabla) {

    tabla.setBackground(new Color(0x0A0F1D));
    tabla.setForeground(new Color(0xE5E5E5));
    tabla.setGridColor(new Color(0x0080FF));
    tabla.setSelectionBackground(new Color(0x002244)); 
    tabla.setSelectionForeground(Color.WHITE);

    tabla.getTableHeader().setBackground(new Color(0x0A0F1D));
    tabla.getTableHeader().setForeground(new Color(0x00EAFF));
    tabla.getTableHeader().setBorder(
        BorderFactory.createLineBorder(new Color(0x00EAFF))
    );
}
    private void configurarTabla() {
    modeloEventos = new DefaultTableModel(
            new Object[]{"#", "Evento", "Clasificación sistema", "Tu clasificación"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3; // solo la del jugador editable
        }
    };

    tabla.setModel(modeloEventos);

    //   Ocultarcolumna 2) 
    tabla.getColumnModel().getColumn(2).setMinWidth(0);
    tabla.getColumnModel().getColumn(2).setMaxWidth(0);
    tabla.getColumnModel().getColumn(2).setPreferredWidth(0);

    
    JComboBox<String> combo = new JComboBox<>();
    combo.addItem("Normal");
    combo.addItem("Sospechoso");
    combo.addItem("Crítico");

    tabla.getColumnModel()
            .getColumn(3)
            .setCellEditor(new DefaultCellEditor(combo));
}
         private String[] eventos;
    private String[] clasificacionSistema;
    private String[] clasificacionJugador;

    private int totalEventos;
    private int puntajeUltimoResumen;


    public void cargarLogsConSelector(Component parent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecciona el archivo de logs");

        int res = chooser.showOpenDialog(parent);
        if (res == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            cargarLogsDesdeArchivo(archivo);  
        }
    }

 //txt con BufferedReader
    public void cargarLogsDesdeArchivo(File archivo) {
        if (archivo == null || !archivo.exists()) {
            mostrarMensaje("El archivo no existe.", "Error");
            return;
        }

        List<String> listaEventos = new ArrayList<>();
        List<String> listaClasSis = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                listaEventos.add(linea);
                listaClasSis.add(clasificarEvento(linea));   
            }
        } catch (IOException e) {
            mostrarMensaje("Error al leer el archivo: " + e.getMessage(), "Error");
            return;
        }

        if (listaEventos.isEmpty()) {
            mostrarMensaje("El archivo no contiene eventos válidos.", "Aviso");
            return;
        }

        totalEventos = listaEventos.size();
        eventos = new String[totalEventos];
        clasificacionSistema = new String[totalEventos];
        clasificacionJugador = new String[totalEventos];

        for (int i = 0; i < totalEventos; i++) {
            eventos[i] = listaEventos.get(i);
            clasificacionSistema[i] = listaClasSis.get(i);
            clasificacionJugador[i] = null;
        }

        mostrarMensaje("Se cargaron " + totalEventos + " eventos.", "Información");
    }

 
    public String clasificarEvento(String evento) {
        String e = evento.toLowerCase();

        // CRÍTICO
        if (contiene(e, "multiple failed login", "bruteforce", "brute force",
                "ransomware", "data exfiltration", "ddos",
                "malware detected", "privilege escalation", "critical alert")) {
            return "Crítico";
        }

        // SOSPECHOSO
        if (contiene(e, "failed login", "access denied", "invalid password",
                "port scan", "scan detected", "suspicious",
                "unknown ip", "unusual", "warning", "alert")) {
            return "Sospechoso";
        }

        
        return "Normal";
    }


    private boolean contiene(String texto, String... patrones) {
        for (String p : patrones) {
            if (texto.contains(p.toLowerCase())) return true;
        }
        return false;
    }

 
    public void registrarClasificacionJugador(int indice, String categoria) {
        if (eventos == null) return;
        if (indice < 0 || indice >= totalEventos) return;
        if (categoria == null) return;

        String cat = categoria.trim().toLowerCase();
        if (cat.startsWith("normal")) clasificacionJugador[indice] = "Normal";
        else if (cat.startsWith("sospe")) clasificacionJugador[indice] = "Sospechoso";
        else if (cat.startsWith("criti")) clasificacionJugador[indice] = "Crítico";
    }

    
    public int contarAciertos() {
        if (eventos == null) return 0;
        int aciertos = 0;

        for (int i = 0; i < totalEventos; i++) {
            String sis = clasificacionSistema[i];
            String jug = clasificacionJugador[i];
            if (jug != null && sis.equalsIgnoreCase(jug)) {
                aciertos++;
            }
        }
        return aciertos;
    }

    public int calcularPuntajeModulo() {
        if (eventos == null || totalEventos == 0) return 0;

        int aciertos = contarAciertos();
        puntajeUltimoResumen = aciertos ; 
        return puntajeUltimoResumen;
    }

    public int contarCategoriaJugador(String categoria) {
        if (eventos == null || categoria == null) return 0;
        int c = 0;
        for (int i = 0; i < totalEventos; i++) {
            String jug = clasificacionJugador[i];
            if (jug != null && jug.equalsIgnoreCase(categoria)) c++;
        }
        return c;
    }

    public void mostrarResumen() {
        if (eventos == null || totalEventos == 0) {
            mostrarMensaje("Primero debes cargar un archivo de logs.", "Aviso");
            return;
        }

        int normales = contarCategoriaJugador("Normal");
        int sospechosos = contarCategoriaJugador("Sospechoso");
        int criticos = contarCategoriaJugador("Crítico");

        int aciertos = contarAciertos();
        int puntaje = calcularPuntajeModulo();
        double porcentaje = (totalEventos == 0) ? 0.0
                : (aciertos * 100.0 / totalEventos);

        String msg =
        "Eventos totales: " + totalEventos + "\n" +
        "Tus clasificaciones:\n" +
        "  Normal: " + normales + "\n" +
        "  Sospechoso: " + sospechosos + "\n" +
        "  Crítico: " + criticos + "\n\n" +
        "Aciertos: " + aciertos + "\n" +
        "Porcentaje de aciertos: " + String.format("%.2f", porcentaje) + " %\n" +
        "Puntaje SecureLog Analyzer: " + puntaje;


        mostrarMensaje(msg, "Resumen SecureLog Analyzer");
    }

   
    private void mostrarMensaje(String msg, String titulo) {
        JOptionPane.showMessageDialog(null, msg, titulo,
                JOptionPane.INFORMATION_MESSAGE);
    }

 
    public String[] getEventos() {
        return eventos;
    }

    public String[] getClasificacionSistema() {
        return clasificacionSistema;
    }

    public String[] getClasificacionJugador() {
        return clasificacionJugador;
    }

    public int getTotalEventos() {
        return totalEventos;
    }

    public int getPuntajeUltimoResumen() {
        return puntajeUltimoResumen;
    }

  


    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        panel = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnload = new javax.swing.JButton();
        Resumen = new javax.swing.JButton();
        btnrestart = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 255));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabla.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tablaAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(tabla);

        panel.setBackground(new java.awt.Color(0, 0, 51));

        btnSave.setFont(new java.awt.Font("ROG Fonts", 3, 24)); // NOI18N
        btnSave.setText("Guardar");
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });

        btnload.setFont(new java.awt.Font("ROG Fonts", 3, 24)); // NOI18N
        btnload.setText("Cargar");
        btnload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnloadMouseClicked(evt);
            }
        });

        Resumen.setFont(new java.awt.Font("ROG Fonts", 1, 24)); // NOI18N
        Resumen.setText("Resumen");
        Resumen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ResumenMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Resumen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(btnload, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Resumen, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(332, Short.MAX_VALUE))
        );

        btnrestart.setFont(new java.awt.Font("ROG Fonts", 3, 24)); // NOI18N
        btnrestart.setText("Reiniciar");
        btnrestart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnrestartMouseClicked(evt);
            }
        });

        btnsalir.setFont(new java.awt.Font("ROG Fonts", 3, 24)); // NOI18N
        btnsalir.setText("Salir");
        btnsalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsalirMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(btnrestart)
                        .addGap(67, 67, 67)
                        .addComponent(btnsalir))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnrestart)
                            .addComponent(btnsalir))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tablaAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaAncestorAdded

    private void btnsalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsalirMouseClicked
      
      this.setVisible(false);
      FramePrincipal frame1 = new FramePrincipal();
      frame1.setVisible(true);
    }//GEN-LAST:event_btnsalirMouseClicked

    private void btnloadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnloadMouseClicked
cargarLogsConSelector(this);
llenarTablaConEventos();
     

    }//GEN-LAST:event_btnloadMouseClicked

    private void ResumenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ResumenMouseClicked
        actualizarClasificacionesDesdeTabla();
        mostrarResumen();

        int puntajeM4 = getPuntajeUltimoResumen();
        System.out.println("Puntaje módulo 4: " + puntajeM4);
        Main.puntajeSecureLogAnalyzer+=puntajeM4;
        
    }//GEN-LAST:event_ResumenMouseClicked

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked
        actualizarClasificacionesDesdeTabla();
    JOptionPane.showMessageDialog(this,
            "Clasificaciones registradas.", "SecureLog",
            JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnSaveMouseClicked

    private void btnrestartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrestartMouseClicked
          eventos = null;
    clasificacionSistema = null;
    clasificacionJugador = null;
    totalEventos = 0;
    puntajeUltimoResumen = 0;

    // 🔹 Limpiar tabla visual
    if (modeloEventos != null) {
        modeloEventos.setRowCount(0);
    }

    // 🔹 Mensaje al usuario
    JOptionPane.showMessageDialog(
        this,
        "El módulo ha sido reiniciado.\nCargue un nuevo archivo para continuar.",
        "SecureLog Analyzer",
        JOptionPane.INFORMATION_MESSAGE
    );
    }//GEN-LAST:event_btnrestartMouseClicked

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
            java.util.logging.Logger.getLogger(FrameSec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameSec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameSec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameSec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameSec().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Resumen;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnload;
    private javax.swing.JButton btnrestart;
    private javax.swing.JButton btnsalir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
