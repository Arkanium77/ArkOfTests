package team.isaz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.isaz.exceptions.PackageNotFoundException;
import team.isaz.framework.ArkOfTests;

import java.io.IOException;
/**
 * Hello world!
 *
 */
public class App 
{
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) throws ClassNotFoundException, IOException, PackageNotFoundException {
        ArkOfTests.execute("team.isaz.exampleClassesTests");
    }
}
