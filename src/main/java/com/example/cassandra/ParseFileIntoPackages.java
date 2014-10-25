package com.example.cassandra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;

public class ParseFileIntoPackages extends Thread{
	
	FileInfoBean FIB;
	Pack pack;
	
	public ParseFileIntoPackages(FileInfoBean FIB){
		this.pack = new Pack(FIB);
		this.FIB = FIB;
		this.start();
	}

	@Override
	public void run() {
		Charset encoding = Charset.defaultCharset();
        handleFile(this.FIB.getFile(), encoding);
		
	}

    private void handleFile(File file, Charset encoding){
    	/* setup stream and readers */
        try (InputStream in = new FileInputStream(file);
             Reader reader = new InputStreamReader(in, encoding);
             Reader buffer = new BufferedReader(reader)) {
             handleCharacters(buffer);
        } catch (FileNotFoundException fnfe) {
        	System.err.println(fnfe.getCause());
        	System.err.println(fnfe.getStackTrace());
		} catch (IOException ioe) {
			System.err.println(ioe.getCause());
			System.err.println(ioe.getStackTrace());
		}
    }

	private void handleCharacters(Reader reader) {
		int r;
        StringBuffer sb = new StringBuffer();
        
        try {
			while ((r = reader.read()) != -1) {
			    char ch = (char) r;
			    /* als sequentie past bij te importeren Values gaan opslaan tot er een pattern match is 
			       --> sequentie <-- ( [0-9]*,
			       anders bij nieuw startpatroon empty StringBuilder en start nieuwe test sequentie
			       bij x aantal in String[] naar ThreadPool voor opslag in Cassandra */
			    
			    if(ch == '(' || this.FIB.isRecording() == true){
			    	this.FIB.setRecording(true);
			    	sb.append(ch);
			    	if(ch=='\''){
			    		this.FIB.setInText((this.FIB.isInText()==false) ? true : false);
			    	}if(ch == ')' && this.FIB.isInText()==false){
			    		Matcher m = this.FIB.getPattern().matcher(sb.toString());
			    		if (m.find()) {
			    			//System.out.println(stringsPerPackCounter+" : "+stringsPerPack+" FIND>> "+sb.toString());
			    			if(this.FIB.getStringsPerPackCounter()<this.FIB.getStringsPerPack()){
			    				/* toevoegen aan String[] voor TreadPool*/
			    				this.pack.getPackStrings()[this.FIB.getStringsPerPackCounter()] = sb.toString();
			    				this.FIB.setStringsPerPackCounter(this.FIB.getStringsPerPackCounter()+1);
			        			//System.out.println(Arrays.deepToString(pack));
			     			} 
			    			if(this.FIB.getStringsPerPackCounter()==this.FIB.getStringsPerPack()){
			     				/*Vol pack versturen naar ThreadPool voor verwerking naar Cassandra*/
			     				
			    				this.pack.setFIB(FIB);
			    				new SendPackageToPersistence(this.pack);
			    				this.FIB.setStringsPerPackCounter(0);
			     				
			    				//hier naar kijken
			    				this.pack.getPackStrings()[this.FIB.getStringsPerPackCounter()]= sb.toString();
			     				
			     				
			     				
			     				Thread.sleep((this.FIB.getDelay()));
			    			}
			         	}
			    		this.FIB.setRecording(false);
			    		sb.setLength(0);
			    	}
			    }
			}
			
			if(((r = reader.read()) == -1)){System.out.println("EOF");}
			
			if(((r = reader.read()) == -1) && this.FIB.getStringsPerPackCounter()<this.FIB.getStringsPerPack() && this.FIB.getStringsPerPackCounter() !=0){
				/*versturen naar ThreadPool voor verwerking naar cassandra terwijl pack 0 < stringsPerPackCounter < stringsPerPack */
				
				this.pack.setFIB(FIB);
				new SendPackageToPersistence(this.pack);
				this.FIB.setStringsPerPackCounter(0);
 
				//hier naar kijken
				this.pack.getPackStrings()[this.FIB.getStringsPerPackCounter()]= sb.toString();
				
				
 			}
			
			/*bij einde bestand schakelaar op uit zetten en wachten tot alle Threads klaar zijn op af te sluiten*/
			if(((r = reader.read()) == -1)){this.FIB.setMore(false);}
			
		} catch (IOException | InterruptedException ioe2) {
			System.err.println(ioe2.getCause());
			System.err.println(ioe2.getStackTrace());
		}
    }

}
