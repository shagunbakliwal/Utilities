package com.acg.utilities;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

public class Screenshot {
	private static final String UNDERSCORE_SEPARATOR = "_";
	private static final String PERIOD_SEPARATOR = ".";
	private static final String SPACE_SEPARATOR = " ";
	private static final String SCREENSHOTS_DIRECTORY = "/screenshots";

	public static void capture(String format, String path, Long delay)
			throws AWTException, InterruptedException, IOException {
		Robot robot = new Robot();
		Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		if (path == null) {
			path = new File("").getAbsolutePath();
		}
		String pathToWrite = path + SCREENSHOTS_DIRECTORY;
		File writingDirectory = new File(pathToWrite);
		if (createDirectory(writingDirectory)) {
			File f = new File(pathToWrite + UNDERSCORE_SEPARATOR + new Date().toString()
					.replaceAll(SPACE_SEPARATOR, UNDERSCORE_SEPARATOR).replaceAll(":", UNDERSCORE_SEPARATOR));
			System.out.println("Renaming Existing Screenshots folder to " + f.getAbsolutePath());
			writingDirectory.renameTo(f);
			createDirectory(new File(pathToWrite));
		}
		File file = null;
		long count = 1;
		while (true) {
			Thread.sleep(delay);
			file = new File(pathToWrite + "/" + count++ + PERIOD_SEPARATOR + format);
			ImageIO.write(robot.createScreenCapture(rectangle), format, file);
			System.out.println("Saved " + file.getAbsoluteFile());
		}
	}

	private static boolean createDirectory(File writingDirectory) {
		if (!writingDirectory.isDirectory())
			writingDirectory.mkdir();
		return true;
	}

}
