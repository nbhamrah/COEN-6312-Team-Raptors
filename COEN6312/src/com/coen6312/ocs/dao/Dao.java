package com.coen6312.ocs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.coen6312.ocs.bean.AppointmentBean;
import com.coen6312.ocs.bean.CredentialsBean;
import com.coen6312.ocs.bean.DoctorBean;
import com.coen6312.ocs.bean.PatientBean;
import com.coen6312.ocs.bean.ProfileBean;
import com.coen6312.ocs.bean.ScheduleBean;
import com.coen6312.ocs.util.DBUtil;

public class Dao {
	public String generateCandidateId(String name) throws ClassNotFoundException, SQLException{
		String generatedid="";
		int num=0;
		Connection connection3;
		connection3=DBUtil.getConnection();
		String query="select OCS_SEQ_DOCTORID.nextVal from dual";
		PreparedStatement pstmt3=connection3.prepareStatement(query);
		ResultSet rs=pstmt3.executeQuery();
		while(rs.next()){
			num=rs.getInt(1);
		}
		generatedid=name.substring(0,2).toUpperCase() + num;
		return generatedid;
	}
	
	public String generateUserIdByName(String name) throws ClassNotFoundException, SQLException
	{
		String generatedid="";
		PreparedStatement pmt=DBUtil.getConnection().prepareStatement("select OCS_SEQ_USERID.nextval from dual");
		ResultSet rs=pmt.executeQuery();
		if(rs.next())
		{
			
			generatedid=name.substring(0, 2).toUpperCase()+rs.getString(1);
		}
	return generatedid;
	}
	public String generateappointmentidbypatientiddoctorid(String date) throws ClassNotFoundException, SQLException{
		String generateid="";
		int num=0;
		Connection connection;
		connection=DBUtil.getConnection();
		String query="select OCS_SEQ_APPOINTMENTID.nextVal from dual";
		PreparedStatement pstmt3=connection.prepareStatement(query);
		ResultSet rs=pstmt3.executeQuery();
		while(rs.next()){
			num=rs.getInt(1);
		}
		generateid=date.substring(3,5).toUpperCase()+ date.substring(0,2).toUpperCase() + num;
		return generateid;
	}
	
	/*public String generateCandidateId1(Class obj){
		return null;
		
	}*/
	public ArrayList<DoctorBean> findAll() throws ClassNotFoundException, SQLException{
		ArrayList<DoctorBean> list=new ArrayList<DoctorBean>();
		Connection connection;
		connection=DBUtil.getConnection();
		String query="select * from OCS_TBL_Doctor";
		PreparedStatement pstmt=connection.prepareStatement(query);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			DoctorBean doctorBean=new DoctorBean();
			doctorBean.setDoctorID(rs.getString(1));
			doctorBean.setDoctorName(rs.getString(2));
			doctorBean.setDateOfBirth(rs.getDate(3));
			doctorBean.setDateOfJoining(rs.getDate(4));
			doctorBean.setGender(rs.getString(5));
			doctorBean.setQualification(rs.getString(6));
			doctorBean.setSpecialization(rs.getString(7));
			doctorBean.setYearsOfExperience(rs.getInt(8));
			doctorBean.setStreet(rs.getString(9));
			doctorBean.setLocation(rs.getString(10));
			doctorBean.setCity(rs.getString(11));
			doctorBean.setState(rs.getString(12));
			doctorBean.setPincode(rs.getString(13));
			doctorBean.setContactNumber(rs.getString(14));
			doctorBean.setEmailID(rs.getString(15));
			list.add(doctorBean);
		}
		return list;
	}
	
	public ArrayList<AppointmentBean> findAllappointments() throws ClassNotFoundException, SQLException{
		ArrayList<AppointmentBean> list=new ArrayList<AppointmentBean>();
		Connection connection;
		connection=DBUtil.getConnection();
		String query="select * from OCS_TBL_Appointments where STATUS='PENDING'";
		PreparedStatement pstmt=connection.prepareStatement(query);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			AppointmentBean appointmentBean=new AppointmentBean();
			appointmentBean.setAppointmentID((rs.getString(1)));
			appointmentBean.setDoctorID(rs.getString(2));
			appointmentBean.setPatientID((rs.getString(3)));
			appointmentBean.setAppointmentDate((rs.getDate(4)));
			appointmentBean.setAppointmentTime((rs.getString(5)));
			//appointmentBean.setS(rs.getString(6));
			
			list.add(appointmentBean);
		}
		return list;
	}
	
	
	public CredentialsBean findUserByUserId(String userId) throws ClassNotFoundException, SQLException{
		Connection connection5;
		connection5=DBUtil.getConnection();
		CredentialsBean credentialsBean=new CredentialsBean();
		String query6="select * from OCS_TBL_User_Credentials where USERID=?";
		PreparedStatement pstmt6=connection5.prepareStatement(query6);
		pstmt6.setString(1,userId);
		ResultSet rs6=pstmt6.executeQuery();
		if(rs6.next()){
			credentialsBean.setUserID(userId);
			credentialsBean.setLoginStatus(rs6.getInt("LOGINSTATUS"));
			credentialsBean.setPassword(rs6.getString("PASSWORD"));
			credentialsBean.setUserType(rs6.getString("USERTYPE"));
			
			return credentialsBean;
		}else
		{
		return null;
		}
	}
	public boolean changePassword(CredentialsBean bean,String newpswrd) throws ClassNotFoundException, SQLException
	{
		boolean flag=false;
		Connection connection6;
		connection6=DBUtil.getConnection();
		String query7="update OCS_TBL_User_Credentials set PASSWORD=? where USERID=?";
		PreparedStatement pstmt7=connection6.prepareStatement(query7);
		pstmt7.setString(1,newpswrd);
		pstmt7.setString(2,bean.getUserID());
		int row=pstmt7.executeUpdate();
		if(row!=0)
		{
			flag=true;
		}
		return flag;
	}
	public String generateAdminId1(String name) throws ClassNotFoundException, SQLException
	{
		String generatedid="";
		PreparedStatement pmt=DBUtil.getConnection().prepareStatement("select OCS_SEQ_ADMINID.nextval from dual");
		ResultSet rs=pmt.executeQuery();
		if(rs.next())
		{
			
			generatedid=name.substring(0, 2).toUpperCase()+rs.getString(1);
		}
		
		return generatedid;
		
	}
	public boolean setAdminPassWord(CredentialsBean credentialsBean) throws ClassNotFoundException, SQLException
	{
		boolean result=false;
		
		PreparedStatement pmt=DBUtil.getConnection().prepareStatement("update OCS_TBL_User_Credentials set USERID=?,PASSWORD=? where USERID='admin' and PASSWORD='admin'");
		pmt.setString(1, credentialsBean.getUserID());
		pmt.setString(2,credentialsBean.getPassword());
		int temp=pmt.executeUpdate();
		if(temp>0)
		{
			result=true;
		}
		return result;
	}
	public boolean deleteDoctorByID(String doctorID) throws ClassNotFoundException, SQLException
	{
		boolean result=false;
		PreparedStatement pmt=DBUtil.getConnection().prepareStatement("delete from OCS_TBL_Doctor where DOCTORID=?");
		pmt.setString(1, doctorID);
		int temp=pmt.executeUpdate();
		if(temp>0)
		{
			result=true;
		}
		return result;
		
	}
	
	public boolean addPatientDetails(ProfileBean profilebean) throws ClassNotFoundException, SQLException
	{
		boolean result=false;
		PreparedStatement pmt=DBUtil.getConnection().prepareStatement("insert into OCS_TBL_User_Profile values(?,?,?,?,?,?,?,?,?,?,?,?)");
		pmt.setString(1, profilebean.getUserId());
		System.out.println(profilebean.getUserId());
		pmt.setString(2, profilebean.getFirstName());
		pmt.setString(3, profilebean.getLastName());
		java.sql.Date sqldate=new java.sql.Date(profilebean.getDateOfBirth().getTime());
		pmt.setDate(4, sqldate);
		pmt.setString(5, profilebean.getGender());
		pmt.setString(6, profilebean.getStreet());
		pmt.setString(7, profilebean.getLocation());
		pmt.setString(8, profilebean.getCity());
		pmt.setString(9, profilebean.getState());
		pmt.setString(10, profilebean.getPincode());
		pmt.setString(11, profilebean.getMobileNo());
		pmt.setString(12, profilebean.getEmailID());
		int temp=pmt.executeUpdate();
		if(temp>0)
		{
			result=true;
		}
		return result;	
	}
	
	public boolean addUserByUserId(CredentialsBean credentialsBean) throws ClassNotFoundException, SQLException
	{
		boolean result=false;
		PreparedStatement pmt=DBUtil.getConnection().prepareStatement("insert into OCS_TBL_User_Credentials values(?,?,'P','0')");
		pmt.setString(1, credentialsBean.getUserID());
		pmt.setString(2, credentialsBean.getPassword());
		int temp=pmt.executeUpdate();
		if(temp>0)
		{
			result=true;
		}
		return result;
	}
	public boolean findDoctorByDoctorId(DoctorBean dBean) throws ClassNotFoundException, SQLException
	{
		
		
		PreparedStatement pstmt1=DBUtil.getConnection().prepareStatement("select * from OCS_TBL_Doctor where DOCTORID=?");
		pstmt1.setString(1,dBean.getDoctorID());
		ResultSet rs1=pstmt1.executeQuery();
		
		while(rs1.next())
		{
			//System.out.println("1");
			//DoctorBean doctorBean=new DoctorBean();
			dBean.setDoctorID(rs1.getString(1));
			dBean.setDoctorName(rs1.getString(2));
			dBean.setDateOfBirth(rs1.getDate(3));
			dBean.setDateOfJoining(rs1.getDate(4));
			dBean.setGender(rs1.getString(5));
			dBean.setQualification(rs1.getString(6));
			dBean.setSpecialization(rs1.getString(7));
			dBean.setYearsOfExperience(rs1.getInt(8));
			dBean.setStreet(rs1.getString(9));
			dBean.setLocation(rs1.getString(10));
			dBean.setCity(rs1.getString(11));
			dBean.setState(rs1.getString(12));
			dBean.setPincode(rs1.getString(13));
			dBean.setContactNumber(rs1.getString(14));
			dBean.setEmailID(rs1.getString(15));
			return true;
		}
		
		return false;
		
	}
	public String updateDoctorDetails(DoctorBean dBean,String doctorId) throws ClassNotFoundException, SQLException
	{
		java.sql.Date dob=new java.sql.Date(dBean.getDateOfBirth().getTime());
		java.sql.Date doj=new java.sql.Date(dBean.getDateOfJoining().getTime());
		
		PreparedStatement ps1=DBUtil.getConnection().prepareStatement("update OCS_TBL_Doctor set DOCTORNAME=?,DATEOFBIRTH=?,DATEOFJOINING=?,GENDER=?,QUALIFICATION=?,SPECIALIZATION=?,YEARSOFEXPERIENCE=?,STREET=?,LOCATION=?,CITY=?,STATE=?,PINCODE=?,CONTACTNUMBER=?,EMAILID=? where DOCTORID=?");
		ps1.setString(1,dBean.getDoctorName());
		ps1.setDate(2,dob);
		ps1.setDate(3,doj);
		ps1.setString(4,dBean.getGender());
		ps1.setString(5,dBean.getQualification());
		ps1.setString(6,dBean.getSpecialization());
		ps1.setInt(7,dBean.getYearsOfExperience());
		ps1.setString(8,dBean.getStreet());
		ps1.setString(9,dBean.getLocation());
		ps1.setString(10,dBean.getCity());
		ps1.setString(11,dBean.getState());
		ps1.setString(12,dBean.getPincode());
		ps1.setString(13,dBean.getContactNumber());
		ps1.setString(14,dBean.getEmailID());
		ps1.setString(15,doctorId);
		int ii=ps1.executeUpdate();
		if(ii!=0)
		{
			return "SUCCESSFULLY UPDATED";
		}
		
		return "UNSUCCESSFUL";
			
	}
	public Map<PatientBean,AppointmentBean> findPatientsByDate(Date appointmentDate) throws ClassNotFoundException, SQLException{
		Connection connection7;
		connection7=DBUtil.getConnection();
		Map<PatientBean,AppointmentBean> mapdetails=new HashMap<PatientBean,AppointmentBean>();
		
		AppointmentBean appointmentBean=new AppointmentBean();
		String query="select * from OCS_TBL_Appointments natural join OCS_TBL_Patient where APPOINTMENT_DATE=?";
		PreparedStatement pstmt8=connection7.prepareStatement(query);
		pstmt8.setDate(1,new java.sql.Date(appointmentDate.getTime()));
		//System.out.println(new java.sql.Date(appointmentDate.getTime()));
		ResultSet rs=pstmt8.executeQuery();
		while(rs.next()){
			PatientBean patientBean=new PatientBean();
			patientBean.setPatientID(rs.getString(1));
			patientBean.setAppointmentDate(rs.getDate(2));
			appointmentBean.setAppointmentID(rs.getString(3));
			appointmentBean.setDoctorID(rs.getString(4));
			appointmentBean.setAppointmentTime(rs.getString(5));
			patientBean.setUserID(rs.getString(7));
			patientBean.setAilmentType(rs.getString(8));
			patientBean.setAilmentDetails(rs.getString(9));
			patientBean.setDiagnosisHistory(rs.getString(10));
			mapdetails.put(patientBean,appointmentBean);
		}
		return mapdetails;
		
	}
	public String getPatientId(String UserId) throws ClassNotFoundException, SQLException
	{
		String result="";
		System.out.println("kritika1"+UserId);
		Connection connection5;
		connection5=DBUtil.getConnection();
		CredentialsBean credentialsBean=new CredentialsBean();
		String query6="select PATIENTID from OCS_TBL_PATIENT where USERID=?";
		PreparedStatement pstmt6=connection5.prepareStatement(query6);
		pstmt6.setString(1,UserId);
		ResultSet rs6=pstmt6.executeQuery();
		if(rs6.next()){
			//AppointmentBean appbean=new AppointmentBean();
			result=rs6.getString(1);
			System.out.println("kritika"+result);
			//appbean.setAppointmentID(result);
			
		}		
		return result;
		
	}
	
	
	public String getPatientAppointmentId(String PatientId) throws ClassNotFoundException, SQLException
	{
		String result="";
		System.out.println("kritika2");
		Connection connection5;
		connection5=DBUtil.getConnection();
		CredentialsBean credentialsBean=new CredentialsBean();
		String query6="select APPOINTMENTID from OCS_TBL_APPOINTMENTS where PATIENTID=?";
		PreparedStatement pstmt6=connection5.prepareStatement(query6);
		pstmt6.setString(1,PatientId);
		ResultSet rs6=pstmt6.executeQuery();
		if(rs6.next()){
			AppointmentBean appbean=new AppointmentBean();
			result=rs6.getString(1);
			appbean.setAppointmentID("kritika2"+result);
			System.out.println(result);
			
		}		
		return result;
		
	}
	
	
	/*Patient function below this */
	
	
	
	public String addAilmentDetails(PatientBean patientBean) throws ClassNotFoundException, SQLException
	{
		
		java.sql.Date appdate=new java.sql.Date(patientBean.getAppointmentDate().getTime());
		
		
		PreparedStatement ps5=DBUtil.getConnection().prepareStatement("insert into OCS_TBL_Patient values(?,?,?,?,?,?)");
		ps5.setString(1,patientBean.getPatientID());
		ps5.setString(2,patientBean.getUserID());
		ps5.setDate(3,appdate);
		ps5.setString(4,patientBean.getAilmentType());
		ps5.setString(5, patientBean.getAilmentDetails());
		ps5.setString(6,patientBean.getDiagnosisHistory());
		int l=ps5.executeUpdate();
		if(l!=0)
		{
			System.out.println("success dao");
			return "SUCCESS";
		}
		
		return "FAIL";

	}
	
	
	public String generatePatientId() throws ClassNotFoundException, SQLException
	{
		
		PreparedStatement ps6=DBUtil.getConnection().prepareStatement("select OCS_SEQ_PATIENTID.nextval from dual");
		ResultSet rs6=ps6.executeQuery();
		int seq=0;
		
		while(rs6.next())
		{
			seq=rs6.getInt(1);
		
			String sequence="PA" + seq;
			return sequence;
		
		}
	
		return null;
		
	}
	
	public String findPatientByUserId(String userId) throws ClassNotFoundException, SQLException
	{	
		String str="fail";
		PreparedStatement ps7=DBUtil.getConnection().prepareStatement("select * from OCS_TBL_Patient where USERID=?");
		ps7.setString(1,userId);
		ResultSet rs7=ps7.executeQuery();
		while(rs7.next())
		{
			
			str=rs7.getString(1);
		}
		
		return str;
		
	}
	
	
	
	
	public boolean viewAilmentDetails(PatientBean patientBean) throws ClassNotFoundException, SQLException
	{
		
		
		
		PreparedStatement ps11=DBUtil.getConnection().prepareStatement("select * from OCS_TBL_Patient where USERID=?");
		ps11.setString(1,patientBean.getUserID());
		ResultSet rs11=ps11.executeQuery();
		while(rs11.next())
		{
			
			patientBean.setPatientID((rs11.getString(1)));
			patientBean.setUserID(rs11.getString(2));
			patientBean.setAppointmentDate(rs11.getDate(3));
			patientBean.setAilmentType(rs11.getString(4));
			patientBean.setAilmentDetails(rs11.getString(5));
			patientBean.setDiagnosisHistory(rs11.getString(6));
			return true;
		}
		
		
		
		return false;
	}
	
	public String updatePatientAilmentDetails(PatientBean pBean,String userId) throws ClassNotFoundException, SQLException
	{
		java.sql.Date appointdate=new java.sql.Date(pBean.getAppointmentDate().getTime());
		
		PreparedStatement ps12=DBUtil.getConnection().prepareStatement("update OCS_TBL_Patient set APPOINTMENT_DATE=?,AILMENT_TYPE=?,AILMENT_DETAILS=?,DIAGNOSIS_HISTORY=? where USERID=?");
		ps12.setDate(1, appointdate);
		ps12.setString(2,pBean.getAilmentType());
		ps12.setString(3,pBean.getAilmentDetails());
		ps12.setString(4,pBean.getDiagnosisHistory());
		ps12.setString(5,pBean.getUserID());
		int i12=ps12.executeUpdate();
		if(i12!=0)
		{
			return "SUCCESSFUL";
		}
		
		return "UNSUCCESSFUL";
		
	 }
	public ArrayList<DoctorBean> findAllDoctorsbySpecialization(String specialization) throws ClassNotFoundException, SQLException{
		ArrayList<DoctorBean> list=new ArrayList<DoctorBean>();
		Connection connection;
		connection=DBUtil.getConnection();
		String query="select * from OCS_TBL_Doctor where SPECIALIZATION =?";
		PreparedStatement pstmt=connection.prepareStatement(query);
		pstmt.setString(1,specialization);
		System.out.println("yo");
		System.out.println(specialization);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			DoctorBean doctorBean=new DoctorBean();
			doctorBean.setDoctorID(rs.getString(1));
			doctorBean.setDoctorName(rs.getString(2));
			doctorBean.setDateOfBirth(rs.getDate(3));
			doctorBean.setDateOfJoining(rs.getDate(4));
			doctorBean.setGender(rs.getString(5));
			doctorBean.setQualification(rs.getString(6));
			doctorBean.setSpecialization(rs.getString(7));
			doctorBean.setYearsOfExperience(rs.getInt(8));
			doctorBean.setStreet(rs.getString(9));
			doctorBean.setLocation(rs.getString(10));
			doctorBean.setCity(rs.getString(11));
			doctorBean.setState(rs.getString(12));
			doctorBean.setPincode(rs.getString(13));
			doctorBean.setContactNumber(rs.getString(14));
			doctorBean.setEmailID(rs.getString(15));
			list.add(doctorBean);
		}
		return list;
	}
	public String ailmenttypebypatientid(String patientid) throws ClassNotFoundException, SQLException{
		String ailmenttype="";
		Connection connection;
		connection=DBUtil.getConnection();
		String query="select AILMENT_TYPE from OCS_TBL_Patient where PATIENTID=?";
		PreparedStatement pstmt=connection.prepareStatement(query);
		pstmt.setString(1,patientid);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			ailmenttype=rs.getString(1);
		}
		return ailmenttype;
	}
	public Map<AppointmentBean,PatientBean> viewAppointmentDetails(String doctorID,Date date1) throws ClassNotFoundException, SQLException{
		Connection connection9;
		connection9=DBUtil.getConnection();
		Map<AppointmentBean,PatientBean> mapdetails=new HashMap<AppointmentBean,PatientBean>();
		
		PatientBean patientBean=new PatientBean();
		String query="select * from OCS_TBL_Patient natural join OCS_TBL_Appointments where DOCTORID=? and APPOINTMENT_DATE=?";
		PreparedStatement pstmt9=connection9.prepareStatement(query);
		pstmt9.setString(1,doctorID);
		pstmt9.setDate(2,new java.sql.Date(date1.getTime()));
		System.out.println(new java.sql.Date(date1.getTime()));
		//System.out.println(new java.sql.Date(appointmentDate.getTime()));
		ResultSet rs=pstmt9.executeQuery();
		while(rs.next()){
			
			AppointmentBean appointmentBean=new AppointmentBean();
			//System.out.println("yo");
			patientBean.setPatientID(rs.getString("PATIENTID"));
			patientBean.setAppointmentDate(rs.getDate("APPOINTMENT_DATE"));
			appointmentBean.setAppointmentID(rs.getString("APPOINTMENTID"));
			appointmentBean.setDoctorID(rs.getString("DOCTORID"));
			appointmentBean.setAppointmentTime(rs.getString("APPOINTMENT_TIME"));
			patientBean.setUserID(rs.getString("USERID"));
			patientBean.setAilmentType(rs.getString("AILMENT_TYPE"));
			patientBean.setAilmentDetails(rs.getString("AILMENT_DETAILS"));
			patientBean.setDiagnosisHistory(rs.getString("DIAGNOSIS_HISTORY"));
			mapdetails.put(appointmentBean,patientBean);
		}
		
		System.out.println(mapdetails.size());
		return mapdetails;
	}
	public String selectslotbydoctorid(String doctorID) throws ClassNotFoundException, SQLException{
		Dao dao=new Dao();
		String slot="";
		Connection connection10;
		connection10=DBUtil.getConnection();
		String query="select SLOTS from OCS_TBL_Schedules where DOCTORID=?";
		PreparedStatement pstmt10=connection10.prepareStatement(query);
		pstmt10.setString(1,doctorID);
		ResultSet rs=pstmt10.executeQuery();
		while(rs.next()){
			slot=rs.getString(1);
		}
		return slot;
	}
	
	public ArrayList<AppointmentBean> viewappointmentstatusbyappointmentid(String appointmentid1) throws ClassNotFoundException, SQLException{
		ArrayList<AppointmentBean> list=new ArrayList<AppointmentBean>();
		Connection connection;
		connection=DBUtil.getConnection();
		String query="select * from OCS_TBL_Appointments where APPOINTMENTID=?";
		PreparedStatement pstmt=connection.prepareStatement(query);
		pstmt.setString(1,appointmentid1);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			AppointmentBean appointmentbean=new AppointmentBean();
			appointmentbean.setAppointmentID(rs.getString(1));
			appointmentbean.setDoctorID(rs.getString(2));
			appointmentbean.setPatientID(rs.getString(3));
			appointmentbean.setAppointmentDate(rs.getDate(4));
			appointmentbean.setAppointmentTime(rs.getString(5));
			list.add(appointmentbean);
		}
		return list;
	}
	public boolean findtimebydoctorid(String time) throws ClassNotFoundException, SQLException
	{	
		boolean flag=false;
		PreparedStatement ps7=DBUtil.getConnection().prepareStatement("select * from OCS_TBL_Appointments where APPOINTMENT_TIME=?");
		ps7.setString(1,time);
		ResultSet rs7=ps7.executeQuery();
		while(rs7.next())
		{
			
			flag=true;
		}
		
		return flag;
		
	}
	
	public boolean updatestatusbyappointmentid(String appointmentid) throws ClassNotFoundException, SQLException{
		boolean flag=false;
		Connection connection;
		connection=DBUtil.getConnection();
		String query="update OCS_TBL_Appointments set STATUS='CONFIRMED' where APPOINTMENTID=?";
		PreparedStatement pstmt=connection.prepareStatement(query);
		pstmt.setString(1,appointmentid);
		int rs=pstmt.executeUpdate();
		if(rs!=0){
			flag=true;
		}
		return flag;
	}
	public String selectstatusbyappointmentid(String appointmentid){
		String status="";
		try {
		Connection connection = null;
		try {
			connection=DBUtil.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query="select STATUS from OCS_TBL_Appointments where APPOINTMENTID=?";
		PreparedStatement pstmt;
		
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1,appointmentid);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				status=rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return status;
		
	}
	
	//----------------
	public String updateScheduleDetails(ScheduleBean schedulebean) throws ClassNotFoundException, SQLException 
	{
		String result="ERROR";
		PreparedStatement pmt=DBUtil.getConnection().prepareStatement("update OCS_TBL_Schedules set AVAILABLE_DAYS=?, SLOTS=? where DOCTORID=?");
		pmt.setString(1, schedulebean.getAvailableDays()); 
		pmt.setString(2, schedulebean.getSlots()); 
		pmt.setString(3, schedulebean.getDoctorID()); 
		int temp=pmt.executeUpdate();
      		if(temp>0)
		{
			result="SUCCESSFUL";
		}
		return result;
	}
	
	public String generateScheduleId(ScheduleBean schbean) throws ClassNotFoundException, SQLException
	{
		String result="ERROR";
		PreparedStatement pmt=DBUtil.getConnection().prepareStatement("select OCS_SEQ_SCHEDULEID.nextval from dual");
		ResultSet rs=pmt.executeQuery();
		if(rs.next())
		{
			
			result=rs.getString(1);
		}
		return result;
	}
	
	public boolean addScheduleDetails(ScheduleBean schbean) throws SQLException, ClassNotFoundException
	{
		boolean result=false;
		PreparedStatement pmt=DBUtil.getConnection().prepareStatement("insert into OCS_TBL_Schedules values(?,?,?,?)");
		pmt.setString(1, schbean.getScheduleID());
		pmt.setString(2, schbean.getDoctorID());
		pmt.setString(3, schbean.getAvailableDays());
		pmt.setString(4, schbean.getSlots());
	
		int temp=pmt.executeUpdate();
		if(temp>0)
		{
			result=true;
		}
		return result;	
	}
	public ArrayList<ScheduleBean> viewScheduledDoctors() throws ClassNotFoundException, SQLException 
	{
		boolean flag=false;
		ArrayList<ScheduleBean> alist= new ArrayList<ScheduleBean>();
		PreparedStatement pmt=DBUtil.getConnection().prepareStatement("select DISTINCT DOCTORID from OCS_TBL_Schedules");
		ResultSet rs=pmt.executeQuery();
		while(rs.next())
		{
			flag=true;
			ScheduleBean scBean= new ScheduleBean();
			scBean.setDoctorID(rs.getString(1));
			alist.add(scBean);
		}
		if(flag==false)
		{
			return null;
		}
		else
			return alist;
		// TODO Auto-generated method stub
		
	}
	
	
}