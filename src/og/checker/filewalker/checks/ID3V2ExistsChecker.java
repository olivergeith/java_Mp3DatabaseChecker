package og.checker.filewalker.checks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import og.basics.gui.tracepanel.ITracer;
import og.checker.filewalker.DirectoryInfos;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v24Frames;

public class ID3V2ExistsChecker extends AbstractChecker {

	private boolean doRepair = false;

	public ID3V2ExistsChecker(ITracer tracer, boolean doRepair) {
		super(tracer);
		this.doRepair = doRepair;
	}

	@Override
	String getErrorText() {
		return "ID3V2 does't exist";
	}

	@Override
	boolean performCheck(DirectoryInfos dirInfo) {
		if (dirInfo.isMusicDir()) {
			boolean needsrepair = false;
			for (File musicfile : dirInfo.getMusicFiles()) {
				try {
					MP3File f = (MP3File) AudioFileIO.read(musicfile);
					if (!f.hasID3v2Tag()) {
						// getTracer().appendText("No id3v2: " +
						// f.getFile().getName());
						needsrepair = true;
						if (f.hasID3v1Tag()) {
							repairID3V2Tag(f, null);
						}
					} else {
						AbstractID3v2Tag tag = f.getID3v2Tag();
						String albumArtist = tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM_ARTIST);
						String album = tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM);
						String year = TagHelper.getYear(tag);
						if (year.length() == 0 || album.length() == 0 || albumArtist.length() == 0) {
							// getTracer().appendText("Corrupt id3v2: " +
							// f.getFile().getName());
							needsrepair = true;
							if (f.hasID3v1Tag()) {
								repairID3V2Tag(f, tag);
							}
						}
					}
				} catch (CannotReadException e) {
					e.printStackTrace();
					return false;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				} catch (TagException e) {
					e.printStackTrace();
					return false;
				} catch (ReadOnlyFileException e) {
					e.printStackTrace();
					return false;
				} catch (InvalidAudioFrameException e) {
					e.printStackTrace();
					return false;
				}
			}
			if (needsrepair)
				return false;
		}
		return true;
	}

	private void repairID3V2Tag(MP3File f, AbstractID3v2Tag tag) throws FileNotFoundException, IOException, TagException {
		if (doRepair) {
			getTracer().appendText("Repariring id3v2: " + f.getFile().getName());
			if (tag != null)
				f.delete(tag);
			f.setID3v2Tag(f.getID3v1Tag());
			AbstractID3v2Tag newtag = f.getID3v2Tag();
			newtag.addField(FieldKey.ALBUM_ARTIST, newtag.getFirst("TPE1"));
			TagHelper.debugTagFields(f);
			f.save();
		}
	}
}
