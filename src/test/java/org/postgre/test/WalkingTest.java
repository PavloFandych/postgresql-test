package org.postgre.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Pavlo.Fandych
 */

@Slf4j
public class WalkingTest {

    @Test
    public void walkingTest() {
        try (Stream<Path> paths = Files.walk(Paths.get("/home/total/Desktop"))) {
            paths.forEach(item -> {
                final Path parent = item.getParent();
                List<File> children = null;
                if (Files.isDirectory(item)) {
                    children = (List<File>) FileUtils.listFiles(item.toFile(), null, true);
                }

                System.out.println("item: " + item + " Parent: " + parent + " children: " + children);
            });
        } catch (IOException e) {
            /*NOP*/
        }
    }
}