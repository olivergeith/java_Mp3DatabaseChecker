package og.checker.filewalker.checks;

import java.io.File;
import java.io.IOException;

import og.basics.gui.tracepanel.ITracer;
import og.checker.filewalker.DirectoryInfos;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;

public class Mp3BitrateChecker extends AbstractChecker {

	private final long bitrateBorder;

	public Mp3BitrateChecker(ITracer tracer, long bitrateBorder) {
		super(tracer);
		this.bitrateBorder = bitrateBorder;
	}

	@Override
	String getErrorText() {
		return "Mp3 Bitrate Low (<=" + bitrateBorder + "kbps)";
	}

	@Override
	boolean performCheck(DirectoryInfos dirInfo) {
		if (dirInfo.isMusicDir()) {
			for (File musicfile : dirInfo.getMusicFiles()) {
				try {
					MP3File f = (MP3File) AudioFileIO.read(musicfile);
					MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();
					if (audioHeader.getBitRateAsNumber() <= bitrateBorder)
						return false;

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
