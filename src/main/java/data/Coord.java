package data;

import org.jetbrains.annotations.Range;

public record Coord(@Range(from = 0, to = Integer.MAX_VALUE) int x, @Range(from = 0, to = Integer.MAX_VALUE) int y) { }
