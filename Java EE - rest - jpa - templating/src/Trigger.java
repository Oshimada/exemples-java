package ma.otaku.services.servBehavior;
public interface Trigger<T>{

	public void execute(T t)throws Exception;

	public static final int BEFORE =  4;
	public static final int AFTER  =  5;
	public static final int EITHER =  6;

	public int getMode();
	public void setMode(int mode);
}
