package COM.T56;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	
    	String code = OCR.recognizeText(new File("D:/test2.png"));
        System.out.println( "Hello World!:"+code );
    }
}
