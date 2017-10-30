package tddmicroexercises.textconvertor;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class UnicodeFileToHtmlTextConverter
{
    private String fullFilenameWithPath;

    public UnicodeFileToHtmlTextConverter(String fullFilenameWithPath)
    {
        this.fullFilenameWithPath = fullFilenameWithPath;
    }

    public String convertToHtml() throws IOException
    {
        try (BufferedReader reader = generateFileStream())
        {

            String line = reader.readLine();
            String html = "";
            while (line != null)
            {
                html = addLineBreak(line, html);
                line = reader.readLine();
            }
            return html;

        }
    }

    protected String addLineBreak(String line, String html) {
        html += StringEscapeUtils.escapeHtml4(line);
        html += "<br />";
        return html;
    }

    protected BufferedReader generateFileStream () throws FileNotFoundException {
        return new BufferedReader(new FileReader(getFullFilenameWithPath()));
    }

    protected String getFullFilenameWithPath() {
        return fullFilenameWithPath;
    }
}
