package COM.T56;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.exec.OS;

public class OCR {  
	static private final String LANG_OPTION = "-l";  //英文字母小写l，并非数字1  
	static private final String EOL = System.getProperty("line.separator");  
	static private String tessPath = "C://Program Files (x86)//Tesseract-OCR";  
    //private String tessPath = new File("tesseract").getAbsolutePath();  
      
    public static String recognizeText(File imageFile)throws Exception{  
        File outputFile = new File(imageFile.getParentFile(),"output");  
        StringBuffer strB = new StringBuffer();  
        List<String> cmd = new ArrayList<String>();  

        cmd.add(tessPath+"//tesseract");  
        cmd.add("");  
        cmd.add(imageFile.getAbsolutePath());  
        cmd.add("");  
        cmd.add(outputFile.getAbsolutePath());  
        //cmd.add(LANG_OPTION);  
        //cmd.add("chi_sim");  
        //cmd.add("eng");  
          
        ProcessBuilder pb = new ProcessBuilder();  
        pb.directory(imageFile.getParentFile());  
          
        pb.command(cmd);  
        pb.redirectErrorStream(true);  
        
        Process process = pb.start();  
        //tesseract.exe 1.jpg 1 -l chi_sim  
        int w = process.waitFor();  
          
        if(w==0){  
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath()+".txt"),"UTF-8"));  
              
            String str;  
            while((str = in.readLine())!=null){  
                strB.append(str).append(EOL);  
            }  
            in.close();  
        }else{  
            String msg;  
            switch(w){  
                case 1:  
                    msg = "Errors accessing files.There may be spaces in your image's filename.";  
                    break;  
                case 29:  
                    msg = "Cannot recongnize the image or its selected region.";  
                    break;  
                case 31:  
                    msg = "Unsupported image format.";  
                    break;  
                default:  
                    msg = "Errors occurred.";  
            }  
        }  
        new File(outputFile.getAbsolutePath()+".txt").delete();  
        return strB.toString();  
    }  
}  