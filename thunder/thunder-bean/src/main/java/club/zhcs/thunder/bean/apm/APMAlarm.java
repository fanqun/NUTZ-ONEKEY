package club.zhcs.thunder.bean.apm;

import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.lang.Times;
import org.nutz.lang.random.R;

import club.zhcs.titans.utils.db.po.Entity;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project bean
 *
 * @file APMAlarm.java
 *
 * @description 应用性能告警
 *
 * @time 2016年3月15日 下午3:30:08
 *
 */
@Table("t_apm_alarm")
@Comment("性能告警表")
public class APMAlarm extends Entity {

	@Name
	@Column("alarm_code")
	@Comment("报警信息编号")
	private String code = R.UU32();

	@Column("alarm_type")
	@Comment("报警类型")
	private Type type;

	@Column("alarm_time")
	@Comment("报警时间")
	private Date alarmTime = Times.now();

	@Column("alarm_msg")
	@Comment("报警消息")
	@ColDefine(width = 250)
	private String msg;

	@Column("alarm_ip")
	@Comment("报警 ip")
	private String ip;

	@Column("alarm_title")
	@Comment("报警标题")
	private String title;

	@Column("alarm_device")
	@Comment("报警设备")
	private String device;

	@Column("alarm_usage")
	@Comment(" 设备使用情况")
	private double usage;

	@Column("alarm_alarm_point")
	@Comment("设备告警点")
	private int alarm;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public double getUsage() {
		return usage;
	}

	public void setUsage(double usage) {
		this.usage = usage;
	}

	public int getAlarm() {
		return alarm;
	}

	public void setAlarm(int alarm) {
		this.alarm = alarm;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static enum Type {
		SLOW, MEM, DISK, CPU, NETWORK;
	}

}
