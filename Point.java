public class Point {
	/// Predefined
	public Point next;
	public Point prev;
	public Point up;
	public Point down;

	/// Changing
	public int vel;
	public boolean empty;
	public int velLeft;
	public int lastSwitch;

	/// Parameters
	static int maxSpeed = 3;
	static float p = 0.3f;
	static float pChange = 0.2f;

	static int l = 3;
	static int lO = 4;
	static int lB = 2;

	public Point() {
		clear();
	}

	public int lastSwitch() {
		return lastSwitch;
	}

	public void clicked() {
		empty = false;
	}

	public void clear() {
		vel = 0;
		velLeft = 0;
		empty = true;
		lastSwitch = 0;
	}

	public void updateVelocity() {
		if (empty)
			return;

		if (vel < maxSpeed)
			vel += 1;

		if (next != null)
			vel = Math.min(Math.min(vel, next.emptyInFront(0)), maxSpeed);

		if (vel > 0 && Math.random() < p)
			vel -= 1;

		velLeft = vel;
	}

	public void move() {

		if (next == null)
			clear();

		if (velLeft == 0)
			return;

		next.empty = false;
		next.vel = vel;
		next.velLeft = velLeft - 1;
		next.lastSwitch = lastSwitch;

		clear();
	}

	public int shouldChange() {
		int gap = next.emptyInFront(0);

		if (up != null && up.empty) {
			int gapO = up.emptyInFront(-1);
			int gapBack = up.emptyBehind(-1);

			if (gap < l && gapO > lO && gapBack > lB) {
				if (Math.random() < pChange)
					return -1;
			}
		}

		if (down != null && down.empty) {
			int gapO = down.emptyInFront(-1);
			int gapBack = down.emptyBehind(-1);

			if (gapO > lO && gapBack > lB) {
				if (Math.random() < pChange)
					return 1;
			}
		}

		return 0;
	}

	public void changeLanes() {
		if (empty)
			return;

		lastSwitch++;

		int changing = shouldChange();

		if (changing == 0)
			return;

		if (changing == -1) {
			up.empty = false;
			up.vel = vel;
			up.velLeft = velLeft;
		} else {
			down.empty = false;
			down.vel = vel;
			down.velLeft = velLeft;

		}

		clear();
	}

	static private int sightLimit() {
		return Math.max(maxSpeed, Math.max(l, lO)) + 2;
	}

	public int emptyInFront(int emptyBehind) {
		if (!empty)
			return emptyBehind;

		if (next == null || emptyBehind + 1 >= sightLimit())
			return emptyBehind + 1;

		return next.emptyInFront(emptyBehind + 1);
	}

	public int emptyBehind(int emptyBehind) {
		if (!empty)
			return emptyBehind;

		if (prev == null || emptyBehind + 1 > lB)
			return emptyBehind + 1;

		return prev.emptyBehind(emptyBehind + 1);
	}

}