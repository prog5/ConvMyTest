import java.util.Formatter;
import java.util.Scanner;

public class Conv {
	static Formatter x;
	static Scanner scn;
	public static void main(String[] args) {
		try{
			x = new Formatter("res//1.txt");
			scn = new Scanner(System.in);
			System.out.println("Сколько?");
			int a = (int)Double.parseDouble(scn.next()) ;
			System.out.println("Сколько 2?");
			String b = scn.next();
			System.out.println("Сколько 3?");
			String c = scn.next();
			x.format("Строка %s Строка %d Строка %s", b,a,c);
			x.close();
		}catch(Exception e){};
	}
		
}
