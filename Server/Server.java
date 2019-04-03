import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
class Server
{
	public static void main(String []args)
	{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy_MMM_dd");
			Date dt=new Date();
			String choice;
			String msg;
			int count1;
		try(ServerSocket s=new ServerSocket(12345))
		{
			
			System.out.print("Waiting for client request");
			Socket skt=s.accept();
			System.out.println("Connected to Client");
			String str=sdf.format(dt);
			try(BufferedReader br=new BufferedReader(new InputStreamReader(System.in));DataOutputStream dos=new DataOutputStream(skt.getOutputStream());DataInputStream dis=new DataInputStream(skt.getInputStream());FileWriter fr=new FileWriter(str+".txt",true))
			{
				String username=dis.readUTF();
				String password=dis.readUTF();
				count1=0;
				try(BufferedReader s1=new BufferedReader(new FileReader("user.txt")))
				{
				String word="";
				while((word=s1.readLine())!=null)
				{
				args=word.split(" ");
				if(username.equalsIgnoreCase(args[0]) && password.equalsIgnoreCase(args[1]))
				{count1=1;
				break;
				}
				}
				}
				catch(Exception e)
				{
				System.out.println(e);
				}
				if(count1==1)	{
				dos.writeBoolean(true);	

			do
			{
			System.out.println("1. Chat");
			System.out.println("2. View History");
			System.out.println("3. Upload");
			System.out.println("4. Download");
			System.out.println("5. Exit");
			choice=dis.readUTF();
			switch(choice)
			{
			case "1":
			{
				do{
			
			msg=dis.readUTF();
			System.out.println("Client="+msg);
			Date dt2=new Date();
			fr.write("Client:"+"("+dt2.getHours()+":"+dt2.getMinutes()+":"+dt2.getSeconds()+")"+msg+"\n");
			msg=br.readLine();
			Date dt1=new Date();
			fr.write("Server:"+"("+dt1.getHours()+":"+dt1.getMinutes()+":"+dt1.getSeconds()+")"+msg+"\n");
			dos.writeUTF(msg);
			}while(!msg.equalsIgnoreCase("bye"));

			}
			break;
			case "2":
			{
			  String str1=dis.readUTF();
				File f=new File(str1);
					if(f.exists() && f.length()!=0)
					{
					try(BufferedReader s1=new BufferedReader(new FileReader(str1)))
					{
						String words="";
						String line="";
						int count=0;
						while((line=s1.readLine())!=null)
						{
							words+="#"+line;
							count++;
						}
						dos.writeUTF(""+count);
						dos.writeUTF(words);
					}
					catch(Exception e)
					{
					System.out.println(e);
					}

					}
					else
					{
						System.out.println("Such file does not exists");
					}
			}
			break;
			case "3":
			{
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
			case "4":
			{
				File f1=new File("Files");
				File fil[]=f1.listFiles();
				String z="";
				int count=0;
				for(int i=0;i<fil.length;i++){
				z+=fil[i].getName()+",";
				count++;
				}
		dos.writeUTF(""+count);
		dos.writeUTF(z);
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
					dos.writeBoolean(false);
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