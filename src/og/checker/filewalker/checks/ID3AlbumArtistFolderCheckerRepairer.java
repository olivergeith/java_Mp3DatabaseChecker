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
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v24Frames;

public class ID3AlbumArtistFolderCheckerRepairer extends AbstractChecker {
	private boolean doRepair = false;

	public ID3AlbumArtistFolderCheckerRepairer(ITracer tracer, boolean doRepair) {
		super(tracer);
		this.doRepair = doRepair;
	}

	@Override
	String getErrorText() {
		return "ID3 AlbumArtist <-> FolderName Error";
	}

	@Override
	boolean performCheck(DirectoryInfos dirInfo) {
		// check nur auf Musicdirs und wennn icht unter Sampler usw...
		if (dirInfo.isMusicDir() && !dirInfo.isInSamplerXmasMusicalOst()) {
			boolean needsRepair = false;
			for (File musicfile : dirInfo.getMusicFiles()) {
				try {
					MP3File f = (MP3File) AudioFileIO.read(musicfile);
					if (f.hasID3v2Tag()) {
						AbstractID3v2Tag tag = f.getID3v2Tag();
						String albumArtist = tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM_ARTIST);
						String albumDirName = dirInfo.getDirName();
						String parentDirName = dirInfo.getParentDirName();
						if (!albumDirName.startsWith(albumArtist)) {
							// Fehler gefunden
							needsRepair = true;
							if (doRepair && dirInfo.isFolderHirarchieOK()) {
								getTracer().appendText("Trying to repair AlbumArtist: " + f.getFile().getName());
								tag.setField(FieldKey.ALBUM_ARTIST, parentDirName);
								f.save();
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

}
