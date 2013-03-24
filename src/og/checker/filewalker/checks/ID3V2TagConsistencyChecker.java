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

public class ID3V2TagConsistencyChecker extends AbstractChecker {

	public ID3V2TagConsistencyChecker(ITracer tracer) {
		super(tracer);
	}

	@Override
	String getErrorText() {
		return "ID3V2 Tag Consistency Error";
	}

	@Override
	boolean performCheck(DirectoryInfos dirInfo) {
		// check nur auf Musicdirs .....
		if (dirInfo.isMusicDir()) {
			boolean isInSamplerUsw = dirInfo.isInSamplerXmasMusicalOst();

			String firstFileSignature = null;
			for (File musicfile : dirInfo.getMusicFiles()) {
				try {
					MP3File f = (MP3File) AudioFileIO.read(musicfile);
					if (f.hasID3v2Tag()) {
						AbstractID3v2Tag tag = f.getID3v2Tag();
						String albumName = tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM);
						String year = TagHelper.getYear(tag);
						String albumArtist = tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM_ARTIST);
						if (albumName.length() == 0 || year.length() == 0 || albumArtist.length() == 0) {
							// getTracer().appendText("\nConsistency -> Empty Field found: "
							// + f.getFile().getName());
							return false;
						}

						String signature = createSignature(albumName, year, albumArtist, isInSamplerUsw);

						if (firstFileSignature == null)
							firstFileSignature = signature;
						else {
							if (!firstFileSignature.equals(signature)) {
								// getTracer().appendText("\nConsistency -> Signature not identical "
								// + f.getFile().getName());
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

	private String createSignature(String albumName, String year, String albumArtist, boolean isInSamplerUsw) {
		if (isInSamplerUsw)
			return albumName + year;
		else
			return albumName + year + albumArtist;
	}
}
