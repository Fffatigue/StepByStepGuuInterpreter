package interpreter.controller.commandFactory;

import interpreter.controller.commandFactory.commands.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class CommandFactory {
    public static CommandFactory getInstance() throws IOException {
        if (f == null)
            f = new CommandFactory();
        return f;
    }

    public Command getCommand(String id) throws ClassNotFoundException {
        Class<?> c;
        Object command;
        try {
            c = Class.forName( commands.getProperty( id ) );
            command = c.getDeclaredConstructor().newInstance();
        } catch (Exception exc) {
            throw new ClassNotFoundException( "Unknown command : " + id );
        }
        return (Command) command;
    }

    private CommandFactory() throws IOException {
        commands = new Properties();
        try (InputStream is = getClass().getResourceAsStream( CONFIG_FILE_NAME )) {
            if (is != null) {
                try (BufferedReader br = new BufferedReader( new InputStreamReader( is ) )) {
                    commands.load( br );
                } catch (IOException e) {
                    throw new IOException( "Missing config file!" );
                }
            }
        }
    }

    private static final String CONFIG_FILE_NAME = "commands/config.properties";
    private static CommandFactory f = null;
    private static Properties commands;
}