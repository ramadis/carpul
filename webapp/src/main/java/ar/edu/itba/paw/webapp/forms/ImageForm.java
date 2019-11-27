package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.NotNull;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

public class ImageForm {
    @NotNull
    @FormDataParam("file")
    private FormDataBodyPart content;
	
	public ImageForm() { }

	public byte[] getContent() {
		return content.getValueAs(byte[].class);
	}
}
