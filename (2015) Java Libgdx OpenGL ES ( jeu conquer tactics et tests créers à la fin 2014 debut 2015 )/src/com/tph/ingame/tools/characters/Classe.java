package com.tph.ingame.tools.characters;

import com.badlogic.gdx.math.RandomXS128;

public class Classe {

	protected static RandomXS128 random=new RandomXS128();

	public static final byte 
		DRUIDS=0,
		ORCS  =1,
		HUMAN =2;
	public static final byte
		TANK=0,
		WARRIOR=1,
		ARCHER=2,
		MAGE=3;
	public static final byte
		HP=0,
		MAGIC=1,
		ATQ=2,
		EVASION=5,
		ACCURACY=6,
		MOV=7;
	/*
	
	 * T: D ( Tanky DPS ), H ( OffTank ), O ( TANK ) 
	 * W: D ( 7 -DPS -DEF +CC ), H ( 9 =DPS =DEF ), O ( 11 +DPS -Def ) Fighters
	 * A: D ( tricky ), H ( classic ), O ( slow, steady )
	 * M: D ( 6 ), H ( 8 ), O ( 10 )
	*/
	/**HP :
	 * T: D ( 10 ), H ( 12 ), O ( 14 ) 
	 * W: D ( 7 ), H ( 9 ), O ( 11 )
	 * A: D ( 6 ), H ( 8 ), O ( 10 )
	 * M: D ( 6 ), H ( 8 ), O ( 10 )
	 * The informations above display the range of the stat for each job & race.            
	 * Tank ( T ), Warrior ( W ), Archer ( A ), Mage( M ).
	 * DRUIDS ( D ), ORCS ( O ), HUMANS ( H ).
	 * @param classe Class.DRUIDS, Class.ORCS, or Class.HUMAN. 
	 * @param race Class.TANK, Class.WARRIOR, Class.ARCHER, or Class.MAGE.
	 * @return Hp Value
	 */
	public static int newPM(byte classe, byte race){
		if(classe==TANK   ) 
			return 5;
		if(classe==WARRIOR)
			return 7;
		if(classe==ARCHER )
			return 6;
		if(classe==MAGE   )
			return 6;
		return 3;
		
	}
	public static int newHp(byte classe,byte race){
		if(classe==TANK   ) 
			return first(race,HP)+random.nextInt()%3+3;
		if(classe==WARRIOR)
			return first(race,HP)+random.nextInt()%2+2;
		if(classe==ARCHER )
			return first(race,HP)+random.nextInt()%2;
		if(classe==MAGE   )
			return first(race,HP)+random.nextInt()%2+2;
		return first(race,HP)+random.nextInt()%2;
	}
	public static int newAtq(byte classe,byte race){
		
		if(classe==TANK   )
			return first(race,HP)+random.nextInt()%2+1;
		if(classe==WARRIOR)
			return first(race,HP)+random.nextInt()%3+2;
		if(classe==ARCHER )
			return first(race,HP)+random.nextInt()%4+3;
		if(classe==MAGE   )
			return first(race,HP)+random.nextInt()%2+2;
		return first(race,HP)+random.nextInt()%2;
	}
public static int newMagic(byte classe,byte race){
		
		if(classe==TANK   )
			return first(race,HP)+random.nextInt()%2+2;
		if(classe==WARRIOR)
			return first(race,HP)+random.nextInt()%3+3;
		if(classe==ARCHER )
			return first(race,HP)+random.nextInt()%3;
		if(classe==MAGE   )
			return first(race,HP)+random.nextInt()%2+2;
		return first(race,HP)+random.nextInt()%2;
	}
public static int newDef(byte classe,byte race){
	
	if(classe==TANK   )
		return first(race,HP)+random.nextInt()%2+2;
	if(classe==WARRIOR)
		return first(race,HP)+random.nextInt()%3+3;
	if(classe==ARCHER )
		return first(race,HP)+random.nextInt()%3;
	if(classe==MAGE   )
		return first(race,HP)+random.nextInt()%2+2;
		return first(race,HP)+random.nextInt()%2;
}
public static int newRes(byte classe,byte race){
	
	if(classe==TANK   )
		return first(race,HP)+random.nextInt()%2+2;
	if(classe==WARRIOR)
		return first(race,HP)+random.nextInt()%3+3;
	if(classe==ARCHER )
		return first(race,HP)+random.nextInt()%3;
	if(classe==MAGE   )
		return first(race,HP)+random.nextInt()%2+2;
		return first(race,HP)+random.nextInt()%2;
}
public static int newEvasion(byte classe,byte race){
	
	if(classe==TANK   )
		return first(race,HP)+random.nextInt()%2+2;
	if(classe==WARRIOR)
		return first(race,HP)+random.nextInt()%3+3;
	if(classe==ARCHER )
		return first(race,HP)+random.nextInt()%3;
	if(classe==MAGE   )
		return first(race,HP)+random.nextInt()%2+2;
		return first(race,HP)+random.nextInt()%2;
}
public static int newAccuracy(byte classe,byte race){
	
	if(classe==TANK   )
		return first(race,HP)+random.nextInt()%2+2;
	if(classe==WARRIOR)
		return first(race,HP)+random.nextInt()%3+3;
	if(classe==ARCHER )
		return first(race,HP)+random.nextInt()%3;
	if(classe==MAGE   )
		return first(race,HP)+random.nextInt()%2+2;
		return first(race,HP)+random.nextInt()%2;
}
public static int newMov(byte classe,byte race){
	
	if(classe==TANK   )
		return first(race,HP)+random.nextInt()%2+2;
	if(classe==WARRIOR)
		return first(race,HP)+random.nextInt()%3+3;
	if(classe==ARCHER )
		return first(race,HP)+random.nextInt()%3;
	if(classe==MAGE   )
		return first(race,HP)+random.nextInt()%2+2;
		return first(race,HP)+random.nextInt()%2;
}
	
	
	public static int first(byte race,byte stat){
		if(race==DRUIDS) // 5,9,
			switch(stat){
			case HP:
				break;
			case MAGIC:
				break;
			case ATQ:
				break;
			case EVASION:
				break;
			case ACCURACY:
				break;
			case MOV:
				break;
		}
		if(race==ORCS)
			switch(stat){
			case HP:
				break;
			case MAGIC:
				break;
			case ATQ:
				break;
			case EVASION:
				break;
			case ACCURACY:
				break;
			case MOV:
				break;
		}
		if(race==HUMAN)
			switch(stat){
			case HP:
				break;
			case MAGIC:
				break;
			case ATQ:
				break;
			case EVASION:
				break;
			case ACCURACY:
				break;
			case MOV:
				break;
		}
		return 7;	
	}
	public static int newMINRANGE(byte classe, byte race) {
		if(classe==TANK   ) 
			return 1;
		if(classe==WARRIOR)
			return 1;
		if(classe==ARCHER )
			return 2;
		if(classe==MAGE   )
			return 1;
		return 1;
		
	}
	public static int newMAXRANGE(byte classe, byte race) {
		if(classe==TANK   )
			return 1;
		if(classe==WARRIOR)
			return 1;
		if(classe==ARCHER )
			return 3;
		if(classe==MAGE   )
			return 3;
		return 1;
		
	}
	
		
}
