package cr.tec.yatg.desktop.services.comms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by joseph on 10/2/16.
 */
public class ClientRead extends Thread {
	private BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	private String serverIp;
	private int serverPort;


	@Override
	public void run() {
		try {

			// Make connection and initialize streams
			configureIP();

			Socket socket = new Socket(serverIp, serverPort);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			new ClientWrite(out).start();

			// Process all messages from server, according to the protocol.
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);

			}
		} catch (IOException e) {
			System.out.println("Error al conectar con el servidor");
		}
	}

	private void configureIP() throws IOException {
		System.out.println("Ingrese la IP del servidor:");
		String userInput = stdIn.readLine();
		if (userInput != null) {
			serverIp = userInput;
		}

		System.out.println("Ok. Ahora ingrese el puerto:");
		userInput = stdIn.readLine();
		if (userInput != null) {
			serverPort = Integer.parseInt(userInput);
		}

		System.out.println("Ok. Conectando...");
	}

}
