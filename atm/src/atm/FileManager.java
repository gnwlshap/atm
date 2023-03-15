package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileManager {
	private File file;
	private FileWriter fw;
	private FileReader fr;
	private BufferedReader br;
	
	public void saveFile(String name, String file) {
		this.file = new File(name);
		
		try {
			this.fw = new FileWriter(this.file);
			
			this.fw.write(file);
			
			this.fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 

	public String loadFile(String name) {
		this.file = new File(name);
		String dataLine = "";
		if(this.file.exists()) {
			try {
				this.fr = new FileReader(this.file);
				this.br = new BufferedReader(this.fr);
				
				while(this.br.ready())
					dataLine += this.br.readLine()+"\n";
				
				this.fr.close();
				this.br.close();
				
				System.out.println("...파일 로드");
				return dataLine;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataLine;
	}
}
