package heignamerican.gfgazou.base;

public interface UNext {
	public default void gazou(final String fileName) throws Exception {
		gazou(new Files(this, fileName));
	}

	public void gazou(final Files files) throws Exception;

	public default String getWorkFolder() {
		return "work/" + this.getClass().getSimpleName();
	}
}
