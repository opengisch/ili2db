/* This file is part of the ili2ora project.
 * For more information, please see <http://www.interlis.ch>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package ch.ehi.ili2db.converter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import com.vividsolutions.jts.io.ParseException;

import ch.ehi.basics.settings.Settings;
import ch.ehi.ili2db.gui.Config;
import ch.interlis.iom.IomObject;
import ch.interlis.iox_j.jts.Iox2jtsException;

public abstract class AbstractWKBColumnConverter implements SqlColumnConverter {
	@Override
	public void setCoordNull(PreparedStatement stmt, int parameterIndex) throws SQLException {
		stmt.setNull(parameterIndex,java.sql.Types.BINARY);
	}
	@Override
	public void setDecimalNull(PreparedStatement stmt, int parameterIndex) throws SQLException {
		stmt.setNull(parameterIndex,java.sql.Types.DECIMAL);
	}
	@Override
	public void setUuidNull(PreparedStatement stmt, int parameterIndex) throws SQLException {
		stmt.setNull(parameterIndex,java.sql.Types.VARCHAR);
	}
	@Override
	public void setBoolean(java.sql.PreparedStatement stmt,int parameterIndex,boolean value)
	throws java.sql.SQLException
	{
			stmt.setBoolean(parameterIndex, value);
	}
	@Override
	public void setTimestamp(java.sql.PreparedStatement stmt, int parameterIndex,
			Timestamp value) throws SQLException {
		stmt.setTimestamp(parameterIndex, value);
	}

	@Override
	public void setDate(java.sql.PreparedStatement stmt, int parameterIndex, Date value)
			throws SQLException {
		stmt.setDate(parameterIndex, value);
	}

	@Override
	public void setTime(java.sql.PreparedStatement stmt, int parameterIndex, Time value)
			throws SQLException {
		stmt.setTime(parameterIndex, value);
	}
	
	@Override
	public void setPolylineNull(PreparedStatement stmt, int parameterIndex) throws SQLException {
		stmt.setNull(parameterIndex,java.sql.Types.BINARY);
	}
	@Override
	public void setSurfaceNull(PreparedStatement stmt, int parameterIndex) throws SQLException {
		stmt.setNull(parameterIndex,java.sql.Types.BINARY);
	}
	@Override
	public String getInsertValueWrapperCoord(String wkfValue,int srid) {
		//return "ST_GeometryFromWKB("+wkfValue+(srid==-1?"":","+srid)+")";
		return "GeomFromWKB("+wkfValue+(srid==-1?"":","+srid)+")";
	}
	@Override
	public String getInsertValueWrapperMultiCoord(String wkfValue,int srid) {
		//return "ST_GeometryFromWKB("+wkfValue+(srid==-1?"":","+srid)+")";
		return "GeomFromWKB("+wkfValue+(srid==-1?"":","+srid)+")";
	}
	@Override
	public String getInsertValueWrapperPolyline(String wkfValue,int srid) {
		//return "ST_GeometryFromWKB("+wkfValue+(srid==-1?"":","+srid)+")";
		return "GeomFromWKB("+wkfValue+(srid==-1?"":","+srid)+")";
	}
	@Override
	public String getInsertValueWrapperMultiPolyline(String wkfValue,int srid) {
		//return "ST_GeometryFromWKB("+wkfValue+(srid==-1?"":","+srid)+")";
		return "GeomFromWKB("+wkfValue+(srid==-1?"":","+srid)+")";
	}
	@Override
	public String getInsertValueWrapperSurface(String wkfValue,int srid) {
		//return "ST_GeometryFromWKB("+wkfValue+(srid==-1?"":","+srid)+")";
		return "GeomFromWKB("+wkfValue+(srid==-1?"":","+srid)+")";
	}
	@Override
	public String getInsertValueWrapperMultiSurface(String wkfValue,int srid) {
		//return "ST_GeometryFromWKB("+wkfValue+(srid==-1?"":","+srid)+")";
		return "GeomFromWKB("+wkfValue+(srid==-1?"":","+srid)+")";
	}
	@Override
	public String getSelectValueWrapperDate(String sqlColName) {
		 return sqlColName;
	}

	@Override
	public String getSelectValueWrapperTime(String sqlColName) {
		 return sqlColName;
	}

	@Override
	public String getSelectValueWrapperDateTime(String sqlColName) {
		 return sqlColName;
	}
	@Override
	public String getSelectValueWrapperCoord(String dbNativeValue) {
		//return "ST_AsBinary("+dbNativeValue+")";
		return "AsBinary("+dbNativeValue+")";
	}
	@Override
	public String getSelectValueWrapperMultiCoord(String dbNativeValue) {
		//return "ST_AsBinary("+dbNativeValue+")";
		return "AsBinary("+dbNativeValue+")";
	}
	@Override
	public String getSelectValueWrapperPolyline(String dbNativeValue) {
		//return "ST_AsBinary("+dbNativeValue+")";
		return "AsBinary("+dbNativeValue+")";
	}
	@Override
	public String getSelectValueWrapperMultiPolyline(String dbNativeValue) {
		//return "ST_AsBinary("+dbNativeValue+")";
		return "AsBinary("+dbNativeValue+")";
	}
	@Override
	public String getSelectValueWrapperSurface(String dbNativeValue) {
		//return "ST_AsBinary("+dbNativeValue+")";
		return "AsBinary("+dbNativeValue+")";
	}
	@Override
	public String getSelectValueWrapperMultiSurface(String dbNativeValue) {
		//return "ST_AsBinary("+dbNativeValue+")";
		return "AsBinary("+dbNativeValue+")";
	}
	@Override
	public Object fromIomUuid(String value)
			throws SQLException, ConverterException {
		return value;
	}
	@Override
	public java.lang.Object fromIomSurface(
		IomObject value,
		int srid,
		boolean hasLineAttr,
		boolean is3D,double p)
		throws SQLException, ConverterException {
			if(value!=null){			
				com.vividsolutions.jts.geom.Geometry geom;
				try {
					geom = ch.interlis.iox_j.jts.Iox2jts.surface2JTS(value,p);
				} catch (Iox2jtsException ex) {
					throw new ConverterException(ex);
				}
				byte bv[]=new com.vividsolutions.jts.io.WKBWriter(is3D?3:2).write(geom);
				return bv;
			}
			return null;
	}
	@Override
	public java.lang.Object fromIomMultiSurface(
		IomObject value,
		int srid,
		boolean hasLineAttr,
		boolean is3D,double p)
		throws SQLException, ConverterException {
			if(value!=null){			
				throw new ConverterException("MultiSurface not supported");
			}
			return null;
	}
	@Override
	public java.lang.Object fromIomCoord(IomObject value, int srid,boolean is3D)
		throws SQLException, ConverterException {
		if(value!=null){			
			com.vividsolutions.jts.geom.Geometry geom;
			try {
				geom = new com.vividsolutions.jts.geom.GeometryFactory().createPoint(ch.interlis.iox_j.jts.Iox2jts.coord2JTS(value));
			} catch (Iox2jtsException ex) {
				throw new ConverterException(ex);
			}
			byte bv[]=new com.vividsolutions.jts.io.WKBWriter(is3D?3:2).write(geom);
			return bv;
		}
		return null;
	}
	@Override
	public java.lang.Object fromIomMultiCoord(
		IomObject value,
		int srid,
		boolean is3D)
		throws SQLException, ConverterException {
			if(value!=null){			
				throw new ConverterException("MultiCoord not supported");
			}
			return null;
	}
	@Override
	public java.lang.Object fromIomPolyline(IomObject value, int srid,boolean is3D,double p)
		throws SQLException, ConverterException {
			if(value!=null){
				com.vividsolutions.jts.geom.Geometry geom;
				try {
					geom = new com.vividsolutions.jts.geom.GeometryFactory().createLineString(ch.interlis.iox_j.jts.Iox2jts.polyline2JTS(value,false,p).toCoordinateArray());
				} catch (Iox2jtsException ex) {
					throw new ConverterException(ex);
				}
				byte bv[]=new com.vividsolutions.jts.io.WKBWriter(is3D?3:2).write(geom);
				return bv;
			}
			return null;
	}
	@Override
	public java.lang.Object fromIomMultiPolyline(IomObject value, int srid,boolean is3D,double p)
		throws SQLException, ConverterException {
			if(value!=null){
				throw new UnsupportedOperationException();
			}
			return null;
	}
	@Override
	public IomObject toIomCoord(
		Object geomobj,
		String sqlAttrName,
		boolean is3D)
		throws SQLException, ConverterException {
		byte bv[]=(byte [])geomobj;
		com.vividsolutions.jts.geom.Geometry geom;
		try {
			geom = new com.vividsolutions.jts.io.WKBReader().read(bv);
		} catch (ParseException e) {
			throw new ConverterException(e);
		}
		return ch.interlis.iox_j.jts.Jts2iox.JTS2coord(geom.getCoordinate());
	}
	@Override
	public IomObject toIomMultiCoord(
		Object geomobj,
		String sqlAttrName,
		boolean is3D)
		throws SQLException, ConverterException {
		throw new UnsupportedOperationException();
	}
	@Override
	public IomObject toIomSurface(
		Object geomobj,
		String sqlAttrName,
		boolean is3D)
		throws SQLException, ConverterException {
		byte bv[]=(byte [])geomobj;
		com.vividsolutions.jts.geom.Geometry geom;
		try {
			geom = new com.vividsolutions.jts.io.WKBReader().read(bv);
		} catch (ParseException e) {
			throw new ConverterException(e);
		}
		return ch.interlis.iox_j.jts.Jts2iox.JTS2surface((com.vividsolutions.jts.geom.Polygon)geom);
	}
	@Override
	public IomObject toIomMultiSurface(
		Object geomobj,
		String sqlAttrName,
		boolean is3D)
		throws SQLException, ConverterException {
		byte bv[]=(byte [])geomobj;
		com.vividsolutions.jts.geom.Geometry geom;
		try {
			geom = new com.vividsolutions.jts.io.WKBReader().read(bv);
		} catch (ParseException e) {
			throw new ConverterException(e);
		}
		return ch.interlis.iox_j.jts.Jts2iox.JTS2surface((com.vividsolutions.jts.geom.Polygon)geom);
	}
	@Override
	public IomObject toIomPolyline(
		Object geomobj,
		String sqlAttrName,
		boolean is3D)
		throws SQLException, ConverterException {
		byte bv[]=(byte [])geomobj;
		com.vividsolutions.jts.geom.Geometry geom;
		try {
			geom = new com.vividsolutions.jts.io.WKBReader().read(bv);
		} catch (ParseException e) {
			throw new ConverterException(e);
		}
		return ch.interlis.iox_j.jts.Jts2iox.JTS2polyline((com.vividsolutions.jts.geom.LineString)geom);
	}
	@Override
	public IomObject toIomMultiPolyline(
		Object geomobj,
		String sqlAttrName,
		boolean is3D)
		throws SQLException, ConverterException {
		throw new UnsupportedOperationException();
	}
	public AbstractWKBColumnConverter()
	{
	}
	@Override
	public Integer getSrsid(String crsAuthority, String crsCode,Connection conn) 
		throws ConverterException
	{
		
		int srsid;
		try{
			java.sql.Statement stmt=conn.createStatement();
			java.sql.ResultSet ret=stmt.executeQuery("SELECT srid FROM SPATIAL_REF_SYS WHERE AUTH_NAME=\'"+crsAuthority+"\' AND AUTH_SRID="+crsCode);
			if(!ret.next()){
				return null;
			}
			srsid=ret.getInt("srid");
		}catch(java.sql.SQLException ex){
			throw new ConverterException("failed to query srsid from database",ex);
		}
		return srsid;
	}
	@Override
	public void setup(Connection conn, Settings config) {
	}
	@Override
	public void setBlobNull(PreparedStatement stmt, int parameterIndex)
			throws SQLException {
		throw new UnsupportedOperationException();
	}
	@Override
	public void setXmlNull(PreparedStatement stmt, int parameterIndex)
			throws SQLException {
		throw new UnsupportedOperationException();
	}
	@Override
	public Object fromIomBlob(String uuid) throws SQLException,
			ConverterException {
		throw new UnsupportedOperationException();
	}
	@Override
	public Object fromIomXml(String uuid) throws SQLException,
			ConverterException {
		throw new UnsupportedOperationException();
	}
	@Override
	public String toIomXml(Object obj) throws SQLException, ConverterException {
		throw new UnsupportedOperationException();
	}
	@Override
	public String toIomBlob(Object obj) throws SQLException, ConverterException {
		throw new UnsupportedOperationException();
	}

}
