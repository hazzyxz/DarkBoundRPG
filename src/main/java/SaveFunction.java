import java.sql.*;

public class SaveFunction {
    private Connection connection;

    public String[] loadFromFileSave() {
        String[] statFromSave = new String[14];

        try {
            // Establish connection to database
            connection = DriverManager.getConnection("jdbc:sqlite:FileSave.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("create table if not exists player(name string, maxhp integer, hp integer, maxmp integer, mp integer, phyattack integer, magattack integer, phydef integer, magdef integer, level integer, xp integer, spell1 string, spell2 string, spell3 string)");
            ResultSet rs = statement.executeQuery("select * from player");

            // Load data from database
            statFromSave[0] = rs.getString("name");
            statFromSave[1] = rs.getString("maxhp");
            statFromSave[2] = rs.getString("hp");
            statFromSave[3] = rs.getString("maxmp");
            statFromSave[4] = rs.getString("mp");
            statFromSave[5] = rs.getString("phyattack");
            statFromSave[6] = rs.getString("magattack");
            statFromSave[7] = rs.getString("phydef");
            statFromSave[8] = rs.getString("magdef");
            statFromSave[9] = rs.getString("level");
            statFromSave[10] = rs.getString("xp");
            statFromSave[11] = rs.getString("spell1");
            statFromSave[12] = rs.getString("spell2");
            statFromSave[13] = rs.getString("spell3");


            if (statFromSave[0] == null) {
                System.exit(0);
            }

            // IMPORTANT: close connection. BUG: if not closed, game freezes
            rs.close();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if (connection != null)
                    connection.close();
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return statFromSave;
    }

    public String[] loadFromQuickSave() {
        String[] statFromSave = new String[14];
        try {
            // Establish connection to database
            connection = DriverManager.getConnection("jdbc:sqlite:QuickSave.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * from player");

            // Load data from database
            statFromSave[0] = rs.getString("name");
            statFromSave[1] = rs.getString("maxhp");
            statFromSave[2] = rs.getString("hp");
            statFromSave[3] = rs.getString("maxmp");
            statFromSave[4] = rs.getString("mp");
            statFromSave[5] = rs.getString("phyattack");
            statFromSave[6] = rs.getString("magattack");
            statFromSave[7] = rs.getString("phydef");
            statFromSave[8] = rs.getString("magdef");
            statFromSave[9] = rs.getString("level");
            statFromSave[10] = rs.getString("xp");
            statFromSave[11] = rs.getString("spell1");
            statFromSave[12] = rs.getString("spell2");
            statFromSave[13] = rs.getString("spell3");

            if (statFromSave[0] == null) {
                System.exit(0);
            }

            rs.close();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if (connection != null)
                    connection.close();
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return statFromSave;
    }

    public void saveToFileSave(Creature player) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:FileSave.db");
            Statement statement = connection.createStatement();
            // statement.setQueryTimeout(30);

            statement.executeUpdate("drop table if exists player");
            statement.executeUpdate("create table player(name string, maxhp integer, hp integer, maxmp integer, mp integer, phyattack integer, magattack integer, phydef integer, magdef integer, level integer, xp integer, spell1 string, spell2 string, spell3 string)");

            String insertCommand = "insert into player values('"+player.name()+"',"+Integer.toString(player.maxHp())+","+Integer.toString(player.hp())+","+Integer.toString(player.maxMp())+","+Integer.toString(player.mp())+","+Integer.toString(player.phyAttack())+","+Integer.toString(player.magAttack())+","+Integer.toString(player.maxPhyDefense())+","+Integer.toString(player.maxMagDefense())+","+Integer.toString(player.level())+","+Integer.toString(player.xp())+",'"+player.spellList().get(0)+"','"+player.spellList().get(1)+"','"+player.spellList().get(2)+"')";
            statement.executeUpdate(insertCommand);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveToQuickSave(Creature player) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:QuickSave.db");
            Statement statement = connection.createStatement();
            // statement.setQueryTimeout(30);

            statement.executeUpdate("drop table if exists player");
            statement.executeUpdate("create table player(name string, maxhp integer, hp integer, maxmp integer, mp integer, phyattack integer, magattack integer, phydef integer, magdef integer, level integer, xp integer, spell1 string, spell2 string, spell3 string)");

            String insertCommand = "insert into player values('"+player.name()+"',"+Integer.toString(player.maxHp())+","+Integer.toString(player.hp())+","+Integer.toString(player.maxMp())+","+Integer.toString(player.mp())+","+Integer.toString(player.phyAttack())+","+Integer.toString(player.magAttack())+","+Integer.toString(player.maxPhyDefense())+","+Integer.toString(player.maxMagDefense())+","+Integer.toString(player.level())+","+Integer.toString(player.xp())+","+player.spellList().get(0)+","+player.spellList().get(1)+","+player.spellList().get(2)+")";
            statement.executeUpdate(insertCommand);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
