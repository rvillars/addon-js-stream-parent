package com.edorasware.addons.utils;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by rovi on 12.07.17.
 */
public class MinifierTest {

    @Test
    public void minifyTest() throws IOException {
        InputStream originalIs = this.getClass().getResourceAsStream("unminified.js");
        String originalCode = IOUtils.toString(originalIs, StandardCharsets.UTF_8);
        System.out.println(originalCode);

        InputStream codeIs = this.getClass().getResourceAsStream("unminified.js");
        ByteArrayOutputStream codebaos = new ByteArrayOutputStream();
        PrintStream codeps = new PrintStream(codebaos);
        ByteArrayOutputStream errorbaos = new ByteArrayOutputStream();
        PrintStream errorps = new PrintStream(errorbaos);
        String minifiedCode = "";
        String errorCode = "";
        Minifier minifier = new Minifier(new String[0], codeIs, codeps, errorps);
        if (minifier.shouldRunCompiler()) {
            minifier.minify();
            minifiedCode = new String(codebaos.toByteArray(), StandardCharsets.UTF_8);
            errorCode = new String(errorbaos.toByteArray(), StandardCharsets.UTF_8);
        }
        System.out.println(minifiedCode);
        System.err.println(errorCode);
        Assert.assertFalse(minifiedCode.isEmpty());
    }

}