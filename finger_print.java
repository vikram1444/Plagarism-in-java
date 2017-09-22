import java.util.*;
import java.util.Arrays;
import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
class finger_print
{
	protected String FileReading(File file){ //this is used for file reading and returning that string
		String line = null;
		StringBuilder str = new StringBuilder();
		try{
			BufferedReader br1 = new BufferedReader(new FileReader(file));
			while ((line = br1.readLine()) != null){ //until end of file
				str.append(line); 
				str.append(" ");
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return str.toString();
	}
	String removespaces(String s1) //this removes spaces if exists.
	{              //returns string without spaces
		String s3="";
		for(int i=0;i<s1.length();i++)
		{
			if(s1.charAt(i)!=32)
			{
				s3=s3+s1.charAt(i);
			}
		}
		return s3;

	}
	
	ArrayList<String> ngram(String s3,int n)//here we get anagrams by a number nand storing them array list 
	{             //returing the array list
		ArrayList<String> al=new ArrayList<String>();
		for(int i=0;i<s3.length()-n+1;i++)  //getting the respective anagrams.
		{
			al.add(s3.substring(i,i+n));//adding them to array list.
		}
		return al;
	}
	ArrayList<Integer> hashvalues(ArrayList<String> al,int n)
	{                     //getting the hash values for respective anagrams //returns hash values
		ArrayList<Integer> alli=new ArrayList<Integer>();
		
		for(String i:al)
		{
			int sum=0;
			int k=0;
			int d=n;
			while(k<i.length()) //adding them into array list of integer type
			{
				
				sum=(int)(sum+(i.charAt(k)*((Math.pow(2,d))-1)));
				k=k+1;
				d=d-1;
			}
			alli.add(sum);
		}
		return alli;  //returns the hash values array table.
	}
	int commonhashvalues(ArrayList<Integer> alli,ArrayList<Integer> alli1)
		{                        //returning the count of common hash values 
			int count=0;
			for(int i:alli)
			{
				for(int j:alli1)  //if hash values are same count increases.
				{
					if(i==j)     
					{
						count=count+1;
						break;
					}
				
				}
			}
			return count; //returning count of common hash values

		}



	public static void main(String[] args) 
	{
		File[] fileList = new File[100];
		Scanner s=new Scanner(System.in);
		String path=args[0];
		int l=0;
		try
		{    //try & catch blocks for any exceptions.
			File Directory = new File(path);
			File[] files = Directory.listFiles();
			for (File f : files)   //getting the files only of text files
			{
				if (f.getName().endsWith(".txt"))
				{
					fileList[l] = f;
					l++;
				}
			}
		}
		catch (Exception e)
		 {
			e.printStackTrace();
		}
		//Scanner s=new Scanner(System.in);
		String s1;
		String s2;
		finger_print f1=new finger_print();//creating the objects 
		finger_print f2=new finger_print();//creating the objects 
		double[][] mat=new double[l][l];
		int n=4; //taking default number 
		for(int i=0;i<l;i++)
		{
			for (int j = i; j < l; j++)
			 {
				s1 = f1.FileReading(fileList[i]);//calling for file reading
				s2 = f2.FileReading(fileList[j]);//calling for file reading
		
				ArrayList<String> al=new ArrayList<String>();//creating variables for hashvalues,anagrams
				ArrayList<String> all=new ArrayList<String>();//creating variables for hashvalues,anagrams
				ArrayList<Integer> alli=new ArrayList<Integer>();//creating variables for hashvalues,anagrams
				ArrayList<Integer> alli1=new ArrayList<Integer>();//creating variables for hashvalues,anagrams
				
				String s3=f1.removespaces(s1);//removes spaces calling that
				String s4=f1.removespaces(s2);//removes spaces calling that
				al=f1.ngram(s3,n);//getting anagrams
				all=f1.ngram(s4,n);//getting anagrams
				
				
				alli=f1.hashvalues(al,n);//getting hash values
				alli1=f1.hashvalues(all,n);//getting hash values
				
				
				int count=f1.commonhashvalues(alli,alli1);//getting common hash values.
				
				double percentage=(count*2.0)/(alli.size()+alli1.size())*100;//getting the result
				mat[i][j]=percentage;//result in matrices
				mat[j][i]=percentage;//result in matrices
			}
		}
		//printing the result in matrix form 
		System.out.println("\nfinger print:::: \n\n");
		System.out.print("|File\t");//storing the percentages  in one array printing as a array.
		int count=8;
	    for (int i = 0; i < l; ++i)
	    {
	     System.out.printf(fileList[i].getName()+"\t|");   
	     count+=8;
	    }
	    System.out.printf("\n|");
	    for (int i = 0; i < count-1; ++i) System.out.print("#");
	    System.out.printf("|\n");
	    for (int i = 0; i < l; ++i)
	    {
	        System.out.printf("|"+fileList[i].getName()+"\t|");
	        for (int j= 0; j < l; ++j)
	        {
	            System.out.printf("%.2f\t|",mat[i][j]);//pring the matrices after roundoff
	        }
	        System.out.printf("\n");
	    }
	    System.out.printf("#");
	    for (int i = 0; i < count; ++i) System.out.print("#");
	}
}
