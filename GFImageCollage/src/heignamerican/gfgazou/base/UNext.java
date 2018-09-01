package heignamerican.gfgazou.base;

import java.util.stream.Stream;

public interface UNext {
	public default void gazou(final String... fileNames) throws Exception {
		gazou(conv(fileNames));
	}

	public default Files[] conv(String... fileNames) {
		return Stream.of(fileNames).map(f -> new Files(this, f)).toArray(Files[]::new);
	}

	public void gazou(final Files... filesList) throws Exception;

	public default String getWorkFolder() {
		return "work/" + this.getClass().getSimpleName();
	}
}
