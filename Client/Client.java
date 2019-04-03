import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.net.*;
import java.io.*;
class Client
{
	
	public static void main(String []args)
	{
			
			String choice;
			String msg;
		try(Socket skt=new Socket("127.0.0.1",12345))
		{
			try(BufferedReader br=new BufferedReader(new InputStreamReader(System.in));DataOutputStream dos=new DataOutputStream(skt.getOutputStream());DataInputStream dis=new DataInputStream(skt.getInputStream()))
			{
				String username="";
				String password="";
				System.out.println("Enter Username");
				username=br.readLine();
				System.out.println("Enter Password");
				password=br.readLine();
				dos.writeUTF(username);
				dos.writeUTF(password);
				if(dis.readBoolean())
				{
				
			do
			{
			System.out.println("1. Chat");
			System.out.println("2. View History");
			System.out.println("3. Upload");
			System.out.println("4. Download");
			System.out.println("5. Exit");
			System.out.println("Enter your choice (1-5)");
			choice=br.readLine();
			dos.writeUTF(choice);
			
			switch(choice)
			{
			case "1":
			{
				do{
				
				msg=br.readLine();
				dos.writeUTF(msg);
				msg=dis.readUTF();
				System.out.println("Server="+msg);
				}while(!msg.equalsIgnoreCase("bye"));
			}
			break;
			case "2":
			{
				System.out.println("Enter date of which you want to see chat :");
				String str=br.readLine();
				dos.writeUTF(str+".txt");
				File f=new File(str+".txt");
				if(f.exists())
				{
				int count=Integer.parseInt(dis.readUTF());
				String str1=dis.readUTF();
				args=str1.split("#");
				int i=0;
				while(i<count)
				{
					System.out.println(args[i++]);
				}
				}
			}
			break;
			case "3":
			{
			String filename=dis.readUTF();
				File f=new File(filename);
				
				if(f.exists()){
					dos.writeBoolean(true);
					dos.writeUTF(""+f.length());
					System.out.println("Downloading begin");
					try(FileInputStream fis=new FileInputStream(f))
					{
					byte b[]=new byte[4096];	
					int c;
					while((c=fis.read(b))!=-1)
					{
						dos.write(b,0,c);
					}
					System.out.println("File transffered succesfully");
					}
					catch(Exception e)
					{
					System.out.println(e);
					}
				}
				else
				{
					dos.writeBoolean(false);
				}	
			}
			break;
			case "4":
			{
				int count=Integer.parseInt(dis.readUTF());
				String z=dis.readUTF();
				args=z.split(",");
				for(int i=0;i<count;i++)
				{
					System.out.println(args[i]);
				}
				System.out.println("Enter filename");
					String filename=br.readLine();
						dos.writeUTF(filename);
					File f=new File(filename);
					if(dis.readBoolean())
					{
					
					int length=Integer.parseInt(dis.readUTF());
					try(FileOutputStream fos=new FileOutputStream(filename))
					{
					int c;
					byte b[]=new byte[4096];
					int i=0;
					while(i<length)
					{	
					if((c=dis.read(b))!=-1)
					{
						i+=c;
						fos.write(b,0,c);
					}
					
					}
					System.out.println("File downloaded Successfully");
					}
					catch(Exception e)
					{
					System.out.println(e);
					}
					}
					else
					{
						System.out.println("File not found....");
					}
					
			}
			break;
			case "5":
			{
					System.exit(0);
			}
			break;
			default:
			System.out.println("Invalid Choice");
			}
			
			}while(true);
			}
			else
				{
					System.out.println("Password mismatch");
				}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		catch(Exception e)
			{
				System.out.println(e);
			}
	}
}	