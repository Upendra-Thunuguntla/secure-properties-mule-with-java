package server;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpServer;

import handlers.APIHandler;
import handlers.RootHandler;

//import handlers.RootHandler;

public class Main {

	public static void main(String[] args) throws Exception {
		int port = 8080;
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		server.createContext("/", new RootHandler());

		server.createContext("/favicon.ico", t -> {
			byte[] bytes = Files.readAllBytes(Paths.get("ui/favicon.ico"));
			t.sendResponseHeaders(200, bytes.length);
			try (OutputStream os = t.getResponseBody()) {
				os.write(bytes);
			}
		});
		server.createContext("/api/process", new APIHandler());
		System.out.println("Server started on port " + port);
		server.start();
	}
}
