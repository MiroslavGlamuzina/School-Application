package helpers;

public class Attributes {
	private String time;
	private String title;
	private String description;
	private String tag;
	final private String SPLIT_CODE = "*&^%*&^%";

	public Attributes() {

	}

	public void load() {

	}

	public void save() {
		String result = "";
		result = "time" + SPLIT_CODE + time + SPLIT_CODE + "title" + SPLIT_CODE + title + SPLIT_CODE + "description"
				+ SPLIT_CODE + description + SPLIT_CODE + "tag" + SPLIT_CODE + tag + "";
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag += "," + tag;
	}
}
