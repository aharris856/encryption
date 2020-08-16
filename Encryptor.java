
import java.util.ArrayList;
import java.util.Random;

public class Encryptor 
{
	private String valueInput = "";
	private String valueOutput = "";
	private ArrayList<String> valueHold = new ArrayList<String>();
	private ArrayList<ArrayList<String>> valueHoldInt = new ArrayList<ArrayList<String>>();
	private String[] alphabet = new String[3];
	private int key1 = 0;
	private int key2 = 0;
	private int key3 = 0;
	private int key4 = 0;
	//seed for randomly generated alphabet
	private int seed = 0;
	private boolean deep;
	//TEXT OBJECT
	public Encryptor(String value, boolean todo, boolean level)//if to do = to encrypt
	{
		setup(value, todo, level);
	}
	//Getters
	public String getEncrypt()
	{
		if(key1 == 0)return null;
		return valueOutput;
	}
	public String getDecrypt()
	{
		return valueOutput;
	}
	//setup values
	private void setup(String value, boolean todo, boolean level)
	{
		valueInput = value;
		deep = level;
		//prepare keys and load values
		String[] splitVal = valueInput.split("\\s+");
		if(todo) {//encrypt
			Random rand = new Random();
			key1 = rand.nextInt(5)+1;
			key2 = rand.nextInt(5)+1;
			key3 = rand.nextInt(5)+1;
			key4 = rand.nextInt(3);
			valueOutput = key1+" "+key2+" "+key3+" "+key4+" ";
			if(deep){
				seed = rand.nextInt();
				valueOutput = valueOutput+seed+" ";
			}
			for(int i = 0; i < splitVal.length; i++)
				valueHold.add(splitVal[i]);	
			encrypt();
		}else {//decrypt
			if(splitVal.length>3) {
				try {
					key1 = Integer.parseInt(splitVal[0]);
					key2 = Integer.parseInt(splitVal[1]);
					key3 = Integer.parseInt(splitVal[2]);
					key4 = Integer.parseInt(splitVal[3]);
					if(deep){ 
						seed = Integer.parseInt(splitVal[4]);
						for(int i = 5; i < splitVal.length; i++)
							valueHold.add(splitVal[i]);
					}else{
						for(int i = 4; i < splitVal.length; i++)
							valueHold.add(splitVal[i]);
					}
					decrypt();
				}catch(NumberFormatException e) {
					System.out.println("INVALID ENTRY");
				}
			}else System.out.println("INVALID ENTRY");
		}
	}
	///////////////////////////////////////////////// Encrypt
	private void encrypt()
	{
		generateAlpha();
		for(int i = 0; i < valueHold.size(); i++)
		{
			char[] valChar = valueHold.get(i).toCharArray();
			ArrayList<String> internal = new ArrayList<String>();
			for(int j = 0; j < valChar.length; j++)
			{
				int valCheck = encryptHelper(valChar[j]);
				if(valCheck==-1)internal.add(valChar[j]+"");
				else internal.add(valCheck+"");
			}
			valueHoldInt.add(internal);
		}
		
		for(int i = 0; i < valueHoldInt.size(); i++)
		{
			for(int j = 0; j < valueHoldInt.get(i).size(); j++)
				valueOutput = valueOutput+" "+valueHoldInt.get(i).get(j);
			if(i!=(valueHoldInt.size()-1))valueOutput = valueOutput+"_";
		}
	}
	private int encryptHelper(char ch)
	{
		int v = getVal(ch);
		if(v!=-1)return (key1*v*v+key2*v+key3);
		return v;
	}
	//////////////////////////////////////////////////
	////////////////////////////////////////////////// Decrypt
	private void decrypt()
	{
		generateAlpha();
		for(int i = 0; i < valueHold.size(); i++)
			valueOutput = valueOutput+decryptHelper(valueHold.get(i));
	}
	private String decryptHelper(String str)
	{
		if(str.contains("_")){
			return decryptHelper(removeEnd(str))+" ";
		}
		try{
			int strVal = Integer.parseInt(str);
			return decryptHelper2(strVal);
		}catch(NumberFormatException e){
			return str;
		}
	}
	private String decryptHelper2(int val)
	{
		int sol;
		int c = key3-val;
		double solHold = (double)key2*key2-4*(key1*c);
		solHold = -key2+(Math.sqrt(solHold));
		solHold = solHold / (2*key1);
		sol = (int)solHold;
		if(sol <= 0 || sol > 52)return val+"";
		return (alphabet[key4].charAt(sol-1)+"");
	}
	//generate alphabet random
	private void generateAlpha()
	{
		if(deep){
			Random r = new Random(seed);
			int loc = 0;
			String newStr = "";
			for(int i = 0; i < 3; i++){
				String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
				int tracker = str.length();
				int size = str.length();
				newStr = "";
				for(int j = 0; j < size; j++)
				{
					loc = r.nextInt(tracker);
					newStr = newStr+str.charAt(loc);
					str = remove(str,loc);
					tracker = str.length();
				}
				alphabet[i] = newStr;
			}
		}else{
			alphabet[0] = "ENustaPlSWBwMpmZDdhnVoQHcKejbJvRFkziIXqyUfOTxACYLgGr";
			alphabet[1] = "BoXIYCnpDEUFOcQlbyivxLuzWdAeJGkKrSafsVHwNZmPhRtqMTgj";
			alphabet[2] = "nPcxQXEDoFUKuVRZOmseISyfAiCpMBtkdrWHLYlaqgzwjThJbNGv";
		}
	}
	//remove str.charAt(loc) from str
	private static String remove(String str, int loc)
	{
		String newStr = "";
		for(int i = 0; i < loc; i++)
			newStr = newStr+str.charAt(i);
		for(int i = loc+1; i<str.length(); i++)
			newStr = newStr+str.charAt(i);
		return newStr;
	}
	//get value of char in
	private int getVal(char ch)
	{
		if(!alphabet[key4].contains(ch+""))return -1;
		for(int i = 0; i < alphabet[key4].length(); i++)
			if(ch == alphabet[key4].charAt(i))return i+1;
		return -1;
	}
	//remove ending from str
	private String removeEnd(String str)
	{
		String strHold = "";
		for(int i = 0; i < str.length()-1; i++)
			strHold = strHold+str.charAt(i);
		return strHold;
	}
}