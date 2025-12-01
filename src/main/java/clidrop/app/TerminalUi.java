package clidrop.app;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.awt.*;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TerminalUi {

    private final ServerManager manager = new ServerManager();
    private Terminal terminal;

    public static void main(String[] args) throws Exception {
        TerminalUi ui = new TerminalUi();
        ui.run();
    }


    public void run() throws Exception {

        terminal = TerminalBuilder.builder().system(true).build();
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();

        terminal.writer().println("Type 'help' to see available commands.");

        while (true) {

            String line = reader.readLine("clidrop> ");

            if (line == null) {
                break;
            }

            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }

            String[] parts = trimmed.split("\\s+", 2);
            String cmd = parts[0].toLowerCase();
            String args = parts.length > 1 ? parts[1] : null;


            switch (cmd) {

                case "send":

                    handleSend(args);
                    break;

                case "receive":

                    manager.start();

                    String url = manager.getUrl() + "/";
                    Path qrCodePath = QrCodeGenerator.generateForUrl(url);

                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(qrCodePath.toFile());
                    }

                    terminal.writer().flush();
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

    private void handleSend(String args) {
        try {
            manager.start();

            if (args == null) {
                terminal.writer().println("Usage: send <file-or-directory>");
                terminal.writer().flush();
                return;
            }

            Path target = Paths.get(args).toAbsolutePath();
            if (!Files.exists(target)) {
                terminal.writer().println("Path not found: " + target);
                terminal.writer().flush();
                return;
            }

            String sendUrl = manager.createSendUrlFor(target);


            Path qrPath = QrCodeGenerator.generateForUrl(sendUrl);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(qrPath.toFile());
            }


        } catch (Exception e) {
            terminal.writer().println("Error in send: " + e.getMessage());
        }
    }


    private void printHelp(Terminal terminal) {
        
        PrintWriter w = terminal.writer();

        w.println();
        w.println("Usage:");
        w.println("  send <path-to-file>        Send a file from this computer to another device");
        w.println("  receive                    Receive files from a phone on this computer");
        w.println();
        w.println("Available commands:");
        w.println("  send <path-to-file>        Start send mode (PC -> phone), show QR download link");
        w.println("  receive                    Start receive mode (phone -> PC), show QR upload link");
        w.println("  help                       Show this help message");
        w.println("  quit / exit                Exit clidrop");
        w.println();
        w.flush();

    }
}
