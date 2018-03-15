package reformatter;

import java.io.*;
import java.util.ArrayList;

public class UpdateFormat {
	
	public static int[] updateFormat(String filename, int justification, String outputName) {
		int[] returnArray = new int[5];
		String line = "";
		ArrayList<String> writeArray = new ArrayList<String>();
		int blankLines = 0;
        int singleChar;
        int whiteCount = 0;
        int totalChars = 0;
        int lineCount = 0;
		 try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = new FileReader(filename);
	            BufferedReader bufferedReader = new BufferedReader(fileReader);
	            

	            while((singleChar = bufferedReader.read()) != -1) {
	            	totalChars ++;
	            	/*if(singleChar == '\n' || singleChar == '\r') {
	            		blankLines++;
	            		continue;
	            	}*/
	            	
	            	//case 1: havne't reached line max
	            	if(line.length() < 80) { 
	            		if((char)singleChar == '\n' || singleChar == '\r') {
	            			//return on empty line, add one to tracker and continue
	            			if(line.length() < 1) {
	            				blankLines++;
	            			}
            				//not return on empty line so disregard and continue
            				continue;
	            		}
	            		//handle whitespace first
	            		if(Character.isWhitespace((char)singleChar)) {
	            			//have we found a none whitespace for this line yet
	            			if(line.length() < 1) {
	            				//leading whitespace, disregard it and continue
	            				continue;
	            			
	            			//is previous char whitespace too?
	            			}else if(Character.isWhitespace(line.charAt(line.length()-1))){
	            				
	            				//double whitespace, disregard and continue
	            				continue;
	            			}else {
	            				//not leading white space or extra so insert into string and continue
	            				line += (char)singleChar;
	            				whiteCount ++;
	            			}
	            		}else {
	            			line += (char)singleChar;
	            		}
	            	//case 2: have reached line max
	            	}else {
	            		if(Character.isWhitespace((char)singleChar)) {
	            			//80th character is whitespace so put line into the arraylist and wipe it out
	            			writeArray.add(line);
	            			line = "";
	            		}else if(singleChar == '\n' || singleChar == '\r') {
	            			//80th char is line break, put line into array list and wipe it
	            			writeArray.add(line);
	            			line = "";
	            			
	            		}else {
	            			//check if previous char is white space
	            			if(Character.isWhitespace(line.charAt(line.length()-1))) {
	            				//previous char is whitespace so just add line to array list and wipe it
	            				writeArray.add(line);
		            			line = "";
	            			}else {
	            				//word hangs over the line limit need to backtrack to find previous whitespace
	            				char checker = line.charAt(line.length()-2); //we know 1st prev char isn't whitespace so go back 2
	            				int count = 3; //since we're starting 2 chars back and since line.length is one past array spot we init to 3
	            				while(!Character.isWhitespace(checker)) { //break loop when we find a whitespace char
	            					count++;
	            					checker = line.charAt(line.length()-count);
	            				}
	            				//add from the start up to line length - count to array list and leave line length + 1 to end in line
	            				writeArray.add(line.substring(0,line.length()-count));
	            				line = line.substring(line.length()-count+1, line.length());
	            				line += (char)singleChar;
	            			}
	            		}
	            		lineCount++;
	            	}
	            	
	            	/*
	            	if(Character.isWhitespace((char)singleChar)) {
	            		whiteCount++;
	            		
	            		if(line.length() >= 79) {
	            			if(whiteCount == 1 && line.length() >= 79) {//first white space at more than 80 chars means single word is greater than limit, simply write that word on one line
	            				writeArray.add(line); //leave justification at left to avoid truncation
	            			}else if(Character.isWhitespace(line.charAt(line.length()-1))) { //check if previous char is whitespace too
	            				continue; //double whitespace, just skip this one
	            			}else if(line.length() >= 79){ //more than 1 whitespace and 80 or characters, need to backtrack to previous whitespace and write all before it
	            				char checker = line.charAt(line.length()-2); //we know last char isn't whitespace so go back 2
	            				int count = 2;
	            				while(!Character.isWhitespace(checker)) { //break loop when we find a whitespace char
	            					count++;
	            					checker = line.charAt(line.length()-count);
	            				}
	            				//WriteLine(line.substring(0,line.length()-(count+1)), justification, outputName); //write substring up to, but not including whitespace
	            				writeArray.add(line.substring(0,line.length()-(count+1)));
	            				line = line.substring(line.length()-(count-1), line.length()); //reset line as everything after
	            			}
	            		}else {//string isn't at max yet, add one whitespace to end and continue
	            			line += (char)singleChar;
	            		}
	            	}else {//none whitespace, add to string and continue
	            		line += (char)singleChar;
	            	}*/
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
		 returnArray[0] = whiteCount + lineCount + 1;
		 returnArray[1] = lineCount + 1;
		 returnArray[2] = blankLines;
		 returnArray[3] = returnArray[0] / returnArray[1];
		 returnArray[4] = totalChars / returnArray[1];
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

