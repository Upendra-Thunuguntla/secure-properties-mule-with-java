package handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import pojos.Payload;
import services.Processor;

public class APIHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

		//Added for CORS
		if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
			exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
			exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");
			exchange.sendResponseHeaders(204, -1);
			return;
		}

		if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
			exchange.getResponseHeaders().add("Content-Type", "application/json");

			InputStream inputStream = exchange.getRequestBody();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String lines = "";

			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				lines += "\n" + line;
			}

			// Parse the JSON object from the input stream
			System.out.println(lines);

			Gson gson = new Gson();
			Payload payload = gson.fromJson(lines, Payload.class);

			exchange.sendResponseHeaders(200, 0);
			OutputStream outputStream = exchange.getResponseBody();
			JSONObject response = Processor.startProcess(payload);
			System.out.println(response.toString());
			outputStream.write(response.toString().getBytes(StandardCharsets.UTF_8));
			outputStream.flush();
			outputStream.close();
		} else {
			exchange.sendResponseHeaders(405, 0);
		}
	}
}
