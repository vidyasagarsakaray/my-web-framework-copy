package com.mytest.webscraping;

import java.io.File;

import org.testng.annotations.Test;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ImageTextExtractor
{
	@Test
	public void testMethod()
	{
		String text = crackImage("C:\\Users\\vidyasagar.sakaray\\Desktop\\Paragraph.jpg");
		System.out.println(text);
	}

	public static String crackImage(String filePath)
	{
		File imageFile = new File(filePath);
		ITesseract instance = new Tesseract();
		try
		{
			String result = instance.doOCR(imageFile);
			return result;
		}
		catch (TesseractException e)
		{
			System.err.println(e.getMessage());
			return "Error while reading image";
		}
	}
}
