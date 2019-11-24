package barcode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;

import net.sourceforge.barbecue.*;

import javax.imageio.ImageIO;

/**
 * 
 * Print a barcode using Java's print API
 *  
 * @author Sean C. Sullivan
 *
 */
public class PrintingExample
{

	public static void print1(String[] args)
	{
		try
		{
			Barcode b = BarcodeFactory.createCode128("Hello");
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(b);
			if (job.printDialog())
			{
				job.print();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedImage image = ImageIO.read(new File("d:\\out.png"));
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(new Printable() {
			public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
				if (pageIndex > 1) {
					return NO_SUCH_PAGE;
				}
				graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
				return PAGE_EXISTS;
			}
		});
		try {
			if (printJob.printDialog())
			{
				printJob.print();
			}
		} catch (PrinterException e1) {
			e1.printStackTrace();
		}
	}
}
