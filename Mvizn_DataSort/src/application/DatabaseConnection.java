package application;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


import java.util.ArrayList;

public class DatabaseConnection {
	private Connection connection;
	public void connect() {
		String url = "jdbc:mysql://localhost:3306/mvizndata";
		
		try {
			connection = DriverManager.getConnection(url,"root","");
			
			// Set max_allowed_packet value
	        setMaxAllowedPacket(32 * 1024 * 1024); //Fail safe to update max file size upload
	        
	        System.out.println("CONNECTED");
		} catch(SQLException e) {
			System.out.println("Failed to connect to the database!");
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("DISCONNECTED");
            }
        } catch (SQLException e) {
            System.out.println("Failed to disconnect from the database");
            e.printStackTrace();
        }	
	}
	
	public void setMaxAllowedPacket(int maxAllowedPacketSize) {
	    String setMaxAllowedPacketQuery = "SET GLOBAL max_allowed_packet = ?";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(setMaxAllowedPacketQuery)) {
	        preparedStatement.setInt(1, maxAllowedPacketSize);
	        preparedStatement.executeUpdate();

	        System.out.println("max_allowed_packet set successfully to: " + maxAllowedPacketSize + " bytes");
	    } catch (SQLException e) {
	        System.out.println("Failed to set max_allowed_packet");
	        e.printStackTrace();
	    }
	}

	public void insertData(String tableName,String[] columns, Object[] values) {
		if (columns.length != values.length) {
            throw new IllegalArgumentException("Number of columns and values must be the same");
        }
		
		String columnsStr = String.join(",", columns);
        String valuesStr = String.join(",", java.util.Collections.nCopies(values.length, "?"));
        
        String insertQuery = "INSERT INTO " + tableName + " (" + columnsStr + ") VALUES (" + valuesStr + ")";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i + 1, values[i]);
            }
            preparedStatement.executeUpdate();

            System.out.println("Data inserted successfully.");

        } catch (SQLException e) {
            System.out.println("Failed to insert data into the database");
            e.printStackTrace();
        }
  	
	}
	
	public void updateData(String tableName,String columnName, Object newValue,String condition) {
		String updateQuery = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE " + condition;
		
		 try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
			 preparedStatement.setObject(1, newValue);
		     
			 int rowsAffected = preparedStatement.executeUpdate();

		     if (rowsAffected > 0) {
		         //System.out.println("Column updated successfully. Rows affected: " + rowsAffected);
		     } else {
		         System.out.println("No rows were updated. Check your condition.");
		     }
		 } catch (SQLException e) {
		        System.out.println("Failed to update column in the database");
		        e.printStackTrace();
		 }
		
	}
	
	public void updateData(String tableName, String[] columns, Object[] values, String conditionColumn, Object conditionValue) {
        if (columns.length != values.length) {
            throw new IllegalArgumentException("The lengths of columns and values arrays must be the same.");
        }

        // Building the 'SET' part of the SQL query dynamically
        StringBuilder setClause = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            setClause.append(columns[i]).append(" = ?");
            if (i < columns.length - 1) {
                setClause.append(", ");
            }
        }

        // SQL UPDATE query
        String updateQuery = "UPDATE " + tableName + " SET " + setClause + " WHERE " + conditionColumn + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            // Set values for columns to update
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i + 1, values[i]);
            }
            // Set value for the condition
            preparedStatement.setObject(values.length + 1, conditionValue);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " rows updated.");

        } catch (SQLException e) {
            System.out.println("Failed to update data in the database");
            e.printStackTrace();
        }
    }
	
	public void deleteData(String tableName, String condition) {
	    String deleteQuery = "DELETE FROM " + tableName + " WHERE " + condition;

	    try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
	        int rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Data deleted successfully. Rows affected: " + rowsAffected);
	        } else {
	            System.out.println("No rows were deleted. Check your condition.");
	        }
	    } catch (SQLException e) {
	        System.out.println("Failed to delete data from the database");
	        e.printStackTrace();
	    }
	}
	
	public void deleteAllData(String tableName) {
	    String deleteQuery = "DELETE FROM " + tableName;

	    try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
	        int rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("All data deleted successfully. Rows affected: " + rowsAffected);
	        } else {
	            System.out.println("No rows were deleted. Check your condition.");
	        }
	    } catch (SQLException e) {
	        System.out.println("Failed to delete data from the database");
	        e.printStackTrace();
	    }
	}
	
	public int getHighestPrimaryKeyId(String tableName, String columnName) {
		int highestId = -1;
		
		String selectQuery = "SELECT MAX(" + columnName + ") FROM " + tableName;
		
		try (Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(selectQuery)) {

	            if (resultSet.next()) {
	                highestId = resultSet.getInt(1);
	                //System.out.println("Highest primary key ID: " + highestId);
	            } else {
	                System.out.println("Table is empty or no primary key found.");
	            }
	        } catch (SQLException e) {
	            System.out.println("Failed to retrieve the highest primary key ID");
	            e.printStackTrace();
	        }

	        return highestId;
	}
	
	public boolean checkColumnsMatch(String tableName, String column1, Object value1, String column2, Object value2) {
        boolean columnsMatch = false;

        String selectQuery = "SELECT COUNT(*) AS count FROM " + tableName + " WHERE " + column1 + " = ? AND " + column2 + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setObject(1, value1);
            preparedStatement.setObject(2, value2);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                columnsMatch = count > 0;
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error while checking columns match");
            e.printStackTrace();
        }

        return columnsMatch;
    }
	
	public Object getDataByColumn(String tableName, String columnName, String conditionColumn, Object conditionValue) {
        Object data = null;

        String selectQuery = "SELECT " + columnName + " FROM " + tableName + " WHERE " + conditionColumn + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setObject(1, conditionValue);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                data = resultSet.getObject(columnName);
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error while retrieving data by column");
            e.printStackTrace();
        }

        return data;
    }
	
	public List<String> getAllDataByColumn(String tableName, String columnName) {
        List<String> data = new ArrayList<>();

        String selectQuery = "SELECT " + columnName + " FROM " + tableName;

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String columnValue = resultSet.getString(columnName);
                data.add(columnValue);
            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve data from the database");
            e.printStackTrace();
        }

        return data;
    }
	
	public boolean valueExists(String tableName, String columnName, String valueToCheck) {
		boolean Exist = false;
		
		String selectQuery = "SELECT 1 FROM " + tableName + " WHERE " + columnName + " = ?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)){
			preparedStatement.setString(1, valueToCheck);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            Exist = resultSet.next();
		}catch (SQLException e) {
            e.printStackTrace();
        }
		
		return Exist;
	}
	
	public List<ImageData> fetchImageData(String tableName) {
	    List<ImageData> imageDataList = new ArrayList<>();
	    String query = "SELECT imageName, category, image FROM " + tableName;

	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            String imageName = resultSet.getString("imageName");
	            String category = resultSet.getString("category");
	            Blob image = resultSet.getBlob("image");

	            imageDataList.add(new ImageData(imageName, category, image));
	        }
	    } catch (SQLException e) {
	        System.out.println("Error while fetching image data from the database");
	        e.printStackTrace();
	    }

	    return imageDataList;
	}
}
