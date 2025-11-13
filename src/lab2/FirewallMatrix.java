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
    } else {
        mensaje = "❌ Red comprometida\nAciertos: " + aciertos + " / " + totalComprometidos + " (" + Math.round(porcentaje) + "%)";
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitle.setText("CiberDefender Suite: Security Lab");

        btn00.setText("jButton1");
        btn00.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn00MouseClicked(evt);
            }
        });

        btn01.setText("jButton1");
        btn01.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn01MouseClicked(evt);
            }
        });

        btn02.setText("jButton1");
        btn02.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn02MouseClicked(evt);
            }
        });

        btn03.setText("jButton1");
        btn03.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn03MouseClicked(evt);
            }
        });

        btn04.setText("jButton1");
        btn04.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn04MouseClicked(evt);
            }
        });

        btn09.setText("jButton1");
        btn09.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn09MouseClicked(evt);
            }
        });

        btn05.setText("jButton1");
        btn05.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn05MouseClicked(evt);
            }
        });

        btn06.setText("jButton1");
        btn06.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn06MouseClicked(evt);
            }
        });

        btn07.setText("jButton1");
        btn07.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn07MouseClicked(evt);
            }
        });

        btn08.setText("jButton1");
        btn08.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn08MouseClicked(evt);
            }
        });

        btn14.setText("jButton1");
        btn14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn14MouseClicked(evt);
            }
        });

        btn10.setText("jButton1");
        btn10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn10MouseClicked(evt);
            }
        });

        btn11.setText("jButton1");
        btn11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn11MouseClicked(evt);
            }
        });

        btn12.setText("jButton1");
        btn12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn12MouseClicked(evt);
            }
        });

        btn13.setText("jButton1");
        btn13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn13MouseClicked(evt);
            }
        });

        btn19.setText("jButton1");
        btn19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn19MouseClicked(evt);
            }
        });

        btn15.setText("jButton1");
        btn15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn15MouseClicked(evt);
            }
        });

        btn16.setText("jButton1");
        btn16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn16MouseClicked(evt);
            }
        });

        btn17.setText("jButton1");
        btn17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn17MouseClicked(evt);
            }
        });

        btn18.setText("jButton1");
        btn18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn18MouseClicked(evt);
            }
        });

        btn24.setText("jButton1");
        btn24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn24MouseClicked(evt);
            }
        });

        btn20.setText("jButton1");
        btn20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn20MouseClicked(evt);
            }
        });

        btn21.setText("jButton1");
        btn21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn21MouseClicked(evt);
            }
        });

        btn22.setText("jButton1");
        btn22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn22MouseClicked(evt);
            }
        });

        btn23.setText("jButton1");
        btn23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn23MouseClicked(evt);
            }
        });

        lblAciertos.setText("Aciertos: N");

        lblErrores.setText("Errores: N");

        lblPuertosrestantes.setText("Comprometidos restantes: N");

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        btnGuardarresultados.setText("Guardar resultados");
        btnGuardarresultados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarresultadosActionPerformed(evt);
            }
        });

        lbltempopregunta.setText("Tiempo:");

        btnReiniciar1.setText("Reiniciar");
        btnReiniciar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(lblAciertos, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(lblErrores, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(lblPuertosrestantes, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn10)
                            .addComponent(btn20)
                            .addComponent(btn23))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn01)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                                .addComponent(btn02)
                                .addGap(44, 44, 44)
                                .addComponent(btn03)
                                .addGap(107, 107, 107))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn15)
                                    .addComponent(btn21))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn12)
                                .addGap(237, 237, 237))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn06)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn08)
                                .addGap(107, 107, 107)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn22)
                            .addComponent(btn16)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn05)
                                .addGap(179, 179, 179)
                                .addComponent(btn07)))
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn18)
                            .addComponent(btn17)
                            .addComponent(btn13))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCerrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnGuardarresultados, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btn14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 278, Short.MAX_VALUE)
                                        .addComponent(btnReiniciar1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btn09)
                                            .addComponent(btn19)
                                            .addComponent(btn24)
                                            .addComponent(btn04))
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn00, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(lbltempopregunta, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(24, 24, 24))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn00, btn01, btn02, btn03, btn04, btn05, btn06, btn07, btn08, btn09, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20, btn21, btn22, btn23, btn24});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCerrar, btnGuardarresultados, btnIniciar, btnReiniciar1});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbltempopregunta)
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn04)
                            .addComponent(btn03)
                            .addComponent(btn02)
                            .addComponent(btn01)
                            .addComponent(btn00, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGuardarresultados, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnReiniciar1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn14)
                            .addComponent(btn13)
                            .addComponent(btn12)
                            .addComponent(btn11)
                            .addComponent(btn10))
                        .addGap(51, 51, 51)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btn06)
                                .addGap(12, 12, 12))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btn05)
                                .addComponent(btn07)
                                .addComponent(btn09)
                                .addComponent(btn08)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(185, 185, 185)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btn24)
                                    .addComponent(btn17)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btn23)
                                    .addComponent(btn15)
                                    .addComponent(btn16)
                                    .addComponent(btn18)
                                    .addComponent(btn19))
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btn22)
                                    .addComponent(btn21)
                                    .addComponent(btn20))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAciertos)
                    .addComponent(lblErrores)
                    .addComponent(lblPuertosrestantes))
                .addGap(33, 33, 33))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCerrar, btnGuardarresultados, btnIniciar, btnReiniciar1});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn00, btn01, btn02, btn03, btn04, btn05, btn06, btn07, btn08, btn09, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20, btn21, btn22, btn23, btn24});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
            this.dispose();

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
    private javax.swing.JLabel lblAciertos;
    private javax.swing.JLabel lblErrores;
    private javax.swing.JLabel lblPuertosrestantes;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lbltempopregunta;
    // End of variables declaration//GEN-END:variables
}
