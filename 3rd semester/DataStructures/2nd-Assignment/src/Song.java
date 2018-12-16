import java.lang.Comparable;

/**
 * POJO class
 */
public class Song implements Comparable<Song>, Key {
    private int id;
    private int likes;
    private String title;


	public Song(int id, String title, int likes) {
		this.id=id;
		this.title=title;
		this.likes=likes;
	}

	//Getters


	public int getId() {
		return id;
	}

	public int getLikes() {
		return likes;
	}

	public String getTitle() {
		return title;
	}

	@Override
     public int compareTo(Song s1) {
		if(this.likes>s1.likes) {
			return 1;
		}else if(this.likes<s1.likes) {
			return -1;
		}else {
			String title1 = this.title;
			String title2 = s1.title;
			int len1,len2, length;
			len1=title1.length();
			len2=title2.length();
			if(len1<len2)
				 length=len1;
			else 
				length=len2;
			int i=0;
			while(i<length ) {
				if(title1.charAt(i)<title2.charAt(i)) {
					return 1;
				}else if(title1.charAt(i)>title2.charAt(i)) {
					return -1;
				}else {
					i++;
				}
			}
			return 0;
		}
	}

	@Override
	public int getKey() {
		return id;
	}

	@Override
	public String toString() {
		return getLikes() + " likes, achieved by song: " + getTitle() + ".";
	}
}