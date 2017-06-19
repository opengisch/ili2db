package ch.ehi.ili2db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.ehi.basics.logging.EhiLogger;
import ch.ehi.ili2db.base.DbNames;
import ch.ehi.ili2db.base.DbUrlConverter;
import ch.ehi.ili2db.base.Ili2db;
import ch.ehi.ili2db.base.Ili2dbException;
import ch.ehi.ili2db.dbmetainfo.DbExtMetaInfo;
import ch.ehi.ili2db.gui.Config;
import ch.ehi.ili2db.mapping.NameMapping;
import ch.ehi.sqlgen.DbUtility;
import ch.ehi.sqlgen.repository.DbTableName;
import ch.interlis.iom.IomObject;
import ch.interlis.iom_j.xtf.XtfReader;
import ch.interlis.iox.EndBasketEvent;
import ch.interlis.iox.EndTransferEvent;
import ch.interlis.iox.IoxEvent;
import ch.interlis.iox.ObjectEvent;
import ch.interlis.iox.StartBasketEvent;
import ch.interlis.iox.StartTransferEvent;

//-Ddburl=jdbc:postgresql:dbname -Ddbusr=usrname -Ddbpwd=1234
public class MetaInfo23Test {
	private static final String DBSCHEMA = "MetaInfo23";
	String dburl=System.getProperty("dburl"); 
	String dbuser=System.getProperty("dbusr");
	String dbpwd=System.getProperty("dbpwd"); 
	Connection jdbcConnection=null;
	Statement stmt=null;
	
	public Config initConfig(String xtfFilename,String dbschema,String logfile) {
		Config config=new Config();
		new ch.ehi.ili2pg.PgMain().initConfig(config);
		config.setDburl(dburl);
		config.setDbusr(dbuser);
		config.setDbpwd(dbpwd);
		if(dbschema!=null){
			config.setDbschema(dbschema);
		}
		if(logfile!=null){
			config.setLogfile(logfile);
		}
		config.setXtffile(xtfFilename);
		if(xtfFilename!=null && Ili2db.isItfFilename(xtfFilename)){
			config.setItfTransferfile(true);
		}
		return config;
	}
	
	@Test
	public void importIli() throws Exception
	{
		//EhiLogger.getInstance().setTraceFilter(false);
		Connection jdbcConnection=null;
		try{
		    Class driverClass = Class.forName("org.postgresql.Driver");
	        jdbcConnection = DriverManager.getConnection(dburl, dbuser, dbpwd);
	        stmt=jdbcConnection.createStatement();
			stmt.execute("DROP SCHEMA IF EXISTS "+DBSCHEMA+" CASCADE");
			{
				File data=new File("test/data/MetaInfo/MetaInfo23.ili");
				Config config=initConfig(data.getPath(),DBSCHEMA,data.getPath()+".log");
				config.setFunction(Config.FC_SCHEMAIMPORT);
				config.setCreateFk(config.CREATE_FK_YES);
				config.setTidHandling(Config.TID_HANDLING_PROPERTY);
				config.setBasketHandling(config.BASKET_HANDLING_READWRITE);
				config.setCreateEnumDefs(Config.CREATE_ENUM_DEFS_MULTI);
				config.setCatalogueRefTrafo(null);
				config.setMultiSurfaceTrafo(null);
				config.setMultilingualTrafo(null);
				config.setInheritanceTrafo(Config.INHERITANCE_TRAFO_SMART1);
				config.setCreateMetaInfo(true);
				Ili2db.readSettingsFromDb(config);
				Ili2db.run(config,null);
				{
					String selStmt="SELECT "+DbNames.META_INFO_COLUMN_TAB_SETTING_COL+", "+DbNames.META_INFO_COLUMN_TAB_SUBTYPE_COL+" FROM "+DBSCHEMA+"."+DbNames.META_INFO_COLUMN_TAB+" WHERE "+DbNames.META_INFO_COLUMN_TAB_TABLENAME_COL+"=? AND "+DbNames.META_INFO_COLUMN_TAB_COLUMNAME_COL+"=? AND "+DbNames.META_INFO_COLUMN_TAB_TAG_COL+"=?";
					java.sql.PreparedStatement selPrepStmt = jdbcConnection.prepareStatement(selStmt);
					{
						selPrepStmt.setString(1, "classa1");
						selPrepStmt.setString(2, "numx");
						selPrepStmt.setString(3, DbExtMetaInfo.TAG_COL_UNIT);
						ResultSet rs = selPrepStmt.executeQuery();
						Assert.assertTrue(rs.next());
						Assert.assertEquals("m",rs.getString(1));
						Assert.assertEquals("classa1b",rs.getString(2));
					}
					{
						selPrepStmt.setString(1, "classa1");
						selPrepStmt.setString(2, "numa");
						selPrepStmt.setString(3, DbExtMetaInfo.TAG_COL_UNIT);
						ResultSet rs = selPrepStmt.executeQuery();
						Assert.assertTrue(rs.next());
						Assert.assertEquals("m",rs.getString(1));
						Assert.assertEquals(null,rs.getString(2));
					}
				}
			}
		}finally{
			if(jdbcConnection!=null){
				jdbcConnection.close();
			}
		}
	}
	
}