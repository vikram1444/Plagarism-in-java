import java.io.*; 
import java.util.*;
import java.util.Map.Entry;;
import java.lang.*;         //importing for math operations 
import java.lang.Iterable;
import java.text.DecimalFormat;

class Lcs
{
	static String str1[]=new String[20];
	public static void main(String[] args) throws IOException //for opening files
	{
		String s=args[0]; //taking path 
		
		File[] fnames;
		int i=0;
		String fn[]=new String[300];
		File f1=new File(s);
		fnames=f1.listFiles();
		for(File ftext:fnames)      //taking the files
			{
				fn[i]=ftext.toString();
				str1[i]=ftext.getName();
				i++;
            }
		   calls(fn,i);  //calling the function for that files
	}

public static void calls(String[] fn,int l)throws IOException //this is used for displaying the results in matrix form
	{
		System.out.println("\n  lcs method \n");
		ArrayList<String> r1=new ArrayList<String>();
		System.out.print("|file\t|");
		int count=8;
		for(int m=0;m<l;m++){      
			count+=8;                              //this used for giving tabs and spaces
			System.out.print(str1[m]+"\t|");
		}
		System.out.println();
       System.out.print("|");
       for(int i=0;i<count-1;i++) System.out.print("="); //used for displaying
		System.out.print("|\n");
	float mat[][]=new float[l][l];
        for(int m=0;m<l;m++)          //printing in matrix form
		{
			System.out.print("|"+str1[m]+"\t|");
            for(int n=0;n<l;n++)
			{
                if (m==n)
				{
				System.out.print("100.0"+"\t|"); 
                //r1.add("None");
				}
				else if(m>n)
				{
					System.out.printf("%.2f\t|",mat[m][n]); //rounding off
				}
                else
				{
                    StringBuilder a=files_read(fn[m]);  //calling for reading files                              
                    StringBuilder b=files_read(fn[n]); //calling for reading files 
					int c=com_substring(a,b);    //for obtaning largest common substring 
					// r1.add(percentage(a.length(),b.length(),c));
					mat[n][m]=percentage(a.length(),b.length(),c);  //calling the percentage and assigning to matrix
					mat[m][n]=mat[n][m];
					System.out.printf("%.2f\t|",percentage(a.length(),b.length(),c));//printing
				}
				
			}
			System.out.println();  //printing operations

		//System.out.println(fn[m]+"\t"+r1);
           r1.clear();
		}
		 // System.out.println();
       for(int i=0;i<count;i++) System.out.print("=");
       	System.out.print("=");
	}


 public static StringBuilder files_read(String i)throws IOException //this is used for reading files and removing special charcters
	{                                 //returns string builder
	 StringBuilder str = new StringBuilder();
	 FileReader fr=new FileReader(i);    
          BufferedReader br=new BufferedReader(fr);    
  
          String line=null;    
          while((line = br.readLine()) != null)
			{ 
				String line1=line.replaceAll("[\\p{P}&&[^_]]", ""); //removes special characters
				String[] words=line1.split("\\s"); //splitting that into words
				for(String w:words)
				{  
					String alw= w.toLowerCase(); //converting them lower case 
					str.append(alw);   //appending again the words
				}
			} 
return str;
	}
public static int com_substring(StringBuilder a1,StringBuilder b1)
	{                            //this is used for obtaining largest common substring and returning its length 
     int m=a1.length();
     int n=b1.length();
     int max=0,pos;
     String s="";
        for (int i=0;i<m;i++) //using for loops for obtaning lcs length
		{
          pos=i;                                                       
            for (int j=0;j<n ;j++ )
            {
                if (pos<m)
				{
                    if (a1.charAt(pos)==b1.charAt(j))
					{
                        pos=pos+1;            //comparing so that largest common substring can be obtained
						if (pos-i>max)
						{
                            max=pos-i;
                            s=a1.substring(i,pos);
						}
						
					}
                    else
					{
                        pos=i;
					}
				}
			}
		}  
       return max; //returning the value.

	}
public static float percentage(float a,float b,float c)
	{                               //this is used for calculating the result and returning the result.
		float r=((c*2)/(a+b))*100;		
		return r;
    }          
}