package example_cloudinary.cloudinary;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


@WebServlet(name="/upload",urlPatterns= {"/upload"})
public class UploadController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public UploadController() {
		
		super();
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) {
		
		
	}
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) {
		
		
	}
	
	
	protected String urlImg(Part part, String currentPath) throws IOException {

		String tmp = currentPath + "tmp_img";

		File f = new File(tmp);

		if (!f.exists()) {

			f.mkdir();
		}

		System.out.println("content-type: " + part.getContentType());

		String contentType = part.getContentType().toString();

		String ex = contentType.replaceAll("image/", "");

		String name_img = "img" + Integer.toString((int) (Math.random() * 300 + 1));

		File fwrite = new File(tmp + "/" + name_img + "." + ex);

		System.out.println("path and name: " + fwrite.toString());

		FileOutputStream out = new FileOutputStream(fwrite);
		BufferedOutputStream br = new BufferedOutputStream(out);

		byte[] filebytes = new byte[part.getInputStream().available()];

		part.getInputStream().read(filebytes);

		br.write(filebytes);

		String url = new UploadService().getURLImg(fwrite.toString(),name_img,"products", currentPath);

		System.out.println("url get -> " + url);

		fwrite.delete();

		br.close();

		return new StringBuilder(url).insert(4,"s").toString();
	}

}
