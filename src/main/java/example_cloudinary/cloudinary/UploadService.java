package example_cloudinary.cloudinary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UploadService implements IUpload{

	

	@Override
	public String[] properties(String realPath) {
		
		final String URL_FILE ="config/properties.json";
		
		File file = new File(realPath+"/"+URL_FILE);
		
		String [] data = new String[4];
		
		if(file.exists()) {
			
			try {
				
				
				BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
				
				JsonParser parser = new JsonParser();
				
				JsonObject obj = parser.parse(br).getAsJsonObject();
				
				data[0] = obj.get("path_main").getAsString();
				data[1] = obj.get("cloud_name").getAsString();
				data[2] = obj.get("api_key").getAsString();
				data[3] = obj.get("api_secret").getAsString();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	
		
			
		}else {
		
				System.out.println("NO EXISTE ARCHIVO EN: "+file.getAbsolutePath());
	
			
		}
		
		
		
		
		
		return data;
	}

	@Override
	public String getURLImg(String imgPath, String fileName, String serverPath,String currentPath) {
		
		String [] properties = this.properties(currentPath);
		
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", ""+properties[1]+"",
				"api_key",""+properties[2]+"",
				"api_secret", ""+properties[3]+""));
		
		Map params = ObjectUtils.asMap("public_id", ""+properties[0]+"/"+serverPath+"/"+fileName, "overwrite", true, "resource_type",
				"image");

		Map uploadResult = null;

		try {

			uploadResult = cloudinary.uploader().upload(new File(imgPath), params);

		} catch (IOException e) {

			e.printStackTrace();
			
			return "Error al subir el archivo: "+e.toString();
		}

		return uploadResult.get("url").toString();
		
	}
	
	
	

}
