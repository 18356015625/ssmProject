package com.liyan.entity;

/*
 * 封装一个实体给前台
 */
public class ResultDto<T> {
	
	private int code;//和前端约定code为200标识成功
	private String errMsg;//错误原因，非必填
	private T data;//返回给前台的查询出的数据可赋值给该属性
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}


	public ResultDto(int code, String errMsg, T data) {
		super();
		this.code = code;
		this.errMsg = errMsg;
		this.data = data;
	}
	
	public ResultDto(int code) {
		super();
		this.code = code;
	}

	public ResultDto(int code, String errMsg) {
		super();
		this.code = code;
		this.errMsg = errMsg;
	}

	public ResultDto(int code, T data) {
		super();
		this.code = code;
		this.data = data;
	}
	
}
