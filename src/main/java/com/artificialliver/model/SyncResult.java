package com.artificialliver.model;

import java.util.List;

public class SyncResult {
	List<GuardianData> guardian_datas;
	List<PumpSpeedData> pump_speed_datas;
	List<PressureData> pressure_datas;

	public SyncResult(List<GuardianData> guardian_datas,
			List<PumpSpeedData> pump_speed_datas,
			List<PressureData> pressure_datas) {
		this.guardian_datas=guardian_datas;
		this.pump_speed_datas=pump_speed_datas;
		this.pressure_datas=pressure_datas;
	}
}
