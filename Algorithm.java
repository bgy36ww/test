package path.algorithm;

import path.container.*;

public class Algorithm{

	private int[][][] dMap;
	private int[][][] fdMap;
	private int[][][] fpMap;
	private int[][][] fbMap;	
	private int[][][] ffMap;

	private int[][][] traceMapx;
	private int[][][] traceMapy;
	private int[][][] mfdMap;
	private int[][][] mfbMap;
	private int[][][] aMap;
	private int iLength;
	private int jLength;
	private int[] order;
	private int[] value;
	private int startt;
	private int ld;
	
	private ROT bot;



	public int liftPOD(ROT b,int t,int i,int j){
		bot=b;
		bot.state=1-bot.state;
		for (int tt=t;tt<t+3;tt++){
			fdMap[tt][i][j]=5;		
			fbMap[tt][i][j]=b.ID;
			bot.coor[tt].first=i; bot.coor[tt].second=j;
		}
		return t+3;
	}
	public int dropPOD(ROT b,int t,int i,int j){
		bot=b;
		bot.state=1-bot.state;
		for (int tt=t;tt<t+3;tt++){
			fdMap[tt][i][j]=5;		
			fbMap[tt][i][j]=b.ID;
			bot.coor[tt].first=i; bot.coor[tt].second=j;
		}
		placeRest(t,i,j);
		return t+3;
	}


//down
	private void clearRest(int t, int i, int j){
		bot.podhold=fpMap[t][i][j];
		if (bot.podhold==0)  {System.out.println("Error no POD is here");System.exit(0);}
		for (int tt=t;tt<bot.maxsize;tt++){
			fpMap[tt][i][j]=0;
		}
	}
//down	
	public void placeRest(int t,int i,int j){
		for (int tt=t;tt<Amap.currtime;tt++){
			fpMap[tt][i][j]=bot.podhold;
		}
	}
	
//clear
	private boolean checkFringe(int t,int i,int j){
		return (t>=0)&&(t<aMap.length)&&(i>=0)&&(j>=0)&&(i<iLength)&&(j<jLength);
	}
//clear
	private int findTrace(int t,int i,int j){
		if ((checkFringe(t,i,j))&&(aMap[t][i][j]!=0)){
			return aMap[t][i][j];
		}
		return aMap.length*2;
	}
//down
	private void setMark(int t,int i,int j){
		ffMap[t][i][j]=0;
		fbMap[t][i][j]=bot.ID;
		bot.coor[t].first=i; bot.coor[t].second=j;
		mfbMap[t][i][j]=1;
		if ((bot.state==1)&&(bot.podhold>0)){
			fpMap[t][i][j]=bot.podhold;
		}


	}

//questioned
//might be gone
	private void setRestMark(int t,int i,int j){
		ffMap[t][i][j]=0;
	}

//need to combine this
	private int toNumber(int t,int i,int j){
		if (dMap[t][i][j]==1){
			return 1;
		}
		if (dMap[t][i][j]==2){
			return 2;
		}
		if (dMap[t][i][j]==4){
			return 3;
		}
		if (dMap[t][i][j]==8){
			return 4;
		}
		return dMap[t][i][j]+4;
	}
//need to fix this as well
	private char toOutput(int input){
                if (input==1){
                        return '^';
                }
                if (input==2){
                        return '>';
                }
                if (input==3){
                        return 'v';
                }
                if (input==4){
                        return '<';
                }
		return ((char)(input+48));
	}

//really important code here
	private int quickFix(int t,int i, int j){
		if (checkFringe(t+1,i-1,j))
		if (mfdMap[t+1][i-1][j]>0)
		return 1;
		if (checkFringe(t+1,i+1,j))
		if (mfdMap[t+1][i+1][j]>0) 
                return 3;
		if (checkFringe(t+1,i,j-1))
		if (mfdMap[t+1][i][j-1]>0) 
                return 4;
		if (checkFringe(t+1,i,j+1))
		if (mfdMap[t+1][i][j+1]>0) 
                return 2;
 		return mfdMap[t][i][j];
		
	}

//
	public void setRest(int t,int i,int j){
		
		for (int tt=t+1;tt<bot.maxsize;tt++){
                        setRestMark(tt,i,j);
                        fdMap[tt][i][j]=3;
                        mfdMap[tt][i][j]=3;
                        fbMap[tt][i][j]=bot.ID;
                }

	}




//way too redundent
//need to fix here
	public void recMap(int stt,int t,int i,int j){
		int b,b1;		
		int f=1;


		//setRest(t,i,j);

		while ((t>stt)&&(aMap[t][i][j]>0)){
			if (fdMap[t][i][j]>0){t--;continue;}
			b=aMap[t][i][j];
			setMark(t,i,j);
			fdMap[t][i][j]=toNumber(t,i,j);
			mfdMap[t][i][j]=fdMap[t][i][j];
			if (mfdMap[t][i][j]>4){
				fdMap[t][i][j]=quickFix(t,i,j);
				mfdMap[t][i][j]=fdMap[t][i][j];
			}
                        b1=findTrace(t-1,i,j);
                        if ((b-b1==1)){
				fdMap[t][i][j]=ld;
				mfdMap[t][i][j]=fdMap[t][i][j];
				ld=(ld+1)%4;
				f=1;
                                t--;
                                continue;
                        }

	
			b1=findTrace(t-1,i-1,j);
			if ((b-b1==2)&&((dMap[t-1][i-1][j]&4)>0)){
				if (f==1){f=0;fdMap[t][i][j]=3;mfdMap[t][i][j]=fdMap[t][i][j];}
				
				ld=3;
                                t--;
				i--;
                                continue;
                        }
                        b1=findTrace(t-1,i+1,j);
			if ((b-b1==2)&&((dMap[t-1][i+1][j]&1)>0)){
				if (f==1){f=0;fdMap[t][i][j]=1;mfdMap[t][i][j]=fdMap[t][i][j];}
				ld=1;
                                t--;
				i++;
                                continue;
                        }
			b1=findTrace(t-1,i,j-1);
                        if ((b-b1==2)&&((dMap[t-1][i][j-1]&2)>0)){
				if (f==1){f=0;fdMap[t][i][j]=2;mfdMap[t][i][j]=fdMap[t][i][j];}
				ld=2;
                                t--;
				j--;
                                continue;
                        }
			b1=findTrace(t-1,i,j+1);
			if ((b-b1==2)&&((dMap[t-1][i][j+1]&8)>0)){
				if (f==1){f=0;fdMap[t][i][j]=4;mfdMap[t][i][j]=fdMap[t][i][j];}
				ld=4;
                                t--;
				j++;
                                continue;
                        }
			if (mfdMap[t][i][j]>4){
        	                fdMap[t][i][j]=quickFix(t,i,j);
				mfdMap[t][i][j]=fdMap[t][i][j];

	                }
                


		setMark(t,i,j);
                fdMap[t][i][j]=dMap[t][i][j];
                mfdMap[t][i][j]=fdMap[t][i][j];
                if (mfdMap[t][i][j]>4){
                        fdMap[t][i][j]=quickFix(t,i,j);
                        mfdMap[t][i][j]=fdMap[t][i][j];
                }







			return;
		}	
		setMark(t,i,j);
		fdMap[t][i][j]=dMap[t][i][j];
		mfdMap[t][i][j]=fdMap[t][i][j];
	        if (mfdMap[t][i][j]>4){
                	fdMap[t][i][j]=quickFix(t,i,j);
			mfdMap[t][i][j]=fdMap[t][i][j];
             	}



	}
/*
//sort of useless function here
	public void printAMap(int t){
		for (int i=0;i<iLength;++i){
			for (int j=0;j<jLength;++j){
				System.out.print(aMap[t][i][j]);
				System.out.print("  ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void printDMap(int t){
                for (int i=0;i<iLength;++i){
                        for (int j=0;j<jLength;++j){
                                System.out.print(dMap[t][i][j]);
				System.out.print("  ");
                        }
                        System.out.println();
                }
                System.out.println();
	}
*/
        public void printFDMap(int t){
		System.out.println(t);		
                for (int i=0;i<iLength;++i){
                        for (int j=0;j<jLength;++j){
                                System.out.print(fdMap[t][i][j]);
                                System.out.print("  ");
                        }
                        System.out.println();
                }
                System.out.println();
        }



//clear
	private boolean checkRule(int t,int i,int j){
		if (checkFringe(t,i,j))
		{
			//colision check
			if (ffMap[t][i][j]==0) return false;
			if ((bot.state==1)&&(fpMap[t][i][j]!=0)) return false;	
			if ((fpMap[t][i][j]==0)&&(fbMap[t][i][j]==0)) return true;			
			if ((bot.state==0)&&(fbMap[t][i][j]==0)) return true;
		}
		return false;

	}	
//Clear

	private boolean checkColision(int t,int i1,int j1,int i2,int j2){
		if (checkRule(t,i2,j2)||checkRule(t+1,i1,j1)){
			return true;
		}
		return false;
	}


//fixed
	private boolean checkMap(int t,int i1,int j1,int i2,int j2,int nextDir){
		
		//ownMap.printLength();		
		if (checkColision(t,i1,j1,i2,j2)) {		
		if (checkRule(t+1,i2,j2)){
			if(checkShort(t,i1,j1,i2,j2)){
			if ((i1!=i2)||(j1!=j2)){
				aMap[t+1][i2][j2]=aMap[t][i1][j1]+2;
			}
			else{
				aMap[t+1][i2][j2]=aMap[t][i1][j1]+1;	
			}
			this.traceMapx[t+1][i2][j2]=i1;
			this.traceMapy[t+1][i2][j2]=j1;
			dMap[t+1][i2][j2]|=nextDir;
			}
		}
		}
		return false;
	}
//clear

	private boolean checkShort(int t,int i1,int j1,int i2,int j2){
		if (aMap[t+1][i2][j2]!=0)
		return ((aMap[t][i1][j1]<aMap[t+1][i2][j2]));
		return true;
	}
	/* class constructor
	Create every robot with it's starting map*/

//clear
	public int planRoute(ROT b,int stx, int sty, int std, int dsx, int dsy, int stt){
		this.bot=b;
		this.traceMapx=Amap.cMap();
		this.traceMapy=Amap.cMap();
		this.dMap=Amap.cMap();
		this.aMap=Amap.cMap();
		this.iLength=Amap.ilength;
		this.jLength=Amap.jlength;
		
		this.mfdMap=bot.mfdMap;
		this.mfbMap=bot.mfbMap;
		this.fdMap=Amap.fdMap;
		this.fpMap=Amap.fpMap;
		this.fbMap=Amap.fbMap;
		this.ffMap=Amap.ffMap;
		
		startt=stt;
		fdMap[stt][stx][sty]=std;
		fbMap[stt][stx][sty]=bot.ID;
		bot.coor[stt].first=stx; bot.coor[stt].second=sty;

	// stx, sty: starting position  std:starting direction dsx,dsy: ending position  stt: starting time
	// Method to calculate shortest path from starting position to ending position from starting time
	if (bot.state==1){clearRest(stt,stx,sty);}
	
	int t=stt;
	aMap[stt][stx][sty]=1;
	dMap[stt][stx][sty]=todir(std);
	
	while ((aMap[t][dsx][dsy]==0)&&(t+1<aMap.length)){
		for (int i=0;i<iLength;i++){
			for (int j=0;j<jLength;j++){
				if (aMap[t][i][j]!=0){
					turnROT(t,i,j);
				}
			}
		}
		t++;
	}
	this.order=bot.order;
	this.value=bot.value;
	System.out.println(t);
	bot.order[0]=1;
	bot.value[0]=1;

	int ttt=t;
	int iii=dsx;
	int jjj=dsy;	
	System.out.println();
	while (ttt>=stt){
	System.out.println(iii);
	System.out.println(jjj);
	iii=traceMapx[ttt][iii][jjj];
	jjj=traceMapy[ttt][iii][jjj];	
	ttt--;
	}
	System.out.println();



	recMap(stt,t,dsx,dsy);
	
	
	for (int i=stt;i<t+1;i++){
		//printAMap(i);
		printFDMap(i);
		//ownMap.printMap(i);
	}


	for (int i=stt;i<t+1;i++)System.out.println(bot.order[i]);
	bot.direction=fdMap[t][dsx][dsy];
	bot.locationX=dsx;
	bot.locationY=dsy;
	bot.time=t;
	return t;
	}

	private void turnROT(int t,int i, int j){
		if ((dMap[t][i][j]&1)>0)  checkMap(t,i,j,i-1,j,1);
                if ((dMap[t][i][j]&2)>0)  checkMap(t,i,j,i,j+1,2);
                if ((dMap[t][i][j]&4)>0)  checkMap(t,i,j,i+1,j,4);
                if ((dMap[t][i][j]&8)>0)  checkMap(t,i,j,i,j-1,8);
		int nd=dMap[t][i][j]|(dMap[t][i][j]<<1|dMap[t][i][j]>>1);
		if ((dMap[t][i][j]&1)==1)
		nd|=8;
		checkMap(t,i,j,i,j,((nd%16)|(nd/16)));
	}
	public int todir(int t){
		if (t==3) return (4);
		if (t==4) return (8);
		return t;

	}
/*useless printing function
	public void printPlan(int t){
	
		for (int i=0;i<t+1;i++){
		//ownMap.printMap(i);
		printFDMap(i);
		}
	}
*/

}
