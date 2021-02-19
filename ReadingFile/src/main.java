import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class main {
	
	private static String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";
	private static Connection connection = null;

	private static String databaseName;
	private static String serverName;
	private static String user;
	private static String pass;

	public  main() {

	}

	public static void main(String[] args) {

		databaseName="DnD_goodriat_oriansaj_parkhuca30";
		serverName="titan.csse.rose-hulman.edu";
		Scanner sc= new Scanner(System.in);  
		System.out.print("Enter your Username: ");  
		user = sc.nextLine();            
		System.out.print("Enter your Password: "); 
		pass = sc.nextLine();
		
		
		SampleURL =SampleURL.replace("${dbServer}",serverName)
				 .replace("${dbName}",databaseName)
				 .replace("${user}", user)
				 .replace("${pass}", pass);
		//System.out.println(SampleURL);
		try {
			connection = DriverManager.getConnection(SampleURL);
			//insertItems();
			//insertSpells();
			//insertArmorWeights();
			//insertArmors();
			//insertWeaponTypes();
			//insertWeapons();
			//insertSkills();
			//insertSavingThrows();
			//insertStartsWith();
			//insertClass();
			//insertLanguages();
			//insertLangFromRace();
			//insertSubclasses();
			//insertRaceWeaponProf();
			//insertOffersSkill();
			//insertWeaponProf();
			//insertWeaponTypeProf();
			//insertSavingThrowProf();
			//insertArmorWeightProf();
			//insertCanLearnSpell();
			//insertTraits();
			//insertGivesClassFeature(); 
			//insertGivesRaceFeature();
			//insertBackgrounds();
//			insertBackgroundSkillProf();
			insertBackgroundToolProf();
			
			//insertBackgrounds();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		

		System.out.println("TASK COMPLETED");
	}
	
	public static void insertCanLearnSpell() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		ArrayList<ArrayList<String>> spells = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/CanLearn.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			BufferedReader br1 = new BufferedReader(new FileReader("data/Spells.csv"));
			while((line = br1.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				spells.add(temp);
			}
			
			for(int i =0;i<s.size();i++) {
				Boolean found = false;
				for(ArrayList<String> spell : spells) {
					if(s.get(i).get(1).toLowerCase().equals(spell.get(1).toLowerCase())) {
						s.get(i).set(1, spell.get(0));
						found = true;
					}
				}
				if(!found) {
					s.remove(i);
					i--;
				}
			}
			
			
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO [CanLearnSpell] (ClassID, SpellID) VALUES (?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setInt(1, Integer.valueOf(row.get(0)));
				p.setInt(2, Integer.valueOf(row.get(1)));
				
				p.execute();	
			}
			br.close();
			br1.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertArmorWeightProf() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/ArmorWeightProfs.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO [HasArmorWeightProf] (ClassID, ArmorWeightName) VALUES (?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setInt(1, Integer.valueOf(row.get(0)));
				p.setString(2, row.get(1));
				
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertSavingThrowProf() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/SavingThrowProfs.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO [HasSavingThrowProf] (ClassID, SavingThrowName) VALUES (?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setInt(1, Integer.valueOf(row.get(0)));
				p.setString(2, row.get(1));
				
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertWeaponTypeProf() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/WeaponTypeProfs.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO [HasWeaponTypeProf] (ClassID, WeaponTypeName) VALUES (?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setInt(1, Integer.valueOf(row.get(0)));
				p.setString(2, row.get(1));
				
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertWeaponProf() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/ClassWeaponProf.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO [HasWeaponProf] (ClassID, WeaponID) VALUES (?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setInt(1, Integer.valueOf(row.get(0)));
				p.setInt(2, Integer.valueOf(row.get(1)));
				
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertOffersSkill() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/OffersSkill.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO [OffersSkillProficiency] (ClassID, SkillName) VALUES (?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setInt(1, Integer.valueOf(row.get(0)));
				p.setString(2, row.get(1));
				
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertRaceWeaponProf() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/RaceWeaponProf.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO [Race_Weapon_Prof] (RaceName, WeaponID) VALUES (?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setString(1, row.get(0));
				p.setInt(2, Integer.valueOf(row.get(1)));
				
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertSubclasses() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/SubClasses.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO Subclass (SubClassID, Name, ClassID) VALUES (?,?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setInt(1, Integer.valueOf(row.get(0)));
				p.setString(2, row.get(1));
				p.setInt(3, Integer.valueOf(row.get(2)));
				
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertLanguages() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/Languages.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO Language (Name) VALUES (?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setString(1, row.get(0));
	
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertLangFromRace() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/LangFromRace.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO [Knows_Language_From_Race] (RaceName, LanguageName) VALUES (?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setString(1, row.get(0));
				p.setString(2, row.get(1));
				
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertClass() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/Class.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO Class (ClassID, Name, HitDice) VALUES (?,?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setInt(1, Integer.valueOf(row.get(0)));
				p.setString(2, row.get(1));
				p.setInt(3, Integer.valueOf(row.get(2)));
	
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertStartsWith() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/StartsWith.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO StartsWith (ClassID, ItemID, Quantity) VALUES (?,?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setInt(1, Integer.valueOf(row.get(0)));
				p.setInt(2, Integer.valueOf(row.get(1)));
				p.setInt(3, Integer.valueOf(row.get(2)));
	
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	
	public static void insertSavingThrows() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/SavingThrows.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO SavingThrow (Name) VALUES (?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setString(1, row.get(0));
	
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertSkills() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/Skills.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO Skill (Name,relatedSkill) VALUES (?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setString(1, row.get(0));
				p.setString(2, row.get(1));	
				
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertWeaponTypes() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/WeaponTypes.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO WeaponType (Name) VALUES (?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setString(1, row.get(0));
	
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertWeapons() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/Weapons.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO Weapon (ItemID,Damage,Properties,WeaponTypeName) VALUES (?,?,?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setInt(1, Integer.valueOf(row.get(0)));
				p.setString(2, row.get(1));
				p.setString(3, row.get(2));
				p.setString(4, row.get(3));
				
	
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertArmorWeights() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/ArmorWeights.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO ArmorWeight (Name) VALUES (?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setString(1, row.get(0));
	
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}

	public static void insertArmors() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/Armors.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			

			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO Armor (BaseAC, ItemID, AddDex, StrengthMin, DisAdvantageOnStealth,  ArmorWeightName) VALUES (?,?,?,?,?,?)";
				PreparedStatement p = connection.prepareStatement(query);


				p.setInt(1, Integer.valueOf(row.get(1)));
				p.setInt(2, Integer.valueOf(row.get(0)));
				p.setInt(3, Integer.valueOf(row.get(2)));
				p.setInt(4, Integer.valueOf(row.get(3)));
				p.setInt(5, Integer.valueOf(row.get(4)));
				p.setString(6, row.get(5));
				
				
				p.execute();
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertSpells() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/Spells.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			

			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO Spell (Range, CastingTime, SpellID, V, S, M, Duration, Concentration, Name, School, Level) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement p = connection.prepareStatement(query);


				p.setString(1, row.get(3));
				p.setString(2, row.get(2));
				p.setInt(3, Integer.valueOf(row.get(0)));
				p.setInt(4, Integer.valueOf(row.get(4)));
				p.setInt(5, Integer.valueOf(row.get(5)));
				p.setString(6, row.get(6));
				p.setString(7, row.get(7));
				p.setInt(8, Integer.valueOf(row.get(8)));
				p.setString(9, row.get(1));
				p.setString(10, row.get(10));
				p.setInt(11, Integer.valueOf(row.get(9)));

				p.execute();
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertItems() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/Items.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			

			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO Item (ItemID, Cost, Mass, Name) VALUES (?,?,?,?)";
				PreparedStatement p = connection.prepareStatement(query);


				p.setInt(1, Integer.valueOf(row.get(0)));
				p.setString(2, row.get(2));
				p.setDouble(3, Double.valueOf(row.get(3)));
				p.setString(4, row.get(1));

				p.execute();
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	
	public static void insertTraits() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/Traits.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO Trait (TraitID, Name, Description) VALUES (?,?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setString(1, row.get(0));
				p.setString(2, row.get(1));
				p.setString(3, row.get(2));
	
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertGivesClassFeature() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/GivesClassFeature.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO GivesClassFeature (ClassID, ClassFeatureID, GainedAtLevel) VALUES (?,?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setString(1, row.get(0));
				p.setString(2, row.get(1));
				p.setString(3, row.get(2));
	
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertGivesRaceFeature() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/GivesRaceFeature.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO GivesRaceFeature (FeatureID, RaceName) VALUES (?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setString(1, row.get(0));
				p.setString(2, row.get(1));
	
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertBackgrounds() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/Backgrounds.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO Background (Name, Feature, NumLanguagesGained) VALUES (?,?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setString(1, row.get(0));
				p.setString(2, row.get(1));
				p.setString(3, row.get(2));
	
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertBackgroundSkillProf() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/BackgroundSkillProf.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO Background_Skill_Prof (BackgroundName, SkillName) VALUES (?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setString(1, row.get(0));
				p.setString(2, row.get(1));
	
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}
	
	public static void insertBackgroundToolProf() {
		ArrayList<ArrayList<String>> s = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/BackgroundToolProf.csv"));
			String line;
			while((line = br.readLine())!=null) {
				String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> temp = new ArrayList<String>(Arrays.asList(values));
				s.add(temp);
			}
			
			s.remove(0); // remove header row
			for(ArrayList<String> row : s) {
				String query = "INSERT INTO Background_Tool_Prof (BackgroundName, ItemID) VALUES (?,?)";
				PreparedStatement p = connection.prepareStatement(query);

				p.setString(1, row.get(0));
				p.setString(2, row.get(1));
	
				p.execute();	
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILURE");
		}
	}


}

