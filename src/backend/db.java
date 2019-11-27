package backend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
//import frontend.RessourcenInterface;

public class db {
    
    static Connection conn = null;
    //public static db db1 = new db("coapdb");
    public db(String dbname) {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("loaded driver ...");
        } catch (Exception ex) {
            System.out.println("Kann Treiber nicht laden!");
            System.out.println("Exception: " + ex.getMessage());
            System.out.println("ExceptionLocal: " + ex.toString());
        }
        try {
            conn =
            DriverManager.getConnection("jdbc:mysql://localhost/" + dbname + "?" +
                    "user=root&password=1234&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"); 
            
            System.out.println("connected to database");
            
        } catch (SQLException ex) {
            // handle any errors
            System.err.println("Can't connect to server!");
            System.exit(0);
            //System.out.println("SQLException: " + ex.getMessage());
            //System.out.println("SQLState: " + ex.getSQLState());
            //System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
	 private static final String extendStringTo14( String s )
	  {
	    if( null == s ) s = "";
	    final String sFillStrWithWantLen = "              ";
	    final int iWantLen = sFillStrWithWantLen.length();
	    final int iActLen  = s.length();
	    if( iActLen < iWantLen )
	      return (s + sFillStrWithWantLen).substring( 0, iWantLen );
	    if( iActLen > 2 * iWantLen )
	      return s.substring( 0, 2 * iWantLen );
	    return s;
	  }
         
	private void showTable(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
		  int i, n = rsmd.getColumnCount();
		  // Print table content:
		  for( i=0; i<n; i++ )
		    System.out.print( "+---------------" );
		  System.out.println( "+" );
		  for( i=1; i<=n; i++ )    // Attention: first column with 1 instead of 0
		    System.out.print( "| " + extendStringTo14( rsmd.getColumnName( i ) ) );
		  System.out.println( "|" );
		  for( i=0; i<n; i++ )
		    System.out.print( "+---------------" );
		  System.out.println( "+" );
		  while( rs.next() ) {
		    for( i=1; i<=n; i++ )  // Attention: first column with 1 instead of 0
		      System.out.print( "| " + extendStringTo14( rs.getString( i ) ) );
		    System.out.println( "|" );
		  }
	}
        
	/*public void insertHost(Host host) throws SQLException {
	    Statement stmt = conn.createStatement();
	    String befehl = "INSERT INTO hosts(ipAdresse, name,macAdresse) VALUES('" +
	    host.ipAdresse + "','" + host.name + "','" + host.macAdresse+ "')";
	    System.out.println(befehl);
	    int count = stmt.executeUpdate(befehl);
	    System.out.println("Anzahl eingefuegter Datens�tze: " + count); 
	}
        
        public int countActives() throws SQLException{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM resources WHERE active = '1'");
            int count = 0;
            while (rs.next()) {
                count++;
            }
            return count;
        }
        
        public resource[] getActiveIds() throws SQLException{
            
            resource[] activeArray = new resource[countActives()];
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM resources WHERE active = '1'");
            int i = 0;
            while (rs.next()) {
                activeArray[i] = new resource(rs.getString("name"), rs.getString("protocol"), rs.getString("adress"), rs.getString("attribute"), 
                        rs.getInt("queryFrequency"), rs.getString("datatype"), rs.getInt("output"), rs.getInt("upperlimit"), rs.getInt("lowerlimit"));
                i++;
            }
            return activeArray;
        }
        
        public void insertResource(resource resource) throws SQLException {
            Statement stmt = conn.createStatement();
	    String befehl = "INSERT INTO resources(name, protocol, adress, attribute, queryFrequency, datatype, output, upperLimit, lowerLimit) VALUES('" +
                    resource.name + "','" + resource.protocol + "','" + resource.adress+ "','" +
                    resource.attribute + "','" + resource.queryFrquzy + "','" + resource.datatype + "','" + 
                    resource.output + "','" + resource.upperLimit + "','" + resource.lowerLimit + "')";
	    int count = stmt.executeUpdate(befehl);
	    //System.out.println("Anzahl eingefuegter Datensätze: " + count);
        }
        
        public void insertActive(isActive active) throws SQLException {
            Statement stmt = conn.createStatement();
	    String befehl = "INSERT INTO isactive(active, resources_idresources) VALUES('" + active.active + "','" + active.resources_idresources + "')";
	    int count = stmt.executeUpdate(befehl);
	    //System.out.println("Anzahl eingefuegter Datensätze: " + count);
        }*/
        
        public void setActive(String id, int active) throws SQLException {
            Statement stmt = conn.createStatement();
	    String befehl = "Update resources Set active=' " + active + " ' WHERE idresources = " + id;
	    int count = stmt.executeUpdate(befehl);
	    System.out.println("Anzahl eingefuegter Datensätze: " + count);
        }
        
        public void deleteResource(String id) throws SQLException{
            Statement stmt = conn.createStatement();
	    String befehl = "DELETE FROM resources WHERE idresources = " + id;
            int count = stmt.executeUpdate(befehl);
            System.out.println("Anzahl gelöschter Datensätze: " + count);
        }
        
        public static void countRows(String tablename) throws SQLException{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + tablename);
            int count = 0;
            while (rs.next()) {
              count++;
            }  
            System.out.println(count);
        }
        
        public String[] countColumns(String tablename) throws SQLException{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tablename);
            int col = rs.getMetaData().getColumnCount();
            System.out.println(col);
            String colNames[] = new String[col];
            for (int i = 0; i < col; i++){
                String col_name = rs.getMetaData().getColumnName(i);
                System.out.println(col_name);
                colNames[i] = col_name;
            }
            return colNames;
        }
        
	public static void main(String[] args) throws SQLException {
            
            
            //resource resource = new resource("test-abfrage", "coap", "://coap.me/", "test", 1, "String", 1, null, null, null);
            //db.insertResource(resource);
            //db1.setActive("110", 1);
            //db.countRows("hosts");
            
            //countRows("hosts");
            //String colNames[] = db.countColumns("resource");
            //System.out.println(colNames[0]);
            /*
            Host host1 = new Host("Meiner", "47.11.47.11", "00:01:02:03:04:05");
            host1.setIpAdresse("47.11.47.10");                
            System.out.println("hostinfo: " + host1.getHostInfo());		
            db.insertHost (host1);*/
                
            //db.showTable("ka");
	}



	

}
