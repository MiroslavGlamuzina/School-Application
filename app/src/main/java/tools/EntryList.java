package tools;

public class EntryList {

	String date = "DATE";
	String title = "TITLE";

	int noteSize = 0;
	int videoSize = 0;
	int audioSize = 0;
	int drawingSize = 0;
	int pictureSize = 0;
	int tagsSize = 0;
	int flagSize = 0;
	int ID = 0;

	public EntryList(String title, String date, int audioSize, int drawingSize, int pictureSize, int tagsSize, int notesSize, int flagsSize, int ID) {
		this.title = title;
		this.date = date;
		this.audioSize = audioSize;
		this.drawingSize = drawingSize;
		this.pictureSize = pictureSize;
		this.pictureSize = pictureSize;
		this.tagsSize = tagsSize;
		this.flagSize = flagsSize;
		this.noteSize = notesSize;
		this.ID = ID;
	}

	public int getNoteSize() {
		return noteSize;
	}

	public void setNoteSize(int noteSize) {
		this.noteSize = noteSize;
	}

	public int getVideoSize() {
		return videoSize;
	}

	public void setVideoSize(int videoSize) {
		this.videoSize = videoSize;
	}

	public int getAudioSize() {
		return audioSize;
	}

	public void setAudioSize(int audioSize) {
		this.audioSize = audioSize;
	}

	public int getDrawingSize() {
		return drawingSize;
	}

	public void setDrawingSize(int drawingSize) {
		this.drawingSize = drawingSize;
	}

	public int getPictureSize() {
		return pictureSize;
	}

	public void setPictureSize(int pictureSize) {
		this.pictureSize = pictureSize;
	}

	public int getTagsSize() {
		return tagsSize;
	}

	public void setTagsSize(int tagsSize) {
		this.tagsSize = tagsSize;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
