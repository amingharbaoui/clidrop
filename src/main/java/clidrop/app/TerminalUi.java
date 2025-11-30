package clidrop.app;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class TerminalUi {
    public static void main(String[] args) throws Exception {
        Terminal terminal = TerminalBuilder.builder().system(true).build();

        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();

        while (true) {

            String line = reader.readLine("clidrop> ");
            if (line == null) {
                break;
            }

            switch (line.toLowerCase()) {
                case "start":
                    System.out.println("Server started");
                    break;

                case "qrcode":
                    System.out.println("QR code requested");
                    break;

                case "stop":
                    System.out.println("Server stopped");
                    break;

                case "exit":
                    System.out.println("Exit requested");
                    break;

                case "help":
                    System.out.println("help requested");
                    break;

                default:
                    System.out.println("Unknown command: " + line);
            }
        }

    }
}
