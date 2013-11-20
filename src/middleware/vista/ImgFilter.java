package middleware.vista;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ImgFilter extends FileFilter {

	@Override
	public boolean accept(File f) {

		if (f == null)
			return false;
		if (f.isDirectory())
			return true;
		return f.getName().toLowerCase().endsWith("jpg")
				|| f.getName().toLowerCase().endsWith("png")
				|| f.getName().toLowerCase().endsWith("bmp");

	}

	@Override
	public String getDescription() {

		return "Imagenes";
	}

}
