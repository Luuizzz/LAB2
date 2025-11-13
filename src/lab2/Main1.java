package lab2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main1 extends JFrame {
    
    // Variables de instancia
    private JButton btnPhishHunter;
    private JButton btnFirewallMatrix;
    private JButton btnCryptoBreaker;
    private JButton btnSecureLog;
    private JButton btnVerPuntajes;
    private JButton btnAyuda;
    private JButton btnSalir;
    
    // Constructor
    public Main1() {
        inicializarComponentes();
        configurarVentana();
        configurarBotones();
    }
    
    // Procedimiento para inicializar componentes
    private void inicializarComponentes() {
        // Crear panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(20, 30, 48));
        
        // Panel superior con título
        JPanel panelTitulo = crearPanelTitulo();
        
        // Panel central con botones de módulos
        JPanel panelModulos = crearPanelModulos();
        
        // Panel inferior con botones adicionales
        JPanel panelInferior = crearPanelInferior();
        
        // Agregar paneles al frame
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelModulos, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    // Función para crear panel de título
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(15, 25, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        JLabel lblTitulo = new JLabel("CiberDefender Suite: Security Lab");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(0, 255, 255));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblSubtitulo = new JLabel("Sistema de Entrenamiento en Ciberseguridad");
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 14));
        lblSubtitulo.setForeground(new Color(180, 200, 255));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.setLayout(new GridLayout(2, 1));
        panel.add(lblTitulo);
        panel.add(lblSubtitulo);
        
        return panel;
    }
    
    // Función para crear panel de módulos
    private JPanel crearPanelModulos() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 20, 20));
        panel.setBackground(new Color(20, 30, 48));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // Crear botones de módulos
        btnPhishHunter = crearBotonModulo("[P] PhishHunter", 
            "Detecta el Engaño", new Color(255, 100, 100));
        
        btnFirewallMatrix = crearBotonModulo("[F] FirewallMatrix", 
            "La Muralla Digital", new Color(100, 150, 255));
        
        btnCryptoBreaker = crearBotonModulo("[C] CryptoBreaker", 
            "Desencripta el Codigo", new Color(150, 255, 100));
        
        btnSecureLog = crearBotonModulo("[S] SecureLog Analyzer", 
            "Deteccion Forense", new Color(255, 200, 100));
        
        panel.add(btnPhishHunter);
        panel.add(btnFirewallMatrix);
        panel.add(btnCryptoBreaker);
        panel.add(btnSecureLog);
        
        return panel;
    }
    
    // Función para crear botón de módulo personalizado
    private JButton crearBotonModulo(String titulo, String subtitulo, Color color) {
        JButton boton = new JButton();
        boton.setLayout(new BorderLayout());
        boton.setBackground(new Color(30, 40, 60));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(color, 3));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(color);
        
        JLabel lblSubtitulo = new JLabel(subtitulo, SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);
        
        JPanel contenido = new JPanel(new GridLayout(2, 1, 0, 5));
        contenido.setOpaque(false);
        contenido.add(lblTitulo);
        contenido.add(lblSubtitulo);
        
        boton.add(contenido, BorderLayout.CENTER);
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(45, 55, 75));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(30, 40, 60));
            }
        });
        
        return boton;
    }
    
    // Función para crear panel inferior
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(new Color(15, 25, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnVerPuntajes = crearBotonInferior("Ver Puntajes", new Color(100, 200, 100));
        btnAyuda = crearBotonInferior("Ayuda", new Color(100, 150, 255));
        btnSalir = crearBotonInferior("Salir", new Color(255, 100, 100));
        
        panel.add(btnVerPuntajes);
        panel.add(btnAyuda);
        panel.add(btnSalir);
        
        return panel;
    }
    
    // Función para crear botones inferiores
    private JButton crearBotonInferior(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(new Color(30, 40, 60));
        boton.setForeground(color);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(color, 2));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(150, 40));
        
        return boton;
    }
    
    // Procedimiento para configurar la ventana
    private void configurarVentana() {
        setTitle("CiberDefender Suite - Lab2");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    // Procedimiento para configurar los eventos de los botones
    private void configurarBotones() {
        // Botón PhishHunter
        btnPhishHunter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirModuloPhishHunter();
            }
        });
        
        // Botón FirewallMatrix
        btnFirewallMatrix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirModuloFirewallMatrix();
            }
        });
        
        // Botón CryptoBreaker
        btnCryptoBreaker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirModuloCryptoBreaker();
            }
        });
        
        // Botón SecureLog
        btnSecureLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirModuloSecureLog();
            }
        });
        
        // Botón Ver Puntajes
        btnVerPuntajes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPuntajes();
            }
        });
        
        // Botón Ayuda
        btnAyuda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarAyuda();
            }
        });
        
        // Botón Salir
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salirAplicacion();
            }
        });
    }
    
    // ========== PROCEDIMIENTOS PARA ABRIR MÓDULOS ==========
    
    private void abrirModuloPhishHunter() {
        FramePhishHunter frame = new FramePhishHunter();
        frame.setVisible(true);
    }
    
    private void abrirModuloFirewallMatrix() {
        FrameFirewallMatrix frame = new FrameFirewallMatrix();
        frame.setVisible(true);
    }
    
    private void abrirModuloCryptoBreaker() {
        FrameCryptoBreaker frame = new FrameCryptoBreaker();
        frame.setVisible(true);
    }
    
    private void abrirModuloSecureLog() {
        FrameSecureLog frame = new FrameSecureLog();
        frame.setVisible(true);
    }
    
    // Procedimiento para mostrar puntajes
    private void mostrarPuntajes() {
        int puntajeTotal = calcularPuntajeGlobal();
        JOptionPane.showMessageDialog(this,
            "PUNTAJE GLOBAL: " + puntajeTotal + " puntos\n\n" +
            "================================\n" +
            "[P] PhishHunter: " + obtenerPuntajeModulo(1) + " pts\n" +
            "[F] FirewallMatrix: " + obtenerPuntajeModulo(2) + " pts\n" +
            "[C] CryptoBreaker: " + obtenerPuntajeModulo(3) + " pts\n" +
            "[S] SecureLog: " + obtenerPuntajeModulo(4) + " pts\n" +
            "================================",
            "Puntajes Totales - CiberDefender",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Función para calcular puntaje global
    private int calcularPuntajeGlobal() {
        int total = 0;
        for (int i = 1; i <= 4; i++) {
            total += obtenerPuntajeModulo(i);
        }
        return total;
    }
    
    // Función para obtener puntaje de cada módulo (desde archivos)
    private int obtenerPuntajeModulo(int modulo) {
        // TODO: Implementar lectura desde archivos
        // Ejemplo: leer "puntaje_modulo1.txt"
        return 0; // Por ahora retorna 0
    }
    
    
    private void mostrarAyuda() {
        String mensajeAyuda = "===================================\n" +
                             "   COMO JUGAR - LAB2\n" +
                             "===================================\n\n" +
                             "1. PhishHunter: Identifica correos de phishing\n" +
                             "   - Detecta palabras sospechosas\n" +
                             "   - Clasifica 10 mensajes\n\n" +
                             "2. FirewallMatrix: Bloquea puertos comprometidos\n" +
                             "   - Matriz 5x5 de puertos\n" +
                             "   - Bloquea antes del ataque\n\n" +
                             "3. CryptoBreaker: Desencripta mensajes secretos\n" +
                             "   - Descifra el codigo\n" +
                             "   - Contra el tiempo!\n\n" +
                             "4. SecureLog Analyzer: Analiza logs de seguridad\n" +
                             "   - Clasifica eventos\n" +
                             "   - Analisis forense\n\n" +
                             "Completa todos los modulos para ser un experto en ciberseguridad!";
        
        JOptionPane.showMessageDialog(this,
            mensajeAyuda,
            "Ayuda - CiberDefender Suite",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    private void salirAplicacion() {
        int respuesta = JOptionPane.showConfirmDialog(this,
            "Estas seguro de que deseas salir de CiberDefender Suite?",
            "Confirmar Salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}