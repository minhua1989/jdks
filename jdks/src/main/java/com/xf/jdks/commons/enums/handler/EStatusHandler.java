package com.xf.jdks.commons.enums.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.xf.jdks.commons.enums.EStatus;

public class EStatusHandler extends BaseTypeHandler<EStatus> {

	private Class<EStatus> eStatus;

	private EStatus[] enums;

	public EStatusHandler(Class<EStatus> eStatus) {
        if (eStatus != null)
        {
		this.eStatus = eStatus;
		this.enums = eStatus.getEnumConstants();
        }
        
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, EStatus parameter, JdbcType jdbcType) throws SQLException {
		
		ps.setString(i, parameter.getValue());
	}

	@Override
	public EStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String i = rs.getString(columnName);	
		if (rs.wasNull()) {
			return null;
		} else {
			return EStatus.getEnum(i);
		}
	}

	@Override
	public EStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String i = rs.getString(columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			return EStatus.getEnum(i);
		}
	}

	@Override
	public EStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String i = cs.getString(columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			return EStatus.getEnum(i);
		}
	}

	public Class<EStatus> getEStatus() {
		return eStatus;
	}

	public void setEStatus(Class<EStatus> eStatus) {
		this.eStatus = eStatus;
	}

	public EStatus[] getEnums() {
		return enums;
	}

	public void setEnums(EStatus[] enums) {
		this.enums = enums;
	}

}
