package og.checker.filewalker.checks;

import java.io.File;
import java.io.IOException;
import java.util.List;

import og.basics.gui.tracepanel.ITracer;
import og.checker.filewalker.DirectoryInfos;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;

public class ID3CoverCheckerRepairer extends AbstractChecker {

	private boolean doRepair = false;

	public ID3CoverCheckerRepairer(ITracer tracer, boolean doRepair) {
		super(tracer);
		this.doRepair = doRepair;
	}

	@Override
	String getErrorText() {
		return "ID3 CoverArt Error";
	}

	@Override
	boolean performCheck(DirectoryInfos dirInfo) {
		if (dirInfo.isMusicDir()) {
			boolean needsRepair = false;
			for (File musicfile : dirInfo.getMusicFiles()) {
				try {
					MP3File f = (MP3File) AudioFileIO.read(musicfile);
					if (f.hasID3v2Tag()) {
						AbstractID3v2Tag tag = f.getID3v2Tag();
						List<Artwork> artw = tag.getArtworkList();
						if (artw == null || artw.size() == 0) {
							// Fehler gefunden
							needsRepair = true;
							// Soll es repariert werden ?
							if (doRepair) {
								try2RepairArtwork(dirInfo, f, tag);
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
			// wenn was repariert werden mußt(e) dann false zurück, damit eine
			// Zeile ins log kommt
			if (needsRepair == true)
				return false;
		}
		return true;
	}

	/**
	 * @param dirInfo
	 * @param f
	 * @param tag
	 * @throws IOException
	 * @throws FieldDataInvalidException
	 * @throws TagException
	 */
	private boolean try2RepairArtwork(DirectoryInfos dirInfo, MP3File f, AbstractID3v2Tag tag) throws IOException, FieldDataInvalidException, TagException {
		if (dirInfo.hasFolderJpgs()) {
			getTracer().appendText("Trying to repair CoverArt: " + f.getFile().getName());
			Artwork art = new Artwork();
			art.setFromFile(dirInfo.getFolderJpg());
			tag.setField(art);
			f.save();
			return true;
		}
		return false;
	}

}
