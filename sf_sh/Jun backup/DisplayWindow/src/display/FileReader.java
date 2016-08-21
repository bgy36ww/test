package display;

import java.io.File;
import java.util.Scanner;

public class FileReader {
	private Scanner sc;
	private File file;
	private int []Rmap;
	
	public int[] getRmap() {
		return Rmap;
	}
	
	public FileReader(){
		file = new File("/Users/June/Documents/workspace/DisplayWindow/src/display/tst");
		try{
			sc = new Scanner(file);
			int n=sc.nextInt();
			Rmap=new int[4*n];
			for(int i=0;i<n;i++){
				Rmap[4*i] = sc.nextInt();
				Rmap[4*i+1] = sc.nextInt();
				Rmap[4*i+2] = sc.nextInt();
				Rmap[4*i+3] = sc.nextInt();
			}
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(Rmap);
	}
	
}
