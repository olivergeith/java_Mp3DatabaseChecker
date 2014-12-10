package og.checker.filewalker.checks;

import java.io.File;
import java.io.IOException;

import og.basics.gui.tracepanel.ITracer;
import og.checker.filewalker.DirectoryInfos;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v24Frames;

public class ID3V2GenreChecker extends AbstractChecker {

	public ID3V2GenreChecker(ITracer tracer) {
		super(tracer);
	}

	@Override
	String getErrorText() {
		return "ID3V2 Genre Consistency Error";
	}

	@Override
	boolean performCheck(DirectoryInfos dirInfo) {
		// check nur auf Musicdirs .....
		if (dirInfo.isMusicDir()) {

			String firstGenre = null;
			for (File musicfile : dirInfo.getMusicFiles()) {
				try {
					MP3File f = (MP3File) AudioFileIO.read(musicfile);
					if (f.hasID3v2Tag()) {
						AbstractID3v2Tag tag = f.getID3v2Tag();
						String genre = tag.getFirst(ID3v24Frames.FRAME_ID_GENRE);
						if (genre.length() == 0) {
							return false;
						}

						if (firstGenre == null)
							firstGenre = genre;
						else {
							if (!firstGenre.equals(genre)) {
								return false;
							}
						}
					}

				} catch (CannotReadException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TagException e) {
					e.printStackTrace();
				} catch (ReadOnlyFileException e) {
					e.printStackTrace();
				} catch (InvalidAudioFrameException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
}
