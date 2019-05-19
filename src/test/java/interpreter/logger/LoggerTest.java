package interpreter.logger;

import interpreter.interpreter.Logger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoggerTest {
    @Test
    public void testLoggerStackTrace(){
        Logger logger = new Logger();
        logger.addFunctionCall( 0,"check1",0 );
        logger.addFunctionCall( 10,"check2",1 );
        String actual = logger.getTrace();
        String expected = " 0 : check1\n- 10 : check2\n";
        assertEquals(expected,actual);
    }
}
