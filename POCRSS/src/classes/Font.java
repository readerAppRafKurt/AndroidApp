package classes;

public class Font{
	
	private String type, color;
	private boolean bold, italic;
	private int size;
	
	
	public Font(){
		
	}
	
	public Font(int size, String type, String color, boolean bold, boolean italic){
		
		this.size=size;
		this.type=type;
		this.color=color;
		this.bold=bold;
		this.italic=italic;

	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public boolean isItalic() {
		return italic;
	}

	public void setItalic(boolean italic) {
		this.italic = italic;
	}

}
