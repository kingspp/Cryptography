/*
 *  Program: Cryptography series - Play Fair Cipher
 *  @Author: Kingspp
 *  
 *  Interesting ASCII Values:
 *	32 = space
 *	65 = A		97 = a
 *	90 - Z		122 = z
 * 
 *  Features:
 *  Key updating algorithm to remove redundant characters in cipher key
 *  Sbox generation - Fill the key
 *  				- Fill the remaining alphabets
 *  
 *  The i/j option is said to be selected using 'sel' variable.
 *  Rectangle formula applied
 *  Reverse column and reverse row are pending
 *  Same row multiple difference and Same column multiple difference is pending
 */

public class PlayFair {

	private static char sbox[][] = new char[5][5];
	private static char sel = 'j'; // i will be replaced instead of j
	private static String plainText = "Hello World";
	private static String key = "Hello";
	private static char str[];
	private static int place[]=new int[4];
	private static boolean spos[]=new boolean[100];
	private static String cipher;
	
	public static void main(String args[]) {		
		textConv();
		sBoxGen();
		printSbox();		
		cipher();			
 	}
	
	public static void cipher()
	{
		for(int i=0;i<plainText.length();i+=2)
		{
			findText(str[i], str[i+1]);
			
			//case 1 : Rectangle Formation
			if(place[0]!=place[2] && place[1]!=place[3])
			{	
				System.out.print(sbox[place[0]][place[1]]);
				System.out.print(sbox[place[2]][place[3]]);
				System.out.print(":");
				System.out.print(sbox[place[0]][place[3]]);
				System.out.println(sbox[place[2]][place[1]]);
			}
			
			//Same row
			if(place[0]==place[2] )
			{
				
				System.out.print(sbox[place[0]][place[1]]);
				System.out.print(sbox[place[2]][place[3]]);
				System.out.print(":");
				System.out.print(sbox[place[2]][place[3]]);
				if(place[3]+1>5)
					System.out.println(sbox[place[2]][place[3]+1-5]);
				else
					System.out.println(sbox[place[2]][place[3]+1]);
					
			}
			
			//Same column
			if(place[1]==place[3] ) 
			{
				System.out.print(sbox[place[0]][place[1]]);
				System.out.print(sbox[place[2]][place[3]]);
				System.out.print(":");
				System.out.print(sbox[place[2]][place[3]]);
				if(place[0]-1<0)
					System.out.println(sbox[place[0]-1+5][place[1]]);
				else
					System.out.println(sbox[place[0]-1][place[1]]);				
			}
			
			
			if((place[1]-place[3]!=-4) && (place[1]-place[3]!=4) && place[1]==place[3])
					{
						System.out.print(sbox[place[0]+1][place[1]]);
						System.out.println(sbox[place[0]][place[1]]);
					}
			
			 if((place[0]-place[2]!=4)&&(place[0]-place[2]!=-4)&& place[0]==place[2])
			 {

					System.out.print(sbox[place[0]][place[1]+1]);
					System.out.print(sbox[place[0]][place[1]]);
			 }			
		}			
	}
	
	public static int[] findText(char a, char b)
	{
		
		for(int i=0;i<5;i++)
			for(int j=0;j<5;j++){
				if(sbox[i][j]==a)
				{
					place[0]=i;
					place[1]=j;
				}
				else if(sbox[i][j]==b)
				{
					place[2]=i;
					place[3]=j;
				}
			}
		return place;					
	}

	//convert and divide the text
	public static void textConv()
	{
		plainText = plainText.toLowerCase();
		str = plainText.toCharArray();
		
		for(int i=0;i<plainText.length();i++)
		{
			if (str[i]==32){
				spos[i]=true;								
				break;
			}
			if(i!=0)
				if(str[i-1]==str[i])
					str[i]='x'; // Replace repeated values with 'x'
		}
		
		plainText=new String(str);
		plainText=plainText.replaceAll("\\s+","");
		if(plainText.length()%2!=0)
		{
			plainText=plainText+'x';		
		}
		
		
		str=plainText.toCharArray();
		System.out.print("Converted text: ");
		System.out.println(plainText);
	}


	// Function used to generate SBox
	public static void sBoxGen() {

		key = key.toLowerCase();
		char k[] = key.toCharArray();
		int temp = 0;
		boolean alpha[] = new boolean[26];

		//In this loop, the key is said to be checked 
		//for duplicates and if found are removed, and the end of 
		//the updated key is said to be filled with '!' for looping purposes.
		char keyUp[]=new char[50];
		int n=0;
		for(int i=1;i<k.length;i++)
			if(k[i-1]==k[i])			
				continue;							
			else
				keyUp[n++]=k[i-1];
		keyUp[n++]=k[k.length-1];
		keyUp[n++]='!';	
		
		System.out.print("Key: ");;
		for(int i=0;keyUp[i]!='!';i++)
			System.out.print(keyUp[i]);
		System.out.println();
		
		initSbox();
		// Change the values of the boolean array to false
		for (int i = 0; i < 26; i++)
			alpha[i] = false;

		// Set the key values
		for (int i = 0; keyUp[i] != '!'; i++)
			alpha[keyUp[i] - 97] = true;

		// Choose between i/j
		alpha[sel - 97] = true;

		// Add the secret key to the SBox
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {				
				sbox[i][j] = keyUp[temp++];
				// System.out.println(i+":"+j+":"+k[temp-1]);
				if (keyUp[temp] == '!')
					break;
			}
			if (keyUp[temp] == '!')
				break;
		}

		// Fill up the SBox with the remaining alphabets
		temp = 0;

		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++)
				if (sbox[i][j] == '0') {
					while (alpha[temp++] == true) {
					}
					sbox[i][j] = (char) (temp + 96);
					// printSbox();
				}
	}

	// Function used to print SBox
	public static void printSbox() {
		System.out.println("SBox design matrix:");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(sbox[i][j]);
				System.out.print("\t");
			}
			System.out.println("\t");
		}
		System.out.println();
	}

	// Function used to initialize SBox
	public static void initSbox() {
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++)
				sbox[i][j] = '0';
		
		for (int i = 0; i < 100; i++)
			spos[i]=false;		
	}
}
