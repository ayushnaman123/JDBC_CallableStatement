package in.coder.main;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import in.coder.util.JdbcUtil;

public class CsStoredProcedureMySql {
    private static final String storedProcedureCall = "{CALL P_GET_PRODUCT_DETAILS_BY_ID(?,?,?,?)}";
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = null;
		CallableStatement cstmt = null;
		Scanner scanner = null;
		Integer id = null;
		try {
			connection = JdbcUtil.getJdbcConnection();
			if(connection!=null)
				cstmt = connection.prepareCall(storedProcedureCall);
			
			if(cstmt!=null) {
				scanner = new Scanner(System.in);
				
				if(scanner!=null) {
					System.out.print("Enter the id:: ");
					id = scanner.nextInt();
				}
				
				//Setting the input values
				if(id!=null) {
					cstmt.setInt(1, id);
				}
			}
			
			//Setting the datatype for output values
			if(cstmt!=null) {
				cstmt.registerOutParameter(2, Types.VARCHAR);
				cstmt.registerOutParameter(3, Types.INTEGER);
				cstmt.registerOutParameter(4, Types.INTEGER);
			}
			
			//run the stored procedure
			if(cstmt!=null)
				cstmt.execute();
			
			//retrieve the result
			if(cstmt!=null) {
				System.out.println("Product Name is:: "+cstmt.getString(2));
				System.out.println("Product Price is:: "+cstmt.getInt(3));
				System.out.println("Product Quantity is:: "+cstmt.getInt(4));
			}
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JdbcUtil.cleanUp(connection, cstmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scanner.close();
		}
		
		

	}

}
