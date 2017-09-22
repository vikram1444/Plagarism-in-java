import java.io.*;
import java.util.*;
import java.lang.*;
class Bow
{
	//this is the list of arguments we use in our code
	
	static String FC;//content in the file i
	static String FC1;
	static String FC2;
	static int Dpv;//dot product 
	static double Ecn1;//Ecn1 is eucledian norm 
	static double Ecn2;//Ecn2 is eucledian norm 
	static double output;
	static double[][] matrix;
	static String Dire_Name;//input which we give contains differnt charactes so we take as string
	static String str;
	static ArrayList<String> file_arr=new ArrayList<String>();//Array list to give pass many input files as
	static String str1[]=new String[20];
	Scanner sc = new Scanner(System.in);	

	
    public static String file_Reading(String p)
    {
    	try{                //try catch blocks to handle exception when they are raised
	    	File F1 = new File(p);//string is sent to File class
	    	FileInputStream FIS = new FileInputStream(F1);//to read the entire file
			byte[] Data = new byte[(int) F1.length()];//type caste 
			//byte array contains data in the form of bytes
			FIS.read(Data);//reading file content by fis 
			FIS.close();
			str = new String(Data, "UTF-8");//UTF-8 is inbuilt parameter
    	}	//we are converting that data in byte array in to string 
		catch(Exception E)
		{
			System.out.println(E);
	    }
	    return str;
	}
	
	public void GetListofFiles_Folder(File addedpath) //method for taking different files as input from a folder
	{
		int k=0;
	    for (File entryFile : addedpath.listFiles()) //takes  a file in list of files at that path location
	    										
	    {
	        if (entryFile.isDirectory()) 
	        {
	            GetListofFiles_Folder(entryFile);//it calls function until it doesn't become a directory
	        } 
	        else 
	        {
	         file_arr.add(Dire_Name+"/"+entryFile.getName());//adding file name with directory to arraylist 
			str1[k]=entryFile.getName();//getting file name
			System.out.println("###"+str1[k]);
			k+=1;
	     }
	    }
	    matrix =new double[file_arr.size()][file_arr.size()];//setting a square matrix of array size
    } 
	public static Map file_Frequency(String FC)//return type is Map
    {                  //used for finding no of times word repeated and getting dot
    	String[] wds = FC.split(" ");//taking string  and splitting from files
		Map<String, Integer> map = new HashMap<>();
    	for (String w : wds) //wds is words
    	{
	        Integer n = map.get(w);//we get each word and give count to them
	        n = (n == null) ? 1 : ++n;//it is Terenary operator:if a word doesn't repeat itsds value will be become 1 
	        						//else we increment its value and put it back in map.
	        map.put(w, n);
    	}
    	return map;
	}

	public static int valueOfDotproduct(Map<String,Integer> map1,Map<String,Integer> map2)
	{	                          //getting dot product
		int res=0;
		int m1=0,m2=0;

		Set s1 = map1.entrySet();//converting map in to set
		Set s2 = map2.entrySet();
      	Iterator it2 = s2.iterator();//iterating over the set using iterator() method
      	Iterator it1 = s1.iterator();
      	while(it1.hasNext()) {//while loop continues until set has next element
         	Map.Entry M_entry = (Map.Entry)it1.next();//checking in if key in map1 exist in map 2 also
         	if (map2.containsKey(M_entry.getKey()))
         	{	
         		m1 = (int)M_entry.getValue();
         		m2 = (int)map2.get(M_entry.getKey());
         		res = res + (m1 * m2);
         	}
      }
      return res; //returning result
	}
	public static double EucledianNorm_calcul(Map<String,Integer> map)//calculating eucledian norm
	{
		double res=0;
	 	for(int current_value : map.values()){//key is no of times a word repeated??
			res = res + (current_value*current_value);			
     	} 
      return res;
	}
    public static double BagofWords(String F1,String F2)
    {
    	FC1 = file_Reading(F1);//here the two inputs are two files of string type
    	FC2 = file_Reading(F2);//we read the two files F1,F2
    	Map<String, Integer> map1 = new HashMap<>();//Hashmap contains String ==keys&& Integer==value
    	Map<String, Integer> map2 = new HashMap<>();
    	map1 = file_Frequency(FC1); //getting the maps so we can do dot product from them
    	map2 = file_Frequency(FC2);//getting the maps so we can do dot product from them
    	Dpv = valueOfDotproduct(map1,map2);//calculating dot product
    	Ecn1 = EucledianNorm_calcul(map1);//calculating euclidean norm
    	Ecn1 = Math.sqrt(Ecn1);//finding square root
    	Ecn2 = EucledianNorm_calcul(map2);
    	Ecn2 = Math.sqrt(Ecn2);//finding square root
    	output = Dpv / (Ecn1 * Ecn2);
    	double RES=output*100;
    	return RES;//getting result
    }
	public static void main(String[] args)//used for printing the result in desired way 
	{
		Scanner s = new Scanner(System.in);
		
		Dire_Name = args[0];//getting the path
		File path = new File(Dire_Name);//similar to scanner function
		Bow p = new Bow();

		p.GetListofFiles_Folder(path);//folder of type File is passed to this funcn
		
		for(int i=0;i<file_arr.size();i++)//finding plagirism % in a matrix
		{
			
			for(int j=i;j<file_arr.size();j++)
			{
				if(i==j)
				{
					matrix[i][j]=100.0;
				}
				else
				{ //rounding off the result and taking into matrix //calling bagof words so that result is obtained.
					matrix[i][j]=Math.round((float)p.BagofWords((String)file_arr.get(i),(String)file_arr.get(j))*100.0)/100.0;
					matrix[j][i]=matrix[i][j];
				}
			
			}
		}
		double Maxvalue=0;
		int r=0,q=0,k=0,count=8;
		System.out.println("\n");
		System.out.println("bag of words results::::\n"); 
		System.out.print("file\t|");
		for(int i=0;i<file_arr.size();i++)
		{
			count+=8;
			 System.out.print(str1[i]+"\t|");
		}
		System.out.println();
		for(int i=0;i<count;i++) System.out.print("=");
		System.out.print("|\n");
		for(int i=0;i<file_arr.size();i++)
		{
			System.out.print(str1[i]+"\t|"); //pring the file names
			k+=1;
			for(int j=0;j<file_arr.size();j++)
			{
				
				
				System.out.print(matrix[i][j]+"\t|"); //printing the matrix value.
				if (matrix[i][j] > Maxvalue) {//we check and update maximum value plagirised
           			Maxvalue = matrix[i][j];
           			r=i;q=j;//updating r & i values
        		}
			}
			System.out.println(" ");
		}
	}
	
}

