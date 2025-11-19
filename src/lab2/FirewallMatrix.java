package lab2;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Alejandro Cotes
 */
public class FirewallMatrix extends javax.swing.JFrame {
 javax.swing.JButton[][] gridButtons = new javax.swing.JButton[5][5];
 int[][] estado = new int[5][5];
 boolean[][] bloqueado = new boolean[5][5];
 boolean[][] infectado = new boolean[5][5];

 int totalComprometidos = 0;
 int aciertos = 0;
 int errores = 0;

 javax.swing.Timer attackTimer;
 java.util.List<java.awt.Point> attackOrder;
 int attackIndex;
 int attackDelayMs = 800;
 
 
private void styleSimpleUI() {
    getContentPane().setBackground(new java.awt.Color(10, 35, 70));

    if (lblTitle != null) {
        lblTitle.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 25)); 
        lblTitle.setForeground(java.awt.Color.WHITE);
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    }

    java.awt.Font indicadorFont = new java.awt.Font("SansSerif", java.awt.Font.BOLD, 16); 
    java.awt.Color indicadorColor = java.awt.Color.WHITE;

    if (lbltempopregunta != null) {
        lbltempopregunta.setFont(indicadorFont);
        lbltempopregunta.setForeground(indicadorColor);
    }
    if (lblAciertos != null) {
        lblAciertos.setFont(indicadorFont);
        lblAciertos.setForeground(indicadorColor);
    }
    if (lblErrores != null) {
        lblErrores.setFont(indicadorFont);
        lblErrores.setForeground(indicadorColor);
    }
    if (lblPuertosrestantes != null) {
        lblPuertosrestantes.setFont(indicadorFont);
        lblPuertosrestantes.setForeground(indicadorColor);
    }

    java.awt.Color azulBtn = new java.awt.Color(40, 120, 220);
    java.awt.Color verdeBtn = new java.awt.Color(24, 160, 100);
    java.awt.Color naranjaBtn = new java.awt.Color(245, 130, 35);
    java.awt.Color rojoBtn = new java.awt.Color(200, 40, 40);

    if (btnIniciar != null) {
        btnIniciar.setBackground(azulBtn);
        btnIniciar.setForeground(java.awt.Color.WHITE);
        btnIniciar.setOpaque(true);
        btnIniciar.setBorder(javax.swing.BorderFactory.createLineBorder(azulBtn.darker()));
        btnIniciar.setFont(btnIniciar.getFont().deriveFont(java.awt.Font.BOLD, 14f));
    }
    if (btnGuardarresultados != null) {
        btnGuardarresultados.setBackground(verdeBtn);
        btnGuardarresultados.setForeground(java.awt.Color.WHITE);
        btnGuardarresultados.setOpaque(true);
        btnGuardarresultados.setBorder(javax.swing.BorderFactory.createLineBorder(verdeBtn.darker()));
        btnGuardarresultados.setFont(btnGuardarresultados.getFont().deriveFont(java.awt.Font.BOLD, 13f));
    }
    if (btnReiniciar1 != null) {
        btnReiniciar1.setBackground(naranjaBtn);
        btnReiniciar1.setForeground(java.awt.Color.WHITE);
        btnReiniciar1.setOpaque(true);
        btnReiniciar1.setBorder(javax.swing.BorderFactory.createLineBorder(naranjaBtn.darker()));
        btnReiniciar1.setFont(btnReiniciar1.getFont().deriveFont(java.awt.Font.BOLD, 13f));
    }
    if (btnCerrar != null) {
        btnCerrar.setBackground(rojoBtn);
        btnCerrar.setForeground(java.awt.Color.WHITE);
        btnCerrar.setOpaque(true);
        btnCerrar.setBorder(javax.swing.BorderFactory.createLineBorder(rojoBtn.darker()));
        btnCerrar.setFont(btnCerrar.getFont().deriveFont(java.awt.Font.BOLD, 13f));
    }

    for (int r = 0; r < 5; r++) {
        for (int c = 0; c < 5; c++) {
            javax.swing.JButton b = gridButtons[r][c];
            if (b != null) {
                b.setFont(b.getFont().deriveFont(12f));
                b.setOpaque(true);
                b.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(60, 60, 60)));
                b.setBackground(new java.awt.Color(18, 40, 65));
                b.setForeground(new java.awt.Color(220, 230, 240));
            }
        }
    }
}
private void generarAtaqueAleatorio() {
    for (int r = 0; r < 5; r++) {
        for (int c = 0; c < 5; c++) {
            estado[r][c] = 0;
            bloqueado[r][c] = false;
            infectado[r][c] = false;
        }
    }
    java.util.Random rnd = new java.util.Random();
    totalComprometidos = 0;
    for (int r = 0; r < 5; r++) {
        for (int c = 0; c < 5; c++) {
            if (rnd.nextDouble() < 0.30) {
                estado[r][c] = 1;
                totalComprometidos++;
            } else {
                estado[r][c] = 0;
            }
        }
    }
    actualizarVista();
    actualizarLabels();
    btnIniciar.setEnabled(true);
    btnReiniciar1.setEnabled(true);
}

private void verificarBloqueo(int fila, int columna) {
    if (bloqueado[fila][columna] || infectado[fila][columna]) return;

    if (totalComprometidos == 0) {
        generarAtaqueAleatorio();
    }

    if (estado[fila][columna] == 1) {
        bloqueado[fila][columna] = true;
        aciertos++;
        gridButtons[fila][columna].setText("🔒");
        gridButtons[fila][columna].setBackground(new java.awt.Color(16, 163, 95));
    } else {
        bloqueado[fila][columna] = true;
        errores++;
        gridButtons[fila][columna].setText("✖");
        gridButtons[fila][columna].setBackground(new java.awt.Color(150, 60, 60));
    }
    actualizarLabels();
}

private void iniciarAtaque() {
    if (totalComprometidos == 0) {
        generarAtaqueAleatorio();
    }

    attackOrder = new java.util.ArrayList<java.awt.Point>();
    for (int r = 0; r < 5; r++) {
        for (int c = 0; c < 5; c++) {
            if (estado[r][c] == 1) attackOrder.add(new java.awt.Point(r, c));
        }
    }
    java.util.Collections.shuffle(attackOrder);
    attackIndex = 0;

    if (attackTimer != null && attackTimer.isRunning()) attackTimer.stop();

    attackTimer = new javax.swing.Timer(attackDelayMs, new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent ev) {
            if (attackIndex >= attackOrder.size()) {
                attackTimer.stop();
                finalizarJuego();
                return;
            }
            java.awt.Point p = attackOrder.get(attackIndex);
            int r = p.x;
            int c = p.y;
            if (!bloqueado[r][c] && !infectado[r][c]) {
                infectado[r][c] = true;
                gridButtons[r][c].setText("💀");
                gridButtons[r][c].setBackground(new java.awt.Color(120, 10, 10));
            }
            attackIndex++;
            // actualizar tiempo estimado (opcional)
            int remaining = Math.max(0, (attackOrder.size() - attackIndex) * attackDelayMs / 1000);
            if (lbltempopregunta != null) lbltempopregunta.setText("Tiempo: " + remaining + "s");
        }
    });
    attackTimer.setInitialDelay(2000);
    attackTimer.start();

    btnIniciar.setEnabled(false);
}

private void finalizarJuego() {
    for (int r = 0; r < 5; r++) {
        for (int c = 0; c < 5; c++) {
            if (estado[r][c] == 1 && !bloqueado[r][c] && !infectado[r][c]) {
                infectado[r][c] = true;
                gridButtons[r][c].setText("💀");
                gridButtons[r][c].setBackground(new java.awt.Color(120, 10, 10));
            }
        }
    }
    double porcentaje = (totalComprometidos == 0) ? 0.0 : (100.0 * aciertos / totalComprometidos);
    String mensaje;
    if (porcentaje >= 80.0) {
        mensaje = "✅ Red protegida con éxito\nAciertos: " + aciertos + " / " + totalComprometidos + " (" + Math.round(porcentaje) + "%)";
        Main.puntajeFirewallMatrix+=aciertos;
    } else {
        mensaje = "❌ Red comprometida\nAciertos: " + aciertos + " / " + totalComprometidos + " (" + Math.round(porcentaje) + "%)";
        Main.puntajeFirewallMatrix+=-1;
    }
    javax.swing.JOptionPane.showMessageDialog(this, mensaje);
    
    btnReiniciar1.setEnabled(true);
    btnGuardarresultados.setEnabled(true);
}

private void guardarResultados() {
    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
    int sel = chooser.showSaveDialog(this);
    if (sel != javax.swing.JFileChooser.APPROVE_OPTION) return;
    java.io.File f = chooser.getSelectedFile();
    try {
        java.io.BufferedWriter w = new java.io.BufferedWriter(new java.io.FileWriter(f));
        w.write("FirewallMatrix - resultados\n");
        w.write("Aciertos:" + aciertos + "\n");
        w.write("Errores:" + errores + "\n");
        w.write("Comprometidos totales:" + totalComprometidos + "\n");
        w.write("Porcentaje protección:" + (totalComprometidos == 0 ? 0 : (100.0 * aciertos / totalComprometidos)) + "\n");
        w.write("\nMatriz (fila x columna):\n");
        for (int r = 0; r < 5; r++) {
            StringBuilder line = new StringBuilder();
            for (int c = 0; c < 5; c++) {
                char mark = estado[r][c] == 1 ? '1' : '0';
                if (infectado[r][c]) mark = 'X';
                if (bloqueado[r][c] && estado[r][c] == 1) mark = 'B';
                line.append(mark);
                if (c < 4) line.append(',');
            }
            w.write(line.toString() + "\n");
        }
        w.flush();
        w.close();
        javax.swing.JOptionPane.showMessageDialog(this, "Resultados guardados en: " + f.getAbsolutePath());
    } catch (java.io.IOException ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error guardando archivo: " + ex.getMessage());
    }
}

private void reiniciar() {
    if (attackTimer != null && attackTimer.isRunning()) attackTimer.stop();

    for (int r = 0; r < 5; r++) {
        for (int c = 0; c < 5; c++) {
            estado[r][c] = 0;
            bloqueado[r][c] = false;
            infectado[r][c] = false;
            javax.swing.JButton b = gridButtons[r][c];
            if (b != null) {
                b.setText("");
                b.setBackground(new java.awt.Color(8, 17, 35));
                b.setToolTipText(null);
            }
        }
    }
    totalComprometidos = 0;
    aciertos = 0;
    errores = 0;
    if (lblAciertos != null) lblAciertos.setText("Aciertos: 0");
    if (lblErrores != null) lblErrores.setText("Errores: 0");
    if (lbltempopregunta != null) lbltempopregunta.setText("Tiempo: --");
    if (lblPuertosrestantes != null) lblPuertosrestantes.setText("Puertos comprometidos: 0");

    btnIniciar.setEnabled(true);
    btnReiniciar1.setEnabled(false);
    btnGuardarresultados.setEnabled(false);
}

private void actualizarVista() {
    for (int r = 0; r < 5; r++) {
        for (int c = 0; c < 5; c++) {
            javax.swing.JButton b = gridButtons[r][c];
            if (b == null) continue;
            if (infectado[r][c]) {
                b.setText("💀");
                b.setBackground(new java.awt.Color(120, 10, 10));
            } else if (bloqueado[r][c]) {
                b.setText("🔒");
                b.setBackground(new java.awt.Color(16, 163, 95));
            } else if (estado[r][c] == 1) {
                b.setText("⚠");
                b.setBackground(new java.awt.Color(197, 148, 0));
            } else {
                b.setText("");
                b.setBackground(new java.awt.Color(8, 17, 35));
            }
        }
    }
}

private void actualizarLabels() {
    if (lblAciertos != null) lblAciertos.setText("Aciertos: " + aciertos);
    if (lblErrores != null) lblErrores.setText("Errores: " + errores);
    if (lblPuertosrestantes != null) lblPuertosrestantes.setText("Puertos comprometidos: " + totalComprometidos);
}
private void inicializarBindings() {
    gridButtons[0][0] = btn00; gridButtons[0][1] = btn01; gridButtons[0][2] = btn02; gridButtons[0][3] = btn03; gridButtons[0][4] = btn04;
    gridButtons[1][0] = btn05; gridButtons[1][1] = btn06; gridButtons[1][2] = btn07; gridButtons[1][3] = btn08; gridButtons[1][4] = btn09;
    gridButtons[2][0] = btn10; gridButtons[2][1] = btn11; gridButtons[2][2] = btn12; gridButtons[2][3] = btn13; gridButtons[2][4] = btn14;
    gridButtons[3][0] = btn15; gridButtons[3][1] = btn16; gridButtons[3][2] = btn17; gridButtons[3][3] = btn18; gridButtons[3][4] = btn19;
    gridButtons[4][0] = btn20; gridButtons[4][1] = btn21; gridButtons[4][2] = btn22; gridButtons[4][3] = btn23; gridButtons[4][4] = btn24;

    for (int r = 0; r < 5; r++) {
        for (int c = 0; c < 5; c++) {
            javax.swing.JButton b = gridButtons[r][c];
            if (b != null) {
                b.putClientProperty("pos", new java.awt.Point(r, c));
                b.setText("");
                b.setBackground(new java.awt.Color(8, 17, 35));
                b.setForeground(new java.awt.Color(191, 220, 255));
            }
        }
    }

    
}
    public FirewallMatrix() {
        initComponents();
        inicializarBindings();
        styleSimpleUI();
        this.setLocationRelativeTo(null);
         Musica.reproducirJuego();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        btn00 = new javax.swing.JButton();
        btn01 = new javax.swing.JButton();
        btn02 = new javax.swing.JButton();
        btn03 = new javax.swing.JButton();
        btn04 = new javax.swing.JButton();
        btn09 = new javax.swing.JButton();
        btn05 = new javax.swing.JButton();
        btn06 = new javax.swing.JButton();
        btn07 = new javax.swing.JButton();
        btn08 = new javax.swing.JButton();
        btn14 = new javax.swing.JButton();
        btn10 = new javax.swing.JButton();
        btn11 = new javax.swing.JButton();
        btn12 = new javax.swing.JButton();
        btn13 = new javax.swing.JButton();
        btn19 = new javax.swing.JButton();
        btn15 = new javax.swing.JButton();
        btn16 = new javax.swing.JButton();
        btn17 = new javax.swing.JButton();
        btn18 = new javax.swing.JButton();
        btn24 = new javax.swing.JButton();
        btn20 = new javax.swing.JButton();
        btn21 = new javax.swing.JButton();
        btn22 = new javax.swing.JButton();
        btn23 = new javax.swing.JButton();
        lblAciertos = new javax.swing.JLabel();
        lblErrores = new javax.swing.JLabel();
        lblPuertosrestantes = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        btnIniciar = new javax.swing.JButton();
        btnGuardarresultados = new javax.swing.JButton();
        lbltempopregunta = new javax.swing.JLabel();
        btnReiniciar1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setText("CiberDefender Suite: Security Lab");
        getContentPane().add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(267, 17, 409, 72));

        btn00.setText("jButton1");
        btn00.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn00MouseClicked(evt);
            }
        });
        getContentPane().add(btn00, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 86, 42));

        btn01.setText("jButton1");
        btn01.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn01MouseClicked(evt);
            }
        });
        getContentPane().add(btn01, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 86, 42));

        btn02.setText("jButton1");
        btn02.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn02MouseClicked(evt);
            }
        });
        getContentPane().add(btn02, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 86, 42));

        btn03.setText("jButton1");
        btn03.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn03MouseClicked(evt);
            }
        });
        getContentPane().add(btn03, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 86, 42));

        btn04.setText("jButton1");
        btn04.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn04MouseClicked(evt);
            }
        });
        getContentPane().add(btn04, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, 86, 42));

        btn09.setText("jButton1");
        btn09.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn09MouseClicked(evt);
            }
        });
        getContentPane().add(btn09, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 200, 86, 42));

        btn05.setText("jButton1");
        btn05.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn05MouseClicked(evt);
            }
        });
        getContentPane().add(btn05, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 86, 42));

        btn06.setText("jButton1");
        btn06.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn06MouseClicked(evt);
            }
        });
        getContentPane().add(btn06, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 86, 42));

        btn07.setText("jButton1");
        btn07.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn07MouseClicked(evt);
            }
        });
        getContentPane().add(btn07, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 200, 86, 42));

        btn08.setText("jButton1");
        btn08.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn08MouseClicked(evt);
            }
        });
        getContentPane().add(btn08, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, 86, 42));

        btn14.setText("jButton1");
        btn14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn14MouseClicked(evt);
            }
        });
        getContentPane().add(btn14, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 280, 86, 42));

        btn10.setText("jButton1");
        btn10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn10MouseClicked(evt);
            }
        });
        getContentPane().add(btn10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 86, 42));

        btn11.setText("jButton1");
        btn11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn11MouseClicked(evt);
            }
        });
        getContentPane().add(btn11, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, 86, 42));

        btn12.setText("jButton1");
        btn12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn12MouseClicked(evt);
            }
        });
        getContentPane().add(btn12, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, 86, 42));

        btn13.setText("jButton1");
        btn13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn13MouseClicked(evt);
            }
        });
        getContentPane().add(btn13, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 280, 86, 42));

        btn19.setText("jButton1");
        btn19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn19MouseClicked(evt);
            }
        });
        getContentPane().add(btn19, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 350, 86, 42));

        btn15.setText("jButton1");
        btn15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn15MouseClicked(evt);
            }
        });
        getContentPane().add(btn15, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 350, 86, 42));

        btn16.setText("jButton1");
        btn16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn16MouseClicked(evt);
            }
        });
        getContentPane().add(btn16, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 350, 86, 42));

        btn17.setText("jButton1");
        btn17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn17MouseClicked(evt);
            }
        });
        getContentPane().add(btn17, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 440, 86, 42));

        btn18.setText("jButton1");
        btn18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn18MouseClicked(evt);
            }
        });
        getContentPane().add(btn18, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 350, 86, 42));

        btn24.setText("jButton1");
        btn24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn24MouseClicked(evt);
            }
        });
        getContentPane().add(btn24, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 440, 86, 42));

        btn20.setText("jButton1");
        btn20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn20MouseClicked(evt);
            }
        });
        getContentPane().add(btn20, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, 86, 42));

        btn21.setText("jButton1");
        btn21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn21MouseClicked(evt);
            }
        });
        getContentPane().add(btn21, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 440, 86, 42));

        btn22.setText("jButton1");
        btn22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn22MouseClicked(evt);
            }
        });
        getContentPane().add(btn22, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 440, 86, 42));

        btn23.setText("jButton1");
        btn23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn23MouseClicked(evt);
            }
        });
        getContentPane().add(btn23, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 86, 42));

        lblAciertos.setText("Aciertos: ");
        getContentPane().add(lblAciertos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 530, 145, -1));

        lblErrores.setText("Errores: ");
        getContentPane().add(lblErrores, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 530, 136, -1));

        lblPuertosrestantes.setText("Comprometidos restantes: ");
        getContentPane().add(lblPuertosrestantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 530, 333, -1));

        btnCerrar.setText("Cerrar");
        btnCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCerrarMouseClicked(evt);
            }
        });
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 420, 130, 42));

        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });
        getContentPane().add(btnIniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 120, 130, 42));

        btnGuardarresultados.setText("Guardar resultados");
        btnGuardarresultados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarresultadosActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardarresultados, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 250, -1, 42));

        lbltempopregunta.setText("Tiempo:");
        getContentPane().add(lbltempopregunta, new org.netbeans.lib.awtextra.AbsoluteConstraints(74, 45, 134, -1));

        btnReiniciar1.setText("Reiniciar");
        btnReiniciar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciar1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnReiniciar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 330, 130, 42));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lab2/images/R-removebg-preview.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 40, 377, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        

    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        // TODO add your handling code here:
            iniciarAtaque();

    }//GEN-LAST:event_btnIniciarActionPerformed

    private void btnGuardarresultadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarresultadosActionPerformed
        // TODO add your handling code here:
            guardarResultados();

    }//GEN-LAST:event_btnGuardarresultadosActionPerformed

    private void btn00MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn00MouseClicked
            verificarBloqueo(0, 0);



// TODO add your handling code here:
    }//GEN-LAST:event_btn00MouseClicked

    private void btnReiniciar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciar1ActionPerformed
        // TODO add your handling code here:
            reiniciar();

    }//GEN-LAST:event_btnReiniciar1ActionPerformed

    private void btn01MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn01MouseClicked
        // TODO add your handling code here:
                    verificarBloqueo(0, 1);

    }//GEN-LAST:event_btn01MouseClicked

    private void btn02MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn02MouseClicked
        // TODO add your handling code here:
                    verificarBloqueo(0, 2);

    }//GEN-LAST:event_btn02MouseClicked

    private void btn03MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn03MouseClicked
        // TODO add your handling code here:
                    verificarBloqueo(0, 3);

    }//GEN-LAST:event_btn03MouseClicked

    private void btn04MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn04MouseClicked
        // TODO add your handling code here:
                    verificarBloqueo(0, 4);

    }//GEN-LAST:event_btn04MouseClicked

    private void btn05MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn05MouseClicked
        // TODO add your handling code here:
                    verificarBloqueo(1, 0);

    }//GEN-LAST:event_btn05MouseClicked

    private void btn06MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn06MouseClicked
        // TODO add your handling code here:
                            verificarBloqueo(1, 1);

    }//GEN-LAST:event_btn06MouseClicked

    private void btn07MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn07MouseClicked
        // TODO add your handling code here:
                            verificarBloqueo(1, 2);

    }//GEN-LAST:event_btn07MouseClicked

    private void btn08MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn08MouseClicked
        // TODO add your handling code here:
                            verificarBloqueo(1, 3);

    }//GEN-LAST:event_btn08MouseClicked

    private void btn09MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn09MouseClicked
        // TODO add your handling code here:
                            verificarBloqueo(1, 4);

    }//GEN-LAST:event_btn09MouseClicked

    private void btn10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn10MouseClicked
        // TODO add your handling code here:
                            verificarBloqueo(2, 0);

    }//GEN-LAST:event_btn10MouseClicked

    private void btn11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn11MouseClicked
        // TODO add your handling code here:
                                    verificarBloqueo(2, 1);

    }//GEN-LAST:event_btn11MouseClicked

    private void btn12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn12MouseClicked
        // TODO add your handling code here:
                                    verificarBloqueo(2, 2);

    }//GEN-LAST:event_btn12MouseClicked

    private void btn13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn13MouseClicked
        // TODO add your handling code here:
                                    verificarBloqueo(2, 3);

    }//GEN-LAST:event_btn13MouseClicked

    private void btn14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn14MouseClicked
        // TODO add your handling code here:
                                    verificarBloqueo(2, 4);

    }//GEN-LAST:event_btn14MouseClicked

    private void btn15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn15MouseClicked
        // TODO add your handling code here:
                                            verificarBloqueo(3, 0);

    }//GEN-LAST:event_btn15MouseClicked

    private void btn16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn16MouseClicked
        // TODO add your handling code here:
                                            verificarBloqueo(3, 1);

    }//GEN-LAST:event_btn16MouseClicked

    private void btn17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn17MouseClicked
        // TODO add your handling code here:
                                            verificarBloqueo(3, 2);

    }//GEN-LAST:event_btn17MouseClicked

    private void btn18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn18MouseClicked
        // TODO add your handling code here:
                                                    verificarBloqueo(3, 3);

    }//GEN-LAST:event_btn18MouseClicked

    private void btn19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn19MouseClicked
        // TODO add your handling code here:
                                                    verificarBloqueo(3, 4);

    }//GEN-LAST:event_btn19MouseClicked

    private void btn20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn20MouseClicked
        // TODO add your handling code here:
                                                    verificarBloqueo(4, 0);

    }//GEN-LAST:event_btn20MouseClicked

    private void btn21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn21MouseClicked
        // TODO add your handling code here:
        
                                                            verificarBloqueo(4, 1);

    }//GEN-LAST:event_btn21MouseClicked

    private void btn22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn22MouseClicked
        // TODO add your handling code here:
                                                            verificarBloqueo(4, 2);

    }//GEN-LAST:event_btn22MouseClicked

    private void btn23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn23MouseClicked
        // TODO add your handling code here:
                                                            verificarBloqueo(4, 3);

    }//GEN-LAST:event_btn23MouseClicked

    private void btn24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn24MouseClicked
        // TODO add your handling code here:
                                                            verificarBloqueo(4, 4);

    }//GEN-LAST:event_btn24MouseClicked

    private void btnCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCerrarMouseClicked
        // TODO add your handling code here:
        FramePrincipal frame = new  FramePrincipal();
        frame.setVisible(true);
        FirewallMatrix.this.setVisible(false);
        
    }//GEN-LAST:event_btnCerrarMouseClicked

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
            java.util.logging.Logger.getLogger(FirewallMatrix.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FirewallMatrix.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FirewallMatrix.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FirewallMatrix.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FirewallMatrix().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn00;
    private javax.swing.JButton btn01;
    private javax.swing.JButton btn02;
    private javax.swing.JButton btn03;
    private javax.swing.JButton btn04;
    private javax.swing.JButton btn05;
    private javax.swing.JButton btn06;
    private javax.swing.JButton btn07;
    private javax.swing.JButton btn08;
    private javax.swing.JButton btn09;
    private javax.swing.JButton btn10;
    private javax.swing.JButton btn11;
    private javax.swing.JButton btn12;
    private javax.swing.JButton btn13;
    private javax.swing.JButton btn14;
    private javax.swing.JButton btn15;
    private javax.swing.JButton btn16;
    private javax.swing.JButton btn17;
    private javax.swing.JButton btn18;
    private javax.swing.JButton btn19;
    private javax.swing.JButton btn20;
    private javax.swing.JButton btn21;
    private javax.swing.JButton btn22;
    private javax.swing.JButton btn23;
    private javax.swing.JButton btn24;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGuardarresultados;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnReiniciar1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblAciertos;
    private javax.swing.JLabel lblErrores;
    private javax.swing.JLabel lblPuertosrestantes;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lbltempopregunta;
    // End of variables declaration//GEN-END:variables
}
