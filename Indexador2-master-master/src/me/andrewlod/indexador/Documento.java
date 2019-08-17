package me.andrewlod.indexador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Documento extends File{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	
	
	public Documento(String _nome, String arg0) {
		super(arg0);
		nome = _nome;
		// TODO Auto-generated constructor stub
	}
	public String getNome() {
		return nome;
	}
	public String read() {
		File f = new File(this.getPath());
		String s = "";
		FileReader reader = null;
		BufferedReader bfr = null;
		
		try {
			reader = new FileReader(f);
			bfr = new BufferedReader(reader);
			String line;
			
			while((line=bfr.readLine())!= null) {
				s += line + " ";
			}
			bfr.close();
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	public String readBR() {
		File f = new File(this.getPath());
		String s = "";
		FileReader reader = null;
		BufferedReader bfr = null;
		
		try {
			reader = new FileReader(f);
			bfr = new BufferedReader(reader);
			String line;
			
			while((line=bfr.readLine())!= null) {
				s += line + "\n";
			}
			bfr.close();
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	public String read(String path) {
		File f = new File(path);
		String s = "";
		FileReader reader = null;
		BufferedReader bfr = null;
		
		try {
			reader = new FileReader(f);
			bfr = new BufferedReader(reader);
			String line;
			
			while((line=bfr.readLine())!= null) {
				s += line + " ";
			}
			bfr.close();
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	public HashMap<String, Integer> getDicionario(){
		return Separador.fazerDicionario(Separador.separar(this.read()));
	}
	public HashMap<String, Integer> getDicionario(String[] stopwords){
		return Separador.fazerDicionario(Separador.separar(this.read(), stopwords));
	}
}
