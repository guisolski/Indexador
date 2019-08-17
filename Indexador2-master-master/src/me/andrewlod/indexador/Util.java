package me.andrewlod.indexador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Util {
	public static double log(double x, double base) {
		return (Math.log(x) / Math.log(base));
	}
	public static double sum(double[] v) {
		double s = 0;
		for(double i : v) {
			s+=i;
		}
		return s;
	}
	public static double[] multiEsc(double[] u, double[] v) {
		double[] resultado = new double[u.length];
		
		for(int i = 0; i < u.length; i++) {
			resultado[i] = u[i] * v[i];
		}
		
		return resultado;
	}
	public static int maiorIndex(ArrayList<Double> valores) {
		int maior = 0;
		for(int i = 1; i < valores.size(); i++) {
			if(valores.get(i) > valores.get(maior)){
				maior = i;
			}
		}
		return maior;
	}
	public static ArrayList<Double> toArrayList(double[] array){
		ArrayList<Double> convertido = new ArrayList<>();
		for(double o : array) {
			convertido.add(o);
		}
		return convertido;
	}
	public static MapStrDouble getMaior(ArrayList<Double> valores, ArrayList<Documento> docs) {
		int index = 0;
		for(int i = 0; i < valores.size(); i++) {
			if(valores.get(i) > valores.get(index)) {
				index = i;
			}
		}
		if(valores.get(index) == 0) {
			return null;
		}
		MapStrDouble h = new MapStrDouble(docs.get(index).getPath(), valores.get(index));
		return h;
	}
	public static boolean insereArqTexto(String path,String texto) throws IOException {
		String fullPath = path;
		String[] seprador = texto.split("\n");
		try {
			File teste = new File(fullPath);
			if(!teste.exists()) {
			FileOutputStream fos = new FileOutputStream(fullPath);
			PrintStream ps = new PrintStream(fos);
			
			for(String i : seprador) {
				ps.print(i);
				ps.println();
			}
			fos.close();
			return true;
			}
			return false;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean removeArqTexto(String path) {
		File file = new File(path);
		return file.delete();
	}
	
	public static LinkedHashMap<String,Double> listaIndexados(ArrayList<Documento> docs, double[] sims) {
		LinkedHashMap<String,Double> list = new LinkedHashMap<String, Double>();
		ArrayList<Double> simi = toArrayList(sims);
		int s = docs.size();
		for(int i = 0; i < s; i++) {
			if(getMaior(simi, docs) == null) continue;
			MapStrDouble temporario = getMaior(simi, docs);
			list.put(temporario.getString(), temporario.getDouble());
			int index = simi.indexOf(temporario.getDouble());
			simi.remove(index);
			docs.remove(index);
		}
		return list;
	}
}
