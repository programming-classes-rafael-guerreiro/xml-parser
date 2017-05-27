package com.rguerreiro.xml.parser;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class App {
	public static void main(String[] args) {
		validateArguments(args);
		List<FileProcessor> files = buildFileProcessors(asList(args));
		FilesProcessor processor = new FilesProcessor(files);

		processor.processAll();
	}

	// O(n)
	private static List<FileProcessor> buildFileProcessors(final Collection<String> collection) {
		final List<FileProcessor> files = new ArrayList<>(collection.size() >> 1);

		final Iterator<String> iterator = collection.iterator();
		while (iterator.hasNext()) {
			final String source = iterator.next();
			final String output = iterator.next();

			files.add(new FileProcessor(source, output));
		}

		return files;
	}

	private static void validateArguments(String[] args) {
		boolean isThereArguments = args != null && args.length > 0;

		if (!isThereArguments || args.length % 2 != 0) {
			printHelper(args);
			System.exit(1);
		}
	}

	private static void printHelper(String[] args) {
		System.err.println("Invalid command run the program.");
		System.err.println(
				"run this program as: java -jar xml-parser.jar /path/to/source1.xml /path/to/output1.xml /path/to/source2.xml /path/to/output2.xml");
		System.err.println("\n\nYou tried to run with: " + Arrays.toString(args));
	}
}
