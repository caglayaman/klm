package my.excercise.stats;

public class Statistics {

	static long minTime = 0;
	static long maxTime = 0;

	static int OK = 0;
	static int err400 = 0;
	static int err500 = 0;

	static long resTime = 0;
	static int callCount = 0;

	public static void newResponseTime(long responseTime) {

		resTime += responseTime;
		callCount++;

		if (minTime == 0 || minTime > responseTime) {
			minTime = responseTime;
		}
		if (maxTime == 0 || maxTime < responseTime) {
			maxTime = responseTime;
		}
	}

	public static void err400() {
		err400++;
	}

	public static void err500() {
		err500++;
	}

	public static void Ok() {
		OK++;
	}
}
