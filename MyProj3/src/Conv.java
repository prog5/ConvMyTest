import java.io.*;
import java.util.ArrayList;

class Answer {
	public String text;
	public String yes;
}

class Qwestion {
	public String text;
	public int idx;
	public Answer[] answers;

	public Qwestion(String text, int idx) {
		this.text = text;
		this.idx = idx;
		this.answers = new Answer[4];
		for (int i=0;i<=3;i++){
			this.answers[i] = new Answer();
		}
	}

	public void print() {
		System.out.println(this.text + ">> ");
		//for (String arg : args)
		//	System.out.print(arg + " << ");
	}
}

class TestList {
    private ArrayList<Qwestion> v = new ArrayList<Qwestion>();
    private void add(Qwestion obj) {
        if (! obj.text.isEmpty()){
        	v.add(obj);
        }
    }
    //private Qwestion get(int index) {
    //      return (Qwestion)v.get(index);
    //}
    public int size() {
          return v.size();
    }      
    public void addQwestion(String text){
    	int pos1 = text.indexOf("«");
		int pos2 = text.lastIndexOf("»");
		
		int pos3 = text.indexOf("(");
		int pos4 = text.indexOf(")");
		
		if (pos1>=0 & pos2>=0 & pos3>=0 & pos4>=0){
			//System.out.println(text.substring(pos3 + 1, pos4)+" "+text.substring(pos1 + 1, pos2-1));
			this.add(new Qwestion(text.substring(pos1 + 1, pos2),Integer.parseInt(text.substring(pos3 + 1, pos4)))); 
		}
    }
    public void addAnswer(String text){
    	int pos1 = text.indexOf("(");
		int pos2 = text.indexOf(",");
		
		int pos3 = text.indexOf(",");
		int pos4 = text.indexOf(")");
		
		int pos5 = text.indexOf("«");
		int pos6 = text.lastIndexOf(".");
		
		int pos7 = text.lastIndexOf(".");
		int pos8 = text.lastIndexOf("»");
		
		if (pos1>=0 & pos2>=0 & pos3>=0 & pos4>=0 & pos5>=0 & pos6>=0 & pos7>=0 & pos8>=0){
			int idxQwestion = Integer.parseInt(text.substring(pos1 + 1, pos2).trim());
			int idxAnswer = Integer.parseInt(text.substring(pos3 + 1, pos4).trim());
			String textAnswer = text.substring(pos5 + 1, pos6);
			String yesAnswer = text.substring(pos7 + 1, pos8);
			System.out.println("idxQwestion=["+idxQwestion+"] idxAnswer=["+idxAnswer+"] textAnswer=["+textAnswer+"] yesAnswer=["+yesAnswer+"]");
			
			for (int i=0;i<this.v.size();i++){
				Qwestion q = v.get(i);
				if (q.idx == idxQwestion){
	    			 q.answers[idxAnswer-1].text = textAnswer.trim();
	    			 q.answers[idxAnswer-1].yes = yesAnswer.trim();
	    		}
	        }
		}
    }
    
    public void ExportFile(String fileName) throws FileNotFoundException, UnsupportedEncodingException { // <- мы не можем контроллировать эти исключения здесь, ошибка не относится к данной функциональности и должна обрабатываться вызывающим методом.
		FileOutputStream fileOutputStream = new FileOutputStream(fileName);
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "windows-1251"));
		String yes = "1";
		String no = "0";
		try {
			for (int i=0;i<this.v.size();i++){
				Qwestion q = v.get(i);
				bufferedWriter.write("#"+q.text);
				bufferedWriter.newLine();
				for (int j=0;j<=3;j++){
					//System.out.println("["+q.answers[j].yes+"] i=["+i+"] j=["+j+"]");
					if (! q.answers[j].text.equals(no)) {
						if (q.answers[j].yes.equals(yes)){
							bufferedWriter.write("+ "+q.answers[j].text);
						}else{
							bufferedWriter.write("- "+q.answers[j].text);
						}
						bufferedWriter.newLine();	
					}
				}
				bufferedWriter.newLine();
			}	
			
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException ex) {
			// handle exception
		} finally { 
			try {
				bufferedWriter.close();
			} catch (IOException ex) {
			}
			
			try {
				fileOutputStream.close();
			} catch (IOException ex) {
			}
		}
	}
}

public class Conv {
	public static void main (String[] args) {
 
    	TestList testList = readArrayFromFile("D:\\work\\workspace\\MyProj3\\res\\q.txt"
    				,"D:\\work\\workspace\\MyProj3\\res\\a.txt");
    	
    	try {
			testList.ExportFile("D:\\work\\workspace\\MyProj3\\res\\e.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
 
    }
    
    public static TestList readArrayFromFile(String fileNameQ,String fileNameA)  {
    	TestList testList = new TestList();
    
        try {
        	File file = new File(fileNameQ);
        	FileInputStream fis = new FileInputStream(file);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis, "UTF8")); 
            String str;
            while ((str = bufferedReader.readLine()) != null) {
            	testList.addQwestion(str);
            }
            bufferedReader.close();
            fis.close();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
        	File file = new File(fileNameA);
        	FileInputStream fis = new FileInputStream(file);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis, "UTF8")); 
            String str;
            while ((str = bufferedReader.readLine()) != null) {
            	testList.addAnswer(str);
            }
            bufferedReader.close();
            fis.close();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testList;
    }


}