package me.andrewlod.indexador;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InterfaceGrafica {
	private static int index;
	private static double numeroPag;
	private static ArrayList<Diretorio> dirs;
	private static String STOPWORDS_FILE,FILES_PATH;
	private static Documento doc;
	private static JFrame frame;
	private static FileDialog fd;
	public InterfaceGrafica(ArrayList<Diretorio> d, String stop, String filesP) {
		dirs = d;
		STOPWORDS_FILE = stop;
		FILES_PATH = filesP;
		doc = new Documento("", STOPWORDS_FILE);
		frame = new JFrame("Sistema de indexador");     
	    frame.setSize(1000,500);  
	    frame.setLayout(null);  
	    frame.setVisible(true);
	    fd = new FileDialog(frame, "Escolha um arquivo", FileDialog.LOAD);
	    fd.setDirectory(FILES_PATH);
	    fd.setFile("*.txt");

	}
	
	public void FramePrincipal(){
        JButton remove = new JButton( "Remover um Documento" );  
        JButton recupera = new JButton( "Recupera um Documento" ); 
        JButton adiciona = new JButton( "Adiciona novo documento" ); 
        JButton sair = new JButton( "Sair" ); 
        
        remove.setBounds(110,150,200,30); 
        remove.addActionListener((ActionEvent e) -> {
        	InterfaceRemoveDoc(fd);}); 
        
        recupera.setBounds(360,150,200,30); 
        recupera.addActionListener((ActionEvent e) -> {
        	InterfaceRecuperar();}); 
        
 
        
        adiciona.setBounds(610,150,200,30); 
        adiciona.addActionListener((ActionEvent e) -> {
        	IntefaceAddDoc(fd,doc);}); 
        
        sair.setBounds(800,400,150,30); 
        sair.addActionListener((ActionEvent e) -> {
            frame.setVisible(false);}); 
    
        JLabel nomes[] = new JLabel[3]; 
        nomes[0] = new JLabel("Desenvolvedores: ");
        nomes[1] = new JLabel("André de Macedo ");  
        nomes[2] = new JLabel("Guilherme Solski "); 
    
        nomes[0].setBounds(10,390, 120,30);
        nomes[1].setBounds(10,410, 120,30);  
        nomes[2].setBounds(10,430, 120,30); 
        
        frame.add(remove);
        frame.add(recupera);
        frame.add(sair);
        frame.add(adiciona);
        for (JLabel i : nomes)
            frame.add(i); 
    }
	public static void IntefaceAddDoc(FileDialog fd,Documento doc){
        JDialog dialog = new JDialog();
        dialog.setLayout(null);
        dialog.setTitle("Adicionar Documento");
        JButton sair = new JButton( "Sair" );
        JButton pesquisa = new JButton( "Procure um documento" );
        JTextField nameDoc = new JTextField("Entre com o nome do arquivo");
        JTextArea textDoc = new JTextArea("Entre com o texto do documento");
        JButton addDocB = new JButton( "Adicionar documento" );

        nameDoc.setBounds(50,100,300,25); 
        textDoc.setBounds(50,150,300,100);
        
        addDocB.setBounds(100,270,200,25); 
        addDocB.addActionListener((ActionEvent e) -> {
        	Func_addDoc(nameDoc.getText()+".txt",textDoc.getText());});
        
        sair.setBounds(350,400,100,25); 
        sair.addActionListener((ActionEvent e) -> {
        	dialog.setVisible(false);}); 
         
        pesquisa.setBounds(100,300,200,25); 
        pesquisa.addActionListener((ActionEvent e) -> {
            fd.setVisible(true);
            String fileName = fd.getFile();
            String filePath = fd.getDirectory() + fileName;
            String text = doc.read(filePath);
            Func_addDoc(fileName,text);
        }); 
        
        dialog.add(sair);
        dialog.add(addDocB);
        dialog.add(nameDoc);
        dialog.add(textDoc);
        dialog.add(pesquisa);
        dialog.setSize(500,500);
        dialog.setVisible(true);
        

    }

    public static void InterfaceRemoveDoc(FileDialog fd){
        JDialog dialog = new JDialog();
        dialog.setLayout(null);
        dialog.setTitle("Remover Documento");
        JButton sair = new JButton( "Sair" );
        JButton remove = new JButton( "Procure um documento para remover" );
        
        sair.setBounds(350,400,100,25); 
        sair.addActionListener((ActionEvent e) -> {
            dialog.setVisible(false);}); 
        
        remove.setBounds(50,210,300,25); 
        remove.addActionListener((ActionEvent e) -> {
            fd.setVisible(true);
            String fileName = fd.getFile();
            String filePath = fd.getDirectory() + fileName;
            Func_removerDoc(filePath,fileName);
        }); 
        
        dialog.add(sair);
        dialog.add(remove);
        dialog.setSize(500,500);
        dialog.setVisible(true);

    }
    public static void InterfaceRecuperar(){
        JDialog dialog = new JDialog();
        dialog.setLayout(null);
        dialog.setTitle("Recuperar");
        JButton sair = new JButton( "Sair" );
        JTextField pesquisa = new JTextField("Entre com a sua pesquisa ");
        JButton pesquisarDocs = new JButton( "Pesquisar" );
        
        pesquisarDocs.setBounds(100,250,200,25); 
        pesquisarDocs.addActionListener((ActionEvent e) -> {
        	dialog.setVisible(false);
        	Recuperar_showDocs(pesquisa.getText());}); 
        
        pesquisa.setBounds(50,210,300,25); 
        
        sair.setBounds(350,400,100,25); 
        sair.addActionListener((ActionEvent e) -> {
            dialog.setVisible(false);}); 
        
        dialog.add(sair);
        dialog.add(pesquisarDocs);
        dialog.add(pesquisa);
        dialog.setSize(500,500);
        dialog.setVisible(true);
        
    }
    public static void Func_addDoc(String name,String text){
    	try {
    		ArrayList<Double> similaridades = new ArrayList<Double>();
    		String[] stopwords = Separador.separar(new Documento("", STOPWORDS_FILE).read());
    		
    		for(int i = 0; i < dirs.size(); i++) {
    			similaridades.add(dirs.get(i).calcularSimilaridade(text, stopwords));
    		}
    		
    		int index = Util.maiorIndex(similaridades);
    		String path = dirs.get(index).getPath() + "\\"+ name;
    		if(name.equals(null))
    			JOptionPane.showMessageDialog(null, "Error.","Remover", JOptionPane.INFORMATION_MESSAGE);
    		else if(Util.insereArqTexto(path,text)) 
    		JOptionPane.showMessageDialog(null, "Documento adicionado com sucesso ao caminho " + path,"Adicionar", JOptionPane.INFORMATION_MESSAGE);
    		else
    			JOptionPane.showMessageDialog(null, "Documento já existe","Adicionar", JOptionPane.INFORMATION_MESSAGE);
    	}catch (Exception e) {
    		JOptionPane.showMessageDialog(null, "Error.","Adicionar", JOptionPane.INFORMATION_MESSAGE);
    	}
    } 

    
    public static void Recuperar_showDocs(String pesquisa){
    	JDialog dialog = new JDialog();
        dialog.setTitle("Lista de arquivos");
        dialog.setLayout(null);
        JButton sair = new JButton( "Sair" );
        JButton abrir = new JButton( "Abrir arquivo" );
        JButton anvacar = new JButton( "Avançar Pagina" );
        JButton voltar = new JButton( "Voltar Pagina" );
        ArrayList<JComboBox<String>> combos = new ArrayList<>();
        index = 0;
        ArrayList<HashMap<String, Integer>> listWordsFile = new ArrayList<>();
		//String[] stopwords = Separador.separar(doc.read());

		ArrayList<String> words = Separador.fazerDicionarioString(Separador.separar(pesquisa));
		String[] stopwords = Separador.separar(new Documento("", STOPWORDS_FILE).read());
		Diretorio d = new Diretorio(FILES_PATH);
		dirs = new ArrayList<Diretorio>();
		dirs.add(d);
		dirs.addAll(d.getSubdirs());
		for(int i = 0; i < dirs.size(); i++) {
			for (int j = 0; j < dirs.get(i).getSizeFiles(); j++)
				listWordsFile.add(dirs.get(i).getDictAtIndex(j,stopwords));
		}

		Dicionario dict = new Dicionario(listWordsFile);
		double[] similaridades = dict.getSimilaridades(words);
		ArrayList<Documento> docs = new ArrayList<Documento>();
		for(int i = 0; i < dirs.size(); i++) {
			docs.addAll(dirs.get(i).getFiles());
		}
		LinkedHashMap<String, Double> h = Util.listaIndexados(docs, similaridades);
		if(h.size() == 0) {
			h.clear();
			combos.clear();
			dialog.setVisible(false);
			JOptionPane.showMessageDialog(null, "Não existem um dococumento que tenha sua pesquisa ","Recuperar documento", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int termosPP = 3;
		numeroPag =  (int) Math.ceil(((double)h.size() / termosPP)); 
        for(int i = 0;i<numeroPag;i++) 
        	combos.add(new JComboBox<>());
        
        for(int i = 0;i<combos.size();i++) {
        	combos.get(i).setBounds(25,125,450,25);
        	combos.get(i).setVisible(false);
        }
        
        combos.get(index).setVisible(true);
        for(int i = 0; i < h.size(); i++) {
        	if(i >= termosPP) {
        		termosPP += termosPP;
        		index += 1;
        		combos.get(index).addItem(h.keySet().toArray()[i].toString());
        	}
        	else 
        		combos.get(index).addItem(h.keySet().toArray()[i].toString());
        	
        }
        index = 0;
        anvacar.setBounds(340,200,125,25); 
        anvacar.addActionListener((ActionEvent e) -> {
        	combos.get(index).setVisible(false);
        	if(index < numeroPag-1 )
        		index+=1;
        	combos.get(index).setVisible(true);
        });
        
        voltar.setBounds(40,200,125,25); 
        voltar.addActionListener((ActionEvent e) -> {
        	combos.get(index).setVisible(false);
        	if(index != 0 )
        		index-=1;
        	combos.get(index).setVisible(true);
        }); 
        

        
        abrir.setBounds(190,200,125,25); 
        abrir.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, new Documento("", combos.get(index).getSelectedItem().toString()).readBR(), combos.get(index).getSelectedItem().toString(), JOptionPane.INFORMATION_MESSAGE);}); 
        
        for(int i = 0;i<combos.size();i++)
        	dialog.add(combos.get(i));
        
        sair.setBounds(350,400,100,25); 
        sair.addActionListener((ActionEvent e) -> {
        	h.clear();
        	combos.clear();
            dialog.setVisible(false);}); 
        dialog.add(sair);
        dialog.add(voltar);
        dialog.add(anvacar);
        dialog.add(abrir);
        dialog.setSize(500,500);
        dialog.setVisible(true);
       
    }
    public static void Func_removerDoc(String path,String name){
    	try {
    		if(name.equals(null))
    			JOptionPane.showMessageDialog(null, "Error.","Remover", JOptionPane.INFORMATION_MESSAGE);
    		else {
    		Util.removeArqTexto(path);
    		JOptionPane.showMessageDialog(null, "O documento " + name + "  Foi removido com sucesso","Remover", JOptionPane.INFORMATION_MESSAGE);
    		}
    	}catch (Exception e) {
    		JOptionPane.showMessageDialog(null, "Error.","Remover", JOptionPane.INFORMATION_MESSAGE);
    	}
    }

	
}
