package tddmicroexercises.textconvertor;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UnicodeFileToHtmlTextConverterTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void shouldInitialize() throws Exception {
        //setup
        String testFilePath = "PATH_TO_FILE";
        UnicodeFileToHtmlTextConverter unicodeFileToHtmlTextConverter =
                new UnicodeFileToHtmlTextConverter(testFilePath);

        //assertion
        assertTrue("Converter should not be null --> result should be true",
                unicodeFileToHtmlTextConverter != null);

        assertEquals("file path should be 'PATH_TO_FILE'", testFilePath,
                unicodeFileToHtmlTextConverter.getFullFilenameWithPath());
    }

    @Test
    public void shouldConvertFileCorrectly() throws Exception {
        //setup
        UnicodeFileToHtmlTextConverter unicodeFileToHtmlTextConverter =
                new UnicodeFileToHtmlTextConverter("PATH_TO_FILE");
        UnicodeFileToHtmlTextConverter spyUnicodeFileToHtmlTextConverter = spy(unicodeFileToHtmlTextConverter);
        BufferedReader mockedBufferedReader = mock(BufferedReader.class);
        String expected = "first line.<br />second line.<br />";

        //exercise
        when(mockedBufferedReader.readLine()).thenReturn("first line.").thenReturn("second line.").thenReturn(null);
        doReturn(mockedBufferedReader).when(spyUnicodeFileToHtmlTextConverter).generateFileStream();
        String result = spyUnicodeFileToHtmlTextConverter.convertToHtml();

        //assertion
        assertEquals("converted html string should match --> first line.<br />second line.<br />",
                expected, result);
        assertNotEquals("converted html string should not match Something", "Something", result);
    }

    @Test
    public void addLineBreakToText() throws Exception {
        //setup
        UnicodeFileToHtmlTextConverter unicodeFileToHtmlTextConverter =
                new UnicodeFileToHtmlTextConverter("PATH_TO_FILE");
        String expected = "ba<br />";

        //exercise
        String result = unicodeFileToHtmlTextConverter.addLineBreak("a", "b");

        //assertion
        assertEquals("should added line break --> ba<br />", expected, result);
        assertNotEquals("should not euals Something", "Something", result);
    }

    @Test
    public void shouldCreateABufferedReaderOfGivenFile() throws Exception {
        //setup
        File file = folder.newFile("someTestFile.txt");
        UnicodeFileToHtmlTextConverter unicodeFileToHtmlTextConverter =
                new UnicodeFileToHtmlTextConverter(file.getAbsolutePath());

        //exercise
        BufferedReader result = unicodeFileToHtmlTextConverter.generateFileStream();

        //assertion
        assertTrue("result should not be null --> equals true", result != null);
        assertTrue("result should be instance of BufferedReaderClass --> equals true",
                result instanceof BufferedReader);
    }

    @Test (expected = FileNotFoundException.class)
    public void shouldThrowAFileNotFoundExceptionIfGivenFileDoesNotExist() throws Exception {
        //setup
        UnicodeFileToHtmlTextConverter unicodeFileToHtmlTextConverter =
                new UnicodeFileToHtmlTextConverter("INVALID_PATH_TO_FILE");

        //exercise
       unicodeFileToHtmlTextConverter.generateFileStream();
    }

    @Test (expected = IOException.class)
    public void shouldThrowAIOExceptionIfGivenFileDoesNotExist() throws Exception {
        //setup
        UnicodeFileToHtmlTextConverter unicodeFileToHtmlTextConverter =
                new UnicodeFileToHtmlTextConverter("INVALID_PATH_TO_FILE");

        //exercise
        unicodeFileToHtmlTextConverter.convertToHtml();
    }

}