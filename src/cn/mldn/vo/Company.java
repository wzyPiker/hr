package cn.mldn.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Company implements Serializable {
	private String title ;
	private Date credate ;
	private double p [] ;
	public void setP(double[] p) {
		this.p = p;
	}
	public double[] getP() {
		return p;
	}
	public void setCredate(Date credate) {
		this.credate = credate;
	}
	public Date getCredate() {
		return credate;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
}
