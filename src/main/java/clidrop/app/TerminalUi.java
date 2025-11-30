package clidrop.app;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.PrintWriter;

public class TerminalUi {
    private final ServerManager manager = new ServerManager();

    public static void main(String[] args) throws Exception {
        TerminalUi ui = new TerminalUi();
        ui.run();
    }

    public void run() throws Exception {
        Terminal terminal = TerminalBuilder.builder().system(true).build();

        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();

        while (true) {

            String line = reader.readLine("clidrop> ");
            if (line == null) {
                break;
            }

            switch (line.toLowerCase()) {
                case "start":
                    manager.start();
                    terminal.writer().println("Server started at: " + manager.getUrl());
                    break;

                case "stop":
                    if (!manager.isRunning()) {
                        terminal.writer().println("No server is running.");
                    } else {
                        manager.stop();
                        terminal.writer().println("Server at " + manager.getUrl() + " " +
                                "has left the dropzone.");
                    }
                    break;

                case "help":
                    printHelp(terminal);
                    break;

                case "exit":
                case "quit":
                    terminal.writer().flush();
                    return;


                default:
                    System.out.println("This command isn’t in the dropzone. Type 'help' for options.");
            }
        }

    }

    private void printHelp(Terminal terminal) {
        PrintWriter w = terminal.writer();

        w.println();
        w.println("clidrop – drop it in the terminal, open it anywhere");
        w.println();
        w.println("Usage: <command>");
        w.println();
        w.println("Available commands:");
        w.println("  start         Start web server");
        w.println("  stop          Stop web server");
        w.println("  help          Show this help");
        w.println("  quit/exit     Exit CLI");
        w.println();

        w.flush();


    }
}
