package com.rguerreiro.xml.parser;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.google.common.base.Charsets;
import com.rguerreiro.xml.parser.model.Player;
import com.rguerreiro.xml.parser.model.PlayerOutput;
import com.thoughtworks.xstream.XStream;

public class FileProcessor {
	private final String source;
	private final String output;

	public FileProcessor(String source, String output) {
		checkArgument(!isNullOrEmpty(source), "Source is required.");
		checkArgument(!isNullOrEmpty(output), "Output is required.");

		this.source = source;
		this.output = output;
	}

	@Override
	public String toString() {
		return new StringBuilder(source).append(" => ").append(output).toString();
	}

	public Player read() {
		final File file = new File(source);
		if (!file.isFile() || !file.canRead()) // guard clause
			return null;

		try (InputStream is = new FileInputStream(file)) {
			XStream xstream = new XStream();
			xstream.alias("player", Player.class);

			Player player = (Player) xstream.fromXML(is);
			return player;
		} catch (IOException e) {
			throw new RuntimeException("Unable to read " + source, e);
		}
	}

	public void write(PlayerOutput player) {
		// gerar o xml a partir do player
		XStream xstream = new XStream();
		xstream.alias("player", PlayerOutput.class);

		String xml = xstream.toXML(player);
		System.out.println("Generated -> " + xml);

		// escrever o conteudo xml no output

		final File file = new File(output);
		if (file.exists() && !file.canWrite()) // guard clause
			return;

		try (OutputStream os = new FileOutputStream(file)) {
			byte[] bytes = xml.getBytes(UTF_8);
			os.write(bytes);
			os.flush();
			
			System.out.println("Wrote -> " + output);
		} catch (IOException e) {
			throw new RuntimeException("Unable to write " + output, e);
		}
	}
}
