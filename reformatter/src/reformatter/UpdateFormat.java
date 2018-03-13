package reformatter;

import java.io.*;

public class UpdateFormat {
	
	public static void updateFormat(String filename, int justification, String outputName) {
		String line = null;
		
		 try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = new FileReader(filename);
	            BufferedReader bufferedReader = new BufferedReader(fileReader);
	            
	            int singleChar;
	            int whiteCount = 0;
	            while((singleChar = bufferedReader.read()) != -1) {
	            	if(Character.isWhitespace((char)singleChar)) {
	            		whiteCount++;
	            		
	            		if(line.length() >= 79) {
	            			if(whiteCount == 1) {//first white space at more than 80 chars means single word is greater than limit, simply write that word on one line
	            				WriteLine(line, 0, outputName); //leave justification at left to avoid truncation
	            			}else if(Character.isWhitespace(line.charAt(line.length()-1))) { //check if previous char is whitespace too
	            				continue; //double whitespace, just skip this one
	            			}else{ //more than 1 whitespace and 80 or characters, need to backtrack to previous whitespace and write all before it
	            				char checker = line.charAt(line.length()-2); //we know last char isn't whitespace so go back 2
	            				int count = 2;
	            				while(!Character.isWhitespace(checker)) { //break loop when we find a whitespace char
	            					count++;
	            					checker = line.charAt(line.length()-count);
	            				}
	            				WriteLine(line.substring(0,line.length()-(count+1)), justification, outputName); //write substring up to, but not including whitespace
	            				line = line.substring(line.length()-(count-1), line.length()-1); //reset line as everything after, but not including, the whitespace
	            			}
	            		}else {//string isn't at max yet, add one whitespace to end and continue
	            			line += (char)singleChar;
	            		}
	            	}else {//none whitespace, add to string and continue
	            		line += (char)singleChar;
	            	}
	            }   
	            // Always close files.
	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) {
	        	System.out.println("Unable to open file '" + filename + "'");                
	        }
	        catch(IOException ex) {
	        	System.out.println("Error reading file '" + filename + "'");                 
	        }
	}
	
	static void WriteLine(String lineToWrite, int justification, String outputName) {
		 try {
	            // Assume default encoding.
	            FileWriter fileWriter = new FileWriter(outputName);

	            // Always wrap FileWriter in BufferedWriter.
	            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	            if(justification == 1) { //if right justified
	            	String formatted = String.format("%80d", lineToWrite);
	            	bufferedWriter.write(formatted);
	            }else {
	            	bufferedWriter.write(lineToWrite);
	            }
	            	bufferedWriter.newLine();

	            // Always close files.
	            bufferedWriter.close();
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Error writing to file '" + outputName + "'");
	            // Or we could just do this:
	            // ex.printStackTrace();
	        }
	    }
	}

