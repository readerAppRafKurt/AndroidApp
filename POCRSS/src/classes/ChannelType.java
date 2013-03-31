package classes;

public enum ChannelType {
	
	BINNENLAND(1,"Nieuwsblad.be: Binnenland","http://www.nieuwsblad.be/rss.aspx?foto=1&intro=1&full=1&mobile=1&sectionid=55178e67-15a8-4ddd-a3d8-bfe5708f8932"), 
	BUITENLAND(2,"Nieuwsblad.be: Buitenland","http://www.nieuwsblad.be/rss.aspx?foto=1&intro=1&full=1&mobile=1&sectionid=7f1bc231-66e7-49f0-a126-b7346eb3e2fa&ContentType=Article");/*
	CULTUUR(3,"Nieuwsblad.be: Cultuur","http://www.nieuwsblad.be/rss.aspx?foto=1&intro=1&full=1&mobile=1&sectionid=d4338791-310d-4eeb-b9d6-7bb9825b48bb&ContentType=Article"),
	ECONOMIE(4,"Nieuwsblad.be: Economie","http://www.nieuwsblad.be/rss.aspx?foto=1&intro=1&full=1&mobile=1&sectionid=c0c3b215-10be-4f82-86d6-8b8584a5639d&ContentType=Article"),
	LIFE(5,"Nieuwsblad.be: Life","http://www.nieuwsblad.be/rss.aspx?foto=1&intro=1&full=1&mobile=1&sectionid=2b11c4e2-caed-4ea5-b0bf-a689302e89d4&ContentType=Article");*/
	
	private String type,url;
	private int id;
	
	
	private ChannelType(int id,String type,String url){
		this.id=id;
		this.type=type;
		this.url=url;
	}

	public String getType() {
		return type;
	}

	public int getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

}
