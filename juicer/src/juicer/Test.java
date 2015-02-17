package juicer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class Test {
	
	@SuppressWarnings("serial")
	public static class MyException extends Exception{
		public MyException(String message){
			super(message);
		}
	}

	public static String filepath = "src/juicer/";
	static ArrayList<TreeSet<String>> list  = new ArrayList<TreeSet<String>>();
	static TreeSet<String>components = new TreeSet<String>();
	static ArrayList<String>allComponents = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException, MyException {
		try{
			handleInput();
		}
		catch(MyException e){
			System.out.println(e.toString());
		}		
		
		try{
			task1();
			task2();
			sortCollection();
			task3();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static int countWashes(ArrayList<TreeSet<String>> juices){
		ArrayDeque<TreeSet<String>>queue = new ArrayDeque<TreeSet<String>>(juices);
		TreeSet<String>head = queue.peek(), temp = null;
		do{
			temp = queue.poll();
			for (Iterator<TreeSet<String>>juice=queue.iterator();juice.hasNext();){
				if (juice.next().containsAll(temp)){
					juice.remove();
				}
			}
			queue.addLast(temp);
			
		}while(!queue.peek().equals(head));
		
		return queue.size();
	}

	@SuppressWarnings("resource")
	public static void handleInput() throws FileNotFoundException,MyException{
		Scanner sc = new Scanner(new FileReader(filepath + "juice1.in"));
		
		if (!sc.hasNext())
			throw new MyException("input file is empty");
		
		while(sc.hasNextLine()){
			StringTokenizer strtok = new StringTokenizer(sc.nextLine());
			TreeSet<String> juice = new TreeSet<String>();
			while (strtok.hasMoreTokens()){
				String temp = strtok.nextToken();  
				if (temp.length() <= 30)
					juice.add(temp);
			}
			list.add(juice);
			components.addAll(juice);
			allComponents.addAll(juice);
		}
		sc.close();
	}

	public static void task1() throws IOException{
		FileWriter writer;
		writer = new FileWriter(filepath+"juice1.out");
		writer.write(components.toString());
		writer.close();
	}
	
	public static void task2() throws IOException{
		FileWriter writer;
		writer = new FileWriter(filepath+"juice2.out");
		Collections.sort(allComponents,(fruit1,fruit2)->fruit1.compareTo(fruit2));
		writer.write(allComponents.toString());
		writer.close();
	}
	
	public static void task3() throws IOException{
		FileWriter writer;
		writer = new FileWriter(filepath+"juice3.out");
		writer.write(Integer.toString(countWashes(list)));
		writer.close();

	}
	
	public static void sortCollection(){
		Thread subThread = new Thread(()->
		{	
			Collections.sort(list, (juice1,juice2)->Integer.compare(juice1.size(), juice2.size()));
		});
		subThread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}




