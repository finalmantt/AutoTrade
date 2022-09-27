package autotrade;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HTMLCreation {
	String path= "D://web";
	
	public void createFile(String str,String filename ) {
		String html= "<html>\r\n" + 
				"  <head>\r\n" + 
				"    <link rel=\"stylesheet\" href=\"style.css\" />\r\n" + 
				"  </head>\r\n" + 
				"  <body>\r\n" + 
				"    Hello world\r\n" + 
				"    <div class=\"test\">"+str+"</div>\r\n" + 
				"  </body>\r\n" + 
				"</html>\r\n" + 
				"";
		if (filename == ""){
			filename = path + "//filename.html";
		}
		File theDir = new File(path);
		if (!theDir.exists()){
		    theDir.mkdirs();
		}		
		try {
		      FileWriter myWriter = new FileWriter(filename);
		      myWriter.write(html);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }


	}
	
	public void openHtml(String url ) {

		File htmlFile = new File(url);

		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
