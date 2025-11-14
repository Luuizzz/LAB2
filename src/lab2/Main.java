/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab2;

/**
 *
 * @author juluc
 */
public class Main {

    
    public static void main(String[] args) {
        
        //frame.setVisible(true);
        FramePrincipal frame1 = new FramePrincipal();
       frame1.setVisible(true);
       
    }
        public static int puntajePhishHunter = 0;
    public static int puntajeFirewallMatrix = 0;
    public static int puntajeCryptoBreaker = 0;
    public static int puntajeSecureLogAnalyzer = 0;
    public static int calcularPuntajeGlobal(/*int puntajePhishHunter, int puntajeFirewallMatrix,int puntajeCryptoBreaker,int puntajeSecureLogAnalyzer*/) {
    return puntajePhishHunter+ puntajeFirewallMatrix + puntajeCryptoBreaker + puntajeSecureLogAnalyzer;
}
}
