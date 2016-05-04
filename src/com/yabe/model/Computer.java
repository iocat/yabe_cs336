package com.yabe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.yabe.util.DBConnector;
import com.yabe.util.sql.SQLUtils;

public class Computer extends Item implements Retrievable, Updatable{
	public class Screen{
		String type;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public float getWidth() {
			return width;
		}
		public void setWidth(float width) {
			this.width = width;
		}
		public float getHeight() {
			return height;
		}
		public void setHeight(float height) {
			this.height = height;
		}
		public int getResolutionX() {
			return resolutionX;
		}
		public void setResolutionX(int resolutionX) {
			this.resolutionX = resolutionX;
		}
		public int getResolutionY() {
			return resolutionY;
		}
		public void setResolutionY(int resolutionY) {
			this.resolutionY = resolutionY;
		}
		float width;
		float height;
		int resolutionX;
		int resolutionY;
	}
	enum ComputerConnection{
		BLUETOOTH{
			float version;
		},
		WIFI{
			int bandwidth;
		},
		GPS
	}
	
	class Processor{
		String name;
		String manufacturer;
		float speed;
	}
	
	class GraphicCard {
		String name;
		String manufacturer;
		String description;
	}
	
	enum HardDrive{
		HHD,SSD;
		String driveName;
		int size;
	}
	
	class Camera{
		float hasFlash;
		float megapixel;
		float isBack;
	}

	
	private HardDrive hardDrive;
	public HardDrive getHardDrive() {
		return hardDrive;
	}

	public void setHardDrive(HardDrive hardDrive) {
		this.hardDrive = hardDrive;
	}

	public GraphicCard getGraphicCard() {
		return graphicCard;
	}

	public void setGraphicCard(GraphicCard graphicCard) {
		this.graphicCard = graphicCard;
	}

	public ArrayList<Camera> getCamera() {
		return camera;
	}

	public void setCamera(ArrayList<Camera> camera) {
		this.camera = camera;
	}

	public ArrayList<Processor> getProcessors() {
		return processors;
	}

	public void setProcessors(ArrayList<Processor> processors) {
		this.processors = processors;
	}

	public ArrayList<ComputerConnection> getConnections() {
		return connections;
	}

	public void setConnections(ArrayList<ComputerConnection> connections) {
		this.connections = connections;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(int batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	private GraphicCard graphicCard;
	private ArrayList<Camera> camera;
	private ArrayList<Processor> processors;
	private ArrayList<ComputerConnection> connections = new ArrayList<ComputerConnection>();
	private int ram;
	private String brandName;
	private float weight;
	private String operatingSystem;
	private Screen screen;
	private float width;
	private float height;
	private float depth;
	private String color;
	private int batteryCapacity;
	
	public Computer(String itemId) {
		super(itemId);
	}
	
	//Constructor for Computer
	public Computer(String itemId,int ram, String brandName, float weight, String operatingSystem, 
			String screenType, float screenWidth, float screenHeight, int screenResX,
			int screenResY, float width, float height, float depth, String color, int batteryCapacity){
		super(itemId);
		this.ram = ram;
		this.brandName = brandName;
		this.weight = weight;
		this.operatingSystem = operatingSystem;
		this.screen = new Screen();
		this.screen.type = screenType;
		this.screen.width = screenWidth;
		this.screen.height = screenHeight;
		this.screen.resolutionX = screenResX;
		this.screen.resolutionY = screenResY;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.color = color;
		this.batteryCapacity = batteryCapacity;
	}
	
	public static Computer getComputer(String itemId){
		Computer computer = new Desktop(itemId);
		if (!computer.retrieve()){
			computer = new Laptop(itemId);
			if(!computer.retrieve()){
				computer = new Handheld(itemId);
				if(!computer.retrieve()){
					computer = null;
				}
			}
		}
		return computer;
	}
	
	private final String SQL_CREATE_COMPUTER = "INSERT INTO "
			+ "computer(itemId,ram,brandName,weight,operatingSystem,screenType,screenWidth,screenHeight, screenResolutionX,screenResolutionY,sizeWidth,sizeHeight,sizeDepth,color,batteryCapacity) "
			+ "VALUES "
			+ " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/*
	 * insertIntoDB() inserts this computer data into the database
	 */
	public boolean insertIntoDB() throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_CREATE_COMPUTER);
			stmt.setString(1, this.getItemId());
			stmt.setInt(2, this.ram);
			stmt.setString(3, this.brandName);
			stmt.setFloat(4, this.weight);
			stmt.setString(5, this.operatingSystem);
			stmt.setString(6,this.screen.type);
			stmt.setFloat(7, this.screen.width);
			stmt.setFloat(8, this.screen.height);
			stmt.setInt(9,this.screen.resolutionX);
			stmt.setInt(10, this.screen.resolutionY);
			stmt.setFloat(11,this.width);
			stmt.setFloat(12, this.height);
			stmt.setFloat(13, this.depth);
			stmt.setString(14, this.color);
			stmt.setInt(15, this.batteryCapacity);
			// Optionally receivesdth
			rows = stmt.executeUpdate();
		} finally {
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
		}
		return rows == 1;
	}

	
	private final static String SQL_RETRIEVE = 
			"SELECT * FROM (computer LEFT OUTER JOIN graphicCard "
			+ "ON graphicCard.graphicCardName = computer.graphicCardName)"
			+ " WHERE itemID = ?";
	public boolean retrieve(){
		boolean found= false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_RETRIEVE);
			stmt.setString(1, this.getItemId());
			rs = stmt.executeQuery();
			if (rs.next()){
				found = true;
				this.batteryCapacity = rs.getInt("batteryCapacity");
				this.color = rs.getString("color");
				this.width = rs.getFloat("sizeWidth");
				this.height = rs.getFloat("sizeHeight");
				this.width = rs.getFloat("sizeDepth");
				this.operatingSystem = rs.getString("operatingSystem");
				this.weight = rs.getFloat("weight");
				this.brandName = rs.getString("brandName");
				this.ram = rs.getInt("ram");
				this.screen = new Screen();
				this.screen.height = rs.getFloat("screenHeight");
				this.screen.width = rs.getFloat("screenWidth");
				this.screen.type = rs.getString("screenType");
				this.screen.resolutionX = rs.getInt("screenResolutionX");
				this.screen.resolutionY = rs.getInt("screenResolutionY");
				this.graphicCard = new GraphicCard();
				this.graphicCard.name = rs.getString("graphicCardName");
				this.graphicCard.manufacturer =  rs.getString("graphicCardName");
				this.graphicCard.description = rs.getString("description");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(rs);
			SQLUtils.closeQuitely(stmt);
		}
		if(found){
			super.retrieve();
		}
		return found;
	}
	
	public void update(){
		
	};
	
	
	

}
