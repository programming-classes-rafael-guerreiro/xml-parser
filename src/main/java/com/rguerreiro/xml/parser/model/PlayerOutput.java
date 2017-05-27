package com.rguerreiro.xml.parser.model;

import static com.google.common.base.Preconditions.checkArgument;

public class PlayerOutput {
	private final String name;
	private final int score;
	private final int position; // 0, 1, 2, 3

	public PlayerOutput(Player player, int position) {
		checkArgument(player != null, "Player is required.");

		this.name = player.getName();
		this.score = player.getScore();
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public int getPosition() {
		return position;
	}
}