package com.javacreed.examples.swing.worker.part1_3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;

/**
 * Searches the text files under the given directory and counts the number of instances a given word is found in these
 * file.
 * 
 * @author Albert Attard
 */
public class SearchForWordWorker extends SwingWorker<Integer, String> {

  private static void failIfInterrupted() throws InterruptedException {
    if (Thread.currentThread().isInterrupted()) {
      throw new InterruptedException("Interrupted while searching files");
    }
  }

  /** The word that is searched */
  private final String word;

  /** The directory under which the search occurs. All text files found under the given directory are searched. */
  private final File directory;

  /** The text area where messages are written. */
  private final JTextArea messagesTextArea;

  /**
   * Creates an instance of the worker
   * 
   * @param word
   *          The word to search
   * @param directory
   *          the directory under which the search will occur. All text files found under the given directory are
   *          searched
   * @param messagesTextArea
   *          The text area where messages are written
   */
  public SearchForWordWorker(final String word, final File directory, final JTextArea messagesTextArea) {
    this.word = word;
    this.directory = directory;
    this.messagesTextArea = messagesTextArea;
  }

  @Override
  protected Integer doInBackground() throws Exception {
    // The number of instances the word is found
    int matches = 0;

    /*
     * List all text files under the given directory using the Apache IO library. This process cannot be interrupted
     * (stopped through cancellation). That is why we are checking right after the process whether it was interrupted or
     * not.
     */
    publish("Listing all text files under the directory: " + directory);
    final List<File> textFiles = new ArrayList<>(FileUtils.listFiles(directory, new SuffixFileFilter(".txt"),
        TrueFileFilter.TRUE));
    SearchForWordWorker.failIfInterrupted();
    publish("Found " + textFiles.size() + " text files under the directory: " + directory);

    for (int i = 0, size = textFiles.size(); i < size; i++) {
      /*
       * In order to respond to the cancellations, we need to check whether this thread (the worker thread) was
       * interrupted or not. If the thread was interrupted, then we simply throw an InterruptedException to indicate
       * that the worker thread was cancelled.
       */
      SearchForWordWorker.failIfInterrupted();

      // Update the status and indicate which file is being searched.
      final File file = textFiles.get(i);
      publish("Searching file: " + file);

      /*
       * Read the file content into a string, and count the matches using the Apache common IO and Lang libraries
       * respectively.
       */
      final String text = FileUtils.readFileToString(file);
      matches += StringUtils.countMatches(text, word);

      // Update the progress
      setProgress((i + 1) * 100 / size);
    }

    // Return the number of matches found
    return matches;
  }

  @Override
  protected void process(final List<String> chunks) {
    // Updates the messages text area
    for (final String string : chunks) {
      messagesTextArea.append(string);
      messagesTextArea.append("\n");
    }
  }
}
