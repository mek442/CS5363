package com.mek442.node;

public class Counter {
	static int count = 0;
	private static Counter Instance;

	private Counter() {
		count = 0;

	}

	public static Counter getInstance() {
		if (Instance == null) {
			Instance = new Counter();
		}
		return Instance;
	}

	public int getCount() {

		int temp = count;

		count++;

		return temp;

	}

}
