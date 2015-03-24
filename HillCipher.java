/*
 * Hill Cipher
 * @Author: Kingspp
 * This program encrypts the given message in Hill cipher format
 * To do:
 * Add space in the cipher
 */

public class HillCipher {	
	private static int pr=2;
	private static int pc=2;
	private static int qr=2;
	private static int qc=1;
	private static int p[][] = new int[pr][pc];
	private static int q[][] = new int[qr][qc];;
	static int[][] res=new int[pr][qc];
	static int key[]={9,4,5,7};	
	private static String plainText="cryptography";
	private static char[] str=(plainText=plainText.toLowerCase()).toCharArray();
	private static String result="";
	private static boolean space[]=new boolean[plainText.length()+1];

	public static void main(String v[])
	{		
		System.out.println("Hill Cipher\n");
		System.out.print("Key: ");
		for(int i=0;i<key.length;i++)
			System.out.print(key[i]);
		System.out.println("");
		System.out.println("Plain Text:  "+ plainText);
		

		//Initialize space boolen array
		for(int i=0;i<space.length;i++)
			space[i]=false;

		//Check for the plain text spaces and evenness
		for(int i=0;i<plainText.length();i++)		
			if(str[i]==32)
				space[i]=true;
		
		plainText=plainText.replaceAll("\\s+","");

		//Check for evenness
		if(plainText.length()%2!=0)
			plainText=plainText+'x';
		str=plainText.toCharArray();

		//Assign key to the matrix;	
		int temp=0;
		for(int i=0;i<pr;i++)
			for(int j=0;j<pc;j++)
				p[i][j]=key[temp++];
		//printMat(p,pr,pc);
		
		//Divide and convert it to ascii
		for(int i=0;i<plainText.length();i+=2)
		{
			q[0][0]=str[i]-97;
			q[1][0]=str[i+1]-97;			
			matMul(p, q);			
			while(true)
			{
				for(int i1=0;i1<pr;i1++)
					for(int j=0;j<qc;j++)
						if(res[i1][j]>25)
							resMod();
						else
							break;
				break;
			}
			char a=(char) (res[0][0]+97);
			char b=(char) (res[1][0]+97);
			result+=a;
			result+=b;			
		}		
		//printMat(res, pr, qc);
		System.out.println("Cipher Text: "+result);
		
	}
	
	static void resMod()
	{
		for(int i=0;i<pr;i++)
			for(int j=0;j<qc;j++)
				res[i][j]=res[i][j]%26;
		//printMat(res,pr,qc);
	}

	static void matMul(int p[][], int q[][]){
		if(pc!=qr)		
			System.out.println("Matrix multiplication not possible");
		else
			for(int i=0;i<pr;i++)
			{
				for(int j=0;j<qc;j++)
				{
					int sum=0;
					for(int k=0;k<qr;k++)
						sum+=p[i][k]*q[k][j];					
					res[i][j]=sum;					
				}				
			}		
	}

	//Function to print the matrix
	static void printMat(int m[][],int r,int c){
		for(int i=0;i<r;i++)
		{
			for(int j=0;j<c;j++)
			{
				System.out.print(m[i][j]);
				System.out.print("\t");
			}
			System.out.print("\n");
		}
		System.out.print("\n");		
	}

}