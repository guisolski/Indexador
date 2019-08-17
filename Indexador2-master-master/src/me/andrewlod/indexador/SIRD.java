package me.andrewlod.indexador;


import java.util.ArrayList;


public class SIRD {
	private static final String FILES_PATH = "src\\me\\andrewlod\\indexador\\files\\";
	private static final String STOPWORDS_FILE = "src\\me\\andrewlod\\indexador\\stopwords\\pt_br.txt";
	private static ArrayList<Diretorio> dirs;
	private static Diretorio d;

	public static void main(String[] args) {
		d = new Diretorio(FILES_PATH);
		dirs = new ArrayList<Diretorio>();
		dirs.add(d);
		dirs.addAll(d.getSubdirs());

	    InterfaceGrafica ig = new InterfaceGrafica(dirs,STOPWORDS_FILE,FILES_PATH);
	    ig.FramePrincipal();

	}
}
