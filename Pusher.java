import java.net.*;
import java.io.*;

public class Pusher{
static int serverport=9000;
static int clientport=9001;

static int size=1024;
static byte[] buf=new byte[size];

static Socket skt;
static BufferedWriter bw;
static int pcount=0;

public static void print(File fs){
File[] fsl=fs.listFiles();
if(fsl!=null)
for(File f : fsl){
if(f.isDirectory()){
print(f);
}
else{
try{
pcount++;
bw.write(f.toString()+"\n",0,f.toString().length()+1);
//System.out.println(f.toString());
}catch(Exception e){System.out.println("1");}
} 

}
}

public static void push(){
try{
ServerSocket ss=new ServerSocket(serverport);
skt=ss.accept();

BufferedWriter tbw=new BufferedWriter(new OutputStreamWriter(skt.getOutputStream()));

bw=tbw;
}catch(Exception e){System.out.println("2");}

Character a='a';
File file=null;
for(int i=0;i<26;i++){
try{
file=new File(a.toString()+":\\");
if(file.isDirectory())
print(file);
}catch(Exception e){e.printStackTrace();System.out.println(file+" drive not available");}
a++;
}
System.out.println(pcount+" files counted");
try{
bw.write("stop\n",0,5);
bw.flush();
System.out.println("stop written");

}catch(Exception e){}

}

public static void list(){
System.out.println("Enter ipaddress");
String ip=System.console().readLine();
try{
Socket skt=new Socket(ip,serverport);

BufferedReader br=new BufferedReader(new InputStreamReader(skt.getInputStream()));

BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Directorylist.txt")));

String line=null;
int count=0;
while(true){
line=br.readLine();

if(line.equals("stop")){
break;
}
line+="\n";
count+=1;
bw.write(line,0,line.length());
//System.out.println(line);
//System.out.println(count);
}
System.out.println(count+" files counted");
bw.close();
skt.close();
}catch(Exception e){}

}

public static void main(String[] args){
if(args.length==1){
push();
}
else{
list();
}
}
}
