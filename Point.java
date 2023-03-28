public class Point {

	public Point next;
	public Point prev;

	public Point side;

	public int vel;
	public boolean empty;
	public int velLeft;

	static int maxSpeed = 5;
	static float p = 0.3f;

	public Point() {
		clear();
	}

	public void clicked() {
		empty = false;
	}

	public void clear() {
		vel = 0;
		velLeft = 0;
		empty = true;
	}

	public void updateVelocity() {
		if (vel < maxSpeed)
			vel += 1;

		if (next != null)
			vel = Math.min(vel, next.emptyInFront(0));

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

		empty = true;
		vel = 0;
	}

	public int emptyInFront(int emptyBehind) {
		if (!empty)
			return emptyBehind;

		if (next == null || emptyBehind + 1 >= maxSpeed)
			return maxSpeed;

		return next.emptyInFront(emptyBehind + 1);
	}

	public int emptyBehind(int emptyBehind) {
		if (!empty)
			return emptyBehind;

		if (prev == null || emptyBehind + 1 >= maxSpeed)
			return maxSpeed;

		return prev.emptyBehind(emptyBehind + 1);
	}

}