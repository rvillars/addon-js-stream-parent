package com.edorasware.addons.utils;

import com.google.javascript.jscomp.CommandLineRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Created by rovi on 12.07.17.
 */
public class Minifier extends CommandLineRunner {

    public Minifier(String[] args, InputStream in, PrintStream out, PrintStream err) {
        super(args, in, out, err);
    }

    public void minify() throws IOException {
        doRun();
    }
}
