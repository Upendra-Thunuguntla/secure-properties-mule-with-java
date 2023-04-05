package handlers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RootHandler implements HttpHandler {

	public void handle(HttpExchange he) throws IOException {
		String path = he.getRequestURI().getRawPath();

		StringBuilder sb = new StringBuilder();
		if (path.contains(".html")) {
			he.sendResponseHeaders(200, 0);
			for (String line : Files.readAllLines(Paths.get("ui" + path))) {
				sb.append(line + "\n");
			}
		}else if (path.contains(".js")) {
			he.sendResponseHeaders(200, 0);
			for (String line : Files.readAllLines(Paths.get("ui" + path))) {
				sb.append(line + "\n");
			}
		} else if (path.equals("/")) {
			he.sendResponseHeaders(200, 0);
			for (String line : Files.readAllLines(Paths.get("ui/index.html"))) {
				sb.append(line + "\n");
			}
		} else {
			System.out.println(path);
			System.out.println("404");
			he.sendResponseHeaders(404, 0);
		}
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
		out.write(sb.toString());

		out.close();

	}

}