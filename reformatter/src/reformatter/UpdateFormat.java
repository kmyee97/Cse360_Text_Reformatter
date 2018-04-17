package reformatter;

import java.io.*;
import java.util.ArrayList;

public class UpdateFormat {
	
	public static int[] updateFormat(String filename, int justification, int length, int spacing, String outputName) {
		int[] returnArray = new int[6];
		String line = "";
		ArrayList<String> writeArray = new ArrayList<String>();
		int blankLines = 0;
        int singleChar;
        int whiteCount = 0;
        int totalChars = 0;
        int lineCount = 0;
        int spacesAdded = 0;
		 try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = new FileReader(filename);
	            BufferedReader bufferedReader = new BufferedReader(fileReader);
	            

	            while((singleChar = bufferedReader.read()) != -1) {
	            	totalChars ++;
	            	//case 1: haven't reached line max
	            	if(line.length() < length) { 
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
	            }   
	            // Always close files.
	            bufferedReader.close();
	            String[] toWrite = writeArray.toArray(new String[writeArray.size()]);
	            spacesAdded = WriteLine(toWrite, justification, length, spacing, outputName);
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
		 returnArray[5] = spacesAdded;
		 return returnArray;
	}
	
	static int WriteLine(String[] writeArray, int justification, int length, int spacing, String outputName) {
		int spacesAdded = 0; 
		try {
	            // Assume default encoding.
	            //FileWriter fileWriter = new FileWriter(outputName);

	            // Always wrap FileWriter in BufferedWriter.
	            //BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	            
			PrintWriter writer = new PrintWriter(outputName, "UTF-8");
			if(justification == 1) { //if right justified
				 int spaceleft = 0;
				 for(int i = 0; i < writeArray.length; i++) {
					 spaceleft = length-writeArray[i].length();
					 for(int j = 0; j < spaceleft; j++)
					 {
						 writer.print(" ");
					 }
					 //String formatted = String.format("%80d", writeArray[i]);
					 //writer.println(formatted);
					 if(spacing == 1 && i != 0)
						 writer.println("\n");
					 writer.println(writeArray[i]);
				 }
			}else if(justification == 0) { //left justified
            	for(int i = 0; i < writeArray.length; i++) {
            		if(spacing == 1 && i != 0)
						 writer.println("\n");
            		writer.println(writeArray[i]);
            	}
        	}else if(justification == 2) { //full justified
        		for(int i = 0; i < writeArray.length; i++) {
        			String tempString = writeArray[i];
    				while(tempString.length() < length) {
    					System.out.println("inside loop");
    					boolean whiteSpace = false;
    					for(int j = 0; j < tempString.length(); j++) {
    						if(tempString.length() >= length) {
    							break;
    						}else if(Character.isWhitespace(tempString.charAt(j))){
    							String sub1 = tempString.substring(0, j);
    							String sub2 = tempString.substring(j, tempString.length());
    							tempString = sub1 + " " + sub2;
    							j++;
    							spacesAdded ++;
    							whiteSpace = true;
    						}
    					}
    					if(!whiteSpace) {
    						tempString = " " + tempString + " ";
    					}
    						
    				}
    				writeArray[i] = tempString;
        		}
        		for(int i = 0; i < writeArray.length; i++) {
        			if(spacing == 1 && i != 0)
						 writer.println("\n");
        			writer.println(writeArray[i]);
            	}
        	}
            //Always close files.
            writer.close();
            return spacesAdded;
	        }
	        catch(IOException ex) {
	            ex.printStackTrace();
	            return 0;
	            
	        }
	    }
	}