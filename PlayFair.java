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
 *  The i/j option is said to be selected using sel variable.
 */

public class Main {

	private static char sbox[][] = new char[5][5];
	private static char sel = 'j'; // i will be replaced instead of j
	private static String plainText = "Hello World";
	private static String key = "Hello";
	private static char str[];

	public static void main(String args[]) {		
		textConv();
		sBoxGen();
		printSbox();
		int p = 0,q=0,r=0,s=0;
		for(int k=0;k<plainText.length()/2;k+=2)
			if(str[k]==32)			
				k++;
			else
			{
				for(int i=0;i<5;i++)
					for(int j=0;j<5;j++)
						if(sbox[i][j]==str[k])
						{
							p=i;
							q=j;
							System.out.println(p+":"+q);
						}
						else if(sbox[i][j]==str[k+1])
						{
							r=i;
							s=j;
							System.out.println(r+":"+s);

						}
			}
	}

	//convert and divide the text
	public static void textConv()
	{
		plainText = plainText.toLowerCase();
		str = plainText.toCharArray();
		int space=0;
		for(int i=0;i<plainText.length();i++)
		{
			if (str[i]==32){
				space++;
				break;
			}
			if(i!=0)
				if(str[i-1]==str[i])
					str[i]='x'; // Replace repeated values with 'x'
		}
		if((plainText.length()-space)%2!=0)
		{
			str[plainText.length()-1]='x';
			plainText=str.toString();			
		}
		System.out.print("Converted text: ");
		System.out.println(str);
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
	}
}
