package com.tph.ingame.tools.characters;

public class Stats{
	private int 
		PM,PM_MAX,
		hp,HP_MAX,
		magic,
		atq,
		evasion,
		accuracy,
		mov,
		MIN_RANGE,
		MAX_RANGE
	;
	private byte classe,race;
	private float exp=0;
	private int lvl=1;
	private String url;
	
	public Stats( String url,int lvl){
		
		this.lvl=lvl;
		this.url=url;
		
		if( url.equals ( "sniper") )
			newStats( Classe.ARCHER,Classe.HUMAN,lvl);
		if( url.equals ( "necromancer") )
			newStats( Classe.ARCHER,Classe.ORCS,lvl);
		if( url.equals ( "elementalist") )
			newStats( Classe.ARCHER,Classe.DRUIDS,lvl);
	
		if( url.equals ( "warrior") )
			newStats( Classe.WARRIOR,Classe.HUMAN,lvl);
		if( url.equals ( "slayer") )
			newStats( Classe.WARRIOR,Classe.ORCS,lvl);
		if( url.equals ( "shinobi") )
			newStats( Classe.WARRIOR,Classe.DRUIDS,lvl);
		
		if( url.equals ( "gunner") )
			newStats( Classe.TANK,Classe.HUMAN,lvl);
		else if( url.equals ( "fear") )
			newStats( Classe.TANK,Classe.ORCS,lvl);
		else if( url.equals ( "white mage") )
			newStats( Classe.TANK,Classe.DRUIDS,lvl);
		
		else if( url.equals ( "alchemist") )
			newStats( Classe.MAGE,Classe.HUMAN,lvl);
		else if( url.equals ( "priest") )
			newStats( Classe.MAGE,Classe.ORCS,lvl);
		else if( url.equals ( "black mage") )
			newStats( Classe.MAGE,Classe.DRUIDS,lvl);
	}
	
	
	private void newStats( byte classe,byte race,int lvl){
		
		this.classe=classe;
		this.race=race;
		HP_MAX=hp=Classe.newHp( classe, race)*lvl;
		magic=Classe.newMagic( classe, race)*lvl;
		atq=Classe.newAtq( classe, race)*lvl;
		evasion=Classe.newEvasion( classe, race)*lvl;
		accuracy=Classe.newAccuracy( classe, race)*lvl;
		mov=Classe.newMov( classe, race);
		PM_MAX=PM=Classe.newPM( classe,race);
		MIN_RANGE=Classe.newMINRANGE( classe,race);
		MAX_RANGE=Classe.newMAXRANGE( classe,race);
		
	}
	public void update( ){
		if( exp>=100) lvlUp( );
	}
	protected void lvlUp( ){
		int lvl_nb=( int)exp/100;
		if( lvl+lvl_nb<=20){
			lvl+=lvl_nb;
			statsByLvl( lvl_nb);
		}
		else
		{
			lvl_nb=20-lvl;
			if( lvl_nb>0)
				statsByLvl( lvl_nb);
		}
		exp%=100;
	}

	protected void statsByLvl( int nb){

		HP_MAX=hp+=Classe.newHp( classe, race);
		magic+=Classe.newMagic( classe, race);
		atq+=Classe.newAtq( classe, race);
		evasion+=Classe.newEvasion( classe, race);
		accuracy+=Classe.newAccuracy( classe, race);
		mov+=Classe.newMov( classe, race);
	}
	public int getPM( ) {return PM;}
	public int getPM_MAX( ) {return PM_MAX;}
	public int getHp( ) {return hp;}
	public int getHP_MAX( ) {return HP_MAX;}
	public int getMagic( ) {return magic;}
	public int getAtq( ) {return atq;}
	public int getEvasion( ) {return evasion;}
	public int getAccuracy( ) {return accuracy;}
	public int getMov( ) {return mov;}
	public byte getClasse( ) {return classe;}
	public byte getRace( ) {return race;}
	public float getExp( ) {	return exp;}
	public int getLvl( ) {	return lvl;}
	public int getMIN_RANGE( ) { return MIN_RANGE; }
	public int getMAX_RANGE( ) { return MAX_RANGE; }
	public void setHp( int hp) { this.hp = hp; }
	public boolean isRanged( ) { if ( classe == Classe.ARCHER ) return true ; return false ;}
	public boolean isSupport( ) { if ( classe == Classe.MAGE ) return true ; return false ;}
	public boolean isCac( ) { if ( classe == Classe.TANK || classe == Classe.WARRIOR ) return true ; return false ;}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}
	
}
