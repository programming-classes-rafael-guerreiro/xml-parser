package com.rguerreiro.xml.parser;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.reverseOrder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.rguerreiro.xml.parser.model.Player;
import com.rguerreiro.xml.parser.model.PlayerOutput;

public class FilesProcessor {

	private final Collection<FileProcessor> files = new ArrayList<>(0);

	public FilesProcessor(Collection<FileProcessor> files) {
		checkArgument(files != null && !files.isEmpty(), "The files collection must not be empty.");

		this.files.addAll(files);
	}

	@Override
	public String toString() {
		return new StringBuilder("FilesProcessor -> ").append(files).toString();
	}

	public void processAll() {
		final Map<Player, FileProcessor> map = new HashMap<>();
		final Queue<Player> players = new PriorityQueue<>(reverseOrder(this::comparePlayers));

		for (FileProcessor processor : files) {
			Player player = processor.read();

			players.add(player);
			map.put(player, processor);
		}

		int position = 0;
		while (!players.isEmpty()) {
			Player player = players.poll();
			PlayerOutput output = new PlayerOutput(player, position++);

			map.get(player).write(output);
		}
	}

	private int comparePlayers(Player o1, Player o2) {
		if (o1 == o2)
			return 0;
		if (o1 == null)
			return -1;
		if (o2 == null)
			return 1;

		return o1.getScore() - o2.getScore();
	}
}
