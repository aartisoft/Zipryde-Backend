package com.trivecta.zipryde.mongodb;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.trivecta.zipryde.constants.ZipRydeConstants.ZIPRYDE_CONFIGURATION;
import com.trivecta.zipryde.model.entity.ZiprydeMstr;
import com.trivecta.zipryde.model.service.ZiprydeConfigService;

@Component
public class MongoDbClient {

	@Autowired
	ZiprydeConfigService ziprydeConfigService;
	
	static MongoClient mongoClient = new MongoClient("localhost", 27017);
	static MongoDatabase mongoDatabase = mongoClient.getDatabase("ZIPRYDE");
	static MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("driversession");
	
	public UserGeoSpatialResponse getGeoLocationByDriverId(String userId) {
		UserGeoSpatialResponse  userResponse = new UserGeoSpatialResponse();
		FindIterable<Document> findIterable = mongoCollection.find(new Document("userId",userId).append("isActive",1));
		findIterable.forEach(new Block<Document>() {
			public void apply(final Document document) {
				Document geoDoc = (Document) document.get("loc");
				userResponse.setUserId(Integer.parseInt(document.get("userId").toString()));
				userResponse.setLatitude(new BigDecimal(geoDoc.get("lat").toString()));
				userResponse.setLongitude(new BigDecimal(geoDoc.get("lon").toString()));	
			}
		});
		return userResponse;
	}
	
	public List<UserGeoSpatialResponse> getNearByActiveDrivers(Double longitude,Double latitude){
		Double noOfMilesToSearch = 40d;
		ZiprydeMstr ziprydeMstr = ziprydeConfigService.getZiprydeMstrByType(ZIPRYDE_CONFIGURATION.NO_OF_MILES_TO_SEARCH);
		if(ziprydeMstr != null) {
			noOfMilesToSearch = Double.valueOf(ziprydeMstr.getValue());
		}
		Double noOfMiles = noOfMilesToSearch / 3963.2 ;
		
		final List<UserGeoSpatialResponse> userResponseList = new ArrayList<UserGeoSpatialResponse>();
		List<Double> coordinates = new LinkedList<Double>();
		coordinates.add(longitude);
		coordinates.add(latitude);
		
		List circle = new ArrayList();
		circle.add(coordinates);
		circle.add(noOfMiles);
		
		FindIterable<Document> findIterable = mongoCollection.find(new Document("isActive",1)
				.append("lastUpdatedTime", 
				new Document("$gte", ZonedDateTime.now(ZoneOffset.UTC).minus(5, ChronoUnit.MINUTES).format(DateTimeFormatter.ISO_INSTANT))
				.append("$lt",ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT)))
				.append("loc",new Document("$geoWithin",new Document("$centerSphere",circle))));
		
		findIterable.forEach(new Block<Document>() {
			public void apply(final Document document) {
				Document geoDoc = (Document) document.get("loc");
				UserGeoSpatialResponse  userResponse = new UserGeoSpatialResponse();
				userResponse.setUserId(Integer.parseInt(document.get("userId").toString()));
				userResponse.setLatitude(new BigDecimal(geoDoc.get("lat").toString()));
				userResponse.setLongitude(new BigDecimal(geoDoc.get("lon").toString()));
				userResponseList.add(userResponse);
				/*System.out.println(document.get("userId") + "--->" + document.get("loc"));
				System.out.println(geoDoc.get("lon")+" -- > "+geoDoc.get("lat"));*/
			}			
		});
		
		return userResponseList;
	}
	
	public Integer checkDriverNearByBookingLocation(String userId,Double longitude,Double latitude){
		Double noOfMilesToSearch = 1.5;
		ZiprydeMstr ziprydeMstr = ziprydeConfigService.getZiprydeMstrByType(ZIPRYDE_CONFIGURATION.NO_OF_MILES_DRIVER_ONSITE);
		if(ziprydeMstr != null) {
			noOfMilesToSearch = Double.valueOf(ziprydeMstr.getValue());
		}
		Double noOfMiles = noOfMilesToSearch / 3963.2 ;
		
		UserGeoSpatialResponse  userResponse = new UserGeoSpatialResponse();
		List<Double> coordinates = new LinkedList<Double>();
		coordinates.add(longitude);
		coordinates.add(latitude);
		
		List circle = new ArrayList();
		circle.add(coordinates);
		circle.add(noOfMiles);
		
		FindIterable<Document> findIterable = mongoCollection.find(new Document("isActive",1).append("userId", userId)
				.append("lastUpdatedTime", 
				new Document("$gte", ZonedDateTime.now(ZoneOffset.UTC).minus(5, ChronoUnit.MINUTES).format(DateTimeFormatter.ISO_INSTANT))
				.append("$lt",ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT)))
				.append("loc",new Document("$geoWithin",new Document("$centerSphere",circle))));
		
		findIterable.forEach(new Block<Document>() {
			public void apply(final Document document) {
				Document geoDoc = (Document) document.get("loc");
				userResponse.setUserId(Integer.parseInt(document.get("userId").toString()));
			}			
		});
		
		return userResponse.getUserId();
	}
	
	public void updateIdleDriverToOffline() {

		Bson filter = 
				Filters.gte("lastUpdatedTime", 
						ZonedDateTime.now(ZoneOffset.UTC).minus(5, ChronoUnit.MINUTES).format(DateTimeFormatter.ISO_INSTANT));
		Bson filter1 = Filters.lt("lastUpdatedTime", ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT));
		
		Bson filters = Filters.and(filter,filter1);
		Bson lastUpdatedTimeFilter = Filters.not(filters);

		Bson finalFliters = Filters.and(lastUpdatedTimeFilter,Filters.eq("isActive", 1));
		
		mongoCollection.updateMany(
				finalFliters,
				new Document("$set", new Document("isActive", 0)
				.append("lastUpdatedTime", ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT))));
	}

	public List<Integer> findDriversByActive(int isActive) {
		List<Integer> userIds = new ArrayList<Integer>();		
		Bson filter = Filters.eq("isActive", isActive);
		FindIterable<Document> findIterable = mongoCollection.find(filter);
		if(findIterable !=null) {
			findIterable.forEach(new Block<Document>() {
				public void apply(final Document document) {
					if(document != null) 
						userIds.add(Integer.parseInt(document.get("userId").toString()));
			}});	
		}
		return userIds;				
	}
	
	@Async
	public void updateDriverOnlineStatus(String userId,int isActive) {
		Bson filter = Filters.eq("userId", userId);
		mongoCollection.updateOne(filter, new Document("$set", new Document("isActive", isActive)
				.append("lastUpdatedTime", ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT))));
	}
	
	@Async
	public void updateDriverSession(String userId,Double longitude,Double latitude) {		
		Bson filter = Filters.eq("userId", userId);
		Bson toUpdate = new Document("loc",new Document("lon",longitude).append("lat", latitude))
				.append("isActive", 1).append("lastUpdatedTime", ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT));
		//Bson toInsert = new Document("isActive",1);
		Bson toInsert = new Document("userId",userId);
		UpdateOptions options = new UpdateOptions().upsert(true);
		mongoCollection.updateOne(filter, new Document("$set",toUpdate).append("$setOnInsert",toInsert),options);
	}
	
	/*public static void main(String args[]) {
		//getNearByDrivers1((Double)80.052893 ,(Double)12.918186);
		getNearByActiveDrivers((Double)80.052893 ,(Double)12.918186);
		Double  lat = new Double("9.9252007");
		Double lon = new Double("78.1197754");
		//insertDriverSession("30",d, (Double)40.7143528);
		//updateDriverStatus("3",0);
		//updateDriverStatus("15",lon,lat);
	}*/
}
