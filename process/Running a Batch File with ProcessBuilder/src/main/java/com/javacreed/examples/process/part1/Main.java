/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
package com.javacreed.examples.process.part1;

import java.io.File;

public class Main {

  public static void main(final String[] args) throws Exception {
    // The batch file to execute
    final File batchFile = new File("batch\\process.bat");

    // The output file. All activity is written to this file
    final File outputFile = new File(String.format("target\\output_%tY%<tm%<td_%<tH%<tM%<tS.txt",
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
    processBuilder.directory(new File("work"));

    // Start the process and wait for it to finish.
    final Process process = processBuilder.start();
    final int exitStatus = process.waitFor();
    System.out.println("Processed finished with status: " + exitStatus);

  }
}
