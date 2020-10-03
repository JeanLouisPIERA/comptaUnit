package com.dummy.myerp.consumer.db;



/**
 * Enumération des Datasources utilisées par l'application
 */
public enum DataSourcesEnum {

    /** MYERP */
    MYERP("MYERP", "dev_myerp.db_1");
    
	
	 private String code;
	 private String text;
	  
	private DataSourcesEnum(String code, String text) {
		this.code = code;
		this.text = text;
	}
	
	public static DataSourcesEnum getDataSourceEnumByCode(String code) {
		for(DataSourcesEnum dataSourceEnum : DataSourcesEnum.values()) {
			if(dataSourceEnum.code.equals(code)) {
				return dataSourceEnum;
			}
		}
		return null;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		if(this==MYERP) {
			return "dev_myerp.db_1";
		}
		return super.toString();
	}
    
    

}
