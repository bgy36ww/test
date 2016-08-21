package path.communication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import path.algorithm.*;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.*;
import java.util.Arrays;
import java.util.concurrent.*;
import path.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A TCP server that runs on port 9090.  When a client connects, it
 * sends the client the current date and time, then closes the
 * connection with that client.  Arguably just about the simplest
 * server you can write.
 */
public class ComServer {
	private ServerSocket listener;
	private Socket socket;
	private DataInputStream input;
	private LogWriter logwriter;
	private int id;
	public byte[] state;
	public ComServer() throws IOException {
	listener = new ServerSocket(9000);
	socket = listener.accept();
	socket.setSoTimeout(1000);
	logwriter=new LogWriter();
	input=new DataInputStream(socket.getInputStream());

	
	//byte[] first=new byte[]{(byte)0xFC,0x0C,0x00,0x01,0x00,0x00,0x04,0x00,0x00,0x00,0x00,(byte)0x5A};
	//byte[] cdata=Arrays.copyOfRange(first,0,8);
	//CRC16 crc=new CRC16();
	//byte[] fid=crc.process(cdata);
	//first[9]=fid[0];
	//first[10]=fid[1];
	//write(first);
	//get();
	//second=new byte[]{(byte)0xFC,(byte)0x1D,0x00,0x01,0x00,0x00,0x01,0x00,0x01,0x01,0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x07,(byte)0xD0,0x00,(byte)0x5A,0x00,(byte)0x5A,0x00,(byte)0x2E,0x07,(byte)0x5A};
	//cdata=Arrays.copyOfRange(second,0,25);
	//fid=crc.process(cdata);
	//second[26]=fid[0];
	//second[27]=fid[1];
	//write(second);
	//get();

	}
	public byte[] write(byte[] data,int t) throws IOException {
	DataOutputStream out=new DataOutputStream(socket.getOutputStream());
	//System.out.println("Hello");
        out.write(data);
        byte[] ret=null;
        try{
            ret=get();	
            TimeUnit.SECONDS.sleep(t);
	}
	catch(Exception e){
            e.printStackTrace();
            
        }
	return ret;
        }

	public void close() {
	try{	
	
	listener.close();
	socket.close();
	logwriter.close();}
	catch(Exception e){e.printStackTrace();}	
	}

	public byte[] get() throws IOException{
	ArrayList<Byte> list=new ArrayList<Byte>();
	int n=0;
	byte bt=input.readByte();
	list.add(bt);
        
        //debug
	StringBuilder sb = new StringBuilder();
	sb.append(String.format("%02X ", bt));
        
        //log recording
	logwriter.write(sb.toString()+" ");
	System.out.println(sb.toString()+" ");
	
        //error checking
        while ((int)(bt&0xff)-252!=0){
        
	bt=input.readByte();
	list.add(bt);
	sb = new StringBuilder();
	sb.append(String.format("%02X ", bt));
	logwriter.write(sb.toString()+" ");
	System.out.println(sb.toString()+" ");
	}
        
        
	if (((int)(bt&0xff)-252)==0){
	bt=input.readByte();
	list.add(bt);
	n=(int)bt;
	sb = new StringBuilder();
	sb.append(String.format("%02X ", bt));
	logwriter.write(sb.toString()+" ");
	System.out.println(n-2);
	}
        
	for (int i=0;i<n-2;i++){
	bt=input.readByte();
	list.add(bt);	
	sb = new StringBuilder();
	sb.append(String.format("%02X ", bt));
	logwriter.write(sb.toString()+" ");
	System.out.println(sb.toString()+" ");
	}
        
	state= toArray(list);
	return state;
	}	
        
	private byte[] toArray(ArrayList<Byte> inarray) {
	byte[] ret = new byte[inarray.size()];
    	Iterator<Byte> iterator = inarray.iterator();
    	for (int i = 0; i < ret.length; i++)
    	{
       		ret[i] = iterator.next().byteValue();
    	}
    	return ret;
  	}
}
