package champ;

public class Common {

	public static class Champ {
		public static final int NONE = 0;
		public static final int SKILL = 1;
		public static final int HIT = 2;
		public static final int JUMP = 3;
		public static final int SLIDE = 4;
		public static final int COLLISION = 5;
		public static final int HIT_SLIDE = 6;

		public static int state = NONE;
	}

	public static class GamePan {
		public static final int W = 1100;
		public static final int H = 550;
	}

	public static class Key {
		public static final int DOWN = 8;
		public static final int SPACE = 16;
	}
}
