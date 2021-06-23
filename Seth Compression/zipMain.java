import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;




public class zipMain {
	
	// Hash mapp for 
	Map<Byte, Integer> charFrequency;
	ArrayList<Node> arrayNodes = new ArrayList<>();
	//String rawBytes ="";
	
	StringBuilder rawBytes = new StringBuilder();
	
	// use Stringbuilder class; 
	//	ArrayList<Node> leafNodes = new ArrayList<>();

	
	// this is called 
	public void encoding(Map<String, String>lookFor) {
		
		byte myByte;
		char mychar;
		
		for (Map.Entry entry : lookFor.entrySet()) { 
			System.out.println(entry.getKey() + " " + entry.getValue()); 
			
			Object ob  = entry.getValue();
			myByte = getAscii(ob.toString());
			System.out.println(myByte);
			
			
		}
	}
	
	
	byte getAscii(String string1) {
		byte tempByte;
		if(string1 =="space") {
			tempByte = 32;
			return tempByte;
		}else if(string1 == "newline") {
			tempByte = 10;
			return tempByte;
		}else if(string1 == "return") {
			tempByte = 13;
			return tempByte;
		}else if(string1 == "tab") {
			tempByte = 9;
			return tempByte;
		}else {
			char c = string1.charAt(0);
			return (byte)c;
			
		}
		
	}
	
	// this returns the root node	
	public Node huffMan(ArrayList<Node> myArray) {

		
		while(myArray.size()>1){
				//System.out.println("Running...the Huffman");
			Node firstNode = myArray.get(0);
			Node secondNode = myArray.get(1);
			int firstFreq = firstNode.freq;
			int secondFreq = secondNode.freq;
			int newFreq = firstFreq + secondFreq; 				
			byte nullVar = (byte)0;
			Node newParentNode = new Node(nullVar, firstNode, secondNode, newFreq);
			
			// call the encode here. 
			myArray.add(newParentNode);
			myArray.remove(firstNode);
			myArray.remove(secondNode);	
			Collections.sort(myArray);		
			}
			
		
		if(myArray.size()==1) {
			Node firstNode = myArray.get(0);
			
		//	System.out.println("This is the end of the HuffMan after the while loop");
			

		}

		return myArray.get(0);
		

	}
	

	
	public Map<String , String> buildLookuptTable(Node root){
		Map<String , String> lookupTable = new HashMap<>();
		buildLookupTableImpl("",root, lookupTable);
		
		return lookupTable;
	}
	
	// you apply this to the root 
	public void buildLookupTableImpl( String string, Node node,
									Map<String , String> lookupTable) {
		// TODO Auto-generated method stub
		if(!node.isLeaf()) {
			buildLookupTableImpl(string + '0', node.leftnode,  lookupTable);
			buildLookupTableImpl(string+ '1',node.rightnode, lookupTable);
		}else {
			lookupTable.put(string, node.nameStr );
		}
		
	}
	
	
	public Map<Byte , String> LookuptTableRef(Node root){
		Map<Byte , String> lookupTable = new HashMap<>();
		LookupTableImplRef(root,"", lookupTable);
		
		return lookupTable;
	}
	
	// you apply this to the root 
	public void LookupTableImplRef(  Node node, String string,
									Map<Byte , String> lookupTable) {
		// TODO Auto-generated method stub
		if(!node.isLeaf()) {
			LookupTableImplRef( node.leftnode, string + '0',  lookupTable);
			LookupTableImplRef(node.rightnode, string+ '1', lookupTable);
		}else {
			lookupTable.put(node.asciiVal ,string );
		}
		
	}
	


	public zipMain (String filename) throws IOException {
		
		byte bytesArray[];
		
		charFrequency = new HashMap<>();
		
		
	
		
		try {
			
			FileInputStream fis = new FileInputStream(filename);
			bytesArray = fis.readAllBytes();
			//System.out.println(filename);
			fis.close();
			

			

			 //LOOP O(n) 
			// this is in ASCII value. 
			// Looping through the bytes array an adding them to the hash map 
			for(byte c : bytesArray) {
				if(charFrequency.containsKey(c)) {
					
					// this puts counts the frequency if it the char is already stored 
					charFrequency.put(c, charFrequency.get(c)+1);
					
					//Node j = new Node()
					
				
				}else{
					charFrequency.put(c, 1);
					
				/*	Node n = new Node(c, null, null, 1);
					sortedNodes.add(n);
					
					*/
				}
			}
			
			
			// Storing the hashmap into a node. 
			for (Map.Entry entry : charFrequency.entrySet()) { 
				
				//creating nodes
				//System.out.println("Storing to the ArrayList...");
				byte bit = (byte)entry.getKey();
				int hashFreq = (int)entry.getValue();
				Node node = new Node(bit, null, null, hashFreq); 
				arrayNodes.add(node);
			}
			
			
			
			// Will sort the array of nodes using Collection.sort()
			Collections.sort(arrayNodes);
//			int rootNodeFreq = huffMan(arrayNodes).freq;
//			System.out.println(arrayNodes.get(0).freq);
//			System.out.println(rootNodeFreq);
			
			Node rootNode = huffMan(arrayNodes);
			Map<String, String>lookup = buildLookuptTable(rootNode);
			Map<Byte, String>lookUpRef = LookuptTableRef(rootNode);
			//System.out.println("Pre for loop  ");
			
			// This loops through the binary bits(ASCII) of the input file
			// This works for small files but crashes without any error messages and does not get out the for loop for bigger files
			
//			for (int i = 0; i<bytesArray.length/10;) {
//				rawBytes.append(lookUpRef.get(i));
//				
//			}
			
			long len = 0;
			
			try {
				for(byte myByte : bytesArray) {
//					System.out.println(" switching........");
					rawBytes.append(lookUpRef.get(myByte));
//					
					len++;
				}
			 } catch (OutOfMemoryError e) {
		            System.out.println("Max length on your system is "+len);
		            System.out.println("Error");
		     }
		        System.out.println("End");

		        
		        
			System.out.println("I got out the for loop ");
			
			
			
			
/*---------------------------------------------------------------output file -----------------------------------------------------------------------*/			
			int fileLength = filename.length();
			int fileStart=0;
			int fileEnd = fileLength-4;
			String outPutName = filename.substring(fileStart,fileEnd);
			
			File outPutFile = new File( outPutName +".zip301");
			FileOutputStream fos = new FileOutputStream(outPutFile);
			
			
			for (Map.Entry entry : lookup.entrySet()) { 
				
				String stringEncoding = entry.getKey() + " " + entry.getValue()+"\n"; 
				byte[] convertToByte = stringEncoding.getBytes();
				fos.write(convertToByte);
				// make different node
					
			}

			int byteLength = rawBytes.length();
			int powerOfEight = byteLength % 8;
			
			
			
			String asterisks ="*****" +"\n";
			
			byte[] tempB = asterisks.getBytes();
			fos.write(tempB);
			
			String str = Integer.toString(byteLength)+"\n";
			tempB = str.getBytes();
			
			
			fos.write(tempB);
			
			
			System.out.println(byteLength);
			while(powerOfEight != 0) {
				//System.out.println("Adding 0's");
				//rawBytes+='0';
				
				rawBytes.append("0");
				byteLength = rawBytes.length();
				powerOfEight = byteLength % 8;		
			}
			
			byteLength = rawBytes.length();
			//System.out.print(rawBytes);
			
			
			int startIndex =0;
			int lastIndex = 8;
		
			
			while(startIndex<byteLength) {
				String cutting = rawBytes.substring(startIndex,lastIndex);
				//System.out.print(cutting );
				//System.out.print(cutting );
				int charCode = Integer.parseInt(cutting, 2);
				//System.out.print(charCode );
				fos.write(charCode);
				
				//pw.write(charCode);
				startIndex+=8;
				lastIndex+=8;
			
				
			}

			

			
			
			
			fos.close();

			System.out.print("Your program ended" );
			
			
//			for (Map.Entry entry : lookUpRef.entrySet()) { 
//				System.out.println(entry.getKey() + " " + entry.getValue()); 
//			}
//			
			
			
			
			
//			for (Map.Entry entry : lookup.entrySet()) { 
//				System.out.println(entry.getKey() + " " + entry.getValue()); 
//				// make different node
//				
//				
//			}
			
			

			
			// This is the for loop to check all the ojects are sorted in an arraylist; 
			/* 
			for(Node x : arrayNodes) {
				System.out.println(x.name +" "+ x.freq);
			}
			*/

			
			// This is for printing the number of frequencies of the chars 
		    /*for (Map.Entry entry : charFrequency.entrySet()) { 
				System.out.println(entry.getKey() + " " + entry.getValue()); 
			}*/
			
			//fis.close();
	



			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}

		
	}
	
	
	// sorting for the 

	public static void main(String[] args) throws IOException {
		if(args.length !=1) {
			System.out.println("Usage: java Main <inputfile.txt>");
		}else {
			System.out.println("Running...");
			new zipMain(args[0]);
			
		}

	}

}







/*	
public Map<Character, String> buildLookuptTable(Node root){
	Map<Character, String> lookupTable = new HashMap<>();
	buildLookupTableImpl(root,"", lookupTable);
	
	return lookupTable;
}


// you apply this to the root 
public void buildLookupTableImpl(Node node, String string, 
								Map<Character, String> lookupTable) {
	// TODO Auto-generated method stub
	if(!node.isLeaf()) {
		buildLookupTableImpl(node.leftnode, string + '0', lookupTable);
		buildLookupTableImpl(node.rightnode, string + '1', lookupTable);
	}else {
		lookupTable.put(node.name, string);
	}
	
}
*/
