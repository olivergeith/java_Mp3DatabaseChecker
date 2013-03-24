package og.checker.filewalker.checks;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.jaudiotagger.tag.id3.ID3v23Frames;

public class TagHelper {
	public static void debugTagFields(MP3File f) throws UnsupportedEncodingException {
		System.out.println("Debugging: " + f.getFile().getPath());
		System.out.println(" hasID3v1Tag: " + f.hasID3v1Tag());
		System.out.println(" hasID3v2Tag: " + f.hasID3v2Tag());

		if (f.hasID3v1Tag()) {
			System.out.println("ID3 V1 Tags:");
			ID3v1Tag tag1 = f.getID3v1Tag();
			System.out.println(" - " + tag1.getYear());
			System.out.println(" - " + tag1.getArtist());
		}

		if (f.hasID3v2Tag()) {
			System.out.println("ID3 V2 Tags:");
			AbstractID3v2Tag tag = f.getID3v2Tag();
			Iterator<TagField> iter = tag.getFields();
			while (iter.hasNext()) {
				TagField t = iter.next();
				String id = t.getId();
				String value = tag.getFirst(id);
				System.out.println(" ID=" + id + " Value=" + value);
			}
		}
	}

	public static String getYear(AbstractID3v2Tag tag) {
		String year = tag.getFirst(ID3v23Frames.FRAME_ID_V3_TYER);
		if (year.length() == 0)
			year = tag.getFirst("TDRC");
		return year;
	}

}
