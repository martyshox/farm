import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet(name = "Farm", urlPatterns = {"/farm"})
public class Farm extends HttpServlet
{
    String Eartag,Name,Amount,Date,Period;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        System.out.println("Inside Add Customer ");
        int count = 0;
        response.setContentType("application/octet-stream");
        System.out.println("after response ");
        /******************************************************************/
        InputStream in = request.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(in);

        //String weight = (String)ois.readObject();
        //System.out.println(weight);

        OutputStream outstr = response.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outstr);
        /******************************************************************/


        try{
            //read the object containing the the new customer details
            ArrayList<String> dose = (ArrayList<String>) ois.readObject();

            System.out.println(Eartag);

            if(dose != null)
            {
                System.out.println("Inside Add data ");
                /*************************************************************************************************/
                Class.forName("com.mysql.jdbc.Driver");  // load the driver
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/farm","root", "root");
                /*******************************************************************************************************/
                System.out.println("Debug 1 ");
                /*************************************************************/
                //PreparedStatement sqlPersonID = con.prepareStatement(
                       // "SELECT LAST_INSERT_ID()" );
                /*************************************************************/
                System.out.println("Debug z");
                /*********************************************************************************************************/
                //for(int i=0; i > weight_data.size();i++) {


                    Eartag = dose.get(0);
                    Name = dose.get(1);
                    Amount = dose.get(2);
                    Date = dose.get(3);
                    Period = dose.get(4);

                System.out.println(dose.get(1));

                    //go through the object passed and get the new customer details and insert into database
                    PreparedStatement sqlInsertName = con.prepareStatement("INSERT INTO dose ( Eartag, Name, Amount, Date, Period ) " + "VALUES ( ? , ?,?,?,? )");
                    sqlInsertName.setString(1, Eartag);
                    sqlInsertName.setString(2, Name);
                    sqlInsertName.setString(3, Amount);
                    sqlInsertName.setString(4, Date);
                    sqlInsertName.setString(5, Period);
                    count = sqlInsertName.executeUpdate();
                    /*********************************************************************************************************/
                    System.out.println("Debug e");
                    /*********************************************************************************************************/

                    sqlInsertName.close();
                //}
                con.close();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();

        }
        finally {
            oos.writeInt(count);
            oos.flush();
            oos.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}