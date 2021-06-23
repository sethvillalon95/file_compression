
public class Node implements Comparable<Node> {
	char name; 
	byte asciiVal;
	Node leftnode;
	Node rightnode;
	int freq;
	String encodingStr;
	String nameStr;
	
	
	
	// make a node constructor 
	public Node(byte a, Node n1, Node n2, int f) {
		
		asciiVal = a;
		name = (char) a;
		leftnode =n1;
		rightnode = n2;
		freq = f;
		encodingStr= "";
		nameStr = getSpace(a);

		
		
	}
	
//	public Node(String encoding, String letter) {
//		nameStr = letter;
//		asciiVal = getAscii(letter);	
//		
//	}
	
	boolean isLeaf() {
		return leftnode == null && rightnode == null;
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
	
	String getSpace(byte  b) {
		char  tempchar;
		if(b == 32) {
			return "space";
		}else if(b ==10) {
			return "newline";
		}else if(b==13) {
			return "return";
		}else if(b == 9) {
			return "tab";
		}else {
			tempchar = (char)b;
			return Character.toString(tempchar);	
		}
			
	}

	@Override
	public int compareTo(Node other) {

		if(this.freq > other.freq) {
			return 1;
		}else if(this.freq<other.freq) {
			return -1;
		}else {
			return 0;
		}
		
	}
}
