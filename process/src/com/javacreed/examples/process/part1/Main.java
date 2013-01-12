package com.javacreed.examples.process.part1;

import java.io.File;

public class Main {

  public static void main(final String[] args) throws Exception {
    // The batch file to execute
    final File batchFile = new File("batch\\process.bat");

    // The output file. All activity is written to this file
    final File outputFile = new File(String.format("output\\output_%tY%<tm%<td_%<tH%<tM%<tS.txt",
        System.currentTimeMillis()));

    // The argument to the batch file.
    final String argument = "Albert Attard";

    // Create the process
    final ProcessBuilder processBuilder = new ProcessBuilder(batchFile.getAbsolutePath(), argument);
    // Redirect any output (including error) to a file. This avoids deadlocks
    // when the buffers get full.
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectOutput(outputFile);

    // Add a new environment variable
    processBuilder.environment().put("message", "Example of process builder");

    // Set the working directory. The batch file will run as if you are in this
    // directory.
    processBuilder.directory(new File("C:\\solid\\exa\\projects\\java\\process\\work"));

    // Start the process and wait for it to finish.
    final Process process = processBuilder.start();
    final int exitStatus = process.waitFor();
    System.out.println("Processed finished with status: " + exitStatus);

  }
}
