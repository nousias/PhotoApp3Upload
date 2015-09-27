package it21026.photoapp3upload;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

@Path("/upload")
public class Upload {

    //The method that accepts the file upload 
    @POST
    @Path("/photo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,@FormDataParam("file") FormDataContentDisposition fileDetail) {
        //The full path of the file
        String uploadedFileLocation = "C:/inetpub/wwwroot/"+fileDetail.getFileName();   
        java.nio.file.Path destination = Paths.get(uploadedFileLocation);
        //Try to save inputstream as a file
        try {
            java.nio.file.Files.copy(uploadedInputStream,destination);
        } catch (IOException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            String output = "File could not be saved" + uploadedFileLocation;
            return Response.status(500).entity(output).build();
        }
        //Return a response along with success code 200
        String output = "File uploaded successfully";
        return Response.status(200).entity(output).build();
    }
}