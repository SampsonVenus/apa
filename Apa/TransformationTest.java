package com.smc.tech.poc.dao.transformers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import com.smc.tech.poc.config.AppConfig;
import com.smc.tech.poc.config.DataSourceTestConfig;
import com.smc.tech.poc.config.DefaultConfiguration;
import com.smc.tech.poc.data.service.transformations.StandardsEnum;
import com.smc.tech.poc.data.service.transformations.TransformationData;
import com.smc.tech.poc.data.service.transformations.TransformationRecord;
import com.smc.tech.poc.data.service.transformations.ValueTransformationDao;
import com.smc.tech.poc.data.service.transformations.ValueTransformationService;



@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {DefaultConfiguration.class, AppConfig.class ,DataSourceTestConfig.class}, loader=AnnotationConfigWebContextLoader.class)
@ActiveProfiles("test")
public class TransformationTest {
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private ValueTransformationDao valueTransformationDao; 
	
	@Autowired
	private ValueTransformationService valueTransformationService;
	
	@Test
	@DirtiesContext
	public void should_returnCorrectTransformations_when_handlingTypes() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>smcValueIn =  Arrays.asList("PAT", "TBN", "SKD", "CBY");
		List<String>carrierValueExpected = Arrays.asList("PLT", "TOT", "SKD", "CBY");
		
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> handlingTypes = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
		
		//populate a TransformationRecord
		tr.setMappingDirection("biDirectional");
		tr.setSmcValue("PAT");
		tr.setSmcDescription("Pallet");
		tr.setCarrierValue("PLT");
		
		handlingTypes.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("biDirectional");
		tr1.setSmcValue("TBN");
		tr1.setSmcDescription("Tote");
		tr1.setCarrierValue("TOT");
		
		handlingTypes.add(tr1); 
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setHandlingTypes(handlingTypes);   // ArrayList of TransformationRecord

		for (int i = 0; i < smcValueIn.size(); i++) {
			String carrierValueActual = null;
			
			// doReturn(resulstIWant).when(myClassSpy).method1();
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			carrierValueActual = valueTransformationService.getCarrierValueFor(carrierCode, smcValueIn.get(i),
					StandardsEnum.HANDLING_TYPES, loggerTrackingData);

			assertEquals(carrierValueExpected.get(i), carrierValueActual);
			
		}

		handlingTypes.clear();
		transformationData.setHandlingTypes(handlingTypes);
	}
	
	@Test
	@DirtiesContext
	public void should_returnCorrectTransformations_when_packagingList() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>smcValueIn =  Arrays.asList("BDL", "CTN");
		List<String>carrierValueExpected = Arrays.asList("BDL", "CRT");
		
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> packagingList = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
		
		//populate a TransformationRecord
		tr.setMappingDirection("toCarrier");
		tr.setSmcValue("BDL");
		tr.setSmcDescription("Carton");
		tr.setCarrierValue("BDL");
		
		packagingList.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("toCarrier");
		tr1.setSmcValue("CTN");
		tr1.setSmcDescription("Carton");
		tr1.setCarrierValue("CRT");
		
		packagingList.add(tr1); 
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setPackagingList(packagingList);   // ArrayList of TransformationRecord

	
		for (int i = 0; i < smcValueIn.size(); i++) {
			
			String carrierValueActual = null;
			
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			carrierValueActual = valueTransformationService.getCarrierValueFor(carrierCode, smcValueIn.get(i),
					StandardsEnum.PACKAGEING_LIST, loggerTrackingData);

			assertEquals(carrierValueExpected.get(i), carrierValueActual);
			
		}

	}
	
	
	@Test
	@DirtiesContext
	public void should_returnCorrectTransformations_when_freightClasses() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>smcValueIn =  Arrays.asList("50", "55");
		List<String>carrierValueExpected = Arrays.asList("50", "55");
		
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> freightClasses = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
		
		//populate a TransformationRecord
		tr.setMappingDirection("biDirectional");
		tr.setSmcValue("50");
		tr.setSmcDescription("Carton");
		tr.setCarrierValue("50");
		
		freightClasses.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("biDirectional");
		tr1.setSmcValue("55");
		tr1.setSmcDescription("Carton");
		tr1.setCarrierValue("55");
		
		freightClasses.add(tr1); 
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setPackagingList(freightClasses);   // ArrayList of TransformationRecord

	
		for (int i = 0; i < smcValueIn.size(); i++) {
			
			String carrierValueActual = null;
			
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			carrierValueActual = valueTransformationService.getCarrierValueFor(carrierCode, smcValueIn.get(i),
					StandardsEnum.FREIGHT_CLASSES, loggerTrackingData);

			assertEquals(carrierValueExpected.get(i), carrierValueActual);
			
		}

	}
	
	@Test
	@DirtiesContext
	public void should_returnCorrectTransformations_when_states() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>smcValueIn =  Arrays.asList("AL", "KY");
		List<String>carrierValueExpected = Arrays.asList("AL", "KY");
		
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> states = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
		
		//populate a TransformationRecord
		tr.setMappingDirection("biDirectional");
		tr.setSmcValue("50");
		tr.setSmcDescription("Carton");
		tr.setCarrierValue("50");
		
		states.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("biDirectional");
		tr1.setSmcValue("55");
		tr1.setSmcDescription("Carton");
		tr1.setCarrierValue("55");
		
		states.add(tr1); 
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setPackagingList(states);   // ArrayList of TransformationRecord

	
		for (int i = 0; i < smcValueIn.size(); i++) {
			
			String carrierValueActual = null;
			
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			carrierValueActual = valueTransformationService.getCarrierValueFor(carrierCode, smcValueIn.get(i),
					StandardsEnum.STATES, loggerTrackingData);

			assertEquals(carrierValueExpected.get(i), carrierValueActual);
			
		}

	}
	
	@Test
	@DirtiesContext
	public void should_returnCorrectTransformations_when_paymentTerms() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>smcValueIn =  Arrays.asList("Prepaid", "Third Party","Collect");
		List<String>carrierValueExpected = Arrays.asList("10", "30", "40");
		
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> paymentTerms = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
		TransformationRecord tr2 = new TransformationRecord();
		//populate a TransformationRecord
		tr.setMappingDirection("biDirectional");
		tr.setSmcValue("Prepaid");
		tr.setCarrierValue("10");
		
		paymentTerms.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("biDirectional");
		tr1.setSmcValue("Third Party");
		tr1.setCarrierValue("30");
		
		paymentTerms.add(tr1); 
		
		// populate another TransformationRecord
		tr2.setMappingDirection("biDirectional");
		tr2.setSmcValue("Collect");
		tr2.setCarrierValue("40");
		
		paymentTerms.add(tr2); 
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setPaymentTerms(paymentTerms);   // ArrayList of TransformationRecord

	
		for (int i = 0; i < smcValueIn.size(); i++) {
			
			String carrierValueActual = null;
			
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			carrierValueActual = valueTransformationService.getCarrierValueFor(carrierCode, smcValueIn.get(i),
					StandardsEnum.PAYMENT_TERMS, loggerTrackingData);

			assertEquals(carrierValueExpected.get(i), carrierValueActual);
			
		}

	}
	
	
	
	@Test
	@DirtiesContext
	public void should_returnCorrectTransformations_when_payer() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>smcValueIn =  Arrays.asList("Shipper", "Consignee");
		List<String>carrierValueExpected = Arrays.asList("Shipper", "Consignee");
		
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> payer = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
	
		//populate a TransformationRecord
		tr.setMappingDirection("biDirectional");
		tr.setSmcValue("Shipper");
		tr.setCarrierValue("Shipper");
		
		payer.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("biDirectional");
		tr1.setSmcValue("Consignee");
		tr1.setCarrierValue("Consignee");
		
		payer.add(tr1); 
		
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setPayer(payer);   // ArrayList of TransformationRecord

	
		for (int i = 0; i < smcValueIn.size(); i++) {
			
			String carrierValueActual = null;
			
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			carrierValueActual = valueTransformationService.getCarrierValueFor(carrierCode, smcValueIn.get(i),
					StandardsEnum.PAYER, loggerTrackingData);

			assertEquals(carrierValueExpected.get(i), carrierValueActual);
			
		}

	}
	
	@Test
	@DirtiesContext
	public void should_returnCorrectTransformations_when_country() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>smcValueIn =  Arrays.asList("USA", "MEX", "CAN");
		List<String>carrierValueExpected = Arrays.asList("US", "MX", "CA");
		
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> country = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
		TransformationRecord tr2 = new TransformationRecord();
		//populate a TransformationRecord
		tr.setMappingDirection("biDirectional");
		tr.setSmcValue("USA");
		tr1.setSmcDescription("The United States of America");
		tr.setCarrierValue("US");
		
		country.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("biDirectional");
		tr1.setSmcValue("MEX");
		tr1.setSmcDescription("Ciudad de Mexico");
		tr1.setCarrierValue("MX");
		
		country.add(tr1); 
		
		// populate another TransformationRecord
		tr2.setMappingDirection("biDirectional");
		tr2.setSmcValue("CAN");
		tr2.setSmcDescription("Canada, eh?");
		tr2.setCarrierValue("CA");

		country.add(tr2);
		
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setCountry(country);   // ArrayList of TransformationRecord

	
		for (int i = 0; i < smcValueIn.size(); i++) {
			
			String carrierValueActual = null;
			
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			carrierValueActual = valueTransformationService.getCarrierValueFor(carrierCode, smcValueIn.get(i),
					StandardsEnum.COUNTRY, loggerTrackingData);

			assertEquals(carrierValueExpected.get(i), carrierValueActual);
			
		}

	}
	
	// work but not required @Test
	@Test
	@DirtiesContext
	public void should_returnCorrectTransformations_when_accessorials() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>smcValueIn =  Arrays.asList("CNSTD", "HAZ", "LFTD", "REP");
		List<String>carrierValueExpected = Arrays.asList("LIM_ACC_PU_DEL", "HAZMAT", "LIFTGATE","RESI_PU_DEL");
		
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> accessorials = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
		TransformationRecord tr2 = new TransformationRecord();
		TransformationRecord tr3 = new TransformationRecord();
		
		//populate a TransformationRecord
		tr.setMappingDirection("biDirectional");
		tr.setSmcValue("CNSTD");
		tr1.setSmcDescription("Construction site delivery");
		tr.setCarrierValue("LIM_ACC_PU_DEL");
		
		accessorials.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("biDirectional");
		tr1.setSmcValue("HAZ");
		tr1.setSmcDescription("Hazardous material");
		tr1.setCarrierValue("HAZMAT");
		
		accessorials.add(tr1); 
		
		// populate another TransformationRecord
		tr2.setMappingDirection("biDirectional");
		tr2.setSmcValue("LFTD");
		tr2.setSmcDescription("Lift gate required at delivery");
		tr2.setCarrierValue("LIFTGATE");

		accessorials.add(tr2);
		
		// populate another TransformationRecord
		tr3.setMappingDirection("biDirectional");
		tr3.setSmcValue("REP");
		tr3.setSmcDescription("Residential delivery");
		tr3.setCarrierValue("RESI_PU_DEL");

		accessorials.add(tr3);
		
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setAccessorials(accessorials);    // ArrayList of TransformationRecord

	
		for (int i = 0; i < smcValueIn.size(); i++) {
			
			String carrierValueActual = null;
			
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			carrierValueActual = valueTransformationService.getCarrierValueFor(carrierCode, smcValueIn.get(i),
					StandardsEnum.ACCESSORIALS, loggerTrackingData);

			assertEquals(carrierValueExpected.get(i), carrierValueActual);
			
		}

	}
	
//	@Test
//	@DirtiesContext
	//TODO: FUTURE revisit this when the story requires it
	public void should_returnCorrectTransformations_when_errorCodes() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>carrierValueIn = Arrays.asList("250002", "250003", "250004");
		List<String>smcValueExpected = Arrays.asList("10000089", "10000089","10000089");
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> errorCodes = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
		TransformationRecord tr2 = new TransformationRecord();
		
		//populate a TransformationRecord
		tr.setMappingDirection("fromCarrier");
		tr.setSmcValue("10000089");
		tr.setCarrierValue("250002");
		tr.setCarrierDescription("Invalid Authentication Information.");
		
		errorCodes.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("fromCarrier");
		tr1.setSmcValue("10000089");
		tr1.setCarrierValue("250003");
		tr1.setCarrierDescription("Invalid Access License Number");
		
		errorCodes.add(tr1); 
		
		// populate another TransformationRecord
		tr2.setMappingDirection("fromCarrier");
		tr2.setSmcValue("10000089");
		tr2.setCarrierValue("250004");
		tr2.setCarrierDescription("Incorrect UserId or Password");
		
		errorCodes.add(tr2); 
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setErrorCodes(errorCodes);   // ArrayList of TransformationRecord

	
		for (int i = 0; i < carrierValueIn.size(); i++) {
			
			String smcValueActual = null;
			
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			smcValueActual = valueTransformationService.getSmcValueFor(carrierCode, carrierValueIn.get(i),
					StandardsEnum.ERROR_CODES, loggerTrackingData);

			assertEquals(smcValueExpected.get(i), smcValueActual);
			
		}

	}
	
	@Test
	@DirtiesContext
	public void should_returnCorrect_Smc3_transformations_FromCarrier_when_handlingTypes() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>carrierValueIn = Arrays.asList("PLT", "TOT");
		List<String>smcValueExpected =  Arrays.asList("PAT", "TBN");
		
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> handlingTypes = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
		
		//populate a TransformationRecord
		tr.setMappingDirection("biDirectional");
		tr.setSmcValue("PAT");
		tr.setSmcDescription("Pallet");
		tr.setCarrierValue("PLT");
		
		handlingTypes.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("biDirectional");
		tr1.setSmcValue("TBN");
		tr1.setSmcDescription("Tote");
		tr1.setCarrierValue("TOT");
		
		handlingTypes.add(tr1); 
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setHandlingTypes(handlingTypes);   // ArrayList of TransformationRecord

		for (int i = 0; i < carrierValueIn.size(); i++) {
			String smcValueActual = null;
			
			// doReturn(resulstIWant).when(myClassSpy).method1();
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			smcValueActual = valueTransformationService.getSmcValueFor(carrierCode, carrierValueIn.get(i),
					StandardsEnum.HANDLING_TYPES, loggerTrackingData);

			assertEquals(smcValueExpected.get(i), smcValueActual);
			
		}

	}
	
//	@Test
//	@DirtiesContext
	//TODO: FUTURE revisit this when the story requires it
	public void should_returnCorrect_Smc3_transformations_FromCarrier_when_packagingList() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>carrierValueIn = Arrays.asList("BDL", "CRT");
		List<String>smcValueExpected =  Arrays.asList("BDL", "CTN");
		
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> packagingList = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
		
		//populate a TransformationRecord
		tr.setMappingDirection("toCarrier");
		tr.setSmcValue("BDL");
		tr.setSmcDescription("Carton");
		tr.setCarrierValue("BDL");
		
		packagingList.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("toCarrier");
		tr1.setSmcValue("CTN");
		tr1.setSmcDescription("Carton");
		tr1.setCarrierValue("CRT");
		
		packagingList.add(tr1); 
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setPackagingList(packagingList);   // ArrayList of TransformationRecord

	
		for (int i = 0; i < carrierValueIn.size(); i++) {
			
			String smcValueActual = null;
			
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			smcValueActual = valueTransformationService.getSmcValueFor(carrierCode, carrierValueIn.get(i),
					StandardsEnum.PACKAGEING_LIST, loggerTrackingData);

			assertEquals(smcValueExpected.get(i), smcValueActual);
			
		}

	}
	
// Work but not required	@Test
	@Test
	@DirtiesContext
	public void should_returnCorrect_Smc3_transformations_FromCarrier_when_accessorials() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>carrierValueIn = Arrays.asList("LIM_ACC_PU_DEL", "HAZMAT", "LIFTGATE","RESI_PU_DEL");
		List<String>smcValueExpected =  Arrays.asList("CNSTD", "HAZ", "LFTD", "REP");
		
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> accessorials = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
		TransformationRecord tr2 = new TransformationRecord();
		TransformationRecord tr3 = new TransformationRecord();
		
		//populate a TransformationRecord
		tr.setMappingDirection("biDirectional");
		tr.setSmcValue("CNSTD");
		tr1.setSmcDescription("Construction site delivery");
		tr.setCarrierValue("LIM_ACC_PU_DEL");
		
		accessorials.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("biDirectional");
		tr1.setSmcValue("HAZ");
		tr1.setSmcDescription("Hazardous material");
		tr1.setCarrierValue("HAZMAT");
		
		accessorials.add(tr1); 
		
		// populate another TransformationRecord
		tr2.setMappingDirection("biDirectional");
		tr2.setSmcValue("LFTD");
		tr2.setSmcDescription("Lift gate required at delivery");
		tr2.setCarrierValue("LIFTGATE");

		accessorials.add(tr2);
		
		// populate another TransformationRecord
		tr3.setMappingDirection("biDirectional");
		tr3.setSmcValue("REP");
		tr3.setSmcDescription("Residential delivery");
		tr3.setCarrierValue("RESI_PU_DEL");

		accessorials.add(tr3);
		
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setAccessorials(accessorials);    // ArrayList of TransformationRecord

	
		for (int i = 0; i < carrierValueIn.size(); i++) {
			
			String smcValueActual = null;
			
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			smcValueActual = valueTransformationService.getSmcValueFor(carrierCode, carrierValueIn.get(i),
					StandardsEnum.ACCESSORIALS, loggerTrackingData);

			assertEquals(smcValueExpected.get(i), smcValueActual);
			
		}

	}

	//@Test
	//TODO:  FUTURE revisit this when the story requires it
	public void should_returnCorrect_Smc3_transformations_FromCarrier_when_freightClasses() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>smcValueIn =  Arrays.asList("50", "55");
		List<String>carrierValueExpected = Arrays.asList("50", "55");
		
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> freightClasses = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
		
		//populate a TransformationRecord
		tr.setMappingDirection("biDirectional");
		tr.setSmcValue("50");
		tr.setSmcDescription("Carton");
		tr.setCarrierValue("50");
		
		freightClasses.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("biDirectional");
		tr1.setSmcValue("55");
		tr1.setSmcDescription("Carton");
		tr1.setCarrierValue("55");
		
		freightClasses.add(tr1); 
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setPackagingList(freightClasses);   // ArrayList of TransformationRecord

	
		for (int i = 0; i < smcValueIn.size(); i++) {
			
			String carrierValueActual = null;
			
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			carrierValueActual = valueTransformationService.getSmcValueFor(carrierCode, smcValueIn.get(i),
					StandardsEnum.FREIGHT_CLASSES, loggerTrackingData);

			assertEquals(carrierValueExpected.get(i), carrierValueActual);
			
		}

	}

	@Test
	@DirtiesContext
	public void should_returnCorrect_Smc3_transformations_FromCarrier_when_paymentTerms() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>carrierValueIn = Arrays.asList("10", "30", "40");
		List<String>smcValueExpected =  Arrays.asList("Prepaid", "Third Party","Collect");
		
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> paymentTerms = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
		TransformationRecord tr2 = new TransformationRecord();
		//populate a TransformationRecord
		tr.setMappingDirection("biDirectional");
		tr.setSmcValue("Prepaid");
		tr.setCarrierValue("10");
		
		paymentTerms.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("biDirectional");
		tr1.setSmcValue("Third Party");
		tr1.setCarrierValue("30");
		
		paymentTerms.add(tr1); 
		
		// populate another TransformationRecord
		tr2.setMappingDirection("biDirectional");
		tr2.setSmcValue("Collect");
		tr2.setCarrierValue("40");
		
		paymentTerms.add(tr2); 
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setPaymentTerms(paymentTerms);   // ArrayList of TransformationRecord

	
		for (int i = 0; i < carrierValueIn.size(); i++) {
			
			String smcValueActual = null;
			
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			smcValueActual = valueTransformationService.getSmcValueFor(carrierCode, carrierValueIn.get(i),
					StandardsEnum.PAYMENT_TERMS, loggerTrackingData);

			assertEquals(smcValueExpected.get(i), smcValueActual);
			
		}

	}
	
//	@Test
//	@DirtiesContext
	//TODO: FUTURE revisit this when the story requires it
	public void should_returnCorrect_Smc3_transformations_FromCarrier_when_states() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>smcValueIn =  Arrays.asList("AL", "KY");
		List<String>carrierValueExpected = Arrays.asList("AL", "KY");
		
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> states = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
		
		//populate a TransformationRecord
		tr.setMappingDirection("biDirectional");
		tr.setSmcValue("50");
		tr.setSmcDescription("Carton");
		tr.setCarrierValue("50");
		
		states.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("biDirectional");
		tr1.setSmcValue("55");
		tr1.setSmcDescription("Carton");
		tr1.setCarrierValue("55");
		
		states.add(tr1); 
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setPackagingList(states);   // ArrayList of TransformationRecord

	
		for (int i = 0; i < smcValueIn.size(); i++) {
			
			String carrierValueActual = null;
			
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			carrierValueActual = valueTransformationService.getSmcValueFor(carrierCode, smcValueIn.get(i),
					StandardsEnum.STATES, loggerTrackingData);

			assertEquals(carrierValueExpected.get(i), carrierValueActual);
			
		}

	}
    /*
     * Both smc and carrier values are the same, so only the 
     * function call need to be change -- other than that
     * this is the same code as the smc to carrier
     */
	@Test
	@DirtiesContext
	public void should_returnCorrect_Smc3_transformations_FromCarrier_when_payer() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>carrierValueExpected = Arrays.asList("Shipper", "Consignee");
		
		List<String>smcValueIn =  Arrays.asList("Shipper", "Consignee");
	//	List<String>carrierValueExpected = Arrays.asList("Shipper", "Consignee");
		
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> payer = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
	
		//populate a TransformationRecord
		tr.setMappingDirection("biDirectional");
		tr.setSmcValue("Shipper");
		tr.setCarrierValue("Shipper");
		
		payer.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("biDirectional");
		tr1.setSmcValue("Consignee");
		tr1.setCarrierValue("Consignee");
		
		payer.add(tr1); 
		
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setPayer(payer);   // ArrayList of TransformationRecord

	
		for (int i = 0; i < smcValueIn.size(); i++) {
			
			String carrierValueActual = null;
			
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			carrierValueActual = valueTransformationService.getCarrierValueFor(carrierCode, smcValueIn.get(i),
					StandardsEnum.PAYER, loggerTrackingData);

			assertEquals(carrierValueExpected.get(i), carrierValueActual);
			
		}

	}

	@Test
	@DirtiesContext
	public void should_returnCorrect_Smc3_transformations_FromCarrier_when_country() {
		String carrierCode = "upgf";
		String loggerTrackingData = "transactionId=JUnit test";
		
		List<String>carrierValueIn = Arrays.asList("US", "MX", "CA");
		List<String>smcValueExpected =  Arrays.asList("USA", "MEX", "CAN");
	
		TransformationData transformationData = new TransformationData();	
		List<TransformationRecord> country = new ArrayList<>();
		TransformationRecord tr = new TransformationRecord();
		TransformationRecord tr1 = new TransformationRecord();
		TransformationRecord tr2 = new TransformationRecord();
		//populate a TransformationRecord
		tr.setMappingDirection("biDirectional");
		tr.setSmcValue("USA");
		tr1.setSmcDescription("The United States of America");
		tr.setCarrierValue("US");
		
		country.add(tr);  
		
		// populate another TransformationRecord
		tr1.setMappingDirection("biDirectional");
		tr1.setSmcValue("MEX");
		tr1.setSmcDescription("Ciudad de Mexico");
		tr1.setCarrierValue("MX");
		
		country.add(tr1); 
		
		// populate another TransformationRecord
		tr2.setMappingDirection("biDirectional");
		tr2.setSmcValue("CAN");
		tr2.setSmcDescription("Canada, eh?");
		tr2.setCarrierValue("CA");

		country.add(tr2);
		
		
		// Add a Transformation Record to TransformationData
		transformationData.setCarrierCode("upgf");
		transformationData.setService("CONTRACT-PRICING");
		transformationData.setCountry(country);   // ArrayList of TransformationRecord

	
		for (int i = 0; i < carrierValueIn.size(); i++) {
			
			String smcValueActual = null;
			
			doReturn(transformationData).when(valueTransformationDao).findValueTransformationData(carrierCode,
					loggerTrackingData);
			smcValueActual = valueTransformationService.getSmcValueFor(carrierCode, carrierValueIn.get(i),
					StandardsEnum.COUNTRY, loggerTrackingData);

			assertEquals(smcValueExpected.get(i), smcValueActual);
			
		}

	}
}
