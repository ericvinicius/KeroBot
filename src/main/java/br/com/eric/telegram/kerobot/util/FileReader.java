package br.com.eric.telegram.kerobot.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
	
	public static List<String> pokemons() {
		ArrayList<String> list = new ArrayList<String>();
		Scanner s = null;
		try {
			s = new Scanner(new File("filepath"));
			while (s.hasNext()){
				list.add(s.next());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return list;
	}

}
