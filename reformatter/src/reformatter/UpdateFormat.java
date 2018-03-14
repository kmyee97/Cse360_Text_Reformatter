package reformatter;

import java.io.*;
import java.util.ArrayList;

public class UpdateFormat {
	
	public static int[] updateFormat(String filename, int justification, String outputName) {
		int[] returnArray = new int[5];
		String line = null;
		ArrayList<String> writeArray = new ArrayList<String>();
		int blankLines = 0;
		
		 try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = new FileReader(filename);
	            BufferedReader bufferedReader = new BufferedReader(fileReader);
	            
	            int singleChar;
	            int whiteCount = 0;
	            while((singleChar = bufferedReader.read()) != -1) {
	            	if(singleChar == '\n' || singleChar == '\r') {
	            		blankLines++;
	            		continue;
	            	}
	            	if(Character.isWhitespace((char)singleChar)) {
	            		whiteCount++;
	            		
	            		if(line.length() >= 79) {
	            			if(whiteCount == 1) {//first white space at more than 80 chars means single word is greater than limit, simply write that word on one line
	            				writeArray.add(line); //leave justification at left to avoid truncation
	            			}else if(Character.isWhitespace(line.charAt(line.length()-1))) { //check if previous char is whitespace too
	            				continue; //double whitespace, just skip this one
	            			}else{ //more than 1 whitespace and 80 or characters, need to backtrack to previous whitespace and write all before it
	            				char checker = line.charAt(line.length()-2); //we know last char isn't whitespace so go back 2
	            				int count = 2;
	            				while(!Character.isWhitespace(checker)) { //break loop when we find a whitespace char
	            					count++;
	            					checker = line.charAt(line.length()-count);
	            				}
	            				//WriteLine(line.substring(0,line.length()-(count+1)), justification, outputName); //write substring up to, but not including whitespace
	            				writeArray.add(line.substring(0,line.length()-(count+1)));
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
	            String[] toWrite = writeArray.toArray(new String[writeArray.size()]);
	            WriteLine(toWrite, 0, outputName);
	        }
	        catch(FileNotFoundException ex) {
	        	System.out.println("Unable to open file '" + filename + "'");                
	        }
	        catch(IOException ex) {
	        	System.out.println("Error reading file '" + filename + "'");                 
	        }
		 return returnArray;
	}
	
	static void WriteLine(String[] writeArray, int justification, String outputName) {
		 try {
	            // Assume default encoding.
	            //FileWriter fileWriter = new FileWriter(outputName);

	            // Always wrap FileWriter in BufferedWriter.
	            //BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	            
			 PrintWriter writer = new PrintWriter(outputName, "UTF-8");
			 if(justification == 1) { //if right justified
				 for(int i = 0; i < writeArray.length; i++) {
					 	String formatted = String.format("%80d", writeArray[i]);
	            		writer.println(formatted);
				 }
	            }else {
	            	for(int i = 0; i < writeArray.length; i++) {
	            		writer.println(writeArray[i]);
	            	}
	            	}
	            	

	            // Always close files.
	            writer.close();
	        }
	        catch(IOException ex) {
	            //System.out.println(
	             //   "Error writing to file '" + outputName + "'");
	            // Or we could just do this:
	            ex.printStackTrace();
	            
	        }
	    }
	}

