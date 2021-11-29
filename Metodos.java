
//Mateus Roberto Algayer - 23/11/2021 = Consistencia e Pedaco 
package testes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.text.JTextComponent;

public class Metodos{
  
  //Mateus Roberto Algayer - 23/11/2021
  public static Boolean Consistencia(Boolean pGeraAviso,JComponent ...wComps){
    
    for(JComponent wComp : wComps){
      
      if((wComp instanceof JTextComponent wCompTexto)) {
        
        if(wCompTexto.getText().trim().equals("")){
          
          if(pGeraAviso) Aviso(wComp.getTopLevelAncestor().getName(), "campo '"+wComp.getName()+"' inválido");
          
          wComp.setBackground(Color.YELLOW);
          wComp.requestFocus();
          return false; 
        }

        wComp.setBackground(Color.WHITE);
      } 
      
    }
    
    return true; 
  }
  
  //Mateus Roberto Algayer - 23/11/2021
  public static String Pedaco(String pLinha, String pDelimita, int pNumString){
    
    Boolean wContinua = true;
    String wParte = "";
    int wCiclo = 0;
    int wIdxIni = 0, wIdx = 0;
    
    if(!pLinha.contains(pDelimita)) return "";
    if(pNumString == 0) return "";
    
    wIdx = pLinha.indexOf(pDelimita, wIdx);
    
    //mateus;roberto;algayer
    
    while(wContinua){
      
      if(wIdx == -1){
        wContinua = false;
        wIdx = pLinha.length();
      }
      
      wCiclo++;
      
      if(wCiclo == pNumString){
        
        wParte = pLinha.substring(wIdxIni, wIdx);
        break;
      }
      
      wIdxIni = wIdx+pDelimita.length();
      wIdx = pLinha.indexOf(pDelimita, wIdx + pDelimita.length());
      
    }
    return wParte;
  }
  
  //Mateus Roberto Algayer - 23/11/2021
  public static void GeraConsistenciaCampos(JRootPane pComps){
    
    ArrayList<Component> wListaComps = new ArrayList<>();
    
    wListaComps.addAll(PegaTodosComponentes(pComps));
    
    //Pega todos os componentes na lista e abre uma stream
    //então ele filtra pelos componentes que são instancias de JTextComponent 
    //ordenando cada e criando um evento KeyListener com o método "Consistencia"
    wListaComps.stream().filter(wComp -> (wComp instanceof JTextComponent)).forEachOrdered(wComp -> {
      
      ((JTextComponent)wComp).addKeyListener(new java.awt.event.KeyAdapter() {
        
        @Override
        public void keyReleased(java.awt.event.KeyEvent evt) {
          
          Consistencia(Boolean.FALSE, ((JTextComponent)wComp));
        }
        
      }); 
    });
  }
  
  //Mateus Roberto Algayer - 23/11/2021
  public static ArrayList<Component> PegaTodosComponentes(final Container pComp){
    ArrayList<Component> wListaComps = new ArrayList<>();
    
    Component[] wComps = pComp.getComponents();
    
    for(Component wComp: wComps){
      wListaComps.add(wComp);
      
      //se wComp é um container, chama PegaTodosComponentes recursivamente
      if(wComp instanceof Container container) wListaComps.addAll(PegaTodosComponentes(container));
    }
    
    return wListaComps;
  }
  
  //Mateus Roberto Algayer - 26/11/2021
  public static void Erro(String pTitulo, String pMsg){
    JOptionPane.showMessageDialog(null, pMsg, pTitulo, JOptionPane.ERROR_MESSAGE);
  }
  
  //Mateus Roberto Algayer - 26/11/2021
  public static void Aviso(String pTitulo, String pMsg){
    JOptionPane.showMessageDialog(null, pMsg, pTitulo, JOptionPane.WARNING_MESSAGE);
  }
  
  //Mateus Roberto Algayer - 26/11/2021
  public static boolean CriaTela(Window pTela, boolean pModal){
    try {
      if(pModal) ((JDialog)pTela).setModal(true);

      pTela.setVisible(true);


    } catch (Exception e) {
      Erro("Erro!", e.getMessage());
      
      return false;
    }
    return true;
  }
}
