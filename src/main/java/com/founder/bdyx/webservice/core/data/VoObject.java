package com.founder.bdyx.webservice.core.data;

/**
 * 所有Vo对象的父类，用于保存vo对象的增、删、改状态。
 * @author qjs
 *
 * @Date:2006-07-27
 */
public class VoObject implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final String VOS_CREATE = "NEW";
	public static final String VOS_MODIFY = "MDF";
	public static final String VOS_REMOVE = "DEL";
	public static final String VOS_LOAD = "";

	// 记录前台记录的状态
	protected String _rowStatus="";

	public String get_rowStatus() {
		return _rowStatus;
	}

	public void set_rowStatus(String status) {
		if (status == null) {
			_rowStatus = "";

		} else {
			_rowStatus = status;
		}
	}

	/**
	 *  因为在前端id为空时用0表示，所以在这里将其回转为空。
	 * @param oldId
	 * @return
	 */
	public static Integer getRealId(Integer oldId){
		if (oldId==null) return null;
		if (oldId==0) return null;
		return oldId;
	}

	/**
	 * 有些字段只能是null不能是0，在java端可以用此方法将flex端传回的数据进行转换。
	 * @param oldV
	 * @return
	 */
	public static Double getRealDouble(Double oldV){
		if (oldV==null) return null;
		if (oldV==0) return null;
		return oldV;
	}

	public static boolean isValStr(String str){
		if (str==null) return false;
		if (str.equals("")) return false;
		return true;
	}
}
