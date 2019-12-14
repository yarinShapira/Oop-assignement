package Ex1;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CompletionException;

import com.google.gson.Gson;


import Ex1.StdDraw;

/*
 * This class implements the functions interface.
 * It contains all the methods to draw the functions based on the classes we built before.
 * The object "Functions_GUI" is composed by a LinkedList that contains functions and an array that contains colors.
 */
public class Functions_GUI implements functions {
	private LinkedList<function> Functions;


	public static Color[] Colors = {Color.blue, Color.cyan, Color.MAGENTA, Color.ORANGE, Color.red, Color.GREEN, Color.PINK};


	/*
	 * 
	 */
	public Functions_GUI() {
		Functions = new LinkedList<function>();
	}


	public Functions_GUI(function e) {
		if (Functions == null) {
			Functions = new LinkedList<function>();
		}
		Functions.add(e);
	}


	@Override
	public int size() {
		return Functions.size();
	}


	@Override
	public boolean isEmpty() {
		return Functions.isEmpty();
	}


	@Override
	public boolean contains(Object o) {
		return Functions.contains(o);
	}


	@Override
	public Iterator<function> iterator() {
		Iterator<function> iterator = this.Functions.iterator();
		return iterator;
	}


	@Override
	public Object[] toArray() {
		return Functions.toArray();
	}


	@Override
	public <T> T[] toArray(T[] a) {
		return Functions.toArray(a);
	}


	@Override
	public boolean add(function e) {
		return this.Functions.add(e);
	}


	@Override
	public boolean remove(Object o) {
		return Functions.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return Functions.containsAll(c);
	}


	@Override
	public boolean addAll(Collection<? extends function> c) {
		return this.Functions.addAll(c);
	}


	@Override
	public boolean removeAll(Collection<?> c) {
		return Functions.removeAll(c);
	}


	@Override
	public boolean retainAll(Collection<?> c) {
		return Functions.retainAll(c);
	}


	@Override
	public void clear() {
		this.Functions.clear();
	}


	/*
	 * Compares between this Functions_GUI and another given Functions_GUI.
	 * Returns true or false.
	 */
	public boolean equals(Functions_GUI fg) {
		if(this.toString().equals(fg.toString())) {
			return true;
		}
		return false;
	}


	public String toString() {
		String ans = "";
		Iterator iterator = this.iterator();
		while(iterator.hasNext()) {
			function temp = (function) iterator.next();
			ans = ans + "[" + temp.toString() + "]";
			if(iterator.hasNext()) {
				ans = ans + ", ";
			}
		}
		return ans;
	}


	@Override
	public void initFromFile(String file) throws IOException {
		if (this.Functions == null) {
			Functions_GUI temp = new Functions_GUI();
			this.Functions = temp.Functions;
		}
		String line = "";
		function toAdd = null;
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(file));
			while ((line = buffer.readLine()) != null) {
				int index = 0;
				int space = 0;
				while(index < line.length()) {
					if (line.charAt(index) == ' ') {
						space++;
					}
					if (space == 2) {
						index++;
						break;
					}
					index++;
				}
				line = line.substring(index);
				if((line.charAt(0) >= '0' && line.charAt(0) <= '9') || line.charAt(0) == 'x' || line.charAt(0) == '-' || line.charAt(0) == '+') {
					toAdd = new Polynom(line);
				}
				else {
					toAdd = new ComplexFunction(line);
				}
				this.add(toAdd);
			}

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("could not read file");
		}
	}


	@Override
	public void saveToFile(String file) throws IOException {
		Iterator<function> iter = this.iterator();
		try {
			PrintWriter pw = new PrintWriter(new File(file));
			StringBuilder sb = new StringBuilder();
			while(iter.hasNext()) {
				function temp = iter.next();
				int index = Functions.indexOf(temp);
				sb.append(Colors[index%Colors.length].toString() + " ");
				sb.append("f(x)= ");
				sb.append(temp.toString());
				sb.append("\n");
			}
			pw.write(sb.toString());
			pw.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return;
		}
	}


	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		StdDraw.setCanvasSize(width, height);
		double minX = rx.get_min(), maxX = rx.get_max(),sizX = maxX - minX;
		double minY = ry.get_min(), maxY = ry.get_max(),sizY = maxY - minY;
		StdDraw.setXscale(minX, maxX);
		StdDraw.setYscale(minY, maxY);
		StdDraw.setPenColor(Color.LIGHT_GRAY);
		//vertical lines
		int vertical = (int) minX;
		for (int i = 0; i <= sizX; i++) {
			StdDraw.line(vertical, minY, vertical, maxY);
			vertical = (int) (vertical + (sizX / sizX));
		}
		//horizontal  lines
		int horizontal = (int) minY;
		for (double i = 0; i <= sizY; i++) {
			StdDraw.line(minX, horizontal, maxX, horizontal);
			horizontal = (int) (horizontal + (sizY / sizY));
		}
		//x axis
		vertical = (int) minX;
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.005);
		StdDraw.line(minX,0, maxX, 0);
		StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 15));
		for (int i = 0; i <= sizX; i++) {
			StdDraw.text(vertical, -0.15, "" + vertical);
			vertical =  vertical + 1;
		}
		//y axis
		horizontal = (int) minY;
		StdDraw.line(0,minY, 0, maxY);
		for (int i = 0; i <= sizY; i++) {
			StdDraw.text(-0.15, horizontal, "" + horizontal);
			horizontal =  horizontal + 1;
		}
		//draw functions
		Iterator<function> iterator = this.iterator();
		double steps = sizX / resolution;
		while(iterator.hasNext()) {
			function temp = iterator.next();
			int index = Functions.indexOf(temp);
			StdDraw.setPenColor(Colors[index%Colors.length]);
			for(double i = minX; i <= maxX; i = i + steps) {
				StdDraw.line(i, temp.f(i), i + steps, temp.f(i + steps));
			}
		}
	}


	@Override
	public void drawFunctions(String json_file) {
		try {
			Gson gson = new Gson();
			FileReader reader = new FileReader(json_file);
			GUI_params gp = gson.fromJson(reader,GUI_params.class);
			Range rx = new Range(gp.Range_X[0], gp.Range_X[1]);
			Range ry = new Range(gp.Range_Y[0], gp.Range_Y[1]);
			this.drawFunctions(gp.Width, gp.Height, rx, ry, gp.Resolution);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	class GUI_params{
		public int Width;
		public int Height;
		public int Resolution;
		public double []Range_X;
		public double []Range_Y;
	}
}