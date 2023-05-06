package handlers;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RootHandler implements HttpHandler {

	public void handle(HttpExchange he) {
		String path = he.getRequestURI().getRawPath();
		System.out.println(path);
		StringBuilder sb = new StringBuilder();
		try {
			
			if (path.contains(".html")) {
				he.sendResponseHeaders(200, 0);
				for (String line : Files.readAllLines(getFileFromResource("ui" + path))) {
					sb.append(line + "\n");
				}
			} else if (path.contains(".js")) {
				he.sendResponseHeaders(200, 0);
				for (String line : Files.readAllLines(getFileFromResource("ui" + path))) {
					sb.append(line + "\n");
				}
			} else if (path.equals("/")) {
				he.sendResponseHeaders(200, 0);
				for (String line : Files.readAllLines(getFileFromResource("ui/index.html"))) {
					sb.append(line + "\n");
				}
//				System.out.println("Sending Index.html file over");
			} else {
				System.out.println(path);
				System.out.println("404");
				he.sendResponseHeaders(404, 0);
			}
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
			out.write(sb.toString());

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    private Path getFileFromResource(String fileName) throws URISyntaxException{
    	return Paths.get(fileName);
    }
	
}