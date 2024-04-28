package jfit;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainTest {
    @Test
    public void callPyMustCallPython() {
        assertEquals("In testCallPy.py\n", Main.callPy("../testCallPy.py"));
    }

    @Test
    public void argumentsMustBePassed() {
        assertEquals("In testCallPy.py\nArguments:  ['arg1', 'arg2']\n",
                Main.callPy("../testCallPy.py", "arg1", "arg2"));
    }

    @Test
    public void ThreeArgumentsCanBePassed() {
        assertEquals("In testCallPy.py\nArguments:  ['arg1', 'arg2', 'arg3']\n",
                Main.callPy("../testCallPy.py", "arg1", "arg2", "arg3"));
    }

    @Test
    public void invalidPythonScriptMustThrowException() {
        try {
            Main.callPy("invalid.py");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("invalid.py"));
        }
    }

}
