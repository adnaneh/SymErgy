package main;

public class Message {
	
	public Message(String str, double timestamp) {
		super();
		this.str = str;
		this.unread = false;
		this.t = timestamp;
		this.genId();
	}
	//lastid
	private static int last_id=0;
	//id
	private int id;
	//content
	private String str;
	//marked unread
	private Boolean unread;
	//time of reception
	private double t;
	//constructor
	public double getD() {
		return t;
	}
	public void setD(int t) {
		this.t = t;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public Boolean getUnread() {
		return unread;
	}
	public void setUnread(Boolean unread) {
		this.unread = unread;
	}
	public void genId() {
		int n= Message.last_id;
		this.id= n+1;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
