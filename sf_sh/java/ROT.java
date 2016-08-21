package path.container;
import java.io.*;
import path.communication.*;

public class ROT{
	static public int maxsize=999;
	public int locationX;
	public int locationY;
	private byte[] check;
	
	public int[] CoX;
	public int[] CoY;
	public int[] dir;
	public int time;
	public int state;
	public int pLevel;
	public int speed;
	public int direction;
	public int ID;
	public int podhold;
	public int ld;
	public int[] order;
	public int[] value;
	public Dual[] coor;
	public Dual[] orders;
	public int count;
	public byte[] status;
	public int rem;
        private ComServer coms;
        
        public int missionstatus;

	public int[][][] mfdMap;
	public int[][][] mfbMap;  


	public boolean getStatus(byte[] s){
		status=s;
                System.out.print("The length is:");
                System.out.println(s.length);
                if (status.length==0x2B){
		locationX= (status[11]<<24)&0xff000000|
       		(status[12]<<16)&0x00ff0000|
       		(status[13]<< 8)&0x0000ff00|
       		(status[14]<< 0)&0x000000ff;
		locationY= (status[15]<<24)&0xff000000|
       		(status[16]<<16)&0x00ff0000|
       		(status[17]<< 8)&0x0000ff00|
       		(status[18]<< 0)&0x000000ff;
		direction=(status[19]<< 8)&0x0000ff00|
       		(status[20]<< 0)&0x000000ff;
		rem=(status[25]<< 0)&0x000000ff;
		System.out.print("The current location is:");
		System.out.print(locationX);
		System.out.print(" ");
		System.out.print(locationY);
		System.out.print("direction is:");
		System.out.print(direction);
		System.out.print("Remaining tasks are:");
		System.out.print(rem);
		System.out.println();
                return true;
                }else{if (s.length==13){
                missionstatus=(status[9]<< 0)&0x000000ff;
                if (missionstatus!=0){
                System.out.print("fatal error in communication");
                System.out.print(missionstatus);
                System.out.println();
                System.out.flush();
                return false;
                //System.exit(0);
                }
                return true;
                }else{System.out.println("not a command");return false;}}
                //return false;
	}

	public void setStatus(int s){
		state=s;
	}	


	/* class constructor
	Create every robot with it's starting map*/

	public ROT(int x,int y,int id){
		ID=id;

		locationX=x;
		locationY=y;

		state=0;
		mfdMap=Amap.cMap();
		mfbMap=Amap.cMap();

		//order=new int[maxsize];
		order=new int[maxsize];
		value=new int[maxsize];
		
		value=new int[maxsize];		
		CoX=new int[maxsize];
		CoY=new int[maxsize];
		coor=new Dual[maxsize];
		for(int i=0;i<maxsize;i++){
			coor[i]=new Dual();
		}
		dir=new int[maxsize];
	}
	/*initialize all static member should only done once*/
	public boolean ini(ComServer com,byte[] c){
            coms=com;
            check=c;
            return !((com==null)||(c==null));
	}
        public void write(byte[] bt,int t) throws IOException{
            byte[] ret;
            do{
            do
            {ret=coms.write(check,t);
            }while(!getStatus(ret));
            }while(rem>10);
            
            do
            {ret=coms.write(bt,t);
            }while(!getStatus(ret));
            
            
        }
        



}
