# encryption
fun encryption program i made. You can use the encryptor class file and run it from something you write if you want,
the run Encrypt is just a GUI file that uses the Encryptor file. If you run the runEncrypt it should create a gui
that lets you chooes to enctrypt text, encrypt a file, or do an even harder encrypt on a file! i just made it for
fun so enjoy or tinker with it however you want on your own version :D
I also provided a few text files you can test it on. 
tiny.txt - 10 lines
small.txt - 100 lines
large.txt - 172822 lines
super.txt - 1728220 lines
IF YOU WANT TO BUILD YOUR OWN FILE TO RUN IT HERE IS A QUICK LITTLE STARTUP GUIDE FOR IT
the encryptor class file takes in 3 variables while building an object:
string to encrypt = value
boolean todo or 'to do' (aka what to do... sorry about my poor variable naming xD) 
boolean level.
(i.e. code below directly from the file.)
public Encryptor(String value, boolean todo, boolean level)  
		setup(value, todo, level);                                      
}                                                                    
giving the input of todo = true will result in the file creating an encrypted version of 
your input 'value' and false will decrypt value. this happens on creation of the object.
boolean level is how or how deep you want the encryption to be. just regularly going through the
algorithm and assigning values is the weaker encryption for this input false.
for the more complex one input true for and this creates a randomly generated seed into the encrypted
text. 
other public class calls are: 

public String getEncrypt()
	{
		if(key1 == 0)return null;
		return valueOutput;
	}
	public String getDecrypt()
	{
		return valueOutput;
	}
  
 these simply return the 'value output' however it is worth noting this is just for ease of use on the calls
 if you call getDecrypt on a line that was entered to be encrypted itll just return the encrypted line not decrypted.
 i.e. if you have Encryptor e = new Encryptor("line to encrypt", true, true);
 it creates the encrypted versoin of 'line to encrypt' and to get this back you call
 e.getEnecrypt(); and it will return a string. Calling e.getDecrypt(); should still return the encrypted string not decrypted.

here are some examples of creating your own file to use the encryptor class:

public class exampleClass{
  public static void main(String[]args)
  {
    Encryptor ex1 = new Encryptor("test 1",true,false); //take the text 'test 1' and encrypt in the less complex way
    Encryptor ex2 = new Encryptor("test 2",true,true); //take the text 'test 2' and encrypt it in the more complex way
    Encryptor ex3 = new Encryptor(TEXT_THAT_WAS_ALREADY_ENCRYPTED,false,false); //take text that was already encrypted that you place inside and decrypt it
    Encryptor ex4 = new Encryptor(TEXT_THAT_WAS_ALREADY_ENCRYPTED,false,true); //take complex encrypted text that you place inside and decrypt it
    
    //NOTE YOU CANNOT CALL THE COMPLEX DECRYPT ON A REGULAR ENCRYPTED TEXT NOR CALL REGULAR DECRYPT ON COMPLEX ENCRYPT
    //THEY ARE 2 DIFFERENT RESULTS.
    
    //other valid calls
    System.out.println(ex1.getEncrypt()+"\n\n"+ex2.getEncrypted());//output the encrypted ex1 and ex2
    System.out.println(ex3.getDecrypted()+"\n\n"+ex4.getDecrypted());//output the decrypted ex3 and ex4
    
    //you can call without naming an object as well
    System.out.println( (new Encryptor("this is a test",true,false)).getEncrypted() );
    
    //you can also encrypt/decrypt named Encryptors by using their returned value
    System.out.println( "Encrypted ex 1 =\n"+ex1.getEnrypted()+"\n);
    System.out.println( "Decrypted ex 1 after Encrypted = \n"+ (new Encryptor(ex1.getEncrypted(),false,false)).getDecrypted() );
    }
}//end exampleClass

Anyway have fun and enjoy!
