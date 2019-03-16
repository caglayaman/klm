package my.excercise.stats;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "stats")
public class StatsModel {
	long minTime;
	long maxTime;

	int OK = 0;
	int err400 = 0;
	int err500 = 0;

	long avgTime = 0;
	int totalCall;

	public StatsModel() {
		minTime = Statistics.minTime;
		maxTime = Statistics.maxTime;
		OK = Statistics.OK;
		err400 = Statistics.err400;
		err500 = Statistics.err500;
		totalCall = Statistics.callCount;

		avgTime = totalCall == 0 ? 0 : Statistics.resTime / totalCall;
	}

	public long getMinTime() {
		return minTime;
	}

	public void setMinTime(long minTime) {
		this.minTime = minTime;
	}

	public long getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(long maxTime) {
		this.maxTime = maxTime;
	}

	public int getOK() {
		return OK;
	}

	public void setOK(int oK) {
		OK = oK;
	}

	public int getErr400() {
		return err400;
	}

	public void setErr400(int err400) {
		this.err400 = err400;
	}

	public int getErr500() {
		return err500;
	}

	public void setErr500(int err500) {
		this.err500 = err500;
	}

	public long getAvgTime() {
		return avgTime;
	}

	public void setAvgTime(long avgTime) {
		this.avgTime = avgTime;
	}

	public int getTotalCall() {
		return totalCall;
	}

	public void setTotalCall(int totalCall) {
		this.totalCall = totalCall;
	}

}
